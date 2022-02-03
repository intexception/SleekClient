package org.java_websocket.server;

import org.java_websocket.drafts.*;
import java.nio.*;
import java.util.concurrent.atomic.*;
import org.slf4j.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import org.java_websocket.handshake.*;
import org.java_websocket.*;
import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;
import java.util.*;

public abstract class WebSocketServer extends AbstractWebSocket implements Runnable
{
    private static final int AVAILABLE_PROCESSORS;
    private final Logger log;
    private final Collection<WebSocket> connections;
    private final InetSocketAddress address;
    private ServerSocketChannel server;
    private Selector selector;
    private List<Draft> drafts;
    private Thread selectorthread;
    private final AtomicBoolean isclosed;
    protected List<WebSocketWorker> decoders;
    private List<WebSocketImpl> iqueue;
    private BlockingQueue<ByteBuffer> buffers;
    private int queueinvokes;
    private final AtomicInteger queuesize;
    private WebSocketServerFactory wsf;
    private int maxPendingConnections;
    
    public WebSocketServer() {
        this(new InetSocketAddress(80), WebSocketServer.AVAILABLE_PROCESSORS, null);
    }
    
    public WebSocketServer(final InetSocketAddress address) {
        this(address, WebSocketServer.AVAILABLE_PROCESSORS, null);
    }
    
    public WebSocketServer(final InetSocketAddress address, final int decodercount) {
        this(address, decodercount, null);
    }
    
    public WebSocketServer(final InetSocketAddress address, final List<Draft> drafts) {
        this(address, WebSocketServer.AVAILABLE_PROCESSORS, drafts);
    }
    
    public WebSocketServer(final InetSocketAddress address, final int decodercount, final List<Draft> drafts) {
        this(address, decodercount, drafts, new HashSet<WebSocket>());
    }
    
    public WebSocketServer(final InetSocketAddress address, final int decodercount, final List<Draft> drafts, final Collection<WebSocket> connectionscontainer) {
        this.log = LoggerFactory.getLogger(WebSocketServer.class);
        this.isclosed = new AtomicBoolean(false);
        this.queueinvokes = 0;
        this.queuesize = new AtomicInteger(0);
        this.wsf = new DefaultWebSocketServerFactory();
        this.maxPendingConnections = -1;
        if (address == null || decodercount < 1 || connectionscontainer == null) {
            throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder");
        }
        if (drafts == null) {
            this.drafts = Collections.emptyList();
        }
        else {
            this.drafts = drafts;
        }
        this.address = address;
        this.connections = connectionscontainer;
        this.setTcpNoDelay(false);
        this.setReuseAddr(false);
        this.iqueue = new LinkedList<WebSocketImpl>();
        this.decoders = new ArrayList<WebSocketWorker>(decodercount);
        this.buffers = new LinkedBlockingQueue<ByteBuffer>();
        for (int i = 0; i < decodercount; ++i) {
            final WebSocketWorker ex = new WebSocketWorker();
            this.decoders.add(ex);
        }
    }
    
    public void start() {
        if (this.selectorthread != null) {
            throw new IllegalStateException(this.getClass().getName() + " can only be started once.");
        }
        new Thread(this).start();
    }
    
    public void stop(final int timeout) throws InterruptedException {
        if (!this.isclosed.compareAndSet(false, true)) {
            return;
        }
        final List<WebSocket> socketsToClose;
        synchronized (this.connections) {
            socketsToClose = new ArrayList<WebSocket>(this.connections);
        }
        for (final WebSocket ws : socketsToClose) {
            ws.close(1001);
        }
        this.wsf.close();
        synchronized (this) {
            if (this.selectorthread != null && this.selector != null) {
                this.selector.wakeup();
                this.selectorthread.join(timeout);
            }
        }
    }
    
    public void stop() throws InterruptedException {
        this.stop(0);
    }
    
    public Collection<WebSocket> getConnections() {
        synchronized (this.connections) {
            return Collections.unmodifiableCollection((Collection<? extends WebSocket>)new ArrayList<WebSocket>(this.connections));
        }
    }
    
    public InetSocketAddress getAddress() {
        return this.address;
    }
    
    public int getPort() {
        int port = this.getAddress().getPort();
        if (port == 0 && this.server != null) {
            port = this.server.socket().getLocalPort();
        }
        return port;
    }
    
    public List<Draft> getDraft() {
        return Collections.unmodifiableList((List<? extends Draft>)this.drafts);
    }
    
    public void setMaxPendingConnections(final int numberOfConnections) {
        this.maxPendingConnections = numberOfConnections;
    }
    
    public int getMaxPendingConnections() {
        return this.maxPendingConnections;
    }
    
    @Override
    public void run() {
        if (!this.doEnsureSingleThread()) {
            return;
        }
        if (!this.doSetupSelectorAndServerThread()) {
            return;
        }
        try {
            int shutdownCount = 5;
            int selectTimeout = 0;
            while (!this.selectorthread.isInterrupted() && shutdownCount != 0) {
                SelectionKey key = null;
                try {
                    if (this.isclosed.get()) {
                        selectTimeout = 5;
                    }
                    final int keyCount = this.selector.select(selectTimeout);
                    if (keyCount == 0 && this.isclosed.get()) {
                        --shutdownCount;
                    }
                    final Set<SelectionKey> keys = this.selector.selectedKeys();
                    final Iterator<SelectionKey> i = keys.iterator();
                    while (i.hasNext()) {
                        key = i.next();
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isAcceptable()) {
                            this.doAccept(key, i);
                        }
                        else {
                            if (key.isReadable() && !this.doRead(key, i)) {
                                continue;
                            }
                            if (!key.isWritable()) {
                                continue;
                            }
                            this.doWrite(key);
                        }
                    }
                    this.doAdditionalRead();
                }
                catch (CancelledKeyException ex3) {}
                catch (ClosedByInterruptException e2) {}
                catch (WrappedIOException ex) {
                    this.handleIOException(key, ex.getConnection(), ex.getIOException());
                }
                catch (IOException ex2) {
                    this.handleIOException(key, null, ex2);
                }
                catch (InterruptedException e3) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        catch (RuntimeException e) {
            this.handleFatal(null, e);
        }
        finally {
            this.doServerShutdown();
        }
    }
    
    private void doAdditionalRead() throws InterruptedException, IOException {
        while (!this.iqueue.isEmpty()) {
            final WebSocketImpl conn = this.iqueue.remove(0);
            final WrappedByteChannel c = (WrappedByteChannel)conn.getChannel();
            final ByteBuffer buf = this.takeBuffer();
            try {
                if (SocketChannelIOHelper.readMore(buf, conn, c)) {
                    this.iqueue.add(conn);
                }
                if (buf.hasRemaining()) {
                    conn.inQueue.put(buf);
                    this.queue(conn);
                }
                else {
                    this.pushBuffer(buf);
                }
            }
            catch (IOException e) {
                this.pushBuffer(buf);
                throw e;
            }
        }
    }
    
    private void doAccept(final SelectionKey key, final Iterator<SelectionKey> i) throws IOException, InterruptedException {
        if (!this.onConnect(key)) {
            key.cancel();
            return;
        }
        final SocketChannel channel = this.server.accept();
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        final Socket socket = channel.socket();
        socket.setTcpNoDelay(this.isTcpNoDelay());
        socket.setKeepAlive(true);
        final WebSocketImpl w = this.wsf.createWebSocket((WebSocketAdapter)this, this.drafts);
        w.setSelectionKey(channel.register(this.selector, 1, w));
        try {
            w.setChannel(this.wsf.wrapChannel(channel, w.getSelectionKey()));
            i.remove();
            this.allocateBuffers(w);
        }
        catch (IOException ex) {
            if (w.getSelectionKey() != null) {
                w.getSelectionKey().cancel();
            }
            this.handleIOException(w.getSelectionKey(), null, ex);
        }
    }
    
    private boolean doRead(final SelectionKey key, final Iterator<SelectionKey> i) throws InterruptedException, WrappedIOException {
        final WebSocketImpl conn = (WebSocketImpl)key.attachment();
        final ByteBuffer buf = this.takeBuffer();
        if (conn.getChannel() == null) {
            key.cancel();
            this.handleIOException(key, conn, new IOException());
            return false;
        }
        try {
            if (SocketChannelIOHelper.read(buf, conn, conn.getChannel())) {
                if (buf.hasRemaining()) {
                    conn.inQueue.put(buf);
                    this.queue(conn);
                    i.remove();
                    if (conn.getChannel() instanceof WrappedByteChannel && ((WrappedByteChannel)conn.getChannel()).isNeedRead()) {
                        this.iqueue.add(conn);
                    }
                }
                else {
                    this.pushBuffer(buf);
                }
            }
            else {
                this.pushBuffer(buf);
            }
        }
        catch (IOException e) {
            this.pushBuffer(buf);
            throw new WrappedIOException(conn, e);
        }
        return true;
    }
    
    private void doWrite(final SelectionKey key) throws WrappedIOException {
        final WebSocketImpl conn = (WebSocketImpl)key.attachment();
        try {
            if (SocketChannelIOHelper.batch(conn, conn.getChannel()) && key.isValid()) {
                key.interestOps(1);
            }
        }
        catch (IOException e) {
            throw new WrappedIOException(conn, e);
        }
    }
    
    private boolean doSetupSelectorAndServerThread() {
        this.selectorthread.setName("WebSocketSelector-" + this.selectorthread.getId());
        try {
            (this.server = ServerSocketChannel.open()).configureBlocking(false);
            final ServerSocket socket = this.server.socket();
            socket.setReceiveBufferSize(16384);
            socket.setReuseAddress(this.isReuseAddr());
            socket.bind(this.address, this.getMaxPendingConnections());
            this.selector = Selector.open();
            this.server.register(this.selector, this.server.validOps());
            this.startConnectionLostTimer();
            for (final WebSocketWorker ex : this.decoders) {
                ex.start();
            }
            this.onStart();
        }
        catch (IOException ex2) {
            this.handleFatal(null, ex2);
            return false;
        }
        return true;
    }
    
    private boolean doEnsureSingleThread() {
        synchronized (this) {
            if (this.selectorthread != null) {
                throw new IllegalStateException(this.getClass().getName() + " can only be started once.");
            }
            this.selectorthread = Thread.currentThread();
            if (this.isclosed.get()) {
                return false;
            }
        }
        return true;
    }
    
    private void doServerShutdown() {
        this.stopConnectionLostTimer();
        if (this.decoders != null) {
            for (final WebSocketWorker w : this.decoders) {
                w.interrupt();
            }
        }
        if (this.selector != null) {
            try {
                this.selector.close();
            }
            catch (IOException e) {
                this.log.error("IOException during selector.close", e);
                this.onError(null, e);
            }
        }
        if (this.server != null) {
            try {
                this.server.close();
            }
            catch (IOException e) {
                this.log.error("IOException during server.close", e);
                this.onError(null, e);
            }
        }
    }
    
    protected void allocateBuffers(final WebSocket c) throws InterruptedException {
        if (this.queuesize.get() >= 2 * this.decoders.size() + 1) {
            return;
        }
        this.queuesize.incrementAndGet();
        this.buffers.put(this.createBuffer());
    }
    
    protected void releaseBuffers(final WebSocket c) throws InterruptedException {
    }
    
    public ByteBuffer createBuffer() {
        return ByteBuffer.allocate(16384);
    }
    
    protected void queue(final WebSocketImpl ws) throws InterruptedException {
        if (ws.getWorkerThread() == null) {
            ws.setWorkerThread(this.decoders.get(this.queueinvokes % this.decoders.size()));
            ++this.queueinvokes;
        }
        ws.getWorkerThread().put(ws);
    }
    
    private ByteBuffer takeBuffer() throws InterruptedException {
        return this.buffers.take();
    }
    
    private void pushBuffer(final ByteBuffer buf) throws InterruptedException {
        if (this.buffers.size() > this.queuesize.intValue()) {
            return;
        }
        this.buffers.put(buf);
    }
    
    private void handleIOException(final SelectionKey key, final WebSocket conn, final IOException ex) {
        if (key != null) {
            key.cancel();
        }
        if (conn != null) {
            conn.closeConnection(1006, ex.getMessage());
        }
        else if (key != null) {
            final SelectableChannel channel = key.channel();
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                }
                catch (IOException ex2) {}
                this.log.trace("Connection closed because of exception", ex);
            }
        }
    }
    
    private void handleFatal(final WebSocket conn, final Exception e) {
        this.log.error("Shutdown due to fatal error", e);
        this.onError(conn, e);
        if (this.decoders != null) {
            for (final WebSocketWorker w : this.decoders) {
                w.interrupt();
            }
        }
        if (this.selectorthread != null) {
            this.selectorthread.interrupt();
        }
        try {
            this.stop();
        }
        catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            this.log.error("Interrupt during stop", e);
            this.onError(null, e2);
        }
    }
    
    @Override
    public final void onWebsocketMessage(final WebSocket conn, final String message) {
        this.onMessage(conn, message);
    }
    
    @Override
    public final void onWebsocketMessage(final WebSocket conn, final ByteBuffer blob) {
        this.onMessage(conn, blob);
    }
    
    @Override
    public final void onWebsocketOpen(final WebSocket conn, final Handshakedata handshake) {
        if (this.addConnection(conn)) {
            this.onOpen(conn, (ClientHandshake)handshake);
        }
    }
    
    @Override
    public final void onWebsocketClose(final WebSocket conn, final int code, final String reason, final boolean remote) {
        this.selector.wakeup();
        try {
            if (this.removeConnection(conn)) {
                this.onClose(conn, code, reason, remote);
            }
        }
        finally {
            try {
                this.releaseBuffers(conn);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    protected boolean removeConnection(final WebSocket ws) {
        boolean removed = false;
        synchronized (this.connections) {
            if (this.connections.contains(ws)) {
                removed = this.connections.remove(ws);
            }
            else {
                this.log.trace("Removing connection which is not in the connections collection! Possible no handshake received! {}", ws);
            }
        }
        if (this.isclosed.get() && this.connections.isEmpty()) {
            this.selectorthread.interrupt();
        }
        return removed;
    }
    
    protected boolean addConnection(final WebSocket ws) {
        if (!this.isclosed.get()) {
            synchronized (this.connections) {
                return this.connections.add(ws);
            }
        }
        ws.close(1001);
        return true;
    }
    
    @Override
    public final void onWebsocketError(final WebSocket conn, final Exception ex) {
        this.onError(conn, ex);
    }
    
    @Override
    public final void onWriteDemand(final WebSocket w) {
        final WebSocketImpl conn = (WebSocketImpl)w;
        try {
            conn.getSelectionKey().interestOps(5);
        }
        catch (CancelledKeyException e) {
            conn.outQueue.clear();
        }
        this.selector.wakeup();
    }
    
    @Override
    public void onWebsocketCloseInitiated(final WebSocket conn, final int code, final String reason) {
        this.onCloseInitiated(conn, code, reason);
    }
    
    @Override
    public void onWebsocketClosing(final WebSocket conn, final int code, final String reason, final boolean remote) {
        this.onClosing(conn, code, reason, remote);
    }
    
    public void onCloseInitiated(final WebSocket conn, final int code, final String reason) {
    }
    
    public void onClosing(final WebSocket conn, final int code, final String reason, final boolean remote) {
    }
    
    public final void setWebSocketFactory(final WebSocketServerFactory wsf) {
        if (this.wsf != null) {
            this.wsf.close();
        }
        this.wsf = wsf;
    }
    
    public final WebSocketFactory getWebSocketFactory() {
        return this.wsf;
    }
    
    protected boolean onConnect(final SelectionKey key) {
        return true;
    }
    
    private Socket getSocket(final WebSocket conn) {
        final WebSocketImpl impl = (WebSocketImpl)conn;
        return ((SocketChannel)impl.getSelectionKey().channel()).socket();
    }
    
    @Override
    public InetSocketAddress getLocalSocketAddress(final WebSocket conn) {
        return (InetSocketAddress)this.getSocket(conn).getLocalSocketAddress();
    }
    
    @Override
    public InetSocketAddress getRemoteSocketAddress(final WebSocket conn) {
        return (InetSocketAddress)this.getSocket(conn).getRemoteSocketAddress();
    }
    
    public abstract void onOpen(final WebSocket p0, final ClientHandshake p1);
    
    public abstract void onClose(final WebSocket p0, final int p1, final String p2, final boolean p3);
    
    public abstract void onMessage(final WebSocket p0, final String p1);
    
    public abstract void onError(final WebSocket p0, final Exception p1);
    
    public abstract void onStart();
    
    public void onMessage(final WebSocket conn, final ByteBuffer message) {
    }
    
    public void broadcast(final String text) {
        this.broadcast(text, this.connections);
    }
    
    public void broadcast(final byte[] data) {
        this.broadcast(data, this.connections);
    }
    
    public void broadcast(final ByteBuffer data) {
        this.broadcast(data, this.connections);
    }
    
    public void broadcast(final byte[] data, final Collection<WebSocket> clients) {
        if (data == null || clients == null) {
            throw new IllegalArgumentException();
        }
        this.broadcast(ByteBuffer.wrap(data), clients);
    }
    
    public void broadcast(final ByteBuffer data, final Collection<WebSocket> clients) {
        if (data == null || clients == null) {
            throw new IllegalArgumentException();
        }
        this.doBroadcast(data, clients);
    }
    
    public void broadcast(final String text, final Collection<WebSocket> clients) {
        if (text == null || clients == null) {
            throw new IllegalArgumentException();
        }
        this.doBroadcast(text, clients);
    }
    
    private void doBroadcast(final Object data, final Collection<WebSocket> clients) {
        String strData = null;
        if (data instanceof String) {
            strData = (String)data;
        }
        ByteBuffer byteData = null;
        if (data instanceof ByteBuffer) {
            byteData = (ByteBuffer)data;
        }
        if (strData == null && byteData == null) {
            return;
        }
        final Map<Draft, List<Framedata>> draftFrames = new HashMap<Draft, List<Framedata>>();
        final List<WebSocket> clientCopy;
        synchronized (clients) {
            clientCopy = new ArrayList<WebSocket>(clients);
        }
        for (final WebSocket client : clientCopy) {
            if (client != null) {
                final Draft draft = client.getDraft();
                this.fillFrames(draft, draftFrames, strData, byteData);
                try {
                    client.sendFrame(draftFrames.get(draft));
                }
                catch (WebsocketNotConnectedException ex) {}
            }
        }
    }
    
    private void fillFrames(final Draft draft, final Map<Draft, List<Framedata>> draftFrames, final String strData, final ByteBuffer byteData) {
        if (!draftFrames.containsKey(draft)) {
            List<Framedata> frames = null;
            if (strData != null) {
                frames = draft.createFrames(strData, false);
            }
            if (byteData != null) {
                frames = draft.createFrames(byteData, false);
            }
            if (frames != null) {
                draftFrames.put(draft, frames);
            }
        }
    }
    
    static {
        AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    }
    
    public class WebSocketWorker extends Thread
    {
        private BlockingQueue<WebSocketImpl> iqueue;
        static final /* synthetic */ boolean $assertionsDisabled;
        
        public WebSocketWorker() {
            this.iqueue = new LinkedBlockingQueue<WebSocketImpl>();
            this.setName("WebSocketWorker-" + this.getId());
            this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(final Thread t, final Throwable e) {
                    WebSocketServer.this.log.error("Uncaught exception in thread {}: {}", t.getName(), e);
                }
            });
        }
        
        public void put(final WebSocketImpl ws) throws InterruptedException {
            this.iqueue.put(ws);
        }
        
        @Override
        public void run() {
            WebSocketImpl ws = null;
            try {
                while (true) {
                    ws = this.iqueue.take();
                    final ByteBuffer buf = ws.inQueue.poll();
                    if (!WebSocketWorker.$assertionsDisabled && buf == null) {
                        break;
                    }
                    this.doDecode(ws, buf);
                    ws = null;
                }
                throw new AssertionError();
            }
            catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
            catch (RuntimeException e) {
                WebSocketServer.this.handleFatal(ws, e);
            }
        }
        
        private void doDecode(final WebSocketImpl ws, final ByteBuffer buf) throws InterruptedException {
            try {
                ws.decode(buf);
            }
            catch (Exception e) {
                WebSocketServer.this.log.error("Error while reading from remote connection", e);
            }
            finally {
                WebSocketServer.this.pushBuffer(buf);
            }
        }
    }
}
