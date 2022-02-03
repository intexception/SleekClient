package javassist.util;

import java.lang.instrument.*;
import java.lang.management.*;
import com.sun.tools.attach.*;
import java.io.*;
import java.util.jar.*;
import java.util.zip.*;
import javassist.*;

public class HotSwapAgent
{
    private static Instrumentation instrumentation;
    
    public Instrumentation instrumentation() {
        return HotSwapAgent.instrumentation;
    }
    
    public static void premain(final String agentArgs, final Instrumentation inst) throws Throwable {
        agentmain(agentArgs, inst);
    }
    
    public static void agentmain(final String agentArgs, final Instrumentation inst) throws Throwable {
        if (!inst.isRedefineClassesSupported()) {
            throw new RuntimeException("this JVM does not support redefinition of classes");
        }
        HotSwapAgent.instrumentation = inst;
    }
    
    public static void redefine(final Class<?> oldClass, final CtClass newClass) throws NotFoundException, IOException, CannotCompileException {
        final Class<?>[] old = (Class<?>[])new Class[] { oldClass };
        final CtClass[] newClasses = { newClass };
        redefine(old, newClasses);
    }
    
    public static void redefine(final Class<?>[] oldClasses, final CtClass[] newClasses) throws NotFoundException, IOException, CannotCompileException {
        startAgent();
        final ClassDefinition[] defs = new ClassDefinition[oldClasses.length];
        for (int i = 0; i < oldClasses.length; ++i) {
            defs[i] = new ClassDefinition(oldClasses[i], newClasses[i].toBytecode());
        }
        try {
            HotSwapAgent.instrumentation.redefineClasses(defs);
        }
        catch (ClassNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        }
        catch (UnmodifiableClassException e2) {
            throw new CannotCompileException(e2.getMessage(), e2);
        }
    }
    
    private static void startAgent() throws NotFoundException {
        if (HotSwapAgent.instrumentation != null) {
            return;
        }
        try {
            final File agentJar = createJarFile();
            final String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            final String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf(64));
            final VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentJar.getAbsolutePath(), (String)null);
            vm.detach();
        }
        catch (Exception e) {
            throw new NotFoundException("hotswap agent", e);
        }
        for (int sec = 0; sec < 10; ++sec) {
            if (HotSwapAgent.instrumentation != null) {
                return;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        throw new NotFoundException("hotswap agent (timeout)");
    }
    
    public static File createAgentJarFile(final String fileName) throws IOException, CannotCompileException, NotFoundException {
        return createJarFile(new File(fileName));
    }
    
    private static File createJarFile() throws IOException, CannotCompileException, NotFoundException {
        final File jar = File.createTempFile("agent", ".jar");
        jar.deleteOnExit();
        return createJarFile(jar);
    }
    
    private static File createJarFile(final File jar) throws IOException, CannotCompileException, NotFoundException {
        final Manifest manifest = new Manifest();
        final Attributes attrs = manifest.getMainAttributes();
        attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attrs.put(new Attributes.Name("Premain-Class"), HotSwapAgent.class.getName());
        attrs.put(new Attributes.Name("Agent-Class"), HotSwapAgent.class.getName());
        attrs.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        attrs.put(new Attributes.Name("Can-Redefine-Classes"), "true");
        JarOutputStream jos = null;
        try {
            jos = new JarOutputStream(new FileOutputStream(jar), manifest);
            final String cname = HotSwapAgent.class.getName();
            final JarEntry e = new JarEntry(cname.replace('.', '/') + ".class");
            jos.putNextEntry(e);
            final ClassPool pool = ClassPool.getDefault();
            final CtClass clazz = pool.get(cname);
            jos.write(clazz.toBytecode());
            jos.closeEntry();
        }
        finally {
            if (jos != null) {
                jos.close();
            }
        }
        return jar;
    }
    
    static {
        HotSwapAgent.instrumentation = null;
    }
}
