package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class BooleanType extends Type<Boolean> implements TypeConverter<Boolean>
{
    public BooleanType() {
        super(Boolean.class);
    }
    
    @Override
    public Boolean read(final ByteBuf buffer) {
        return buffer.readBoolean();
    }
    
    @Override
    public void write(final ByteBuf buffer, final Boolean object) {
        buffer.writeBoolean(object);
    }
    
    @Override
    public Boolean from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue() == 1;
        }
        return (Boolean)o;
    }
}
