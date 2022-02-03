package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public final class ObjectIterables
{
    private ObjectIterables() {
    }
    
    public static <K> long size(final Iterable<K> iterable) {
        long c = 0L;
        for (final K dummy : iterable) {
            ++c;
        }
        return c;
    }
}
