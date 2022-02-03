package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.minecraft.entities.*;

public class EntityTypeMapping
{
    public static int getOldEntityId(final int entityId) {
        if (entityId == 4) {
            return Entity1_14Types.PUFFERFISH.getId();
        }
        return (entityId >= 5) ? (entityId - 1) : entityId;
    }
}
