package me.kansio.client.utils.network;

import net.minecraft.network.*;
import net.minecraft.client.*;

public class PacketSleepThread extends Thread
{
    public PacketSleepThread(final Packet lIlIIIlIIlllll, final long lIlIIIlIIllllI) {
        super(() -> {
            sleep_ms(lIlIIIlIIllllI);
            if (Minecraft.getMinecraft().thePlayer != null) {
                PacketUtil.sendPacketNoEvent(lIlIIIlIIlllll);
            }
        });
    }
    
    public static void delayPacket(final Packet lIlIIIlIIlIIII, final long lIlIIIlIIIllll) {
        new PacketSleepThread(lIlIIIlIIlIIII, lIlIIIlIIIllll).start();
    }
    
    static void sleep_ms(final long lIlIIIlIIlIlll) {
        try {
            Thread.sleep(lIlIIIlIIlIlll);
        }
        catch (InterruptedException lIlIIIlIIllIII) {
            lIlIIIlIIllIII.printStackTrace();
        }
    }
}
