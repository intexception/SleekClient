package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectCollection<K> extends Collection<K>, ObjectIterable<K>
{
    ObjectIterator<K> iterator();
    
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 64);
    }
}
