package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class FloatType extends Type<Float> implements TypeConverter<Float>
{
    public FloatType() {
        super(Float.class);
    }
    
    public float readPrimitive(final ByteBuf buffer) {
        return buffer.readFloat();
    }
    
    public void writePrimitive(final ByteBuf buffer, final float object) {
        buffer.writeFloat(object);
    }
    
    @Deprecated
    @Override
    public Float read(final ByteBuf buffer) {
        return buffer.readFloat();
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf buffer, final Float object) {
        buffer.writeFloat(object);
    }
    
    @Override
    public Float from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).floatValue();
        }
        if (o instanceof Boolean) {
            return o ? 1.0f : 0.0f;
        }
        return (Float)o;
    }
}
