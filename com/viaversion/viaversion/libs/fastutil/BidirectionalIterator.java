package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface BidirectionalIterator<K> extends Iterator<K>
{
    K previous();
    
    boolean hasPrevious();
}
