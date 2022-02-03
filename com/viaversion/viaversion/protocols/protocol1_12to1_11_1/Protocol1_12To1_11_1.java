package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_12To1_11_1 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12>
{
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_12To1_11_1() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
        this.metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_12To1_11_1.this.metadataRewriter.objectTrackerHandler());
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
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
                this.map(Types1_12.METADATA_LIST);
                this.handler(Protocol1_12To1_11_1.this.metadataRewriter.trackerAndRewriterHandler(Types1_12.METADATA_LIST));
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        if (!Via.getConfig().is1_12NBTArrayFix()) {
                            return;
                        }
                        try {
                            final JsonElement obj = Protocol1_9To1_8.FIX_JSON.transform(null, wrapper.passthrough(Type.COMPONENT).toString());
                            TranslateRewriter.toClient(obj, wrapper.user());
                            ChatItemRewriter.toClient(obj, wrapper.user());
                            wrapper.set(Type.COMPONENT, 0, obj);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
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
                        for (int i = 0; i < chunk.getSections().length; ++i) {
                            final ChunkSection section = chunk.getSections()[i];
                            if (section != null) {
                                for (int y = 0; y < 16; ++y) {
                                    for (int z = 0; z < 16; ++z) {
                                        for (int x = 0; x < 16; ++x) {
                                            final int block = section.getBlockWithoutData(x, y, z);
                                            if (block == 26) {
                                                final CompoundTag tag = new CompoundTag();
                                                tag.put("color", new IntTag(14));
                                                tag.put("x", new IntTag(x + (chunk.getX() << 4)));
                                                tag.put("y", new IntTag(y + (i << 4)));
                                                tag.put("z", new IntTag(z + (chunk.getZ() << 4)));
                                                tag.put("id", new StringTag("minecraft:bed"));
                                                chunk.getBlockEntities().add(tag);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        this.metadataRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.metadataRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
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
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_12>)this).cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_12>)this).registerServerbound(ServerboundPackets1_12.CLIENT_SETTINGS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final String locale = wrapper.get(Type.STRING, 0);
                        if (locale.length() > 7) {
                            wrapper.set(Type.STRING, 0, locale.substring(0, 7));
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_12>)this).cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_12>)this).cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
    }
    
    private int getNewSoundId(final int id) {
        int newId = id;
        if (id >= 26) {
            newId += 2;
        }
        if (id >= 70) {
            newId += 4;
        }
        if (id >= 74) {
            ++newId;
        }
        if (id >= 143) {
            newId += 3;
        }
        if (id >= 185) {
            ++newId;
        }
        if (id >= 263) {
            newId += 7;
        }
        if (id >= 301) {
            newId += 33;
        }
        if (id >= 317) {
            newId += 2;
        }
        if (id >= 491) {
            newId += 3;
        }
        return newId;
    }
    
    @Override
    public void register(final ViaProviders providers) {
        providers.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_12Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
