package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectIterator<K> extends Iterator<K>
{
    default int skip(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.next();
        }
        return n - i - 1;
    }
}
