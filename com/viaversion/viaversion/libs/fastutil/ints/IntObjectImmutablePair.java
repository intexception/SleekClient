package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class IntObjectImmutablePair<V> implements IntObjectPair<V>, Serializable
{
    private static final long serialVersionUID = 0L;
    protected final int left;
    protected final V right;
    
    public IntObjectImmutablePair(final int left, final V right) {
        this.left = left;
        this.right = right;
    }
    
    public static <V> IntObjectImmutablePair<V> of(final int left, final V right) {
        return new IntObjectImmutablePair<V>(left, right);
    }
    
    @Override
    public int leftInt() {
        return this.left;
    }
    
    @Override
    public V right() {
        return this.right;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof IntObjectPair) {
            return this.left == ((IntObjectPair)other).leftInt() && Objects.equals(this.right, ((IntObjectPair)other).right());
        }
        return other instanceof Pair && Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
    }
    
    @Override
    public int hashCode() {
        return this.left * 19 + ((this.right == null) ? 0 : this.right.hashCode());
    }
    
    @Override
    public String toString() {
        return "<" + this.leftInt() + "," + this.right() + ">";
    }
}
