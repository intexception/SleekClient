package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectBidirectionalIterable<K> extends ObjectIterable<K>
{
    ObjectBidirectionalIterator<K> iterator();
}
