package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public final class InventoryPackets extends ItemRewriter<Protocol1_18To1_17_1>
{
    public InventoryPackets(final Protocol1_18To1_17_1 protocol) {
        super(protocol);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_17_1.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_17_1.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_17_1.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot1_17_1(ClientboundPackets1_17_1.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_17_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_17_1.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        ((AbstractProtocol<ClientboundPackets1_17_1, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17_1.EFFECT, new PacketRemapper() {
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
                        wrapper.set(Type.INT, 1, ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewItemId(data));
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17_1, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17_1.SPAWN_PARTICLE, new PacketRemapper() {
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
                ParticleMappings mappings;
                int data;
                int newId;
                this.handler(wrapper -> {
                    id = wrapper.get((Type<Integer>)Type.INT, 0);
                    if (id == 2) {
                        wrapper.set(Type.INT, 0, 3);
                        wrapper.write(Type.VAR_INT, 7754);
                    }
                    else if (id == 3) {
                        wrapper.write(Type.VAR_INT, 7786);
                    }
                    else {
                        mappings = ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getParticleMappings();
                        if (mappings.isBlockParticle(id)) {
                            data = wrapper.passthrough((Type<Integer>)Type.VAR_INT);
                            wrapper.set(Type.VAR_INT, 0, ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(data));
                        }
                        else if (mappings.isItemParticle(id)) {
                            InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                        newId = ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewParticleId(id);
                        if (newId != id) {
                            wrapper.set(Type.INT, 0, newId);
                        }
                    }
                });
            }
        });
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17_1.DECLARE_RECIPES);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }
}
