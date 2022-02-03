package me.kansio.client.utils.network;

import net.minecraft.network.*;

public class TimedPacket
{
    private /* synthetic */ long time;
    private /* synthetic */ Packet<?> packet;
    
    public void setTime(final long llIIlIllIlIll) {
        this.time = llIIlIllIlIll;
    }
    
    public void setPacket(final Packet<?> llIIlIlIllIIl) {
        this.packet = llIIlIlIllIIl;
    }
    
    public void send() {
        PacketUtil.sendPacket(this.getPacket());
    }
    
    public long getTime() {
        return this.time;
    }
    
    public long postAddTime() {
        return System.currentTimeMillis() - this.time;
    }
    
    public TimedPacket(final Packet<?> llIIllIIIIIll, final long llIIllIIIIIlI) {
        this.time = llIIllIIIIIlI;
        this.packet = llIIllIIIIIll;
    }
    
    public void sendSilent() {
        PacketUtil.sendPacketNoEvent(this.getPacket());
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
}
