package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public final class InventoryStateIds implements StorableObject
{
    private final Int2IntMap ids;
    
    public InventoryStateIds() {
        (this.ids = new Int2IntOpenHashMap()).defaultReturnValue(Integer.MAX_VALUE);
    }
    
    public void setStateId(final short containerId, final int id) {
        this.ids.put(containerId, id);
    }
    
    public int removeStateId(final short containerId) {
        return this.ids.remove(containerId);
    }
}
