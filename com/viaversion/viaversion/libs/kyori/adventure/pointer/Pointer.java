package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

public interface Pointer<V> extends Examinable
{
    @NotNull
    default <V> Pointer<V> pointer(@NotNull final Class<V> type, @NotNull final Key key) {
        return new PointerImpl<V>(type, key);
    }
    
    @NotNull
    Class<V> type();
    
    @NotNull
    Key key();
    
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((ExaminableProperty[])new ExaminableProperty[] { ExaminableProperty.of("type", this.type()), ExaminableProperty.of("key", this.key()) });
    }
}
