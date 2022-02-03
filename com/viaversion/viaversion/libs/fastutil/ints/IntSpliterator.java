package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

public interface IntSpliterator extends Spliterator.OfInt
{
    @Deprecated
    default boolean tryAdvance(final Consumer<? super Integer> action) {
        IntConsumer intConsumer;
        if (action instanceof IntConsumer) {
            intConsumer = (IntConsumer)action;
        }
        else {
            Objects.requireNonNull(action);
            intConsumer = action::accept;
        }
        return this.tryAdvance(intConsumer);
    }
    
    default boolean tryAdvance(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer action) {
        return this.tryAdvance((IntConsumer)action);
    }
    
    @Deprecated
    default void forEachRemaining(final Consumer<? super Integer> action) {
        IntConsumer intConsumer;
        if (action instanceof IntConsumer) {
            intConsumer = (IntConsumer)action;
        }
        else {
            Objects.requireNonNull(action);
            intConsumer = action::accept;
        }
        this.forEachRemaining(intConsumer);
    }
    
    default void forEachRemaining(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer action) {
        this.forEachRemaining((IntConsumer)action);
    }
    
    default long skip(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        long i = n;
        while (i-- != 0L && this.tryAdvance(unused -> {})) {}
        return n - i - 1L;
    }
    
    IntSpliterator trySplit();
    
    default IntComparator getComparator() {
        throw new IllegalStateException();
    }
}
