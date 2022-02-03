package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class MetadataRewriter1_12To1_11_1 extends EntityRewriter<Protocol1_12To1_11_1>
{
    public MetadataRewriter1_12To1_11_1(final Protocol1_12To1_11_1 protocol) {
        super(protocol);
    }
    
    @Override
    protected void handleMetadata(final int entityId, final EntityType type, final Metadata metadata, final List<Metadata> metadatas, final UserConnection connection) {
        if (metadata.getValue() instanceof Item) {
            metadata.setValue(((Protocol1_12To1_11_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        if (type == null) {
            return;
        }
        if (type == Entity1_12Types.EntityType.EVOCATION_ILLAGER && metadata.id() == 12) {
            metadata.setId(13);
        }
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_12Types.getTypeFromId(type, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int type) {
        return Entity1_12Types.getTypeFromId(type, true);
    }
}
