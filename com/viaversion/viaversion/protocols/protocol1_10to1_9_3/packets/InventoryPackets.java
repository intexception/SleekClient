package com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class InventoryPackets extends ItemRewriter<Protocol1_10To1_9_3_4>
{
    public InventoryPackets(final Protocol1_10To1_9_3_4 protocol) {
        super(protocol);
    }
    
    public void registerPackets() {
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        final boolean newItem = item.identifier() >= 213 && item.identifier() <= 217;
        if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }
}
