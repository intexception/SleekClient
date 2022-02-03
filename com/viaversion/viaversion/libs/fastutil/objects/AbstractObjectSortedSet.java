package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public abstract class AbstractObjectSortedSet<K> extends AbstractObjectSet<K> implements ObjectSortedSet<K>
{
    protected AbstractObjectSortedSet() {
    }
    
    @Override
    public abstract ObjectBidirectionalIterator<K> iterator();
}
