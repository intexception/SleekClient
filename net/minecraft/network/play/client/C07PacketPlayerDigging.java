package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class C07PacketPlayerDigging implements Packet<INetHandlerPlayServer>
{
    private BlockPos position;
    private EnumFacing facing;
    private Action status;
    
    public C07PacketPlayerDigging() {
    }
    
    public C07PacketPlayerDigging(final Action statusIn, final BlockPos posIn, final EnumFacing facingIn) {
        this.status = statusIn;
        this.position = posIn;
        this.facing = facingIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.status = buf.readEnumValue(Action.class);
        this.position = buf.readBlockPos();
        this.facing = EnumFacing.getFront(buf.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.status);
        buf.writeBlockPos(this.position);
        buf.writeByte(this.facing.getIndex());
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processPlayerDigging(this);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public Action getStatus() {
        return this.status;
    }
    
    public enum Action
    {
        START_DESTROY_BLOCK, 
        ABORT_DESTROY_BLOCK, 
        STOP_DESTROY_BLOCK, 
        DROP_ALL_ITEMS, 
        DROP_ITEM, 
        RELEASE_USE_ITEM;
    }
}
