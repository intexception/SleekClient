package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.*;

public final class FieldAttributes
{
    private final Field field;
    
    public FieldAttributes(final Field f) {
        $Gson$Preconditions.checkNotNull(f);
        this.field = f;
    }
    
    public Class<?> getDeclaringClass() {
        return this.field.getDeclaringClass();
    }
    
    public String getName() {
        return this.field.getName();
    }
    
    public Type getDeclaredType() {
        return this.field.getGenericType();
    }
    
    public Class<?> getDeclaredClass() {
        return this.field.getType();
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> annotation) {
        return this.field.getAnnotation(annotation);
    }
    
    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.field.getAnnotations());
    }
    
    public boolean hasModifier(final int modifier) {
        return (this.field.getModifiers() & modifier) != 0x0;
    }
    
    Object get(final Object instance) throws IllegalAccessException {
        return this.field.get(instance);
    }
    
    boolean isSynthetic() {
        return this.field.isSynthetic();
    }
}
