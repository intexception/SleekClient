package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class ModernMetaType extends MetaTypeTemplate
{
    private static final int END = 255;
    
    @Override
    public Metadata read(final ByteBuf buffer) throws Exception {
        final short index = buffer.readUnsignedByte();
        if (index == 255) {
            return null;
        }
        final MetaType type = this.getType(Type.VAR_INT.readPrimitive(buffer));
        return new Metadata(index, type, type.type().read(buffer));
    }
    
    protected abstract MetaType getType(final int p0);
    
    @Override
    public void write(final ByteBuf buffer, final Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(255);
        }
        else {
            buffer.writeByte(object.id());
            final MetaType type = object.metaType();
            Type.VAR_INT.writePrimitive(buffer, type.typeId());
            type.type().write(buffer, object.getValue());
        }
    }
}
