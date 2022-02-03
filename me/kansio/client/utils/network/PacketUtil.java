package me.kansio.client.utils.network;

import me.kansio.client.utils.*;
import net.minecraft.network.*;

public class PacketUtil extends Util
{
    public static void sendPacket(final Packet lllllllllllllllllllllIllIlIllIlI) {
        PacketUtil.mc.getNetHandler().addToSendQueue(lllllllllllllllllllllIllIlIllIlI);
    }
    
    public static void sendPacketNoEvent(final Packet lllllllllllllllllllllIllIlIlIlll) {
        PacketUtil.mc.getNetHandler().addToSendQueueNoEvent(lllllllllllllllllllllIllIlIlIlll);
    }
    
    public static void sendPacketNoEvent(final int lllllllllllllllllllllIllIlIIIllI, final Packet lllllllllllllllllllllIllIlIIIlIl) {
        for (int lllllllllllllllllllllIllIlIIlIIl = 0; lllllllllllllllllllllIllIlIIlIIl < lllllllllllllllllllllIllIlIIIllI; ++lllllllllllllllllllllIllIlIIlIIl) {
            PacketUtil.mc.getNetHandler().addToSendQueueNoEvent(lllllllllllllllllllllIllIlIIIlIl);
        }
    }
    
    public static void sendPacket(final int lllllllllllllllllllllIllIlIIllll, final Packet lllllllllllllllllllllIllIlIIlllI) {
        for (int lllllllllllllllllllllIllIlIlIIlI = 0; lllllllllllllllllllllIllIlIlIIlI < lllllllllllllllllllllIllIlIIllll; ++lllllllllllllllllllllIllIlIlIIlI) {
            PacketUtil.mc.getNetHandler().addToSendQueue(lllllllllllllllllllllIllIlIIlllI);
        }
    }
}
