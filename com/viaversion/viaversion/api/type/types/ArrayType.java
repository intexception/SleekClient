package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.lang.reflect.*;
import io.netty.buffer.*;

public class ArrayType<T> extends Type<T[]>
{
    private final Type<T> elementType;
    
    public ArrayType(final Type<T> type) {
        super(type.getTypeName() + " Array", getArrayClass(type.getOutputClass()));
        this.elementType = type;
    }
    
    public static Class<?> getArrayClass(final Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }
    
    @Override
    public T[] read(final ByteBuf buffer) throws Exception {
        final int amount = Type.VAR_INT.readPrimitive(buffer);
        final T[] array = (T[])Array.newInstance(this.elementType.getOutputClass(), amount);
        for (int i = 0; i < amount; ++i) {
            array[i] = this.elementType.read(buffer);
        }
        return array;
    }
    
    @Override
    public void write(final ByteBuf buffer, final T[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (final T o : object) {
            this.elementType.write(buffer, o);
        }
    }
}
