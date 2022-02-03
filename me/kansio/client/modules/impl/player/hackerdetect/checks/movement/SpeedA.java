package me.kansio.client.modules.impl.player.hackerdetect.checks.movement;

import me.kansio.client.modules.impl.player.hackerdetect.checks.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.modules.impl.player.*;
import me.kansio.client.*;

public class SpeedA extends Check
{
    @Override
    public String name() {
        return "Speed (Check A)";
    }
    
    @Override
    public void onUpdate() {
        if (Minecraft.getMinecraft().thePlayer.ticksExisted > 20) {
            for (final EntityPlayer lIllIIIlIIII : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (lIllIIIlIIII.ticksExisted < 20) {
                    continue;
                }
                if (lIllIIIlIIII.fallDistance > 20.0f) {
                    continue;
                }
                if (lIllIIIlIIII.hurtTime != 0) {
                    continue;
                }
                if (BPSUtil.getBPS(lIllIIIlIIII) > 20.0 && BPSUtil.getBPS(lIllIIIlIIII) < 30.0) {
                    HackerDetect.getInstance().getViolations().put(lIllIIIlIIII, HackerDetect.getInstance().getViolations().getOrDefault(lIllIIIlIIII, 1));
                    if (HackerDetect.getInstance().getViolations().get(lIllIIIlIIII) <= 60 || Client.getInstance().getTargetManager().isTarget(lIllIIIlIIII)) {
                        continue;
                    }
                    this.flag(lIllIIIlIIII);
                    Client.getInstance().getTargetManager().getTarget().add(lIllIIIlIIII.getName());
                }
                else {
                    HackerDetect.getInstance().getViolations().put(lIllIIIlIIII, HackerDetect.getInstance().getViolations().getOrDefault(lIllIIIlIIII, 1));
                }
            }
        }
    }
}
