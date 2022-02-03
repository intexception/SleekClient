package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S30PacketWindowItems implements Packet<INetHandlerPlayClient>
{
    private int windowId;
    private ItemStack[] itemStacks;
    
    public S30PacketWindowItems() {
    }
    
    public S30PacketWindowItems(final int windowIdIn, final List<ItemStack> p_i45186_2_) {
        this.windowId = windowIdIn;
        this.itemStacks = new ItemStack[p_i45186_2_.size()];
        for (int i = 0; i < this.itemStacks.length; ++i) {
            final ItemStack itemstack = p_i45186_2_.get(i);
            this.itemStacks[i] = ((itemstack == null) ? null : itemstack.copy());
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.windowId = buf.readUnsignedByte();
        final int i = buf.readShort();
        this.itemStacks = new ItemStack[i];
        for (int j = 0; j < i; ++j) {
            this.itemStacks[j] = buf.readItemStackFromBuffer();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
        buf.writeShort(this.itemStacks.length);
        for (final ItemStack itemstack : this.itemStacks) {
            buf.writeItemStackToBuffer(itemstack);
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleWindowItems(this);
    }
    
    public int func_148911_c() {
        return this.windowId;
    }
    
    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }
}
