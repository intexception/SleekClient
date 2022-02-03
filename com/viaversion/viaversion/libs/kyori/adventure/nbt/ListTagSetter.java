package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ListTagSetter<R, T extends BinaryTag>
{
    @NotNull
    R add(final T tag);
    
    @NotNull
    R add(final Iterable<? extends T> tags);
}
