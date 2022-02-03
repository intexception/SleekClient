package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;

public interface ComponentSerializer<I extends Component, O extends Component, R>
{
    @NotNull
    O deserialize(@NotNull final R input);
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default O deseializeOrNull(@Nullable final R input) {
        return this.deserializeOrNull(input);
    }
    
    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default O deserializeOrNull(@Nullable final R input) {
        return this.deserializeOr(input, null);
    }
    
    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default O deserializeOr(@Nullable final R input, @Nullable final O fallback) {
        if (input == null) {
            return fallback;
        }
        return this.deserialize(input);
    }
    
    @NotNull
    R serialize(@NotNull final I component);
    
    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default R serializeOrNull(@Nullable final I component) {
        return this.serializeOr(component, null);
    }
    
    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default R serializeOr(@Nullable final I component, @Nullable final R fallback) {
        if (component == null) {
            return fallback;
        }
        return this.serialize(component);
    }
}
