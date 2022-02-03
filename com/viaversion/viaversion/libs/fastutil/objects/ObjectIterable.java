package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectIterable<K> extends Iterable<K>
{
    ObjectIterator<K> iterator();
    
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorUnknownSize(this.iterator(), 0);
    }
}
