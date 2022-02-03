package org.reflections.util;

import java.lang.annotation.*;
import java.lang.reflect.*;
import org.reflections.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class AnnotationMergeCollector implements Collector<Annotation, Map<String, Object>, Map<String, Object>>
{
    private final AnnotatedElement annotatedElement;
    private final BiFunction<Object, Object, Object> mergeFunction;
    
    public AnnotationMergeCollector(final AnnotatedElement annotatedElement, final BiFunction<Object, Object, Object> mergeFunction) {
        this.annotatedElement = annotatedElement;
        this.mergeFunction = mergeFunction;
    }
    
    public AnnotationMergeCollector() {
        this(null);
    }
    
    public AnnotationMergeCollector(final AnnotatedElement annotatedElement) {
        this(annotatedElement, AnnotationMergeCollector::concatValues);
    }
    
    @Override
    public Supplier<Map<String, Object>> supplier() {
        return (Supplier<Map<String, Object>>)HashMap::new;
    }
    
    @Override
    public BiConsumer<Map<String, Object>, Annotation> accumulator() {
        return (acc, ann) -> this.mergeMaps(acc, ReflectionUtils.toMap(ann, this.annotatedElement));
    }
    
    @Override
    public BinaryOperator<Map<String, Object>> combiner() {
        return this::mergeMaps;
    }
    
    @Override
    public Function<Map<String, Object>, Map<String, Object>> finisher() {
        return Function.identity();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
    
    private Map<String, Object> mergeMaps(final Map<String, Object> m1, final Map<String, Object> m2) {
        m2.forEach((k1, v1) -> m1.merge(k1, v1, this.mergeFunction));
        return m1;
    }
    
    private static Object concatValues(final Object v1, final Object v2) {
        if (v1.getClass().isArray()) {
            if (v2.getClass().getComponentType().equals(String.class)) {
                return stringArrayConcat((String[])v1, (String[])v2);
            }
            return arrayAdd((Object[])v1, (Object[])v2);
        }
        else {
            if (v2.getClass().equals(String.class)) {
                return stringConcat((String)v1, (String)v2);
            }
            return v2;
        }
    }
    
    private static Object[] arrayAdd(final Object[] o1, final Object[] o2) {
        return (o2.length == 0) ? o1 : ((o1.length == 0) ? o2 : Stream.concat((Stream<?>)Stream.of((T[])o1), (Stream<?>)Stream.of((T[])o2)).toArray(Object[]::new));
    }
    
    private static Object stringArrayConcat(final String[] v1, final String[] v2) {
        return (v2.length == 0) ? v1 : ((v1.length == 0) ? v2 : Arrays.stream(v2).flatMap(s2 -> Arrays.stream(v1).map(s1 -> s2 + s1)).toArray(String[]::new));
    }
    
    private static Object stringConcat(final String v1, final String v2) {
        return v2.isEmpty() ? v1 : (v1.isEmpty() ? v2 : (v1 + v2));
    }
}
