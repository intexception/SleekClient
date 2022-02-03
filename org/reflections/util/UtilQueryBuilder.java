package org.reflections.util;

import org.reflections.*;
import java.lang.reflect.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

public interface UtilQueryBuilder<F, E>
{
    QueryFunction<Store, E> get(final F p0);
    
    default QueryFunction<Store, E> of(final F element) {
        return this.of(ReflectionUtils.extendType().get((AnnotatedElement)element));
    }
    
    default QueryFunction<Store, E> of(final F element, final Predicate<? super E> predicate) {
        return this.of(element).filter(predicate);
    }
    
    default <T> QueryFunction<Store, E> of(final QueryFunction<Store, T> function) {
        return store -> function.apply(store).stream().flatMap(t -> this.get(t).apply(store).stream()).collect((Collector<? super Object, ?, LinkedHashSet<Object>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
    }
}
