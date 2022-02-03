package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface Pointered
{
    @NotNull
    default <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
        return this.pointers().get(pointer);
    }
    
    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
        return this.pointers().getOrDefault(pointer, defaultValue);
    }
    
    default <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
        return this.pointers().getOrDefaultFrom(pointer, defaultValue);
    }
    
    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }
}
