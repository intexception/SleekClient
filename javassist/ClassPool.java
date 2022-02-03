package javassist;

import java.util.*;
import java.net.*;
import javassist.bytecode.*;
import java.io.*;
import java.security.*;
import java.lang.invoke.*;
import javassist.util.proxy.*;

public class ClassPool
{
    public boolean childFirstLookup;
    public static boolean doPruning;
    private int compressCount;
    private static final int COMPRESS_THRESHOLD = 100;
    public static boolean releaseUnmodifiedClassFile;
    public static boolean cacheOpenedJarFile;
    protected ClassPoolTail source;
    protected ClassPool parent;
    protected Hashtable classes;
    private Hashtable cflow;
    private static final int INIT_HASH_SIZE = 191;
    private ArrayList importedPackages;
    private static ClassPool defaultPool;
    
    public ClassPool() {
        this(null);
    }
    
    public ClassPool(final boolean useDefaultPath) {
        this(null);
        if (useDefaultPath) {
            this.appendSystemPath();
        }
    }
    
    public ClassPool(final ClassPool parent) {
        this.childFirstLookup = false;
        this.cflow = null;
        this.classes = new Hashtable(191);
        this.source = new ClassPoolTail();
        this.parent = parent;
        if (parent == null) {
            final CtClass[] pt = CtClass.primitiveTypes;
            for (int i = 0; i < pt.length; ++i) {
                this.classes.put(pt[i].getName(), pt[i]);
            }
        }
        this.cflow = null;
        this.compressCount = 0;
        this.clearImportedPackages();
    }
    
    public static synchronized ClassPool getDefault() {
        if (ClassPool.defaultPool == null) {
            (ClassPool.defaultPool = new ClassPool(null)).appendSystemPath();
        }
        return ClassPool.defaultPool;
    }
    
    protected CtClass getCached(final String classname) {
        return this.classes.get(classname);
    }
    
    protected void cacheCtClass(final String classname, final CtClass c, final boolean dynamic) {
        this.classes.put(classname, c);
    }
    
    protected CtClass removeCached(final String classname) {
        return this.classes.remove(classname);
    }
    
    @Override
    public String toString() {
        return this.source.toString();
    }
    
    void compress() {
        if (this.compressCount++ > 100) {
            this.compressCount = 0;
            final Enumeration e = this.classes.elements();
            while (e.hasMoreElements()) {
                e.nextElement().compress();
            }
        }
    }
    
    public void importPackage(final String packageName) {
        this.importedPackages.add(packageName);
    }
    
    public void clearImportedPackages() {
        (this.importedPackages = new ArrayList()).add("java.lang");
    }
    
    public Iterator<String> getImportedPackages() {
        return this.importedPackages.iterator();
    }
    
    @Deprecated
    public void recordInvalidClassName(final String name) {
    }
    
    void recordCflow(final String name, final String cname, final String fname) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        this.cflow.put(name, new Object[] { cname, fname });
    }
    
    public Object[] lookupCflow(final String name) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        return this.cflow.get(name);
    }
    
    public CtClass getAndRename(final String orgName, final String newName) throws NotFoundException {
        final CtClass clazz = this.get0(orgName, false);
        if (clazz == null) {
            throw new NotFoundException(orgName);
        }
        if (clazz instanceof CtClassType) {
            ((CtClassType)clazz).setClassPool(this);
        }
        clazz.setName(newName);
        return clazz;
    }
    
    synchronized void classNameChanged(final String oldname, final CtClass clazz) {
        final CtClass c = this.getCached(oldname);
        if (c == clazz) {
            this.removeCached(oldname);
        }
        final String newName = clazz.getName();
        this.checkNotFrozen(newName);
        this.cacheCtClass(newName, clazz, false);
    }
    
    public CtClass get(final String classname) throws NotFoundException {
        CtClass clazz;
        if (classname == null) {
            clazz = null;
        }
        else {
            clazz = this.get0(classname, true);
        }
        if (clazz == null) {
            throw new NotFoundException(classname);
        }
        clazz.incGetCounter();
        return clazz;
    }
    
    public CtClass getOrNull(final String classname) {
        CtClass clazz = null;
        if (classname == null) {
            clazz = null;
        }
        else {
            try {
                clazz = this.get0(classname, true);
            }
            catch (NotFoundException ex) {}
        }
        if (clazz != null) {
            clazz.incGetCounter();
        }
        return clazz;
    }
    
    public CtClass getCtClass(final String classname) throws NotFoundException {
        if (classname.charAt(0) == '[') {
            return Descriptor.toCtClass(classname, this);
        }
        return this.get(classname);
    }
    
    protected synchronized CtClass get0(final String classname, final boolean useCache) throws NotFoundException {
        CtClass clazz = null;
        if (useCache) {
            clazz = this.getCached(classname);
            if (clazz != null) {
                return clazz;
            }
        }
        if (!this.childFirstLookup && this.parent != null) {
            clazz = this.parent.get0(classname, useCache);
            if (clazz != null) {
                return clazz;
            }
        }
        clazz = this.createCtClass(classname, useCache);
        if (clazz != null) {
            if (useCache) {
                this.cacheCtClass(clazz.getName(), clazz, false);
            }
            return clazz;
        }
        if (this.childFirstLookup && this.parent != null) {
            clazz = this.parent.get0(classname, useCache);
        }
        return clazz;
    }
    
    protected CtClass createCtClass(String classname, final boolean useCache) {
        if (classname.charAt(0) == '[') {
            classname = Descriptor.toClassName(classname);
        }
        if (classname.endsWith("[]")) {
            final String base = classname.substring(0, classname.indexOf(91));
            if ((!useCache || this.getCached(base) == null) && this.find(base) == null) {
                return null;
            }
            return new CtArray(classname, this);
        }
        else {
            if (this.find(classname) == null) {
                return null;
            }
            return new CtClassType(classname, this);
        }
    }
    
    public URL find(final String classname) {
        return this.source.find(classname);
    }
    
    void checkNotFrozen(final String classname) throws RuntimeException {
        CtClass clazz = this.getCached(classname);
        if (clazz == null) {
            if (!this.childFirstLookup && this.parent != null) {
                try {
                    clazz = this.parent.get0(classname, true);
                }
                catch (NotFoundException ex) {}
                if (clazz != null) {
                    throw new RuntimeException(classname + " is in a parent ClassPool.  Use the parent.");
                }
            }
        }
        else if (clazz.isFrozen()) {
            throw new RuntimeException(classname + ": frozen class (cannot edit)");
        }
    }
    
    CtClass checkNotExists(final String classname) {
        CtClass clazz = this.getCached(classname);
        if (clazz == null && !this.childFirstLookup && this.parent != null) {
            try {
                clazz = this.parent.get0(classname, true);
            }
            catch (NotFoundException ex) {}
        }
        return clazz;
    }
    
    InputStream openClassfile(final String classname) throws NotFoundException {
        return this.source.openClassfile(classname);
    }
    
    void writeClassfile(final String classname, final OutputStream out) throws NotFoundException, IOException, CannotCompileException {
        this.source.writeClassfile(classname, out);
    }
    
    public CtClass[] get(final String[] classnames) throws NotFoundException {
        if (classnames == null) {
            return new CtClass[0];
        }
        final int num = classnames.length;
        final CtClass[] result = new CtClass[num];
        for (int i = 0; i < num; ++i) {
            result[i] = this.get(classnames[i]);
        }
        return result;
    }
    
    public CtMethod getMethod(final String classname, final String methodname) throws NotFoundException {
        final CtClass c = this.get(classname);
        return c.getDeclaredMethod(methodname);
    }
    
    public CtClass makeClass(final InputStream classfile) throws IOException, RuntimeException {
        return this.makeClass(classfile, true);
    }
    
    public CtClass makeClass(InputStream classfile, final boolean ifNotFrozen) throws IOException, RuntimeException {
        this.compress();
        classfile = new BufferedInputStream(classfile);
        final CtClass clazz = new CtClassType(classfile, this);
        clazz.checkModify();
        final String classname = clazz.getName();
        if (ifNotFrozen) {
            this.checkNotFrozen(classname);
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }
    
    public CtClass makeClass(final ClassFile classfile) throws RuntimeException {
        return this.makeClass(classfile, true);
    }
    
    public CtClass makeClass(final ClassFile classfile, final boolean ifNotFrozen) throws RuntimeException {
        this.compress();
        final CtClass clazz = new CtClassType(classfile, this);
        clazz.checkModify();
        final String classname = clazz.getName();
        if (ifNotFrozen) {
            this.checkNotFrozen(classname);
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }
    
    public CtClass makeClassIfNew(InputStream classfile) throws IOException, RuntimeException {
        this.compress();
        classfile = new BufferedInputStream(classfile);
        final CtClass clazz = new CtClassType(classfile, this);
        clazz.checkModify();
        final String classname = clazz.getName();
        final CtClass found = this.checkNotExists(classname);
        if (found != null) {
            return found;
        }
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }
    
    public CtClass makeClass(final String classname) throws RuntimeException {
        return this.makeClass(classname, null);
    }
    
    public synchronized CtClass makeClass(final String classname, final CtClass superclass) throws RuntimeException {
        this.checkNotFrozen(classname);
        final CtClass clazz = new CtNewClass(classname, this, false, superclass);
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }
    
    synchronized CtClass makeNestedClass(final String classname) {
        this.checkNotFrozen(classname);
        final CtClass clazz = new CtNewClass(classname, this, false, null);
        this.cacheCtClass(classname, clazz, true);
        return clazz;
    }
    
    public CtClass makeInterface(final String name) throws RuntimeException {
        return this.makeInterface(name, null);
    }
    
    public synchronized CtClass makeInterface(final String name, final CtClass superclass) throws RuntimeException {
        this.checkNotFrozen(name);
        final CtClass clazz = new CtNewClass(name, this, true, superclass);
        this.cacheCtClass(name, clazz, true);
        return clazz;
    }
    
    public CtClass makeAnnotation(final String name) throws RuntimeException {
        try {
            final CtClass cc = this.makeInterface(name, this.get("java.lang.annotation.Annotation"));
            cc.setModifiers(cc.getModifiers() | 0x2000);
            return cc;
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public ClassPath appendSystemPath() {
        return this.source.appendSystemPath();
    }
    
    public ClassPath insertClassPath(final ClassPath cp) {
        return this.source.insertClassPath(cp);
    }
    
    public ClassPath appendClassPath(final ClassPath cp) {
        return this.source.appendClassPath(cp);
    }
    
    public ClassPath insertClassPath(final String pathname) throws NotFoundException {
        return this.source.insertClassPath(pathname);
    }
    
    public ClassPath appendClassPath(final String pathname) throws NotFoundException {
        return this.source.appendClassPath(pathname);
    }
    
    public void removeClassPath(final ClassPath cp) {
        this.source.removeClassPath(cp);
    }
    
    public void appendPathList(final String pathlist) throws NotFoundException {
        final char sep = File.pathSeparatorChar;
        int i = 0;
        while (true) {
            final int j = pathlist.indexOf(sep, i);
            if (j < 0) {
                break;
            }
            this.appendClassPath(pathlist.substring(i, j));
            i = j + 1;
        }
        this.appendClassPath(pathlist.substring(i));
    }
    
    public Class toClass(final CtClass clazz) throws CannotCompileException {
        return this.toClass(clazz, this.getClassLoader());
    }
    
    public ClassLoader getClassLoader() {
        return getContextClassLoader();
    }
    
    static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    @Deprecated
    public Class toClass(final CtClass ct, final ClassLoader loader) throws CannotCompileException {
        return this.toClass(ct, null, loader, null);
    }
    
    @Deprecated
    public Class toClass(final CtClass ct, final ClassLoader loader, final ProtectionDomain domain) throws CannotCompileException {
        return this.toClass(ct, null, loader, domain);
    }
    
    public Class<?> toClass(final CtClass ct, final Class<?> neighbor) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(neighbor, ct.toBytecode());
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }
    
    public Class<?> toClass(final CtClass ct, final MethodHandles.Lookup lookup) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(lookup, ct.toBytecode());
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }
    
    public Class toClass(final CtClass ct, final Class<?> neighbor, final ClassLoader loader, final ProtectionDomain domain) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(ct.getName(), neighbor, loader, domain, ct.toBytecode());
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
    }
    
    @Deprecated
    public void makePackage(final ClassLoader loader, final String name) throws CannotCompileException {
        DefinePackageHelper.definePackage(name, loader);
    }
    
    static {
        ClassPool.doPruning = false;
        ClassPool.releaseUnmodifiedClassFile = true;
        ClassPool.cacheOpenedJarFile = true;
        ClassPool.defaultPool = null;
    }
}
