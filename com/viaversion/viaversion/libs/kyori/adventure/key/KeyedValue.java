package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.*;
import java.util.*;

public interface KeyedValue<T> extends Keyed
{
    @NotNull
    default <T> KeyedValue<T> of(@NotNull final Key key, @NotNull final T value) {
        return new KeyedValueImpl<T>(key, Objects.requireNonNull(value, "value"));
    }
    
    @NotNull
    T value();
}
