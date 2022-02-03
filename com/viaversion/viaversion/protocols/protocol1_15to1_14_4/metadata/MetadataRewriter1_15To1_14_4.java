package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.*;

public class MetadataRewriter1_15To1_14_4 extends EntityRewriter<Protocol1_15To1_14_4>
{
    public MetadataRewriter1_15To1_14_4(final Protocol1_15To1_14_4 protocol) {
        super(protocol);
    }
    
    public void handleMetadata(final int entityId, final EntityType type, final Metadata metadata, final List<Metadata> metadatas, final UserConnection connection) throws Exception {
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_15To1_14_4)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            final int data = (int)metadata.getValue();
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data));
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_15Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            final int data = (int)metadata.getValue();
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data));
        }
        if (metadata.id() > 11 && type.isOrHasParent(Entity1_15Types.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
        }
        if (type.isOrHasParent(Entity1_15Types.WOLF)) {
            if (metadata.id() == 18) {
                metadatas.remove(metadata);
            }
            else if (metadata.id() > 18) {
                metadata.setId(metadata.id() - 1);
            }
        }
    }
    
    @Override
    public int newEntityId(final int id) {
        return EntityPackets.getNewEntityId(id);
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_15Types.getTypeFromId(type);
    }
}
