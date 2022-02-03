package com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.*;
import java.util.*;

public class MetadataRewriter1_11To1_10 extends EntityRewriter<Protocol1_11To1_10>
{
    public MetadataRewriter1_11To1_10(final Protocol1_11To1_10 protocol) {
        super(protocol);
    }
    
    @Override
    protected void handleMetadata(final int entityId, final EntityType type, final Metadata metadata, final List<Metadata> metadatas, final UserConnection connection) {
        if (metadata.getValue() instanceof DataItem) {
            EntityIdRewriter.toClientItem((Item)metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.is(Entity1_11Types.EntityType.ELDER_GUARDIAN) || type.is(Entity1_11Types.EntityType.GUARDIAN)) {
            final int oldid = metadata.id();
            if (oldid == 12) {
                final boolean val = ((byte)metadata.getValue() & 0x2) == 0x2;
                metadata.setTypeAndValue(MetaType1_9.Boolean, val);
            }
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_SKELETON)) {
            final int oldid = metadata.id();
            if (oldid == 12) {
                metadatas.remove(metadata);
            }
            if (oldid == 13) {
                metadata.setId(12);
            }
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ZOMBIE)) {
            if (type.is(Entity1_11Types.EntityType.ZOMBIE, Entity1_11Types.EntityType.HUSK) && metadata.id() == 14) {
                metadatas.remove(metadata);
            }
            else if (metadata.id() == 15) {
                metadata.setId(14);
            }
            else if (metadata.id() == 14) {
                metadata.setId(15);
            }
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_HORSE)) {
            final int oldid = metadata.id();
            if (oldid == 14) {
                metadatas.remove(metadata);
            }
            if (oldid == 16) {
                metadata.setId(14);
            }
            if (oldid == 17) {
                metadata.setId(16);
            }
            if (!type.is(Entity1_11Types.EntityType.HORSE)) {
                if (metadata.id() == 15 || metadata.id() == 16) {
                    metadatas.remove(metadata);
                }
            }
            if (type.is(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.MULE) && metadata.id() == 13) {
                if (((byte)metadata.getValue() & 0x8) == 0x8) {
                    metadatas.add(new Metadata(15, MetaType1_9.Boolean, true));
                }
                else {
                    metadatas.add(new Metadata(15, MetaType1_9.Boolean, false));
                }
            }
        }
        if (type.is(Entity1_11Types.EntityType.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
            final Metadata flags = this.metaByIndex(11, metadatas);
            final Metadata customName = this.metaByIndex(2, metadatas);
            final Metadata customNameVisible = this.metaByIndex(3, metadatas);
            if (metadata.id() == 0 && flags != null && customName != null && customNameVisible != null) {
                final byte data = (byte)metadata.getValue();
                if ((data & 0x20) == 0x20 && ((byte)flags.getValue() & 0x1) == 0x1 && !((String)customName.getValue()).isEmpty() && (boolean)customNameVisible.getValue()) {
                    final EntityTracker1_11 tracker = this.tracker(connection);
                    if (tracker.addHologram(entityId)) {
                        try {
                            final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.ENTITY_POSITION, null, connection);
                            wrapper.write(Type.VAR_INT, entityId);
                            wrapper.write(Type.SHORT, (Short)0);
                            wrapper.write(Type.SHORT, (short)(128.0 * (-Via.getConfig().getHologramYOffset() * 32.0)));
                            wrapper.write(Type.SHORT, (Short)0);
                            wrapper.write(Type.BOOLEAN, true);
                            wrapper.send(Protocol1_11To1_10.class);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_11Types.getTypeFromId(type, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int type) {
        return Entity1_11Types.getTypeFromId(type, true);
    }
    
    public static Entity1_11Types.EntityType rewriteEntityType(final int numType, final List<Metadata> metadata) {
        final Optional<Entity1_11Types.EntityType> optType = Entity1_11Types.EntityType.findById(numType);
        if (!optType.isPresent()) {
            Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + numType + " with metadata: " + metadata);
            return null;
        }
        final Entity1_11Types.EntityType type = optType.get();
        try {
            if (type.is(Entity1_11Types.EntityType.GUARDIAN)) {
                final Optional<Metadata> options = getById(metadata, 12);
                if (options.isPresent() && ((byte)options.get().getValue() & 0x4) == 0x4) {
                    return Entity1_11Types.EntityType.ELDER_GUARDIAN;
                }
            }
            if (type.is(Entity1_11Types.EntityType.SKELETON)) {
                final Optional<Metadata> options = getById(metadata, 12);
                if (options.isPresent()) {
                    if ((int)options.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.WITHER_SKELETON;
                    }
                    if ((int)options.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.STRAY;
                    }
                }
            }
            if (type.is(Entity1_11Types.EntityType.ZOMBIE)) {
                final Optional<Metadata> options = getById(metadata, 13);
                if (options.isPresent()) {
                    final int value = (int)options.get().getValue();
                    if (value > 0 && value < 6) {
                        metadata.add(new Metadata(16, MetaType1_9.VarInt, value - 1));
                        return Entity1_11Types.EntityType.ZOMBIE_VILLAGER;
                    }
                    if (value == 6) {
                        return Entity1_11Types.EntityType.HUSK;
                    }
                }
            }
            if (type.is(Entity1_11Types.EntityType.HORSE)) {
                final Optional<Metadata> options = getById(metadata, 14);
                if (options.isPresent()) {
                    if ((int)options.get().getValue() == 0) {
                        return Entity1_11Types.EntityType.HORSE;
                    }
                    if ((int)options.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.DONKEY;
                    }
                    if ((int)options.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.MULE;
                    }
                    if ((int)options.get().getValue() == 3) {
                        return Entity1_11Types.EntityType.ZOMBIE_HORSE;
                    }
                    if ((int)options.get().getValue() == 4) {
                        return Entity1_11Types.EntityType.SKELETON_HORSE;
                    }
                }
            }
        }
        catch (Exception e) {
            if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("An error occurred with entity type rewriter");
                Via.getPlatform().getLogger().warning("Metadata: " + metadata);
                e.printStackTrace();
            }
        }
        return type;
    }
    
    public static Optional<Metadata> getById(final List<Metadata> metadatas, final int id) {
        for (final Metadata metadata : metadatas) {
            if (metadata.id() == id) {
                return Optional.of(metadata);
            }
        }
        return Optional.empty();
    }
}
