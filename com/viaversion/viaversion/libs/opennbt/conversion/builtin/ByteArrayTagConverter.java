package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ByteArrayTagConverter implements TagConverter<ByteArrayTag, byte[]>
{
    @Override
    public byte[] convert(final ByteArrayTag tag) {
        return tag.getValue();
    }
    
    @Override
    public ByteArrayTag convert(final byte[] value) {
        return new ByteArrayTag(value);
    }
}
