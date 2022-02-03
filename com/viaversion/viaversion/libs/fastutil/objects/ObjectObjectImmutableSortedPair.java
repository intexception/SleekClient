package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;

public class ObjectObjectImmutableSortedPair<K extends Comparable<K>> extends ObjectObjectImmutablePair<K, K> implements SortedPair<K>, Serializable
{
    private static final long serialVersionUID = 0L;
    
    private ObjectObjectImmutableSortedPair(final K left, final K right) {
        super(left, right);
    }
    
    public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> of(final K left, final K right) {
        if (left.compareTo(right) <= 0) {
            return new ObjectObjectImmutableSortedPair<K>(left, right);
        }
        return new ObjectObjectImmutableSortedPair<K>(right, left);
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other instanceof SortedPair && Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right());
    }
    
    @Override
    public String toString() {
        return "{" + this.left() + "," + this.right() + "}";
    }
}
