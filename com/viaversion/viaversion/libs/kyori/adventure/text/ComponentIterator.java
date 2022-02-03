package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.*;

final class ComponentIterator implements Iterator<Component>
{
    private Component component;
    private final ComponentIteratorType type;
    private final Set<ComponentIteratorFlag> flags;
    private final Deque<Component> deque;
    
    ComponentIterator(@NotNull final Component component, @NotNull final ComponentIteratorType type, @NotNull final Set<ComponentIteratorFlag> flags) {
        this.component = component;
        this.type = type;
        this.flags = flags;
        this.deque = new ArrayDeque<Component>();
    }
    
    @Override
    public boolean hasNext() {
        return this.component != null || !this.deque.isEmpty();
    }
    
    @Override
    public Component next() {
        if (this.component != null) {
            final Component next = this.component;
            this.component = null;
            this.type.populate(next, this.deque, this.flags);
            return next;
        }
        if (this.deque.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.component = this.deque.poll();
        return this.next();
    }
}
