package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class RemainingBytesType extends Type<byte[]>
{
    public RemainingBytesType() {
        super(byte[].class);
    }
    
    @Override
    public byte[] read(final ByteBuf buffer) {
        final byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        return array;
    }
    
    @Override
    public void write(final ByteBuf buffer, final byte[] object) {
        buffer.writeBytes(object);
    }
}
