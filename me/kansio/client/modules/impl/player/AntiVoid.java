package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.util.*;
import me.kansio.client.utils.block.*;
import com.google.common.eventbus.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import net.minecraft.block.*;

@ModuleData(name = "Anti Void", category = ModuleCategory.PLAYER, description = "Prevents you from falling into the void")
public class AntiVoid extends Module
{
    /* synthetic */ double prevX;
    private final /* synthetic */ ModeValue modeValue;
    /* synthetic */ double prevY;
    private final /* synthetic */ NumberValue fallDist;
    /* synthetic */ double prevZ;
    
    @Subscribe
    public void onUpdate(final UpdateEvent lllllllllllllllllllllIlIlllIlIll) {
        if (!(BlockUtil.getBlockAt(new BlockPos(AntiVoid.mc.thePlayer.posX, AntiVoid.mc.thePlayer.posY - 1.0, AntiVoid.mc.thePlayer.posZ)) instanceof BlockAir)) {
            this.prevX = AntiVoid.mc.thePlayer.posX;
            this.prevY = AntiVoid.mc.thePlayer.posY;
            this.prevZ = AntiVoid.mc.thePlayer.posZ;
        }
        if (this.shouldTeleportBack()) {
            AntiVoid.mc.thePlayer.setPositionAndUpdate(this.prevX, this.prevY, this.prevZ);
            AntiVoid.mc.thePlayer.motionZ = 0.0;
            AntiVoid.mc.thePlayer.motionX = 0.0;
            AntiVoid.mc.thePlayer.motionY = 0.0;
        }
    }
    
    public AntiVoid() {
        this.prevX = 0.0;
        this.prevY = 0.0;
        this.prevZ = 0.0;
        this.modeValue = new ModeValue("Mode", this, new String[] { "Basic", "Blink" });
        this.fallDist = new NumberValue("Fall Distance", this, (T)7, (T)0, (T)30, (T)1);
    }
    
    public boolean shouldTeleportBack() {
        final Flight lllllllllllllllllllllIlIlllIIIlI = (Flight)Client.getInstance().getModuleManager().getModuleByName("Flight");
        if (lllllllllllllllllllllIlIlllIIIlI.isToggled()) {
            return false;
        }
        if (AntiVoid.mc.thePlayer.onGround) {
            return false;
        }
        if (AntiVoid.mc.thePlayer.isCollidedVertically) {
            return false;
        }
        if (AntiVoid.mc.thePlayer.fallDistance >= this.fallDist.getValue().doubleValue()) {
            for (double lllllllllllllllllllllIlIlllIIlII = AntiVoid.mc.thePlayer.posY + 1.0; lllllllllllllllllllllIlIlllIIlII > 0.0; --lllllllllllllllllllllIlIlllIIlII) {
                final Block lllllllllllllllllllllIlIlllIIlIl = BlockUtil.getBlockAt(new BlockPos(AntiVoid.mc.thePlayer.posX, AntiVoid.mc.thePlayer.posY - lllllllllllllllllllllIlIlllIIlII, AntiVoid.mc.thePlayer.posZ));
                if (!(lllllllllllllllllllllIlIlllIIlIl instanceof BlockAir)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
