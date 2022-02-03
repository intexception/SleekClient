package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.concurrent.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;

public abstract class Listenable<L>
{
    private final List<L> listeners;
    
    public Listenable() {
        this.listeners = new CopyOnWriteArrayList<L>();
    }
    
    protected final void forEachListener(@NotNull final Consumer<L> consumer) {
        for (final L listener : this.listeners) {
            consumer.accept(listener);
        }
    }
    
    protected final void addListener0(@NotNull final L listener) {
        this.listeners.add(listener);
    }
    
    protected final void removeListener0(@NotNull final L listener) {
        this.listeners.remove(listener);
    }
}
