package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class OldMetaType extends MetaTypeTemplate
{
    private static final int END = 127;
    
    @Override
    public Metadata read(final ByteBuf buffer) throws Exception {
        final byte index = buffer.readByte();
        if (index == 127) {
            return null;
        }
        final MetaType type = this.getType((index & 0xE0) >> 5);
        return new Metadata(index & 0x1F, type, type.type().read(buffer));
    }
    
    protected abstract MetaType getType(final int p0);
    
    @Override
    public void write(final ByteBuf buffer, final Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(127);
        }
        else {
            final int index = (object.metaType().typeId() << 5 | (object.id() & 0x1F)) & 0xFF;
            buffer.writeByte(index);
            object.metaType().type().write(buffer, object.getValue());
        }
    }
}
