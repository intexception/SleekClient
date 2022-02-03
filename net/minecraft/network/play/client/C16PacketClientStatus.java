package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C16PacketClientStatus implements Packet<INetHandlerPlayServer>
{
    private EnumState status;
    
    public C16PacketClientStatus() {
    }
    
    public C16PacketClientStatus(final EnumState statusIn) {
        this.status = statusIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.status = buf.readEnumValue(EnumState.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.status);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processClientStatus(this);
    }
    
    public EnumState getStatus() {
        return this.status;
    }
    
    public enum EnumState
    {
        PERFORM_RESPAWN, 
        REQUEST_STATS, 
        OPEN_INVENTORY_ACHIEVEMENT;
    }
}
