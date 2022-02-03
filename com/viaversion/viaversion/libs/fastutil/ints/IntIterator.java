package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

public interface IntIterator extends PrimitiveIterator.OfInt
{
    int nextInt();
    
    @Deprecated
    default Integer next() {
        return this.nextInt();
    }
    
    default void forEachRemaining(final IntConsumer action) {
        this.forEachRemaining((java.util.function.IntConsumer)action);
    }
    
    @Deprecated
    default void forEachRemaining(final Consumer<? super Integer> action) {
        java.util.function.IntConsumer intConsumer;
        if (action instanceof java.util.function.IntConsumer) {
            intConsumer = (java.util.function.IntConsumer)action;
        }
        else {
            Objects.requireNonNull(action);
            intConsumer = action::accept;
        }
        this.forEachRemaining(intConsumer);
    }
    
    default int skip(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextInt();
        }
        return n - i - 1;
    }
}
