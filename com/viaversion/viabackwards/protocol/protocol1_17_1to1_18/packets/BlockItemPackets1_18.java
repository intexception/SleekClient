package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.rewriter.*;

public final class BlockItemPackets1_18 extends ItemRewriter<Protocol1_17_1To1_18>
{
    public BlockItemPackets1_18(final Protocol1_17_1To1_18 protocol) {
        super(protocol);
    }
    
    @Override
    protected void registerPackets() {
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_18.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_18.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<ClientboundPackets1_18, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_18.EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14);
                this.map(Type.INT);
                final int id;
                final int data;
                this.handler(wrapper -> {
                    id = wrapper.get((Type<Integer>)Type.INT, 0);
                    data = wrapper.get((Type<Integer>)Type.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Type.INT, 1, ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewItemId(data));
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<ClientboundPackets1_18, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PARTICLE, new PacketRemapper() {
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
                int blockState;
                ParticleMappings mappings;
                int data;
                int newId;
                this.handler(wrapper -> {
                    id = wrapper.get((Type<Integer>)Type.INT, 0);
                    if (id == 3) {
                        blockState = wrapper.read((Type<Integer>)Type.VAR_INT);
                        if (blockState == 7786) {
                            wrapper.set(Type.INT, 0, 3);
                        }
                        else {
                            wrapper.set(Type.INT, 0, 2);
                        }
                    }
                    else {
                        mappings = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getParticleMappings();
                        if (mappings.isBlockParticle(id)) {
                            data = wrapper.passthrough((Type<Integer>)Type.VAR_INT);
                            wrapper.set(Type.VAR_INT, 0, ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewBlockStateId(data));
                        }
                        else if (mappings.isItemParticle(id)) {
                            BlockItemPackets1_18.this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                        newId = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewParticleId(id);
                        if (newId != id) {
                            wrapper.set(Type.INT, 0, newId);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_18, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_18.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                final int id;
                final CompoundTag tag;
                final int mappedId;
                String identifier;
                CompoundTag compoundTag = null;
                CompoundTag newTag;
                Position pos;
                final StringTag tag2;
                final Object o2;
                final String tagName;
                this.handler(wrapper -> {
                    id = wrapper.read((Type<Integer>)Type.VAR_INT);
                    tag = wrapper.read(Type.NBT);
                    mappedId = BlockEntityIds.mappedId(id);
                    if (mappedId == -1) {
                        wrapper.cancel();
                    }
                    else {
                        identifier = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(id);
                        if (identifier == null) {
                            wrapper.cancel();
                        }
                        else {
                            if (tag == null) {
                                // new(com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag.class)
                                new CompoundTag();
                            }
                            else {
                                compoundTag = tag;
                            }
                            newTag = compoundTag;
                            pos = wrapper.get(Type.POSITION1_14, 0);
                            new StringTag("minecraft:" + identifier);
                            ((CompoundTag)o2).put(tagName, tag2);
                            newTag.put("x", new IntTag(pos.x()));
                            newTag.put("y", new IntTag(pos.y()));
                            newTag.put("z", new IntTag(pos.z()));
                            BlockItemPackets1_18.this.handleSpawner(id, newTag);
                            wrapper.write(Type.UNSIGNED_BYTE, (short)mappedId);
                            wrapper.write(Type.NBT, newTag);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_18, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_18.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                final EntityTracker tracker;
                final Chunk1_18Type chunkType;
                final Chunk oldChunk;
                final ChunkSection[] sections;
                final BitSet mask;
                final int[] biomeData;
                int biomeIndex;
                int j;
                ChunkSection section;
                DataPalette biomePalette;
                int i;
                final Object o;
                final int n;
                final ArrayList<CompoundTag> blockEntityTags;
                final Iterator<BlockEntity> iterator;
                BlockEntity blockEntity;
                String id;
                CompoundTag tag;
                final StringTag tag2;
                final Object o2;
                final String tagName;
                final Chunk chunk;
                final PacketWrapper lightPacket;
                final int skyLightLength;
                int k;
                final int blockLightLength;
                int l;
                this.handler(wrapper -> {
                    tracker = ((EntityRewriter<Protocol>)((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getEntityRewriter()).tracker(wrapper.user());
                    chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
                    oldChunk = wrapper.read((Type<Chunk>)chunkType);
                    sections = oldChunk.getSections();
                    mask = new BitSet(oldChunk.getSections().length);
                    biomeData = new int[sections.length * 64];
                    biomeIndex = 0;
                    for (j = 0; j < sections.length; ++j) {
                        section = sections[j];
                        biomePalette = section.palette(PaletteType.BIOMES);
                        for (i = 0; i < 64; ++i) {
                            biomeIndex++;
                            o[n] = biomePalette.idAt(i);
                        }
                        if (section.getNonAirBlocksCount() == 0) {
                            sections[j] = null;
                        }
                        else {
                            mask.set(j);
                        }
                    }
                    blockEntityTags = new ArrayList<CompoundTag>(oldChunk.blockEntities().size());
                    oldChunk.blockEntities().iterator();
                    while (iterator.hasNext()) {
                        blockEntity = iterator.next();
                        id = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(blockEntity.typeId());
                        if (id == null) {
                            continue;
                        }
                        else {
                            if (blockEntity.tag() != null) {
                                tag = blockEntity.tag();
                                BlockItemPackets1_18.this.handleSpawner(blockEntity.typeId(), tag);
                            }
                            else {
                                tag = new CompoundTag();
                            }
                            blockEntityTags.add(tag);
                            tag.put("x", new IntTag((oldChunk.getX() << 4) + blockEntity.sectionX()));
                            tag.put("y", new IntTag(blockEntity.y()));
                            tag.put("z", new IntTag((oldChunk.getZ() << 4) + blockEntity.sectionZ()));
                            new StringTag("minecraft:" + id);
                            ((CompoundTag)o2).put(tagName, tag2);
                        }
                    }
                    chunk = new BaseChunk(oldChunk.getX(), oldChunk.getZ(), true, false, mask, oldChunk.getSections(), biomeData, oldChunk.getHeightMap(), blockEntityTags);
                    wrapper.write(new Chunk1_17Type(tracker.currentWorldSectionHeight()), chunk);
                    lightPacket = wrapper.create(ClientboundPackets1_17_1.UPDATE_LIGHT);
                    lightPacket.write(Type.VAR_INT, chunk.getX());
                    lightPacket.write(Type.VAR_INT, chunk.getZ());
                    lightPacket.write(Type.BOOLEAN, (Boolean)wrapper.read((Type<T>)Type.BOOLEAN));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[])(Object)wrapper.read((Type<T>)Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[])(Object)wrapper.read((Type<T>)Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[])(Object)wrapper.read((Type<T>)Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[])(Object)wrapper.read((Type<T>)Type.LONG_ARRAY_PRIMITIVE));
                    skyLightLength = wrapper.read((Type<Integer>)Type.VAR_INT);
                    lightPacket.write(Type.VAR_INT, skyLightLength);
                    for (k = 0; k < skyLightLength; ++k) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, (byte[])(Object)wrapper.read((Type<T>)Type.BYTE_ARRAY_PRIMITIVE));
                    }
                    blockLightLength = wrapper.read((Type<Integer>)Type.VAR_INT);
                    lightPacket.write(Type.VAR_INT, blockLightLength);
                    for (l = 0; l < blockLightLength; ++l) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, (byte[])(Object)wrapper.read((Type<T>)Type.BYTE_ARRAY_PRIMITIVE));
                    }
                    lightPacket.send(Protocol1_17_1To1_18.class);
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_18, C2, S1, S2>)this.protocol).cancelClientbound(ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
    }
    
    private void handleSpawner(final int typeId, final CompoundTag tag) {
        if (typeId == 8) {
            final CompoundTag spawnData = tag.get("SpawnData");
            final CompoundTag entity;
            if (spawnData != null && (entity = spawnData.get("entity")) != null) {
                tag.put("SpawnData", entity);
            }
        }
    }
}
