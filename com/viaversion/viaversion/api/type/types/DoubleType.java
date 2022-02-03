package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class DoubleType extends Type<Double> implements TypeConverter<Double>
{
    public DoubleType() {
        super(Double.class);
    }
    
    @Deprecated
    @Override
    public Double read(final ByteBuf buffer) {
        return buffer.readDouble();
    }
    
    public double readPrimitive(final ByteBuf buffer) {
        return buffer.readDouble();
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf buffer, final Double object) {
        buffer.writeDouble(object);
    }
    
    public void writePrimitive(final ByteBuf buffer, final double object) {
        buffer.writeDouble(object);
    }
    
    @Override
    public Double from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }
        if (o instanceof Boolean) {
            return o ? 1.0 : 0.0;
        }
        return (Double)o;
    }
}
