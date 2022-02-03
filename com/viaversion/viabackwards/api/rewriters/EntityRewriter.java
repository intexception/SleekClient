package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public abstract class EntityRewriter<T extends BackwardsProtocol> extends EntityRewriterBase<T>
{
    protected EntityRewriter(final T protocol) {
        this(protocol, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
    }
    
    protected EntityRewriter(final T protocol, final MetaType displayType, final MetaType displayVisibilityType) {
        super(protocol, displayType, 2, displayVisibilityType, 3);
    }
    
    @Override
    public void registerTrackerWithData(final ClientboundPacketType packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler(fallingBlockType));
            }
        });
    }
    
    public PacketHandler getSpawnTrackerWithDataHandler(final EntityType fallingBlockType) {
        final EntityType entityType;
        int blockState;
        return wrapper -> {
            entityType = this.setOldEntityId(wrapper);
            if (entityType == fallingBlockType) {
                blockState = wrapper.get((Type<Integer>)Type.INT, 0);
                wrapper.set(Type.INT, 0, this.protocol.getMappingData().getNewBlockStateId(blockState));
            }
        };
    }
    
    public void registerSpawnTracker(final ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> EntityRewriter.this.setOldEntityId(wrapper));
            }
        });
    }
    
    private EntityType setOldEntityId(final PacketWrapper wrapper) throws Exception {
        final int typeId = wrapper.get((Type<Integer>)Type.VAR_INT, 1);
        final EntityType entityType = this.typeFromId(typeId);
        this.tracker(wrapper.user()).addEntity(wrapper.get((Type<Integer>)Type.VAR_INT, 0), entityType);
        final int mappedTypeId = this.newEntityId(entityType.getId());
        if (typeId != mappedTypeId) {
            wrapper.set(Type.VAR_INT, 1, mappedTypeId);
        }
        return entityType;
    }
}
