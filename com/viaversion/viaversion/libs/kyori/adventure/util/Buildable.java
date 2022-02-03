package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.*;
import org.jetbrains.annotations.*;

public interface Buildable<R, B extends Builder<R>>
{
    @Contract(mutates = "param1")
    @NotNull
    default <R extends Buildable<R, B>, B extends Builder<R>> R configureAndBuild(@NotNull final B builder, @Nullable final Consumer<? super B> consumer) {
        if (consumer != null) {
            consumer.accept((Object)builder);
        }
        return builder.build();
    }
    
    @Contract(value = "-> new", pure = true)
    @NotNull
    B toBuilder();
    
    public interface Builder<R>
    {
        @Contract(value = "-> new", pure = true)
        @NotNull
        R build();
    }
}
