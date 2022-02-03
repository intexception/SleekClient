package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class ShortByteArrayType extends Type<byte[]>
{
    public ShortByteArrayType() {
        super(byte[].class);
    }
    
    @Override
    public void write(final ByteBuf buffer, final byte[] object) throws Exception {
        buffer.writeShort(object.length);
        buffer.writeBytes(object);
    }
    
    @Override
    public byte[] read(final ByteBuf buffer) throws Exception {
        final byte[] array = new byte[buffer.readShort()];
        buffer.readBytes(array);
        return array;
    }
}
