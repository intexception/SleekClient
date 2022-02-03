package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;

public interface IntIntSortedPair extends IntIntPair, SortedPair<Integer>, Serializable
{
    default IntIntSortedPair of(final int left, final int right) {
        return IntIntImmutableSortedPair.of(left, right);
    }
    
    default boolean contains(final int e) {
        return e == this.leftInt() || e == this.rightInt();
    }
    
    @Deprecated
    default boolean contains(final Object o) {
        return o != null && this.contains((int)o);
    }
}
