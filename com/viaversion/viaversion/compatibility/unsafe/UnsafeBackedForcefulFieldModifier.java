package com.viaversion.viaversion.compatibility.unsafe;

import com.viaversion.viaversion.compatibility.*;
import sun.misc.*;
import java.lang.reflect.*;
import java.util.*;

@Deprecated
public final class UnsafeBackedForcefulFieldModifier implements ForcefulFieldModifier
{
    private final Unsafe unsafe;
    
    public UnsafeBackedForcefulFieldModifier() throws ReflectiveOperationException {
        final Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeField.setAccessible(true);
        this.unsafe = (Unsafe)theUnsafeField.get(null);
    }
    
    @Override
    public void setField(final Field field, final Object holder, final Object object) {
        Objects.requireNonNull(field, "field must not be null");
        final Object ufo = (holder != null) ? holder : this.unsafe.staticFieldBase(field);
        final long offset = (holder != null) ? this.unsafe.objectFieldOffset(field) : this.unsafe.staticFieldOffset(field);
        this.unsafe.putObject(ufo, offset, object);
    }
}
