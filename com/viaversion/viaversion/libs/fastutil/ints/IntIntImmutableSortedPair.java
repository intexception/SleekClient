package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public class IntIntImmutableSortedPair extends IntIntImmutablePair implements IntIntSortedPair, Serializable
{
    private static final long serialVersionUID = 0L;
    
    private IntIntImmutableSortedPair(final int left, final int right) {
        super(left, right);
    }
    
    public static IntIntImmutableSortedPair of(final int left, final int right) {
        if (left <= right) {
            return new IntIntImmutableSortedPair(left, right);
        }
        return new IntIntImmutableSortedPair(right, left);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof IntIntSortedPair) {
            return this.left == ((IntIntSortedPair)other).leftInt() && this.right == ((IntIntSortedPair)other).rightInt();
        }
        return other instanceof SortedPair && Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right());
    }
    
    @Override
    public String toString() {
        return "{" + this.leftInt() + "," + this.rightInt() + "}";
    }
}
