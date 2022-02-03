package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectSortedSet<K> extends ObjectSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K>
{
    ObjectBidirectionalIterator<K> iterator(final K p0);
    
    ObjectBidirectionalIterator<K> iterator();
    
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorFromSorted((ObjectIterator<? extends K>)this.iterator(), Size64.sizeOf(this), 85, this.comparator());
    }
    
    ObjectSortedSet<K> subSet(final K p0, final K p1);
    
    ObjectSortedSet<K> headSet(final K p0);
    
    ObjectSortedSet<K> tailSet(final K p0);
}
