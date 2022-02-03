package com.viaversion.viaversion.protocols.protocol1_11to1_10.storage;

import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.flare.fastutil.*;

public class EntityTracker1_11 extends EntityTrackerBase
{
    private final IntSet holograms;
    
    public EntityTracker1_11(final UserConnection user) {
        super(user, Entity1_11Types.EntityType.PLAYER);
        this.holograms = Int2ObjectSyncMap.hashset();
    }
    
    @Override
    public void removeEntity(final int entityId) {
        super.removeEntity(entityId);
        this.removeHologram(entityId);
    }
    
    public boolean addHologram(final int entId) {
        return this.holograms.add(entId);
    }
    
    public boolean isHologram(final int entId) {
        return this.holograms.contains(entId);
    }
    
    public void removeHologram(final int entId) {
        this.holograms.remove(entId);
    }
}
