package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectListIterator<K> extends ObjectBidirectionalIterator<K>, ListIterator<K>
{
    default void set(final K k) {
        throw new UnsupportedOperationException();
    }
    
    default void add(final K k) {
        throw new UnsupportedOperationException();
    }
    
    default void remove() {
        throw new UnsupportedOperationException();
    }
}
