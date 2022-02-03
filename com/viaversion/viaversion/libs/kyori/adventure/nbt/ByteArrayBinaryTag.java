package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ByteArrayBinaryTag extends ArrayBinaryTag, Iterable<Byte>
{
    @NotNull
    default ByteArrayBinaryTag of(final byte... value) {
        return new ByteArrayBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType<ByteArrayBinaryTag> type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }
    
    byte[] value();
    
    int size();
    
    byte get(final int index);
}
