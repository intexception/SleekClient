package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;

public final class ForwardingIterator<T> implements Iterable<T>
{
    private final Supplier<Iterator<T>> iterator;
    private final Supplier<Spliterator<T>> spliterator;
    
    public ForwardingIterator(@NotNull final Supplier<Iterator<T>> iterator, @NotNull final Supplier<Spliterator<T>> spliterator) {
        this.iterator = Objects.requireNonNull(iterator, "iterator");
        this.spliterator = Objects.requireNonNull(spliterator, "spliterator");
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.iterator.get();
    }
    
    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return this.spliterator.get();
    }
}
