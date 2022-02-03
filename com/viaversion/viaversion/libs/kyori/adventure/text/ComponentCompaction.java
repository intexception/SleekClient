package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;

final class ComponentCompaction
{
    private static final TextDecoration[] DECORATIONS;
    
    private ComponentCompaction() {
    }
    
    static Component compact(@NotNull final Component self, @Nullable final Style parentStyle) {
        final List<Component> children = self.children();
        Component optimized = self.children(Collections.emptyList());
        if (parentStyle != null) {
            optimized = optimized.style(simplifyStyle(self.style(), parentStyle));
        }
        final int childrenSize = children.size();
        if (childrenSize == 0) {
            return optimized;
        }
        if (childrenSize == 1 && self instanceof TextComponent) {
            final TextComponent textComponent = (TextComponent)self;
            if (textComponent.content().isEmpty()) {
                final Component child = children.get(0);
                return child.style(child.style().merge(optimized.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).compact();
            }
        }
        Style childParentStyle = optimized.style();
        if (parentStyle != null) {
            childParentStyle = childParentStyle.merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        final List<Component> childrenToAppend = new ArrayList<Component>(children.size());
        for (int i = 0; i < children.size(); ++i) {
            childrenToAppend.add(compact(children.get(i), childParentStyle));
        }
        while (!childrenToAppend.isEmpty()) {
            final Component child2 = childrenToAppend.get(0);
            final Style childStyle = child2.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            if (!(optimized instanceof TextComponent) || !(child2 instanceof TextComponent) || !Objects.equals(childStyle, childParentStyle)) {
                break;
            }
            optimized = joinText((TextComponent)optimized, (TextComponent)child2);
            childrenToAppend.remove(0);
            childrenToAppend.addAll(0, child2.children());
        }
        int i = 0;
        while (i + 1 < childrenToAppend.size()) {
            final Component child3 = childrenToAppend.get(i);
            final Component neighbor = childrenToAppend.get(i + 1);
            final Style childStyle2 = child3.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            final Style neighborStyle = neighbor.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            if (child3.children().isEmpty() && child3 instanceof TextComponent && neighbor instanceof TextComponent && childStyle2.equals(neighborStyle)) {
                final Component combined = joinText((TextComponent)child3, (TextComponent)neighbor);
                childrenToAppend.set(i, combined);
                childrenToAppend.remove(i + 1);
            }
            else {
                ++i;
            }
        }
        return optimized.children(childrenToAppend);
    }
    
    @NotNull
    private static Style simplifyStyle(@NotNull final Style style, @NotNull final Style parentStyle) {
        if (style.isEmpty()) {
            return style;
        }
        final Style.Builder builder = style.toBuilder();
        if (Objects.equals(style.font(), parentStyle.font())) {
            builder.font(null);
        }
        if (Objects.equals(style.color(), parentStyle.color())) {
            builder.color(null);
        }
        for (final TextDecoration decoration : ComponentCompaction.DECORATIONS) {
            if (style.decoration(decoration) == parentStyle.decoration(decoration)) {
                builder.decoration(decoration, TextDecoration.State.NOT_SET);
            }
        }
        if (Objects.equals(style.clickEvent(), parentStyle.clickEvent())) {
            builder.clickEvent(null);
        }
        if (Objects.equals(style.hoverEvent(), parentStyle.hoverEvent())) {
            builder.hoverEvent(null);
        }
        if (Objects.equals(style.insertion(), parentStyle.insertion())) {
            builder.insertion(null);
        }
        return builder.build();
    }
    
    private static TextComponent joinText(final TextComponent one, final TextComponent two) {
        return new TextComponentImpl(two.children(), one.style(), one.content() + two.content());
    }
    
    static {
        DECORATIONS = TextDecoration.values();
    }
}
