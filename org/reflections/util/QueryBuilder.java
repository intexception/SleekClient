package org.reflections.util;

import org.reflections.*;
import java.lang.reflect.*;
import java.util.function.*;
import java.util.*;

public interface QueryBuilder extends NameHelper
{
    default String index() {
        return this.getClass().getSimpleName();
    }
    
    default QueryFunction<Store, String> get(final String key) {
        return store -> new LinkedHashSet<Object>(store.getOrDefault(this.index(), Collections.emptyMap()).getOrDefault(key, Collections.emptySet()));
    }
    
    default QueryFunction<Store, String> get(final AnnotatedElement element) {
        return this.get(this.toName(element));
    }
    
    default QueryFunction<Store, String> get(final Collection<String> keys) {
        return keys.stream().map((Function<? super String, ? extends QueryFunction<Store, String>>)this::get).reduce(QueryFunction::add).get();
    }
    
    default QueryFunction<Store, String> getAll(final Collection<String> keys) {
        return QueryFunction.set(keys).getAll(this::get);
    }
    
    default QueryFunction<Store, String> getAllIncluding(final String key) {
        return QueryFunction.single(key).add(QueryFunction.single(key).getAll(this::get));
    }
    
    default QueryFunction<Store, String> getAllIncluding(final Collection<String> keys) {
        return QueryFunction.set(keys).add(QueryFunction.set(keys).getAll(this::get));
    }
    
    default QueryFunction<Store, String> of(final Collection<String> keys) {
        return this.getAll(keys);
    }
    
    default QueryFunction<Store, String> of(final String key) {
        return this.getAll(Collections.singletonList(key));
    }
    
    default QueryFunction<Store, String> of(final AnnotatedElement... elements) {
        return this.getAll(this.toNames(elements));
    }
    
    default QueryFunction<Store, String> of(final Set<? extends AnnotatedElement> elements) {
        return this.getAll(this.toNames(elements));
    }
    
    default QueryFunction<Store, String> with(final Collection<String> keys) {
        return this.of(keys);
    }
    
    default QueryFunction<Store, String> with(final String key) {
        return this.of(key);
    }
    
    default QueryFunction<Store, String> with(final AnnotatedElement... keys) {
        return this.of(keys);
    }
    
    default QueryFunction<Store, String> with(final Set<? extends AnnotatedElement> keys) {
        return this.of(keys);
    }
    
    default <T> QueryFunction<Store, T> of(final QueryFunction queryFunction) {
        return queryFunction.add(queryFunction.getAll(this::get));
    }
}
