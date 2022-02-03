package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets
{
    public static void register(final Protocol1_13To1_12_2 protocol) {
        final MetadataRewriter1_13To1_12_2 metadataRewriter = protocol.get(MetadataRewriter1_13To1_12_2.class);
        ((AbstractProtocol<ClientboundPackets1_12_1, C2, S1, S2>)protocol).registerClientbound(ClientboundPackets1_12_1.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int entityId = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                        final byte type = wrapper.get((Type<Byte>)Type.BYTE, 0);
                        final Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
                        if (entType == null) {
                            return;
                        }
                        wrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(entityId, entType);
                        if (entType.is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            final int oldId = wrapper.get((Type<Integer>)Type.INT, 0);
                            final int combined = (oldId & 0xFFF) << 4 | (oldId >> 12 & 0xF);
                            wrapper.set(Type.INT, 0, WorldPackets.toNewId(combined));
                        }
                        if (entType.is(Entity1_13Types.EntityType.ITEM_FRAME)) {
                            int data = wrapper.get((Type<Integer>)Type.INT, 0);
                            switch (data) {
                                case 0: {
                                    data = 3;
                                    break;
                                }
                                case 1: {
                                    data = 4;
                                    break;
                                }
                                case 3: {
                                    data = 5;
                                    break;
                                }
                            }
                            wrapper.set(Type.INT, 0, data);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_12_1, C2, S1, S2>)protocol).registerClientbound(ClientboundPackets1_12_1.SPAWN_MOB, new PacketRemapper() {
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
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        ((AbstractProtocol<ClientboundPackets1_12_1, C2, S1, S2>)protocol).registerClientbound(ClientboundPackets1_12_1.SPAWN_PLAYER, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_12_1.DESTROY_ENTITIES);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_12_1.ENTITY_METADATA, Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
    }
}
