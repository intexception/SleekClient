package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.function.*;
import java.util.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ComponentLike
{
    @NotNull
    default List<Component> asComponents(@NotNull final List<? extends ComponentLike> likes) {
        return asComponents(likes, null);
    }
    
    @NotNull
    default List<Component> asComponents(@NotNull final List<? extends ComponentLike> likes, @Nullable final Predicate<? super Component> filter) {
        final int size = likes.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        ArrayList<Component> components = null;
        for (int i = 0; i < size; ++i) {
            final ComponentLike like = (ComponentLike)likes.get(i);
            final Component component = like.asComponent();
            if (filter == null || filter.test(component)) {
                if (components == null) {
                    components = new ArrayList<Component>(size);
                }
                components.add(component);
            }
        }
        if (components == null) {
            return Collections.emptyList();
        }
        components.trimToSize();
        return Collections.unmodifiableList((List<? extends Component>)components);
    }
    
    @Nullable
    default Component unbox(@Nullable final ComponentLike like) {
        return (like != null) ? like.asComponent() : null;
    }
    
    @Contract(pure = true)
    @NotNull
    Component asComponent();
}
