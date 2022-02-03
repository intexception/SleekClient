package org.reflections;

import java.lang.annotation.*;
import java.net.*;
import java.util.stream.*;
import java.lang.reflect.*;
import org.reflections.util.*;
import java.util.function.*;
import java.util.*;

public abstract class ReflectionUtils extends ReflectionUtilsPredicates
{
    private static final List<String> objectMethodNames;
    public static final Predicate<Method> notObjectMethod;
    public static final UtilQueryBuilder<Class<?>, Class<?>> SuperClass;
    public static final UtilQueryBuilder<Class<?>, Class<?>> Interfaces;
    public static final UtilQueryBuilder<Class<?>, Class<?>> SuperTypes;
    public static final UtilQueryBuilder<AnnotatedElement, Annotation> Annotations;
    public static final UtilQueryBuilder<AnnotatedElement, Class<? extends Annotation>> AnnotationTypes;
    public static final UtilQueryBuilder<Class<?>, Method> Methods;
    public static final UtilQueryBuilder<Class<?>, Constructor> Constructors;
    public static final UtilQueryBuilder<Class<?>, Field> Fields;
    public static final UtilQueryBuilder<String, URL> Resources;
    
    public static <C, T> Set<T> get(final QueryFunction<C, T> function) {
        return function.apply((C)null);
    }
    
    public static <T> Set<T> get(final QueryFunction<Store, T> queryFunction, final Predicate<? super T>... predicates) {
        return get(queryFunction.filter(Arrays.stream(predicates).reduce(t -> true, Predicate::and)));
    }
    
    public static <T extends AnnotatedElement> UtilQueryBuilder<AnnotatedElement, T> extendType() {
        QueryFunction<Store, Class<?>> single;
        return element -> {
            if (element instanceof Class && !element.isAnnotation()) {
                single = QueryFunction.single(element);
                return (QueryFunction<Store, T>)single.add(single.getAll(ReflectionUtils.SuperTypes::get));
            }
            else {
                return (QueryFunction<Store, T>)QueryFunction.single(element);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final T type, final Predicate<Annotation>... predicates) {
        return get(ReflectionUtils.Annotations.of(type), (Predicate<? super Annotation>[])predicates);
    }
    
    public static Set<Class<?>> getAllSuperTypes(final Class<?> type, final Predicate<? super Class<?>>... predicates) {
        final Predicate<? super Class<?>>[] filter = (predicates == null || predicates.length == 0) ? new Predicate[] { t -> !Object.class.equals(t) } : predicates;
        return get(ReflectionUtils.SuperTypes.of(type), filter);
    }
    
    public static Set<Class<?>> getSuperTypes(final Class<?> type) {
        return get(ReflectionUtils.SuperTypes.get(type));
    }
    
    public static Set<Method> getAllMethods(final Class<?> type, final Predicate<? super Method>... predicates) {
        return get(ReflectionUtils.Methods.of(type), predicates);
    }
    
    public static Set<Method> getMethods(final Class<?> t, final Predicate<? super Method>... predicates) {
        return get(ReflectionUtils.Methods.get(t), predicates);
    }
    
    public static Set<Constructor> getAllConstructors(final Class<?> type, final Predicate<? super Constructor>... predicates) {
        return (Set<Constructor>)get(ReflectionUtils.Constructors.of(type), predicates);
    }
    
    public static Set<Constructor> getConstructors(final Class<?> t, final Predicate<? super Constructor>... predicates) {
        return (Set<Constructor>)get(ReflectionUtils.Constructors.get(t), predicates);
    }
    
    public static Set<Field> getAllFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        return get(ReflectionUtils.Fields.of(type), predicates);
    }
    
    public static Set<Field> getFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        return get(ReflectionUtils.Fields.get(type), predicates);
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAnnotations(final T type, final Predicate<Annotation>... predicates) {
        return get(ReflectionUtils.Annotations.get(type), (Predicate<? super Annotation>[])predicates);
    }
    
    public static Map<String, Object> toMap(final Annotation annotation) {
        final Object v1;
        return get(ReflectionUtils.Methods.of(annotation.annotationType()).filter(ReflectionUtils.notObjectMethod.and(ReflectionUtilsPredicates.withParametersCount(0)))).stream().collect(Collectors.toMap((Function<? super Object, ? extends String>)Method::getName, m -> {
            v1 = invoke(m, annotation, new Object[0]);
            return (v1.getClass().isArray() && v1.getClass().getComponentType().isAnnotation()) ? Stream.of((Annotation[])v1).map((Function<? super Annotation, ?>)ReflectionUtils::toMap).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : v1;
        }));
    }
    
    public static Map<String, Object> toMap(final Annotation annotation, final AnnotatedElement element) {
        final Map<String, Object> map = toMap(annotation);
        if (element != null) {
            map.put("annotatedElement", element);
        }
        return map;
    }
    
    public static Annotation toAnnotation(final Map<String, Object> map) {
        return toAnnotation(map, map.get("annotationType"));
    }
    
    public static <T extends Annotation> T toAnnotation(final Map<String, Object> map, final Class<T> annotationType) {
        return (T)Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[] { annotationType }, (proxy, method, args) -> ReflectionUtils.notObjectMethod.test(method) ? map.get(method.getName()) : method.invoke(map, new Object[0]));
    }
    
    public static Object invoke(final Method method, final Object obj, final Object... args) {
        try {
            return method.invoke(obj, args);
        }
        catch (Exception e) {
            return e;
        }
    }
    
    static {
        objectMethodNames = Arrays.asList("equals", "hashCode", "toString", "wait", "notify", "notifyAll");
        notObjectMethod = (m -> !ReflectionUtils.objectMethodNames.contains(m.getName()));
        final Class superclass;
        SuperClass = (element -> ctx -> {
            superclass = element.getSuperclass();
            return (superclass != null && !superclass.equals(Object.class)) ? Collections.singleton(superclass) : Collections.emptySet();
        });
        Interfaces = (element -> ctx -> Stream.of(element.getInterfaces()).collect((Collector<? super Class, ?, LinkedHashSet>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new)));
        SuperTypes = new UtilQueryBuilder<Class<?>, Class<?>>() {
            @Override
            public QueryFunction<Store, Class<?>> get(final Class<?> element) {
                return ReflectionUtils.SuperClass.get(element).add(ReflectionUtils.Interfaces.get(element));
            }
            
            @Override
            public QueryFunction<Store, Class<?>> of(final Class<?> element) {
                return QueryFunction.single(element).getAll(ReflectionUtils.SuperTypes::get);
            }
        };
        Annotations = new UtilQueryBuilder<AnnotatedElement, Annotation>() {
            @Override
            public QueryFunction<Store, Annotation> get(final AnnotatedElement element) {
                return ctx -> Arrays.stream(element.getAnnotations()).collect((Collector<? super Annotation, ?, LinkedHashSet<Object>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
            }
            
            @Override
            public QueryFunction<Store, Annotation> of(final AnnotatedElement element) {
                return ReflectionUtils.extendType().get(element).getAll(ReflectionUtils.Annotations::get, (Function<Annotation, AnnotatedElement>)Annotation::annotationType);
            }
        };
        AnnotationTypes = new UtilQueryBuilder<AnnotatedElement, Class<? extends Annotation>>() {
            @Override
            public QueryFunction<Store, Class<? extends Annotation>> get(final AnnotatedElement element) {
                return ReflectionUtils.Annotations.get(element).map((Function<? super Annotation, ? extends Class<? extends Annotation>>)Annotation::annotationType);
            }
            
            @Override
            public QueryFunction<Store, Class<? extends Annotation>> of(final AnnotatedElement element) {
                return ReflectionUtils.extendType().get(element).getAll(ReflectionUtils.AnnotationTypes::get, a -> a);
            }
        };
        Methods = (element -> ctx -> Arrays.stream(element.getDeclaredMethods()).filter(ReflectionUtils.notObjectMethod).collect((Collector<? super Method, ?, LinkedHashSet>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new)));
        Constructors = (element -> ctx -> Arrays.stream(element.getDeclaredConstructors()).collect((Collector<? super Constructor, ?, LinkedHashSet>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new)));
        Fields = (element -> ctx -> Arrays.stream(element.getDeclaredFields()).collect((Collector<? super Field, ?, LinkedHashSet>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new)));
        Resources = (element -> ctx -> new HashSet(ClasspathHelper.forResource(element, new ClassLoader[0])));
    }
}
