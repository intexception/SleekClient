package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_16_2 extends ItemRewriter<Protocol1_16_1To1_16_2>
{
    public BlockItemPackets1_16_2(final Protocol1_16_1To1_16_2 protocol) {
        super(protocol);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<ClientboundPackets1_16_2, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_16_2.UNLOCK_RECIPES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough((Type<Object>)Type.VAR_INT);
                    wrapper.passthrough((Type<Object>)Type.BOOLEAN);
                    wrapper.passthrough((Type<Object>)Type.BOOLEAN);
                    wrapper.passthrough((Type<Object>)Type.BOOLEAN);
                    wrapper.passthrough((Type<Object>)Type.BOOLEAN);
                    wrapper.read((Type<Object>)Type.BOOLEAN);
                    wrapper.read((Type<Object>)Type.BOOLEAN);
                    wrapper.read((Type<Object>)Type.BOOLEAN);
                    wrapper.read((Type<Object>)Type.BOOLEAN);
                });
            }
        });
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        ((AbstractProtocol<ClientboundPackets1_16_2, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                final Chunk chunk;
                int i;
                ChunkSection section;
                int j;
                int old;
                final Iterator<CompoundTag> iterator;
                CompoundTag blockEntity;
                this.handler(wrapper -> {
                    chunk = wrapper.read((Type<Chunk>)new Chunk1_16_2Type());
                    wrapper.write(new Chunk1_16Type(), chunk);
                    chunk.setIgnoreOldLightData(true);
                    for (i = 0; i < chunk.getSections().length; ++i) {
                        section = chunk.getSections()[i];
                        if (section != null) {
                            for (j = 0; j < section.getPaletteSize(); ++j) {
                                old = section.getPaletteEntry(j);
                                section.setPaletteEntry(j, ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                    chunk.getBlockEntities().iterator();
                    while (iterator.hasNext()) {
                        blockEntity = iterator.next();
                        if (blockEntity != null) {
                            BlockItemPackets1_16_2.this.handleBlockEntity(blockEntity);
                        }
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_16_2, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> BlockItemPackets1_16_2.this.handleBlockEntity(wrapper.passthrough(Type.NBT)));
            }
        });
        ((AbstractProtocol<ClientboundPackets1_16_2, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                final long chunkPosition;
                final int chunkX;
                final int chunkY;
                final int chunkZ;
                final BlockChangeRecord[] blockChangeRecord;
                int i;
                BlockChangeRecord record;
                int blockId;
                this.handler(wrapper -> {
                    chunkPosition = wrapper.read((Type<Long>)Type.LONG);
                    wrapper.read((Type<Object>)Type.BOOLEAN);
                    chunkX = (int)(chunkPosition >> 42);
                    chunkY = (int)(chunkPosition << 44 >> 44);
                    chunkZ = (int)(chunkPosition << 22 >> 42);
                    wrapper.write(Type.INT, chunkX);
                    wrapper.write(Type.INT, chunkZ);
                    blockChangeRecord = wrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                    wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecord);
                    for (i = 0; i < blockChangeRecord.length; ++i) {
                        record = blockChangeRecord[i];
                        blockId = ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId());
                        blockChangeRecord[i] = new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_16>)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(wrapper -> BlockItemPackets1_16_2.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
    }
    
    private void handleBlockEntity(final CompoundTag tag) {
        final StringTag idTag = tag.get("id");
        if (idTag == null) {
            return;
        }
        if (idTag.getValue().equals("minecraft:skull")) {
            final Tag skullOwnerTag = tag.get("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            final CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            if (!skullOwnerCompoundTag.contains("Id")) {
                return;
            }
            final CompoundTag properties = skullOwnerCompoundTag.get("Properties");
            if (properties == null) {
                return;
            }
            final ListTag textures = properties.get("textures");
            if (textures == null) {
                return;
            }
            final CompoundTag first = (textures.size() > 0) ? textures.get(0) : null;
            if (first == null) {
                return;
            }
            final int hashCode = first.get("Value").getValue().hashCode();
            final int[] uuidIntArray = { hashCode, 0, 0, 0 };
            skullOwnerCompoundTag.put("Id", new IntArrayTag(uuidIntArray));
        }
    }
}
