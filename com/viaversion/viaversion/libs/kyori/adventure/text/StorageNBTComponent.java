package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import org.jetbrains.annotations.*;

public interface StorageNBTComponent extends NBTComponent<StorageNBTComponent, Builder>, ScopedComponent<StorageNBTComponent>
{
    @NotNull
    Key storage();
    
    @Contract(pure = true)
    @NotNull
    StorageNBTComponent storage(@NotNull final Key storage);
    
    public interface Builder extends NBTComponentBuilder<StorageNBTComponent, Builder>
    {
        @Contract("_ -> this")
        @NotNull
        Builder storage(@NotNull final Key storage);
    }
}
