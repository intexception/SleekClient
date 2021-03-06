package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;

public interface IntArrayBinaryTag extends ArrayBinaryTag, Iterable<Integer>
{
    @NotNull
    default IntArrayBinaryTag of(final int... value) {
        return new IntArrayBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType<IntArrayBinaryTag> type() {
        return BinaryTagTypes.INT_ARRAY;
    }
    
    int[] value();
    
    int size();
    
    int get(final int index);
    
    PrimitiveIterator.OfInt iterator();
    
    Spliterator.OfInt spliterator();
    
    @NotNull
    IntStream stream();
    
    void forEachInt(@NotNull final IntConsumer action);
}
