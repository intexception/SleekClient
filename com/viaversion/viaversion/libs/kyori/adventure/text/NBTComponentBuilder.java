package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

public interface NBTComponentBuilder<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends ComponentBuilder<C, B>
{
    @Contract("_ -> this")
    @NotNull
    B nbtPath(@NotNull final String nbtPath);
    
    @Contract("_ -> this")
    @NotNull
    B interpret(final boolean interpret);
    
    @Contract("_ -> this")
    @NotNull
    B separator(@Nullable final ComponentLike separator);
}
