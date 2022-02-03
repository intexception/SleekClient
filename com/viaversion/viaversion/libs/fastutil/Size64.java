package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface Size64
{
    long size64();
    
    @Deprecated
    default int size() {
        return (int)Math.min(2147483647L, this.size64());
    }
    
    default long sizeOf(final Collection<?> c) {
        return (c instanceof Size64) ? ((Size64)c).size64() : c.size();
    }
    
    default long sizeOf(final Map<?, ?> m) {
        return (m instanceof Size64) ? ((Size64)m).size64() : m.size();
    }
}
