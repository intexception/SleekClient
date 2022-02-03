package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ShortBinaryTag extends NumberBinaryTag
{
    @NotNull
    default ShortBinaryTag of(final short value) {
        return new ShortBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType<ShortBinaryTag> type() {
        return BinaryTagTypes.SHORT;
    }
    
    short value();
}
