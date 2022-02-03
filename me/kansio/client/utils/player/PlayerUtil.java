package me.kansio.client.utils.player;

import me.kansio.client.utils.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.*;
import me.kansio.client.modules.impl.combat.*;
import me.kansio.client.*;
import me.kansio.client.utils.rotations.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import javax.vecmath.*;
import net.minecraft.client.entity.*;

public class PlayerUtil extends Util
{
    public static void TPGROUND(final MoveEvent llllllllllllllllllllIllIIIIllIII, final double llllllllllllllllllllIllIIIIlIlll, final double llllllllllllllllllllIllIIIIlIllI) {
        float llllllllllllllllllllIllIIIIlIlIl = PlayerUtil.mc.thePlayer.rotationYaw;
        final float llllllllllllllllllllIllIIIIlIlII = PlayerUtil.mc.thePlayer.moveForward;
        final float llllllllllllllllllllIllIIIIlIIll = PlayerUtil.mc.thePlayer.moveStrafing;
        llllllllllllllllllllIllIIIIlIlIl += ((llllllllllllllllllllIllIIIIlIlII < 0.0f) ? 180 : 0);
        if (llllllllllllllllllllIllIIIIlIIll < 0.0f) {
            llllllllllllllllllllIllIIIIlIlIl += ((llllllllllllllllllllIllIIIIlIlII < 0.0f) ? -45 : ((llllllllllllllllllllIllIIIIlIlII == 0.0f) ? 90 : 45));
        }
        if (llllllllllllllllllllIllIIIIlIIll > 0.0f) {
            llllllllllllllllllllIllIIIIlIlIl -= ((llllllllllllllllllllIllIIIIlIlII < 0.0f) ? -45 : ((llllllllllllllllllllIllIIIIlIlII == 0.0f) ? 90 : 45));
        }
        final float llllllllllllllllllllIllIIIIlIIlI = llllllllllllllllllllIllIIIIlIlIl * 0.017453292f;
        final double llllllllllllllllllllIllIIIIlIIIl = PlayerUtil.mc.thePlayer.posX;
        final double llllllllllllllllllllIllIIIIlIIII = PlayerUtil.mc.thePlayer.posY;
        final double llllllllllllllllllllIllIIIIIllll = PlayerUtil.mc.thePlayer.posZ;
        final double llllllllllllllllllllIllIIIIIlllI = -Math.sin(llllllllllllllllllllIllIIIIlIIlI);
        final double llllllllllllllllllllIllIIIIIllIl = Math.cos(llllllllllllllllllllIllIIIIlIIlI);
        final double llllllllllllllllllllIllIIIIIllII = llllllllllllllllllllIllIIIIIlllI * llllllllllllllllllllIllIIIIlIlll;
        final double llllllllllllllllllllIllIIIIIlIll = llllllllllllllllllllIllIIIIIllIl * llllllllllllllllllllIllIIIIlIlll;
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(llllllllllllllllllllIllIIIIlIIIl + llllllllllllllllllllIllIIIIIllII, llllllllllllllllllllIllIIIIlIIII + llllllllllllllllllllIllIIIIlIllI, llllllllllllllllllllIllIIIIIllll + llllllllllllllllllllIllIIIIIlIll, true));
        PlayerUtil.mc.thePlayer.setPosition(llllllllllllllllllllIllIIIIlIIIl + llllllllllllllllllllIllIIIIIllII, llllllllllllllllllllIllIIIIlIIII + llllllllllllllllllllIllIIIIlIllI, llllllllllllllllllllIllIIIIIllll + llllllllllllllllllllIllIIIIIlIll);
    }
    
    public static void setMotion(final MoveEvent llllllllllllllllllllIllIlIllIIIl, final double llllllllllllllllllllIllIlIlIIlll) {
        final EntityLivingBase llllllllllllllllllllIllIlIlIllll = KillAura.target;
        final TargetStrafe llllllllllllllllllllIllIlIlIlllI = (TargetStrafe)Client.getInstance().getModuleManager().getModuleByName("Target Strafe");
        final boolean llllllllllllllllllllIllIlIlIllIl = llllllllllllllllllllIllIlIlIlllI.canStrafe();
        final MovementInput llllllllllllllllllllIllIlIlIllII = PlayerUtil.mc.thePlayer.movementInput;
        double llllllllllllllllllllIllIlIlIlIll = llllllllllllllllllllIllIlIlIllIl ? ((PlayerUtil.mc.thePlayer.getDistanceToEntity(llllllllllllllllllllIllIlIlIllll) <= llllllllllllllllllllIllIlIlIlllI.radius.getValue().floatValue()) ? 0.0 : 1.0) : llllllllllllllllllllIllIlIlIllII.moveForward;
        double llllllllllllllllllllIllIlIlIlIlI = llllllllllllllllllllIllIlIlIllIl ? TargetStrafe.dir : llllllllllllllllllllIllIlIlIllII.moveStrafe;
        double llllllllllllllllllllIllIlIlIlIIl = llllllllllllllllllllIllIlIlIllIl ? AimUtil.getRotationsRandom(llllllllllllllllllllIllIlIlIllll).getRotationYaw() : ((double)PlayerUtil.mc.thePlayer.rotationYaw);
        llllllllllllllllllllIllIlIllIIIl.setStrafeSpeed(llllllllllllllllllllIllIlIlIIlll);
        if (llllllllllllllllllllIllIlIlIlIll == 0.0 && llllllllllllllllllllIllIlIlIlIlI == 0.0) {
            llllllllllllllllllllIllIlIllIIIl.setMotionX(0.0);
            llllllllllllllllllllIllIlIllIIIl.setMotionZ(0.0);
        }
        else {
            if (llllllllllllllllllllIllIlIlIlIlI > 0.0) {
                llllllllllllllllllllIllIlIlIlIlI = 1.0;
            }
            else if (llllllllllllllllllllIllIlIlIlIlI < 0.0) {
                llllllllllllllllllllIllIlIlIlIlI = -1.0;
            }
            if (llllllllllllllllllllIllIlIlIlIll != 0.0) {
                if (llllllllllllllllllllIllIlIlIlIlI > 0.0) {
                    llllllllllllllllllllIllIlIlIlIIl += ((llllllllllllllllllllIllIlIlIlIll > 0.0) ? -45.0 : 45.0);
                }
                else if (llllllllllllllllllllIllIlIlIlIlI < 0.0) {
                    llllllllllllllllllllIllIlIlIlIIl += ((llllllllllllllllllllIllIlIlIlIll > 0.0) ? 45.0 : -45.0);
                }
                llllllllllllllllllllIllIlIlIlIlI = 0.0;
                if (llllllllllllllllllllIllIlIlIlIll > 0.0) {
                    llllllllllllllllllllIllIlIlIlIll = 1.0;
                }
                else if (llllllllllllllllllllIllIlIlIlIll < 0.0) {
                    llllllllllllllllllllIllIlIlIlIll = -1.0;
                }
            }
            final double llllllllllllllllllllIllIlIllIIll = Math.cos(Math.toRadians(llllllllllllllllllllIllIlIlIlIIl + 90.0));
            final double llllllllllllllllllllIllIlIllIIlI = Math.sin(Math.toRadians(llllllllllllllllllllIllIlIlIlIIl + 90.0));
            llllllllllllllllllllIllIlIllIIIl.setMotionX(llllllllllllllllllllIllIlIlIlIll * llllllllllllllllllllIllIlIlIIlll * llllllllllllllllllllIllIlIllIIll + llllllllllllllllllllIllIlIlIlIlI * llllllllllllllllllllIllIlIlIIlll * llllllllllllllllllllIllIlIllIIlI);
            llllllllllllllllllllIllIlIllIIIl.setMotionZ(llllllllllllllllllllIllIlIlIlIll * llllllllllllllllllllIllIlIlIIlll * llllllllllllllllllllIllIlIllIIlI - llllllllllllllllllllIllIlIlIlIlI * llllllllllllllllllllIllIlIlIIlll * llllllllllllllllllllIllIlIllIIll);
        }
    }
    
    public static boolean isOnGround(final double llllllllllllllllllllIllIIIlIlIlI) {
        return !PlayerUtil.mc.theWorld.getCollidingBoundingBoxes(PlayerUtil.mc.thePlayer, PlayerUtil.mc.thePlayer.getEntityBoundingBox().offset(0.0, -llllllllllllllllllllIllIIIlIlIlI, 0.0)).isEmpty();
    }
    
    public static float getBaseSpeed() {
        float llllllllllllllllllllIlllIIIlllll = 0.2873f;
        if (PlayerUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int llllllllllllllllllllIlllIIlIIIII = PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            llllllllllllllllllllIlllIIIlllll *= 1.0f + 0.2f * (llllllllllllllllllllIlllIIlIIIII + 1);
        }
        return llllllllllllllllllllIlllIIIlllll;
    }
    
    public static double getSpeed() {
        double llllllllllllllllllllIllIIlllllII = 0.2873;
        if (PlayerUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int llllllllllllllllllllIllIIlllllIl = PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            llllllllllllllllllllIllIIlllllII *= 1.0 + 0.2 * llllllllllllllllllllIllIIlllllIl;
        }
        return llllllllllllllllllllIllIIlllllII;
    }
    
    public static void sendPosition(final double llllllllllllllllllllIllIIlIllIIl, final double llllllllllllllllllllIllIIlIllIII, final double llllllllllllllllllllIllIIlIllIll, final boolean llllllllllllllllllllIllIIlIllIlI) {
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX + llllllllllllllllllllIllIIlIllIIl, PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIllIIlIllIII, PlayerUtil.mc.thePlayer.posZ + llllllllllllllllllllIllIIlIllIll, llllllllllllllllllllIllIIlIllIlI));
    }
    
    public static void strafe(final float llllllllllllllllllllIlllIIIlIIlI) {
        if (!PlayerUtil.mc.thePlayer.isMoving()) {
            return;
        }
        final double llllllllllllllllllllIlllIIIlIIll = getDirection();
        PlayerUtil.mc.thePlayer.motionX = -Math.sin(llllllllllllllllllllIlllIIIlIIll) * llllllllllllllllllllIlllIIIlIIlI;
        PlayerUtil.mc.thePlayer.motionZ = Math.cos(llllllllllllllllllllIlllIIIlIIll) * llllllllllllllllllllIlllIIIlIIlI;
    }
    
    public static double getVerusBaseSpeed() {
        double llllllllllllllllllllIllIIIlIIlll = 0.2865;
        if (PlayerUtil.mc.thePlayer.isPotionActive(1)) {
            llllllllllllllllllllIllIIIlIIlll *= 1.0 + 0.0495 * (PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return llllllllllllllllllllIllIIIlIIlll;
    }
    
    public static boolean isBlockUnder() {
        for (int llllllllllllllllllllIllIllIIlIlI = 0; llllllllllllllllllllIllIllIIlIlI < PlayerUtil.mc.thePlayer.posY + PlayerUtil.mc.thePlayer.getEyeHeight(); llllllllllllllllllllIllIllIIlIlI += 2) {
            final AxisAlignedBB llllllllllllllllllllIllIllIIlIll = PlayerUtil.mc.thePlayer.getEntityBoundingBox().offset(0.0, -llllllllllllllllllllIllIllIIlIlI, 0.0);
            if (!PlayerUtil.mc.theWorld.getCollidingBoundingBoxes(PlayerUtil.mc.thePlayer, llllllllllllllllllllIllIllIIlIll).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static void damageVerusNoMotion() {
        PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(PlayerUtil.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        double llllllllllllllllllllIllIllIlIlIl = 0.0;
        for (int llllllllllllllllllllIllIllIlIlll = 0; llllllllllllllllllllIllIllIlIlll <= 6; ++llllllllllllllllllllIllIllIlIlll) {
            llllllllllllllllllllIllIllIlIlIl += 0.5;
            PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIllIllIlIlIl, PlayerUtil.mc.thePlayer.posZ, true));
        }
        double llllllllllllllllllllIllIllIlIlII = PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIllIllIlIlIl;
        final ArrayList<Float> llllllllllllllllllllIllIllIlIIll = new ArrayList<Float>();
        llllllllllllllllllllIllIllIlIIll.add(0.0784f);
        llllllllllllllllllllIllIllIlIIll.add(0.0784f);
        llllllllllllllllllllIllIllIlIIll.add(0.23052737f);
        llllllllllllllllllllIllIllIlIIll.add(0.30431682f);
        llllllllllllllllllllIllIllIlIIll.add(0.37663049f);
        llllllllllllllllllllIllIllIlIIll.add(0.4474979f);
        llllllllllllllllllllIllIllIlIIll.add(0.5169479f);
        llllllllllllllllllllIllIllIlIIll.add(0.585009f);
        llllllllllllllllllllIllIllIlIIll.add(0.65170884f);
        llllllllllllllllllllIllIllIlIIll.add(0.15372962f);
        for (final float llllllllllllllllllllIllIllIlIllI : llllllllllllllllllllIllIllIlIIll) {
            llllllllllllllllllllIllIllIlIlII -= llllllllllllllllllllIllIllIlIllI;
        }
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, llllllllllllllllllllIllIllIlIlII, PlayerUtil.mc.thePlayer.posZ, false));
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(PlayerUtil.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
    }
    
    public static void damagePlayer() {
        if (!PlayerUtil.mc.thePlayer.onGround) {
            return;
        }
        final double[] llllllllllllllllllllIlllIIIIIlIl = { 0.062, 0.0 };
        final double[] llllllllllllllllllllIlllIIIIIlII = { 0.4229939999986887, 0.002140803780930446 };
        if (PlayerUtil.mc.getCurrentServerData() != null && PlayerUtil.mc.getCurrentServerData().serverIP != null && (PlayerUtil.mc.getCurrentServerData().serverIP.toLowerCase().contains("funcraft") || PlayerUtil.mc.getCurrentServerData().serverIP.toLowerCase().contains("cubecraft"))) {
            for (int llllllllllllllllllllIlllIIIIlIIl = 0; llllllllllllllllllllIlllIIIIlIIl < (PlayerUtil.mc.thePlayer.isPotionActive(Potion.jump) ? 15 : 8); ++llllllllllllllllllllIlllIIIIlIIl) {
                for (int llllllllllllllllllllIlllIIIIlIll = llllllllllllllllllllIlllIIIIIlII.length, llllllllllllllllllllIlllIIIIlIlI = 0; llllllllllllllllllllIlllIIIIlIlI < llllllllllllllllllllIlllIIIIlIll; ++llllllllllllllllllllIlllIIIIlIlI) {
                    PlayerUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIlllIIIIIlII[llllllllllllllllllllIlllIIIIlIlI], PlayerUtil.mc.thePlayer.posZ, false));
                }
            }
        }
        else {
            for (int llllllllllllllllllllIlllIIIIIllI = 0; llllllllllllllllllllIlllIIIIIllI < (PlayerUtil.mc.thePlayer.isPotionActive(Potion.jump) ? 122 : 49); ++llllllllllllllllllllIlllIIIIIllI) {
                for (int llllllllllllllllllllIlllIIIIlIII = llllllllllllllllllllIlllIIIIIlIl.length, llllllllllllllllllllIlllIIIIIlll = 0; llllllllllllllllllllIlllIIIIIlll < llllllllllllllllllllIlllIIIIlIII; ++llllllllllllllllllllIlllIIIIIlll) {
                    PlayerUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIlllIIIIIlIl[llllllllllllllllllllIlllIIIIIlll], PlayerUtil.mc.thePlayer.posZ, false));
                }
            }
        }
    }
    
    public static boolean isOnWater() {
        return PlayerUtil.mc.thePlayer.isCollidedVertically && PlayerUtil.mc.theWorld.getBlockState(new BlockPos(PlayerUtil.mc.thePlayer)).getBlock() instanceof BlockLiquid;
    }
    
    public static double[] teleportForward(final double llllllllllllllllllllIllIIllIlIIl) {
        final float llllllllllllllllllllIllIIlllIIII = 1.0f;
        final float llllllllllllllllllllIllIIllIllll = 0.0f;
        final float llllllllllllllllllllIllIIllIlllI = PlayerUtil.mc.thePlayer.prevRotationYaw + (PlayerUtil.mc.thePlayer.rotationYaw - PlayerUtil.mc.thePlayer.prevRotationYaw) * PlayerUtil.mc.timer.renderPartialTicks;
        final double llllllllllllllllllllIllIIllIllIl = Math.sin(Math.toRadians(llllllllllllllllllllIllIIllIlllI + 90.0f));
        final double llllllllllllllllllllIllIIllIllII = Math.cos(Math.toRadians(llllllllllllllllllllIllIIllIlllI + 90.0f));
        final double llllllllllllllllllllIllIIllIlIll = 1.0 * llllllllllllllllllllIllIIllIlIIl * llllllllllllllllllllIllIIllIllII + 0.0 * llllllllllllllllllllIllIIllIlIIl * llllllllllllllllllllIllIIllIllIl;
        final double llllllllllllllllllllIllIIllIlIlI = 1.0 * llllllllllllllllllllIllIIllIlIIl * llllllllllllllllllllIllIIllIllIl - 0.0 * llllllllllllllllllllIllIIllIlIIl * llllllllllllllllllllIllIIllIllII;
        return new double[] { llllllllllllllllllllIllIIllIlIll, llllllllllllllllllllIllIIllIlIlI };
    }
    
    public static void TP(final MoveEvent llllllllllllllllllllIlIllllIllll, final double llllllllllllllllllllIlIllllIIIII, final double llllllllllllllllllllIlIlllIlllll) {
        float llllllllllllllllllllIlIllllIllII = PlayerUtil.mc.thePlayer.rotationYaw;
        final float llllllllllllllllllllIlIllllIlIll = PlayerUtil.mc.thePlayer.moveForward;
        final float llllllllllllllllllllIlIllllIlIlI = PlayerUtil.mc.thePlayer.moveStrafing;
        llllllllllllllllllllIlIllllIllII += ((llllllllllllllllllllIlIllllIlIll < 0.0f) ? 180 : 0);
        final int llllllllllllllllllllIlIllllIlIIl = (llllllllllllllllllllIlIllllIlIll < 0.0f) ? -45 : ((llllllllllllllllllllIlIllllIlIll == 0.0f) ? 90 : 45);
        if (llllllllllllllllllllIlIllllIlIlI < 0.0f) {
            llllllllllllllllllllIlIllllIllII += llllllllllllllllllllIlIllllIlIIl;
        }
        if (llllllllllllllllllllIlIllllIlIlI > 0.0f) {
            llllllllllllllllllllIlIllllIllII -= llllllllllllllllllllIlIllllIlIIl;
        }
        final float llllllllllllllllllllIlIllllIlIII = llllllllllllllllllllIlIllllIllII * 0.017453292f;
        final double llllllllllllllllllllIlIllllIIlll = PlayerUtil.mc.thePlayer.posX;
        final double llllllllllllllllllllIlIllllIIllI = PlayerUtil.mc.thePlayer.posY;
        final double llllllllllllllllllllIlIllllIIlIl = PlayerUtil.mc.thePlayer.posZ;
        final double llllllllllllllllllllIlIllllIIlII = -Math.sin(llllllllllllllllllllIlIllllIlIII);
        final double llllllllllllllllllllIlIllllIIIll = Math.cos(llllllllllllllllllllIlIllllIlIII);
        final double llllllllllllllllllllIlIllllIIIlI = llllllllllllllllllllIlIllllIIlII * llllllllllllllllllllIlIllllIIIII;
        final double llllllllllllllllllllIlIllllIIIIl = llllllllllllllllllllIlIllllIIIll * llllllllllllllllllllIlIllllIIIII;
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(llllllllllllllllllllIlIllllIIlll + llllllllllllllllllllIlIllllIIIlI, llllllllllllllllllllIlIllllIIllI + llllllllllllllllllllIlIlllIlllll, llllllllllllllllllllIlIllllIIlIl + llllllllllllllllllllIlIllllIIIIl, PlayerUtil.mc.thePlayer.onGround));
        PlayerUtil.mc.thePlayer.setPosition(llllllllllllllllllllIlIllllIIlll + llllllllllllllllllllIlIllllIIIlI, llllllllllllllllllllIlIllllIIllI + llllllllllllllllllllIlIlllIlllll, llllllllllllllllllllIlIllllIIlIl + llllllllllllllllllllIlIllllIIIIl);
    }
    
    public static double getDirection() {
        float llllllllllllllllllllIlllIIIllIlI = PlayerUtil.mc.thePlayer.rotationYaw;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            llllllllllllllllllllIlllIIIllIlI += 180.0f;
        }
        float llllllllllllllllllllIlllIIIllIIl = 1.0f;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            llllllllllllllllllllIlllIIIllIIl = -0.5f;
        }
        else if (PlayerUtil.mc.thePlayer.moveForward > 0.0f) {
            llllllllllllllllllllIlllIIIllIIl = 0.5f;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing > 0.0f) {
            llllllllllllllllllllIlllIIIllIlI -= 90.0f * llllllllllllllllllllIlllIIIllIIl;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing < 0.0f) {
            llllllllllllllllllllIlllIIIllIlI += 90.0f * llllllllllllllllllllIlllIIIllIIl;
        }
        return llllllllllllllllllllIlllIIIllIlI;
    }
    
    public static double getPlayerSpeed() {
        final double llllllllllllllllllllIlIlllIlIIIl = Math.sqrt(PlayerUtil.mc.thePlayer.motionX * PlayerUtil.mc.thePlayer.motionY * PlayerUtil.mc.thePlayer.motionZ);
        if (Double.isNaN(llllllllllllllllllllIlIlllIlIIIl)) {
            return 0.0;
        }
        return llllllllllllllllllllIlIlllIlIIIl;
    }
    
    public static Vector2d getMotion(final double llllllllllllllllllllIllIIlIIIlll) {
        final MovementInput llllllllllllllllllllIllIIlIIlIll = PlayerUtil.mc.thePlayer.movementInput;
        double llllllllllllllllllllIllIIlIIlIlI = llllllllllllllllllllIllIIlIIlIll.moveForward;
        double llllllllllllllllllllIllIIlIIlIIl = llllllllllllllllllllIllIIlIIlIll.moveStrafe;
        double llllllllllllllllllllIllIIlIIlIII = PlayerUtil.mc.thePlayer.rotationYaw;
        if (llllllllllllllllllllIllIIlIIlIlI != 0.0 || llllllllllllllllllllIllIIlIIlIIl != 0.0) {
            if (llllllllllllllllllllIllIIlIIlIIl > 0.0) {
                llllllllllllllllllllIllIIlIIlIIl = 1.0;
            }
            else if (llllllllllllllllllllIllIIlIIlIIl < 0.0) {
                llllllllllllllllllllIllIIlIIlIIl = -1.0;
            }
            if (llllllllllllllllllllIllIIlIIlIlI != 0.0) {
                if (llllllllllllllllllllIllIIlIIlIIl > 0.0) {
                    llllllllllllllllllllIllIIlIIlIII += ((llllllllllllllllllllIllIIlIIlIlI > 0.0) ? -45.0 : 45.0);
                }
                else if (llllllllllllllllllllIllIIlIIlIIl < 0.0) {
                    llllllllllllllllllllIllIIlIIlIII += ((llllllllllllllllllllIllIIlIIlIlI > 0.0) ? 45.0 : -45.0);
                }
                llllllllllllllllllllIllIIlIIlIIl = 0.0;
                if (llllllllllllllllllllIllIIlIIlIlI > 0.0) {
                    llllllllllllllllllllIllIIlIIlIlI = 1.0;
                }
                else if (llllllllllllllllllllIllIIlIIlIlI < 0.0) {
                    llllllllllllllllllllIllIIlIIlIlI = -1.0;
                }
            }
            final double llllllllllllllllllllIllIIlIIlllI = Math.cos(Math.toRadians(llllllllllllllllllllIllIIlIIlIII + 90.0));
            final double llllllllllllllllllllIllIIlIIllIl = Math.sin(Math.toRadians(llllllllllllllllllllIllIIlIIlIII + 90.0));
            return new Vector2d(llllllllllllllllllllIllIIlIIlIlI * llllllllllllllllllllIllIIlIIIlll * llllllllllllllllllllIllIIlIIlllI + llllllllllllllllllllIllIIlIIlIIl * llllllllllllllllllllIllIIlIIIlll * llllllllllllllllllllIllIIlIIllIl, llllllllllllllllllllIllIIlIIlIlI * llllllllllllllllllllIllIIlIIIlll * llllllllllllllllllllIllIIlIIllIl - llllllllllllllllllllIllIIlIIlIIl * llllllllllllllllllllIllIIlIIIlll * llllllllllllllllllllIllIIlIIlllI);
        }
        return new Vector2d(0.0, 0.0);
    }
    
    public static double getMotion(float llllllllllllllllllllIllIllIIIIIl) {
        final Potion llllllllllllllllllllIllIllIIIIlI = Potion.jump;
        if (PlayerUtil.mc.thePlayer.isPotionActive(llllllllllllllllllllIllIllIIIIlI)) {
            final int llllllllllllllllllllIllIllIIIlII = PlayerUtil.mc.thePlayer.getActivePotionEffect(llllllllllllllllllllIllIllIIIIlI).getAmplifier();
            llllllllllllllllllllIllIllIIIIIl += (llllllllllllllllllllIllIllIIIlII + 1) * 0.1f;
        }
        return llllllllllllllllllllIllIllIIIIIl;
    }
    
    public static void damagePlayer(final boolean llllllllllllllllllllIllIllllIIIl) {
        if (!llllllllllllllllllllIllIllllIIIl || PlayerUtil.mc.thePlayer.onGround) {
            final double llllllllllllllllllllIllIllllIllI = PlayerUtil.mc.thePlayer.posX;
            final double llllllllllllllllllllIllIllllIlIl = PlayerUtil.mc.thePlayer.posY;
            final double llllllllllllllllllllIllIllllIlII = PlayerUtil.mc.thePlayer.posZ;
            double llllllllllllllllllllIllIllllIIll = 3.0;
            if (PlayerUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
                final int llllllllllllllllllllIllIlllllIII = PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
                llllllllllllllllllllIllIllllIIll += (float)(llllllllllllllllllllIllIlllllIII + 1);
            }
            for (int llllllllllllllllllllIllIllllIlll = 0; llllllllllllllllllllIllIllllIlll < (int)Math.ceil(llllllllllllllllllllIllIllllIIll / 0.0624); ++llllllllllllllllllllIllIllllIlll) {
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(llllllllllllllllllllIllIllllIllI, llllllllllllllllllllIllIllllIlIl + 0.0624, llllllllllllllllllllIllIllllIlII, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(llllllllllllllllllllIllIllllIllI, llllllllllllllllllllIllIllllIlIl, llllllllllllllllllllIllIllllIlII, false));
            }
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer(true));
        }
    }
    
    public static void strafe() {
        strafe(getSpeed1());
    }
    
    public static void setMotion(final double llllllllllllllllllllIllIlIIIlIIl) {
        final EntityLivingBase llllllllllllllllllllIllIlIIlIIII = KillAura.target;
        final TargetStrafe llllllllllllllllllllIllIlIIIllll = (TargetStrafe)Client.getInstance().getModuleManager().getModuleByName("Target Strafe");
        final boolean llllllllllllllllllllIllIlIIIlllI = llllllllllllllllllllIllIlIIIllll.canStrafe();
        final MovementInput llllllllllllllllllllIllIlIIIllIl = PlayerUtil.mc.thePlayer.movementInput;
        double llllllllllllllllllllIllIlIIIllII = llllllllllllllllllllIllIlIIIlllI ? ((PlayerUtil.mc.thePlayer.getDistanceToEntity(llllllllllllllllllllIllIlIIlIIII) <= llllllllllllllllllllIllIlIIIllll.radius.getValue().floatValue()) ? 0.0 : 1.0) : llllllllllllllllllllIllIlIIIllIl.moveForward;
        double llllllllllllllllllllIllIlIIIlIll = llllllllllllllllllllIllIlIIIlllI ? TargetStrafe.dir : llllllllllllllllllllIllIlIIIllIl.moveStrafe;
        double llllllllllllllllllllIllIlIIIlIlI = llllllllllllllllllllIllIlIIIlllI ? AimUtil.getRotationsRandom(llllllllllllllllllllIllIlIIlIIII).getRotationYaw() : ((double)PlayerUtil.mc.thePlayer.rotationYaw);
        if (llllllllllllllllllllIllIlIIIllII == 0.0 && llllllllllllllllllllIllIlIIIlIll == 0.0) {
            final EntityPlayerSP thePlayer = PlayerUtil.mc.thePlayer;
            final EntityPlayerSP thePlayer2 = PlayerUtil.mc.thePlayer;
            final double n = 0.0;
            thePlayer2.motionZ = n;
            thePlayer.motionX = n;
        }
        else {
            if (llllllllllllllllllllIllIlIIIlIll > 0.0) {
                llllllllllllllllllllIllIlIIIlIll = 1.0;
            }
            else if (llllllllllllllllllllIllIlIIIlIll < 0.0) {
                llllllllllllllllllllIllIlIIIlIll = -1.0;
            }
            if (llllllllllllllllllllIllIlIIIllII != 0.0) {
                if (llllllllllllllllllllIllIlIIIlIll > 0.0) {
                    llllllllllllllllllllIllIlIIIlIlI += ((llllllllllllllllllllIllIlIIIllII > 0.0) ? -45 : 45);
                }
                else if (llllllllllllllllllllIllIlIIIlIll < 0.0) {
                    llllllllllllllllllllIllIlIIIlIlI += ((llllllllllllllllllllIllIlIIIllII > 0.0) ? 45 : -45);
                }
                llllllllllllllllllllIllIlIIIlIll = 0.0;
                if (llllllllllllllllllllIllIlIIIllII > 0.0) {
                    llllllllllllllllllllIllIlIIIllII = 1.0;
                }
                else if (llllllllllllllllllllIllIlIIIllII < 0.0) {
                    llllllllllllllllllllIllIlIIIllII = -1.0;
                }
            }
            final double llllllllllllllllllllIllIlIIlIIll = Math.cos(Math.toRadians(llllllllllllllllllllIllIlIIIlIlI + 90.0));
            final double llllllllllllllllllllIllIlIIlIIlI = Math.sin(Math.toRadians(llllllllllllllllllllIllIlIIIlIlI + 90.0));
            PlayerUtil.mc.thePlayer.motionX = llllllllllllllllllllIllIlIIIllII * llllllllllllllllllllIllIlIIIlIIl * llllllllllllllllllllIllIlIIlIIll + llllllllllllllllllllIllIlIIIlIll * llllllllllllllllllllIllIlIIIlIIl * llllllllllllllllllllIllIlIIlIIlI;
            PlayerUtil.mc.thePlayer.motionZ = llllllllllllllllllllIllIlIIIllII * llllllllllllllllllllIllIlIIIlIIl * llllllllllllllllllllIllIlIIlIIlI - llllllllllllllllllllIllIlIIIlIll * llllllllllllllllllllIllIlIIIlIIl * llllllllllllllllllllIllIlIIlIIll;
        }
    }
    
    public static void damageVerus() {
        PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(PlayerUtil.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        double llllllllllllllllllllIllIlllIIlII = 0.0;
        for (int llllllllllllllllllllIllIlllIIllI = 0; llllllllllllllllllllIllIlllIIllI <= 6; ++llllllllllllllllllllIllIlllIIllI) {
            llllllllllllllllllllIllIlllIIlII += 0.5;
            PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIllIlllIIlII, PlayerUtil.mc.thePlayer.posZ, true));
        }
        double llllllllllllllllllllIllIlllIIIll = PlayerUtil.mc.thePlayer.posY + llllllllllllllllllllIllIlllIIlII;
        final ArrayList<Float> llllllllllllllllllllIllIlllIIIlI = new ArrayList<Float>();
        llllllllllllllllllllIllIlllIIIlI.add(0.0784f);
        llllllllllllllllllllIllIlllIIIlI.add(0.0784f);
        llllllllllllllllllllIllIlllIIIlI.add(0.23052737f);
        llllllllllllllllllllIllIlllIIIlI.add(0.30431682f);
        llllllllllllllllllllIllIlllIIIlI.add(0.37663049f);
        llllllllllllllllllllIllIlllIIIlI.add(0.4474979f);
        llllllllllllllllllllIllIlllIIIlI.add(0.5169479f);
        llllllllllllllllllllIllIlllIIIlI.add(0.585009f);
        llllllllllllllllllllIllIlllIIIlI.add(0.65170884f);
        llllllllllllllllllllIllIlllIIIlI.add(0.15372962f);
        for (final float llllllllllllllllllllIllIlllIIlIl : llllllllllllllllllllIllIlllIIIlI) {
            llllllllllllllllllllIllIlllIIIll -= llllllllllllllllllllIllIlllIIlIl;
        }
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, llllllllllllllllllllIllIlllIIIll, PlayerUtil.mc.thePlayer.posZ, false));
        PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(PlayerUtil.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
        PlayerUtil.mc.thePlayer.motionY = getMotion(0.42f);
    }
    
    public static float getSpeed1() {
        return (float)Math.sqrt(PlayerUtil.mc.thePlayer.motionX * PlayerUtil.mc.thePlayer.motionX + PlayerUtil.mc.thePlayer.motionZ * PlayerUtil.mc.thePlayer.motionZ);
    }
    
    public static void sendMotion(final double llllllllllllllllllllIllIIIlllIIl, final double llllllllllllllllllllIllIIIllIIIl) {
        Vector2d llllllllllllllllllllIllIIIllIlll = new Vector2d(0.0, 0.0);
        final double llllllllllllllllllllIllIIIllIllI = PlayerUtil.mc.thePlayer.posX;
        final double llllllllllllllllllllIllIIIllIlIl = Math.round(PlayerUtil.mc.thePlayer.posY / 0.015625) * 0.015625;
        final double llllllllllllllllllllIllIIIllIlII = PlayerUtil.mc.thePlayer.posZ;
        for (double llllllllllllllllllllIllIIIllIIll = llllllllllllllllllllIllIIIllIIIl; llllllllllllllllllllIllIIIllIIll < llllllllllllllllllllIllIIIlllIIl; llllllllllllllllllllIllIIIllIIll += llllllllllllllllllllIllIIIllIIIl) {
            if (llllllllllllllllllllIllIIIllIIll > llllllllllllllllllllIllIIIlllIIl) {
                llllllllllllllllllllIllIIIllIIll = llllllllllllllllllllIllIIIlllIIl;
            }
            llllllllllllllllllllIllIIIllIlll = getMotion(llllllllllllllllllllIllIIIllIIll);
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(llllllllllllllllllllIllIIIllIllI + llllllllllllllllllllIllIIIllIlll.x, llllllllllllllllllllIllIIIllIlIl, llllllllllllllllllllIllIIIllIlII + llllllllllllllllllllIllIIIllIlll.y, true));
        }
        PlayerUtil.mc.thePlayer.setPosition(llllllllllllllllllllIllIIIllIllI + llllllllllllllllllllIllIIIllIlll.x, PlayerUtil.mc.thePlayer.posY, llllllllllllllllllllIllIIIllIlII + llllllllllllllllllllIllIIIllIlll.y);
    }
}
