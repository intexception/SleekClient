package me.kansio.client.event.impl;

import net.minecraft.network.*;
import me.kansio.client.event.*;

public class PacketEvent extends Event
{
    private /* synthetic */ Packet packet;
    private final /* synthetic */ PacketDirection packetDirection;
    
    public <T extends Packet> T getPacket() {
        return (T)this.packet;
    }
    
    public PacketEvent(final PacketDirection lIIIlIIlIIIll, final Packet lIIIlIIIlllll) {
        this.packetDirection = lIIIlIIlIIIll;
        this.packet = lIIIlIIIlllll;
    }
    
    public void setPacket(final Packet lIIIlIIIlIlIl) {
        this.packet = lIIIlIIIlIlIl;
    }
    
    public PacketDirection getPacketDirection() {
        return this.packetDirection;
    }
    
    public Class getPacketClass() {
        return this.packet.getClass();
    }
}
