package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class MetadataRewriter1_14_1To1_14 extends EntityRewriter<Protocol1_14_1To1_14>
{
    public MetadataRewriter1_14_1To1_14(final Protocol1_14_1To1_14 protocol) {
        super(protocol);
    }
    
    public void handleMetadata(final int entityId, final EntityType type, final Metadata metadata, final List<Metadata> metadatas, final UserConnection connection) {
        if (type == null) {
            return;
        }
        if ((type == Entity1_14Types.VILLAGER || type == Entity1_14Types.WANDERING_TRADER) && metadata.id() >= 15) {
            metadata.setId(metadata.id() + 1);
        }
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_14Types.getTypeFromId(type);
    }
}
