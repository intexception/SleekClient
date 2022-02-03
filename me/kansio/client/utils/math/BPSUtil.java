package me.kansio.client.utils.math;

import me.kansio.client.utils.*;
import net.minecraft.entity.player.*;

public class BPSUtil extends Util
{
    public static double getBPS() {
        return BPSUtil.mc.thePlayer.getDistance(BPSUtil.mc.thePlayer.lastTickPosX, BPSUtil.mc.thePlayer.lastTickPosY, BPSUtil.mc.thePlayer.lastTickPosZ) * (BPSUtil.mc.timer.ticksPerSecond * BPSUtil.mc.timer.timerSpeed);
    }
    
    public static double getBPS(final EntityPlayer lIIIIlIlllIIII) {
        return lIIIIlIlllIIII.getDistance(lIIIIlIlllIIII.lastTickPosX, lIIIIlIlllIIII.posY, lIIIIlIlllIIII.lastTickPosZ) * (BPSUtil.mc.timer.ticksPerSecond * BPSUtil.mc.timer.timerSpeed);
    }
}
