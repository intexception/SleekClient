package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

public interface NBTComponent<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends BuildableComponent<C, B>
{
    @NotNull
    String nbtPath();
    
    @Contract(pure = true)
    @NotNull
    C nbtPath(@NotNull final String nbtPath);
    
    boolean interpret();
    
    @Contract(pure = true)
    @NotNull
    C interpret(final boolean interpret);
    
    @Nullable
    Component separator();
    
    @NotNull
    C separator(@Nullable final ComponentLike separator);
}
