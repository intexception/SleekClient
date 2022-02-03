package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface ComponentRenderer<C>
{
    @NotNull
    Component render(@NotNull final Component component, @NotNull final C context);
    
    default <T> ComponentRenderer<T> mapContext(final Function<T, C> transformer) {
        return (component, ctx) -> this.render(component, transformer.apply(ctx));
    }
}
