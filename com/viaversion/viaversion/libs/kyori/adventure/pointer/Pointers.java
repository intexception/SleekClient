package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface Pointers extends Buildable<Pointers, Builder>
{
    @Contract(pure = true)
    @NotNull
    default Pointers empty() {
        return PointersImpl.EMPTY;
    }
    
    @Contract(pure = true)
    @NotNull
    default Builder builder() {
        return new PointersImpl.BuilderImpl();
    }
    
    @NotNull
     <T> Optional<T> get(@NotNull final Pointer<T> pointer);
    
    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
        return this.get(pointer).orElse(defaultValue);
    }
    
    default <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
        return this.get(pointer).orElseGet(defaultValue);
    }
    
     <T> boolean supports(@NotNull final Pointer<T> pointer);
    
    public interface Builder extends Buildable.Builder<Pointers>
    {
        @Contract("_, _ -> this")
        @NotNull
        default <T> Builder withStatic(@NotNull final Pointer<T> pointer, @Nullable final T value) {
            return this.withDynamic(pointer, () -> value);
        }
        
        @Contract("_, _ -> this")
        @NotNull
         <T> Builder withDynamic(@NotNull final Pointer<T> pointer, @NotNull final Supplier<T> value);
    }
}
