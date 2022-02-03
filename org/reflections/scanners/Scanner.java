package org.reflections.scanners;

import javassist.bytecode.*;
import org.reflections.vfs.*;
import javax.annotation.*;
import java.util.stream.*;
import java.util.*;

public interface Scanner
{
    List<Map.Entry<String, String>> scan(final ClassFile p0);
    
    @Nullable
    default List<Map.Entry<String, String>> scan(final Vfs.File file) {
        return null;
    }
    
    default String index() {
        return this.getClass().getSimpleName();
    }
    
    default boolean acceptsInput(final String file) {
        return file.endsWith(".class");
    }
    
    default Map.Entry<String, String> entry(final String key, final String value) {
        return new AbstractMap.SimpleEntry<String, String>(key, value);
    }
    
    default List<Map.Entry<String, String>> entries(final Collection<String> keys, final String value) {
        return keys.stream().map(key -> this.entry(key, value)).collect((Collector<? super Object, ?, List<Map.Entry<String, String>>>)Collectors.toList());
    }
    
    default List<Map.Entry<String, String>> entries(final String key, final String value) {
        return Collections.singletonList(this.entry(key, value));
    }
    
    default List<Map.Entry<String, String>> entries(final String key, final Collection<String> values) {
        return values.stream().map(value -> this.entry(key, value)).collect((Collector<? super Object, ?, List<Map.Entry<String, String>>>)Collectors.toList());
    }
}
