package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public interface Object2IntSortedMap<K> extends Object2IntMap<K>, SortedMap<K, Integer>
{
    Object2IntSortedMap<K> subMap(final K p0, final K p1);
    
    Object2IntSortedMap<K> headMap(final K p0);
    
    Object2IntSortedMap<K> tailMap(final K p0);
    
    @Deprecated
    default ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
        return (ObjectSortedSet<Map.Entry<K, Integer>>)this.object2IntEntrySet();
    }
    
    ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet();
    
    ObjectSortedSet<K> keySet();
    
    IntCollection values();
    
    Comparator<? super K> comparator();
    
    public interface FastSortedEntrySet<K> extends ObjectSortedSet<Object2IntMap.Entry<K>>, FastEntrySet<K>
    {
        ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator();
        
        ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(final Object2IntMap.Entry<K> p0);
    }
}
