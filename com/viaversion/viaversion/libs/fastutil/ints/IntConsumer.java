package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

@FunctionalInterface
public interface IntConsumer extends Consumer<Integer>, java.util.function.IntConsumer
{
    @Deprecated
    default void accept(final Integer t) {
        this.accept(t);
    }
    
    default IntConsumer andThen(final java.util.function.IntConsumer after) {
        Objects.requireNonNull(after);
        return t -> {
            this.accept(t);
            after.accept(t);
        };
    }
    
    default IntConsumer andThen(final IntConsumer after) {
        return this.andThen((java.util.function.IntConsumer)after);
    }
    
    @Deprecated
    default Consumer<Integer> andThen(final Consumer<? super Integer> after) {
        return super.andThen(after);
    }
}
