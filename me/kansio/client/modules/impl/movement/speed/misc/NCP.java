package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;

public class NCP extends SpeedMode
{
    private /* synthetic */ int stageOG;
    private /* synthetic */ double moveSpeed;
    private /* synthetic */ int stage;
    private /* synthetic */ double lastDist;
    
    private double getBaseMoveSpeed() {
        double lIIllIIlIIllI = 0.2873;
        if (NCP.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int lIIllIIlIlIII = NCP.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            lIIllIIlIIllI *= 1.0 + 0.2 * lIIllIIlIlIII;
        }
        return lIIllIIlIIllI;
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIIllIIllllIl) {
        final boolean lIIllIIllllII = NCP.mc.thePlayer.ticksExisted % 2 == 0;
        this.lastDist = Math.sqrt((NCP.mc.thePlayer.posX - NCP.mc.thePlayer.prevPosX) * (NCP.mc.thePlayer.posX - NCP.mc.thePlayer.prevPosX) + (NCP.mc.thePlayer.posZ - NCP.mc.thePlayer.prevPosZ) * (NCP.mc.thePlayer.posZ - NCP.mc.thePlayer.prevPosZ));
        if (this.lastDist > 5.0) {
            this.lastDist = 0.0;
        }
    }
    
    @Override
    public void onEnable() {
        if (NCP.mc.thePlayer == null) {
            return;
        }
        this.lastDist = 0.0;
        this.moveSpeed = 0.0;
    }
    
    @Override
    public void onMove(final MoveEvent lIIllIIlIllII) {
        switch (this.stage) {
            case 0: {
                ++this.stage;
                this.lastDist = 0.0;
                break;
            }
            case 2: {
                double lIIllIIllIIII = 0.4025;
                if ((NCP.mc.thePlayer.moveForward != 0.0f || NCP.mc.thePlayer.moveStrafing != 0.0f) && NCP.mc.thePlayer.onGround) {
                    if (NCP.mc.thePlayer.isPotionActive(Potion.jump)) {
                        lIIllIIllIIII += (NCP.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                    }
                    lIIllIIlIllII.setMotionY(NCP.mc.thePlayer.motionY = lIIllIIllIIII);
                    this.moveSpeed *= 2.0;
                    break;
                }
                break;
            }
            case 3: {
                this.moveSpeed = this.lastDist - 0.7 * (this.lastDist - this.getBaseMoveSpeed());
                break;
            }
            default: {
                if ((NCP.mc.theWorld.getCollidingBoundingBoxes(NCP.mc.thePlayer, NCP.mc.thePlayer.getEntityBoundingBox().offset(0.0, NCP.mc.thePlayer.motionY, 0.0)).size() > 0 || NCP.mc.thePlayer.isCollidedVertically) && this.stage > 0) {
                    this.stage = ((NCP.mc.thePlayer.moveForward != 0.0f || NCP.mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                break;
            }
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        PlayerUtil.setMotion(lIIllIIlIllII, this.moveSpeed);
        ++this.stage;
    }
    
    @Override
    public void onPacket(final PacketEvent lIIllIIllIlII) {
        if (lIIllIIllIlII.getPacket() instanceof S08PacketPlayerPosLook) {
            this.lastDist = 0.0;
        }
    }
    
    public NCP() {
        super("NCP");
        this.stage = 1;
        this.stageOG = 1;
    }
}
