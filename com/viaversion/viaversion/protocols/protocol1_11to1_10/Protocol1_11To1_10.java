package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_11To1_10 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
{
    private static final ValueTransformer<Float, Short> toOldByte;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_11To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityRewriter = new MetadataRewriter1_11To1_10(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_11To1_10.this.entityRewriter.objectTrackerHandler());
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE, Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int entityId = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                        final int type = wrapper.get((Type<Integer>)Type.VAR_INT, 1);
                        final Entity1_11Types.EntityType entType = MetadataRewriter1_11To1_10.rewriteEntityType(type, wrapper.get(Types1_9.METADATA_LIST, 0));
                        if (entType != null) {
                            wrapper.set(Type.VAR_INT, 1, entType.getId());
                            wrapper.user().getEntityTracker(Protocol1_11To1_10.class).addEntity(entityId, entType);
                            Protocol1_11To1_10.this.entityRewriter.handleMetadata(entityId, wrapper.get(Types1_9.METADATA_LIST, 0), wrapper.user());
                        }
                    }
                });
            }
        });
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.VAR_INT, 1);
                    }
                });
            }
        });
        this.entityRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.ENTITY_TELEPORT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int entityID = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                        if (Via.getConfig().isHologramPatch()) {
                            final EntityTracker1_11 tracker = wrapper.user().getEntityTracker(Protocol1_11To1_10.class);
                            if (tracker.isHologram(entityID)) {
                                Double newValue = wrapper.get((Type<Double>)Type.DOUBLE, 1);
                                newValue -= Via.getConfig().getHologramYOffset();
                                wrapper.set(Type.DOUBLE, 1, newValue);
                            }
                        }
                    }
                });
            }
        });
        this.entityRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int action = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                        if (action >= 2) {
                            wrapper.set(Type.VAR_INT, 0, action + 1);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.BLOCK_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper actionWrapper) throws Exception {
                        if (Via.getConfig().isPistonAnimationPatch()) {
                            final int id = actionWrapper.get((Type<Integer>)Type.VAR_INT, 0);
                            if (id == 33 || id == 29) {
                                actionWrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final CompoundTag tag = wrapper.get(Type.NBT, 0);
                        if (wrapper.get((Type<Short>)Type.UNSIGNED_BYTE, 0) == 1) {
                            EntityIdRewriter.toClientSpawner(tag);
                        }
                        if (tag.contains("id")) {
                            tag.get("id").setValue(BlockEntityRewriter.toNewIdentifier((String)tag.get("id").getValue()));
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        final Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        final Chunk chunk = wrapper.passthrough((Type<Chunk>)type);
                        wrapper.clearInputBuffer();
                        if (chunk.getBlockEntities() == null) {
                            return;
                        }
                        for (final CompoundTag tag : chunk.getBlockEntities()) {
                            if (tag.contains("id")) {
                                final String identifier = tag.get("id").getValue();
                                if (identifier.equals("MobSpawner")) {
                                    EntityIdRewriter.toClientSpawner(tag);
                                }
                                tag.get("id").setValue(BlockEntityRewriter.toNewIdentifier(identifier));
                            }
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                final ClientWorld clientChunks;
                final int dimensionId;
                this.handler(wrapper -> {
                    clientChunks = wrapper.user().get(ClientWorld.class);
                    dimensionId = wrapper.get((Type<Integer>)Type.INT, 1);
                    clientChunks.setEnvironment(dimensionId);
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                final ClientWorld clientWorld;
                final int dimensionId;
                this.handler(wrapper -> {
                    clientWorld = wrapper.user().get(ClientWorld.class);
                    dimensionId = wrapper.get((Type<Integer>)Type.INT, 0);
                    clientWorld.setEnvironment(dimensionId);
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                final int effectID;
                int data;
                boolean isInstant;
                Pair<Integer, Boolean> newData;
                int data2;
                this.handler(packetWrapper -> {
                    effectID = packetWrapper.get((Type<Integer>)Type.INT, 0);
                    if (effectID == 2002) {
                        data = packetWrapper.get((Type<Integer>)Type.INT, 1);
                        isInstant = false;
                        newData = PotionColorMapping.getNewData(data);
                        if (newData == null) {
                            Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + data + ")");
                            data2 = 0;
                        }
                        else {
                            data2 = newData.key();
                            isInstant = newData.value();
                        }
                        if (isInstant) {
                            packetWrapper.set(Type.INT, 0, 2007);
                        }
                        packetWrapper.set(Type.INT, 1, data2);
                    }
                });
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_9_3>)this).registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map((Type<Object>)Type.FLOAT, (ValueTransformer<Object, Object>)Protocol1_11To1_10.toOldByte);
                this.map((Type<Object>)Type.FLOAT, (ValueTransformer<Object, Object>)Protocol1_11To1_10.toOldByte);
                this.map((Type<Object>)Type.FLOAT, (ValueTransformer<Object, Object>)Protocol1_11To1_10.toOldByte);
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_9_3>)this).registerServerbound(ServerboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final String msg = wrapper.get(Type.STRING, 0);
                        if (msg.length() > 100) {
                            wrapper.set(Type.STRING, 0, msg.substring(0, 100));
                        }
                    }
                });
            }
        });
    }
    
    private int getNewSoundId(int id) {
        if (id == 196) {
            return -1;
        }
        if (id >= 85) {
            id += 2;
        }
        if (id >= 176) {
            ++id;
        }
        if (id >= 197) {
            id += 8;
        }
        if (id >= 207) {
            --id;
        }
        if (id >= 279) {
            id += 9;
        }
        if (id >= 296) {
            ++id;
        }
        if (id >= 390) {
            id += 4;
        }
        if (id >= 400) {
            id += 3;
        }
        if (id >= 450) {
            ++id;
        }
        if (id >= 455) {
            ++id;
        }
        if (id >= 470) {
            ++id;
        }
        return id;
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_11(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    static {
        toOldByte = new ValueTransformer<Float, Short>() {
            @Override
            public Short transform(final PacketWrapper wrapper, final Float inputValue) throws Exception {
                return (short)(inputValue * 16.0f);
            }
        };
    }
}
