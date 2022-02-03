package com.viaversion.viaversion.util;

import com.google.common.collect.*;
import java.util.function.*;
import java.util.*;

public class SetWrapper<E> extends ForwardingSet<E>
{
    private final Set<E> set;
    private final Consumer<E> addListener;
    
    public SetWrapper(final Set<E> set, final Consumer<E> addListener) {
        this.set = set;
        this.addListener = addListener;
    }
    
    public boolean add(final E element) {
        this.addListener.accept(element);
        return super.add((Object)element);
    }
    
    public boolean addAll(final Collection<? extends E> collection) {
        for (final E element : collection) {
            this.addListener.accept(element);
        }
        return super.addAll((Collection)collection);
    }
    
    protected Set<E> delegate() {
        return this.originalSet();
    }
    
    public Set<E> originalSet() {
        return this.set;
    }
}
