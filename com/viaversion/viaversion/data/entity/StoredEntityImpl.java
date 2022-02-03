package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.concurrent.*;

public final class StoredEntityImpl implements StoredEntityData
{
    private final Map<Class<?>, Object> storedObjects;
    private final EntityType type;
    
    public StoredEntityImpl(final EntityType type) {
        this.storedObjects = new ConcurrentHashMap<Class<?>, Object>();
        this.type = type;
    }
    
    @Override
    public EntityType type() {
        return this.type;
    }
    
    @Override
    public <T> T get(final Class<T> objectClass) {
        return (T)this.storedObjects.get(objectClass);
    }
    
    @Override
    public boolean has(final Class<?> objectClass) {
        return this.storedObjects.containsKey(objectClass);
    }
    
    @Override
    public void put(final Object object) {
        this.storedObjects.put(object.getClass(), object);
    }
}
