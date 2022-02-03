package org.reflections.scanners;

import java.util.function.*;
import javax.annotation.*;
import java.util.*;
import org.reflections.*;
import javassist.expr.*;
import javassist.bytecode.*;
import org.reflections.util.*;
import javassist.*;

public class MemberUsageScanner implements Scanner
{
    private Predicate<String> resultFilter;
    private final ClassLoader[] classLoaders;
    private volatile ClassPool classPool;
    
    public MemberUsageScanner() {
        this(ClasspathHelper.classLoaders(new ClassLoader[0]));
    }
    
    public MemberUsageScanner(@Nonnull final ClassLoader[] classLoaders) {
        this.resultFilter = (s -> true);
        this.classLoaders = classLoaders;
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>();
        CtClass ctClass = null;
        try {
            ctClass = this.getClassPool().get(classFile.getName());
            for (final CtBehavior member : ctClass.getDeclaredConstructors()) {
                this.scanMember(member, entries);
            }
            for (final CtBehavior member : ctClass.getDeclaredMethods()) {
                this.scanMember(member, entries);
            }
        }
        catch (Exception e) {
            throw new ReflectionsException("Could not scan method usage for " + classFile.getName(), e);
        }
        finally {
            if (ctClass != null) {
                ctClass.detach();
            }
        }
        return entries;
    }
    
    public Scanner filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    private void scanMember(final CtBehavior member, final List<Map.Entry<String, String>> entries) throws CannotCompileException {
        final String key = member.getDeclaringClass().getName() + "." + member.getMethodInfo().getName() + "(" + parameterNames(member.getMethodInfo()) + ")";
        member.instrument(new ExprEditor() {
            @Override
            public void edit(final NewExpr e) {
                try {
                    MemberUsageScanner.this.add(entries, e.getConstructor().getDeclaringClass().getName() + ".<init>(" + MemberUsageScanner.parameterNames(e.getConstructor().getMethodInfo()) + ")", key + " #" + e.getLineNumber());
                }
                catch (NotFoundException e2) {
                    throw new ReflectionsException("Could not find new instance usage in " + key, e2);
                }
            }
            
            @Override
            public void edit(final MethodCall m) {
                try {
                    MemberUsageScanner.this.add(entries, m.getMethod().getDeclaringClass().getName() + "." + m.getMethodName() + "(" + MemberUsageScanner.parameterNames(m.getMethod().getMethodInfo()) + ")", key + " #" + m.getLineNumber());
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + m.getClassName() + " in " + key, e);
                }
            }
            
            @Override
            public void edit(final ConstructorCall c) {
                try {
                    MemberUsageScanner.this.add(entries, c.getConstructor().getDeclaringClass().getName() + ".<init>(" + MemberUsageScanner.parameterNames(c.getConstructor().getMethodInfo()) + ")", key + " #" + c.getLineNumber());
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + c.getClassName() + " in " + key, e);
                }
            }
            
            @Override
            public void edit(final FieldAccess f) {
                try {
                    MemberUsageScanner.this.add(entries, f.getField().getDeclaringClass().getName() + "." + f.getFieldName(), key + " #" + f.getLineNumber());
                }
                catch (NotFoundException e) {
                    throw new ReflectionsException("Could not find member " + f.getFieldName() + " in " + key, e);
                }
            }
        });
    }
    
    private void add(final List<Map.Entry<String, String>> entries, final String key, final String value) {
        if (this.resultFilter.test(key)) {
            entries.add(this.entry(key, value));
        }
    }
    
    public static String parameterNames(final MethodInfo info) {
        return String.join(", ", JavassistHelper.getParameters(info));
    }
    
    private ClassPool getClassPool() {
        if (this.classPool == null) {
            synchronized (this) {
                if (this.classPool == null) {
                    this.classPool = new ClassPool();
                    for (final ClassLoader classLoader : this.classLoaders) {
                        this.classPool.appendClassPath(new LoaderClassPath(classLoader));
                    }
                }
            }
        }
        return this.classPool;
    }
}
