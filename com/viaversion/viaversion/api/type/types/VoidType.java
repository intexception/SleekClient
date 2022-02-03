package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VoidType extends Type<Void> implements TypeConverter<Void>
{
    public VoidType() {
        super(Void.class);
    }
    
    @Override
    public Void read(final ByteBuf buffer) {
        return null;
    }
    
    @Override
    public void write(final ByteBuf buffer, final Void object) {
    }
    
    @Override
    public Void from(final Object o) {
        return null;
    }
}
