package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.*;

public interface StoredEntityData
{
    EntityType type();
    
    boolean has(final Class<?> p0);
    
     <T> T get(final Class<T> p0);
    
    void put(final Object p0);
}
