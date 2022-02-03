package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import java.util.function.*;
import org.jetbrains.annotations.*;

public interface HoverEventSource<V>
{
    @Nullable
    default <V> HoverEvent<V> unbox(@Nullable final HoverEventSource<V> source) {
        return (source != null) ? source.asHoverEvent() : null;
    }
    
    @NotNull
    default HoverEvent<V> asHoverEvent() {
        return this.asHoverEvent(UnaryOperator.identity());
    }
    
    @NotNull
    HoverEvent<V> asHoverEvent(@NotNull final UnaryOperator<V> op);
}
