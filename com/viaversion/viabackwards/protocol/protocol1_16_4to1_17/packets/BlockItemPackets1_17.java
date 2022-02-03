package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.*;

public final class BlockItemPackets1_17 extends ItemRewriter<Protocol1_16_4To1_17>
{
    public BlockItemPackets1_17(final Protocol1_16_4To1_17 protocol) {
        super(protocol);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_17.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_17.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
        blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
        this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_16_2>)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(wrapper -> BlockItemPackets1_17.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_16_2>)this.protocol).registerServerbound(ServerboundPackets1_16_2.CLICK_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT, Type.NOTHING);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    wrapper.write(Type.VAR_INT, 0);
                    BlockItemPackets1_17.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        ((Protocol<C1, C2, ServerboundPackets1_17, ServerboundPackets1_16_2>)this.protocol).registerServerbound(ServerboundPackets1_16_2.WINDOW_CONFIRMATION, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                short inventoryId;
                short confirmationId;
                boolean accepted;
                PacketWrapper pongPacket;
                this.handler(wrapper -> {
                    wrapper.cancel();
                    if (!(!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements())) {
                        inventoryId = wrapper.read((Type<Short>)Type.UNSIGNED_BYTE);
                        confirmationId = wrapper.read((Type<Short>)Type.SHORT);
                        accepted = wrapper.read((Type<Boolean>)Type.BOOLEAN);
                        if (inventoryId == 0 && accepted && wrapper.user().get(PingRequests.class).removeId(confirmationId)) {
                            pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
                            pongPacket.write(Type.INT, (int)confirmationId);
                            pongPacket.sendToServer(Protocol1_16_4To1_17.class);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.SPAWN_PARTICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                final int id;
                this.handler(wrapper -> {
                    id = wrapper.get((Type<Integer>)Type.INT, 0);
                    if (id == 16) {
                        wrapper.passthrough((Type<Object>)Type.FLOAT);
                        wrapper.passthrough((Type<Object>)Type.FLOAT);
                        wrapper.passthrough((Type<Object>)Type.FLOAT);
                        wrapper.passthrough((Type<Object>)Type.FLOAT);
                        wrapper.read((Type<Object>)Type.FLOAT);
                        wrapper.read((Type<Object>)Type.FLOAT);
                        wrapper.read((Type<Object>)Type.FLOAT);
                    }
                    else if (id == 37) {
                        wrapper.set(Type.INT, 0, -1);
                        wrapper.cancel();
                    }
                    return;
                });
                this.handler(BlockItemPackets1_17.this.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.UPDATE_LIGHT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                final EntityTracker tracker;
                final int startFromSection;
                final long[] skyLightMask;
                final long[] blockLightMask;
                final int cutSkyLightMask;
                final int cutBlockLightMask;
                final long[] emptySkyLightMask;
                final long[] emptyBlockLightMask;
                this.handler(wrapper -> {
                    tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                    startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
                    skyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    blockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
                    cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
                    wrapper.write(Type.VAR_INT, cutSkyLightMask);
                    wrapper.write(Type.VAR_INT, cutBlockLightMask);
                    emptySkyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    emptyBlockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection));
                    wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection));
                    this.writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
                    this.writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
                });
            }
            
            private void writeLightArrays(final PacketWrapper wrapper, final BitSet bitMask, final int cutBitMask, final int startFromSection, final int sectionHeight) throws Exception {
                wrapper.read((Type<Object>)Type.VAR_INT);
                final List<byte[]> light = new ArrayList<byte[]>();
                for (int i = 0; i < startFromSection; ++i) {
                    if (bitMask.get(i)) {
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                }
                for (int i = 0; i < 18; ++i) {
                    if (this.isSet(cutBitMask, i)) {
                        light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                }
                for (int i = startFromSection + 18; i < sectionHeight + 2; ++i) {
                    if (bitMask.get(i)) {
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                }
                for (final byte[] bytes : light) {
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
                }
            }
            
            private boolean isSet(final int mask, final int i) {
                return (mask & 1 << i) != 0x0;
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                final long chunkPos;
                final int chunkY;
                BlockChangeRecord[] array;
                BlockChangeRecord[] records;
                int length;
                int i = 0;
                BlockChangeRecord record;
                this.handler(wrapper -> {
                    chunkPos = wrapper.get((Type<Long>)Type.LONG, 0);
                    chunkY = (int)(chunkPos << 44 >> 44);
                    if (chunkY < 0 || chunkY > 15) {
                        wrapper.cancel();
                    }
                    else {
                        records = (array = wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY));
                        for (length = array.length; i < length; ++i) {
                            record = array[i];
                            record.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                final int y;
                this.handler(wrapper -> {
                    y = wrapper.get(Type.POSITION1_14, 0).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    }
                    else {
                        wrapper.set(Type.VAR_INT, 0, ((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(wrapper.get((Type<Integer>)Type.VAR_INT, 0)));
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                final EntityTracker tracker;
                final int currentWorldSectionHeight;
                final Chunk chunk;
                final int startFromSection;
                final ChunkSection[] sections;
                int i;
                ChunkSection section;
                int j;
                int old;
                this.handler(wrapper -> {
                    tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                    currentWorldSectionHeight = tracker.currentWorldSectionHeight();
                    chunk = wrapper.read((Type<Chunk>)new Chunk1_17Type(currentWorldSectionHeight));
                    wrapper.write(new Chunk1_16_2Type(), chunk);
                    startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
                    chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, startFromSection * 64 + 1024));
                    chunk.setBitmask(BlockItemPackets1_17.this.cutMask(chunk.getChunkMask(), startFromSection, false));
                    chunk.setChunkMask(null);
                    sections = Arrays.copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
                    chunk.setSections(sections);
                    for (i = 0; i < 16; ++i) {
                        section = sections[i];
                        if (section != null) {
                            for (j = 0; j < section.getPaletteSize(); ++j) {
                                old = section.getPaletteEntry(j);
                                section.setPaletteEntry(j, ((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                final int y;
                this.handler(wrapper -> {
                    y = wrapper.passthrough(Type.POSITION1_14).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                final int y;
                this.handler(wrapper -> {
                    y = wrapper.passthrough(Type.POSITION1_14).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17.MAP_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(wrapper -> wrapper.write(Type.BOOLEAN, true));
                this.map(Type.BOOLEAN);
                final boolean hasMarkers;
                this.handler(wrapper -> {
                    hasMarkers = wrapper.read((Type<Boolean>)Type.BOOLEAN);
                    if (!hasMarkers) {
                        wrapper.write(Type.VAR_INT, 0);
                    }
                    else {
                        MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper);
                    }
                });
            }
        });
    }
    
    private int cutLightMask(final long[] mask, final int startFromSection) {
        if (mask.length == 0) {
            return 0;
        }
        return this.cutMask(BitSet.valueOf(mask), startFromSection, true);
    }
    
    private int cutMask(final BitSet mask, final int startFromSection, final boolean lightMask) {
        int cutMask = 0;
        for (int to = startFromSection + (lightMask ? 18 : 16), i = startFromSection, j = 0; i < to; ++i, ++j) {
            if (mask.get(i)) {
                cutMask |= 1 << j;
            }
        }
        return cutMask;
    }
}
