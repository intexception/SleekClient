package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface IntSortedSet extends IntSet, SortedSet<Integer>, IntBidirectionalIterable
{
    IntBidirectionalIterator iterator(final int p0);
    
    IntBidirectionalIterator iterator();
    
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 341, this.comparator());
    }
    
    IntSortedSet subSet(final int p0, final int p1);
    
    IntSortedSet headSet(final int p0);
    
    IntSortedSet tailSet(final int p0);
    
    IntComparator comparator();
    
    int firstInt();
    
    int lastInt();
    
    @Deprecated
    default IntSortedSet subSet(final Integer from, final Integer to) {
        return this.subSet((int)from, (int)to);
    }
    
    @Deprecated
    default IntSortedSet headSet(final Integer to) {
        return this.headSet((int)to);
    }
    
    @Deprecated
    default IntSortedSet tailSet(final Integer from) {
        return this.tailSet((int)from);
    }
    
    @Deprecated
    default Integer first() {
        return this.firstInt();
    }
    
    @Deprecated
    default Integer last() {
        return this.lastInt();
    }
}
