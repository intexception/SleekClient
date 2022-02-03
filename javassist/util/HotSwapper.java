package javassist.util;

import java.io.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.*;
import com.sun.jdi.request.*;
import java.util.*;
import com.sun.jdi.event.*;

public class HotSwapper
{
    private VirtualMachine jvm;
    private MethodEntryRequest request;
    private Map<ReferenceType, byte[]> newClassFiles;
    private Trigger trigger;
    private static final String HOST_NAME = "localhost";
    private static final String TRIGGER_NAME;
    
    public HotSwapper(final int port) throws IOException, IllegalConnectorArgumentsException {
        this(Integer.toString(port));
    }
    
    public HotSwapper(final String port) throws IOException, IllegalConnectorArgumentsException {
        this.jvm = null;
        this.request = null;
        this.newClassFiles = null;
        this.trigger = new Trigger();
        final AttachingConnector connector = (AttachingConnector)this.findConnector("com.sun.jdi.SocketAttach");
        final Map<String, Connector.Argument> arguments = (Map<String, Connector.Argument>)connector.defaultArguments();
        arguments.get("hostname").setValue("localhost");
        arguments.get("port").setValue(port);
        this.jvm = connector.attach((Map)arguments);
        final EventRequestManager manager = this.jvm.eventRequestManager();
        this.request = methodEntryRequests(manager, HotSwapper.TRIGGER_NAME);
    }
    
    private Connector findConnector(final String connector) throws IOException {
        final List<Connector> connectors = (List<Connector>)Bootstrap.virtualMachineManager().allConnectors();
        for (final Connector con : connectors) {
            if (con.name().equals(connector)) {
                return con;
            }
        }
        throw new IOException("Not found: " + connector);
    }
    
    private static MethodEntryRequest methodEntryRequests(final EventRequestManager manager, final String classpattern) {
        final MethodEntryRequest mereq = manager.createMethodEntryRequest();
        mereq.addClassFilter(classpattern);
        mereq.setSuspendPolicy(1);
        return mereq;
    }
    
    private void deleteEventRequest(final EventRequestManager manager, final MethodEntryRequest request) {
        manager.deleteEventRequest((EventRequest)request);
    }
    
    public void reload(final String className, final byte[] classFile) {
        final ReferenceType classtype = this.toRefType(className);
        final Map<ReferenceType, byte[]> map = new HashMap<ReferenceType, byte[]>();
        map.put(classtype, classFile);
        this.reload2(map, className);
    }
    
    public void reload(final Map<String, byte[]> classFiles) {
        final Map<ReferenceType, byte[]> map = new HashMap<ReferenceType, byte[]>();
        String className = null;
        for (final Map.Entry<String, byte[]> e : classFiles.entrySet()) {
            className = e.getKey();
            map.put(this.toRefType(className), e.getValue());
        }
        if (className != null) {
            this.reload2(map, className + " etc.");
        }
    }
    
    private ReferenceType toRefType(final String className) {
        final List<ReferenceType> list = (List<ReferenceType>)this.jvm.classesByName(className);
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("no such class: " + className);
        }
        return list.get(0);
    }
    
    private void reload2(final Map<ReferenceType, byte[]> map, final String msg) {
        synchronized (this.trigger) {
            this.startDaemon();
            this.newClassFiles = map;
            this.request.enable();
            this.trigger.doSwap();
            this.request.disable();
            final Map<ReferenceType, byte[]> ncf = this.newClassFiles;
            if (ncf != null) {
                this.newClassFiles = null;
                throw new RuntimeException("failed to reload: " + msg);
            }
        }
    }
    
    private void startDaemon() {
        new Thread() {
            private void errorMsg(final Throwable e) {
                System.err.print("Exception in thread \"HotSwap\" ");
                e.printStackTrace(System.err);
            }
            
            @Override
            public void run() {
                EventSet events = null;
                try {
                    events = HotSwapper.this.waitEvent();
                    final EventIterator iter = events.eventIterator();
                    while (iter.hasNext()) {
                        final Event event = iter.nextEvent();
                        if (event instanceof MethodEntryEvent) {
                            HotSwapper.this.hotswap();
                            break;
                        }
                    }
                }
                catch (Throwable e) {
                    this.errorMsg(e);
                }
                try {
                    if (events != null) {
                        events.resume();
                    }
                }
                catch (Throwable e) {
                    this.errorMsg(e);
                }
            }
        }.start();
    }
    
    EventSet waitEvent() throws InterruptedException {
        final EventQueue queue = this.jvm.eventQueue();
        return queue.remove();
    }
    
    void hotswap() {
        final Map<ReferenceType, byte[]> map = this.newClassFiles;
        this.jvm.redefineClasses((Map)map);
        this.newClassFiles = null;
    }
    
    static {
        TRIGGER_NAME = Trigger.class.getName();
    }
}
