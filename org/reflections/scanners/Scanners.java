package org.reflections.scanners;

import java.lang.annotation.*;
import javassist.bytecode.*;
import org.reflections.vfs.*;
import org.reflections.util.*;
import org.reflections.*;
import java.util.function.*;
import java.util.stream.*;
import java.lang.reflect.*;
import java.util.*;

public enum Scanners implements Scanner, QueryBuilder, NameHelper
{
    SubTypes {
        {
            this.filterResultsBy(new FilterBuilder().excludePattern("java\\.lang\\.Object"));
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            entries.add(this.entry(classFile.getSuperclass(), classFile.getName()));
            entries.addAll(this.entries(Arrays.asList(classFile.getInterfaces()), classFile.getName()));
        }
    }, 
    TypesAnnotated {
        public boolean acceptResult(final String annotation) {
            return super.acceptResult(annotation) || annotation.equals(Inherited.class.getName());
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            entries.addAll(this.entries(JavassistHelper.getAnnotations(classFile::getAttribute), classFile.getName()));
        }
    }, 
    MethodsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.addAll(this.entries(JavassistHelper.getAnnotations(method::getAttribute), JavassistHelper.methodName(classFile, method))));
        }
    }, 
    ConstructorsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getConstructors(classFile).forEach(constructor -> entries.addAll(this.entries(JavassistHelper.getAnnotations(constructor::getAttribute), JavassistHelper.methodName(classFile, constructor))));
        }
    }, 
    FieldsAnnotated {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            classFile.getFields().forEach(field -> entries.addAll(this.entries(JavassistHelper.getAnnotations(field::getAttribute), JavassistHelper.fieldName(classFile, field))));
        }
    }, 
    Resources {
        @Override
        public boolean acceptsInput(final String file) {
            return !file.endsWith(".class");
        }
        
        @Override
        public List<Map.Entry<String, String>> scan(final Vfs.File file) {
            return Collections.singletonList(this.entry(file.getName(), file.getRelativePath()));
        }
        
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            throw new IllegalStateException();
        }
        
        @Override
        public QueryFunction<Store, String> with(final String pattern) {
            return store -> store.getOrDefault(this.index(), Collections.emptyMap()).entrySet().stream().filter(entry -> entry.getKey().matches(pattern)).flatMap(entry -> entry.getValue().stream()).collect((Collector<? super Object, ?, LinkedHashSet<Object>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
        }
    }, 
    MethodsParameter {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            final String value;
            JavassistHelper.getMethods(classFile).forEach(method -> {
                value = JavassistHelper.methodName(classFile, method);
                entries.addAll(this.entries(JavassistHelper.getParameters(method), value));
                JavassistHelper.getParametersAnnotations(method).forEach(annotations -> entries.addAll(this.entries(annotations, value)));
            });
        }
    }, 
    ConstructorsParameter {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            final String value;
            JavassistHelper.getConstructors(classFile).forEach(constructor -> {
                value = JavassistHelper.methodName(classFile, constructor);
                entries.addAll(this.entries(JavassistHelper.getParameters(constructor), value));
                JavassistHelper.getParametersAnnotations(constructor).forEach(annotations -> entries.addAll(this.entries(annotations, value)));
            });
        }
    }, 
    MethodsSignature {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.add(this.entry(JavassistHelper.getParameters(method).toString(), JavassistHelper.methodName(classFile, method))));
        }
        
        @Override
        public QueryFunction<Store, String> with(final AnnotatedElement... keys) {
            return QueryFunction.single(this.toNames(keys).toString()).getAll(this::get);
        }
    }, 
    ConstructorsSignature {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getConstructors(classFile).forEach(constructor -> entries.add(this.entry(JavassistHelper.getParameters(constructor).toString(), JavassistHelper.methodName(classFile, constructor))));
        }
        
        @Override
        public QueryFunction<Store, String> with(final AnnotatedElement... keys) {
            return QueryFunction.single(this.toNames(keys).toString()).getAll(this::get);
        }
    }, 
    MethodsReturn {
        public void scan(final ClassFile classFile, final List<Map.Entry<String, String>> entries) {
            JavassistHelper.getMethods(classFile).forEach(method -> entries.add(this.entry(JavassistHelper.getReturnType(method), JavassistHelper.methodName(classFile, method))));
        }
    };
    
    private Predicate<String> resultFilter;
    
    private Scanners() {
        this.resultFilter = (s -> true);
    }
    
    @Override
    public String index() {
        return this.name();
    }
    
    public Scanners filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    @Override
    public final List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>();
        this.scan(classFile, entries);
        return entries.stream().filter(a -> this.acceptResult(a.getKey())).collect((Collector<? super Object, ?, List<Map.Entry<String, String>>>)Collectors.toList());
    }
    
    abstract void scan(final ClassFile p0, final List<Map.Entry<String, String>> p1);
    
    protected boolean acceptResult(final String fqn) {
        return fqn != null && this.resultFilter.test(fqn);
    }
}
