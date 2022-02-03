package me.kansio.client.utils.rotations;

import me.kansio.client.utils.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.concurrent.*;

public final class AimUtil extends Util
{
    private static final /* synthetic */ Minecraft mc;
    
    public static Rotation getBowAngles(final Entity llllllllllllllllllllIIlIIllIIlII) {
        final double llllllllllllllllllllIIlIIlllIIII = llllllllllllllllllllIIlIIllIIlII.posX - llllllllllllllllllllIIlIIllIIlII.lastTickPosX;
        final double llllllllllllllllllllIIlIIllIllll = llllllllllllllllllllIIlIIllIIlII.posZ - llllllllllllllllllllIIlIIllIIlII.lastTickPosZ;
        final double llllllllllllllllllllIIlIIllIlllI = AimUtil.mc.thePlayer.getDistanceToEntity(llllllllllllllllllllIIlIIllIIlII) % 0.8;
        final boolean llllllllllllllllllllIIlIIllIllIl = llllllllllllllllllllIIlIIllIIlII.isSprinting();
        final double llllllllllllllllllllIIlIIllIllII = llllllllllllllllllllIIlIIllIlllI / 0.8 * llllllllllllllllllllIIlIIlllIIII * (llllllllllllllllllllIIlIIllIllIl ? 1.45 : 1.3);
        final double llllllllllllllllllllIIlIIllIlIll = llllllllllllllllllllIIlIIllIlllI / 0.8 * llllllllllllllllllllIIlIIllIllll * (llllllllllllllllllllIIlIIllIllIl ? 1.45 : 1.3);
        final double llllllllllllllllllllIIlIIllIlIlI = llllllllllllllllllllIIlIIllIIlII.posX + llllllllllllllllllllIIlIIllIllII - AimUtil.mc.thePlayer.posX;
        final double llllllllllllllllllllIIlIIllIlIIl = AimUtil.mc.thePlayer.posY + AimUtil.mc.thePlayer.getEyeHeight() - (llllllllllllllllllllIIlIIllIIlII.posY + llllllllllllllllllllIIlIIllIIlII.getEyeHeight());
        final double llllllllllllllllllllIIlIIllIlIII = llllllllllllllllllllIIlIIllIIlII.posZ + llllllllllllllllllllIIlIIllIlIll - AimUtil.mc.thePlayer.posZ;
        final double llllllllllllllllllllIIlIIllIIlll = AimUtil.mc.thePlayer.getDistanceToEntity(llllllllllllllllllllIIlIIllIIlII);
        final float llllllllllllllllllllIIlIIllIIllI = (float)Math.toDegrees(Math.atan2(llllllllllllllllllllIIlIIllIlIII, llllllllllllllllllllIIlIIllIlIlI)) - 90.0f;
        final float llllllllllllllllllllIIlIIllIIlIl = (float)Math.toDegrees(Math.atan2(llllllllllllllllllllIIlIIllIlIIl, llllllllllllllllllllIIlIIllIIlll));
        return new Rotation(llllllllllllllllllllIIlIIllIIllI, llllllllllllllllllllIIlIIllIIlIl);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static float wrapAngle(float llllllllllllllllllllIIlIIlllllll) {
        llllllllllllllllllllIIlIIlllllll %= (int)360.0f;
        if (llllllllllllllllllllIIlIIlllllll >= 180.0f) {
            llllllllllllllllllllIIlIIlllllll -= (int)360.0f;
        }
        if (llllllllllllllllllllIIlIIlllllll < -180.0f) {
            llllllllllllllllllllIIlIIlllllll += (int)360.0f;
        }
        return llllllllllllllllllllIIlIIlllllll;
    }
    
    public static Rotation getScaffoldRotations(final BlockPos llllllllllllllllllllIIlIIIllIIlI) {
        final double llllllllllllllllllllIIlIIIllIIIl = direction();
        final double llllllllllllllllllllIIlIIIllIIII = -Math.sin(llllllllllllllllllllIIlIIIllIIIl) * 0.5;
        final double llllllllllllllllllllIIlIIIlIllll = Math.cos(llllllllllllllllllllIIlIIIllIIIl) * 0.5;
        final double llllllllllllllllllllIIlIIIlIlllI = llllllllllllllllllllIIlIIIllIIlI.getX() - AimUtil.mc.thePlayer.posX - llllllllllllllllllllIIlIIIllIIII;
        final double llllllllllllllllllllIIlIIIlIllIl = llllllllllllllllllllIIlIIIllIIlI.getY() - AimUtil.mc.thePlayer.prevPosY - AimUtil.mc.thePlayer.getEyeHeight();
        final double llllllllllllllllllllIIlIIIlIllII = llllllllllllllllllllIIlIIIllIIlI.getZ() - AimUtil.mc.thePlayer.posZ - llllllllllllllllllllIIlIIIlIllll;
        final double llllllllllllllllllllIIlIIIlIlIll = Math.hypot(llllllllllllllllllllIIlIIIlIlllI, llllllllllllllllllllIIlIIIlIllII);
        final float llllllllllllllllllllIIlIIIlIlIlI = (float)(Math.atan2(llllllllllllllllllllIIlIIIlIllII, llllllllllllllllllllIIlIIIlIlllI) * 180.0 / 3.141592653589793 - 90.0);
        final float llllllllllllllllllllIIlIIIlIlIIl = (float)(-(Math.atan2(llllllllllllllllllllIIlIIIlIllIl, llllllllllllllllllllIIlIIIlIlIll) * 180.0 / 3.141592653589793));
        return new Rotation(AimUtil.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(llllllllllllllllllllIIlIIIlIlIlI - AimUtil.mc.thePlayer.rotationYaw), AimUtil.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(llllllllllllllllllllIIlIIIlIlIIl - AimUtil.mc.thePlayer.rotationPitch));
    }
    
    public static Rotation attemptFacePosition(final double llllllllllllllllllllIIlIIlIIIlIl, final double llllllllllllllllllllIIlIIlIIllIl, final double llllllllllllllllllllIIlIIlIIllII) {
        final double llllllllllllllllllllIIlIIlIIlIll = llllllllllllllllllllIIlIIlIIIlIl - AimUtil.mc.thePlayer.posX;
        final double llllllllllllllllllllIIlIIlIIlIlI = llllllllllllllllllllIIlIIlIIllIl - AimUtil.mc.thePlayer.posY - 1.2;
        final double llllllllllllllllllllIIlIIlIIlIIl = llllllllllllllllllllIIlIIlIIllII - AimUtil.mc.thePlayer.posZ;
        final double llllllllllllllllllllIIlIIlIIlIII = Math.hypot(llllllllllllllllllllIIlIIlIIlIll, llllllllllllllllllllIIlIIlIIlIIl);
        final float llllllllllllllllllllIIlIIlIIIlll = (float)(Math.atan2(llllllllllllllllllllIIlIIlIIlIIl, llllllllllllllllllllIIlIIlIIlIll) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllllllllllllllllllIIlIIlIIIllI = (float)(-(Math.atan2(llllllllllllllllllllIIlIIlIIlIlI, llllllllllllllllllllIIlIIlIIlIII) * 180.0 / 3.141592653589793));
        return new Rotation(llllllllllllllllllllIIlIIlIIIlll, llllllllllllllllllllIIlIIlIIIllI);
    }
    
    public static Rotation getRotationsRandom(final EntityLivingBase llllllllllllllllllllIIlIlIIIllll) {
        final ThreadLocalRandom llllllllllllllllllllIIlIlIIIlllI = ThreadLocalRandom.current();
        final double llllllllllllllllllllIIlIlIIIllIl = llllllllllllllllllllIIlIlIIIlllI.nextDouble(-0.05, 0.1);
        final double llllllllllllllllllllIIlIlIIIllII = llllllllllllllllllllIIlIlIIIlllI.nextDouble(-0.05, 0.1);
        final double llllllllllllllllllllIIlIlIIIlIll = llllllllllllllllllllIIlIlIIIllll.posX + llllllllllllllllllllIIlIlIIIllIl;
        final double llllllllllllllllllllIIlIlIIIlIlI = llllllllllllllllllllIIlIlIIIllll.posY + llllllllllllllllllllIIlIlIIIllll.getEyeHeight() / 2.05 + llllllllllllllllllllIIlIlIIIllII;
        final double llllllllllllllllllllIIlIlIIIlIIl = llllllllllllllllllllIIlIlIIIllll.posZ + llllllllllllllllllllIIlIlIIIllIl;
        return attemptFacePosition(llllllllllllllllllllIIlIlIIIlIll, llllllllllllllllllllIIlIlIIIlIlI, llllllllllllllllllllIIlIlIIIlIIl);
    }
    
    public static void turnToEntityClient(final Rotation llllllllllllllllllllIIlIlIIllIII) {
        AimUtil.mc.thePlayer.rotationYaw = llllllllllllllllllllIIlIlIIllIII.getRotationYaw();
        AimUtil.mc.thePlayer.rotationPitch = llllllllllllllllllllIIlIlIIllIII.getRotationPitch();
    }
    
    private static double direction() {
        float llllllllllllllllllllIIlIIIIlllII = AimUtil.mc.thePlayer.rotationYaw;
        if (AimUtil.mc.thePlayer.movementInput.moveForward < 0.0f) {
            llllllllllllllllllllIIlIIIIlllII += 180.0f;
        }
        float llllllllllllllllllllIIlIIIIllIll = 1.0f;
        if (AimUtil.mc.thePlayer.movementInput.moveForward < 0.0f) {
            llllllllllllllllllllIIlIIIIllIll = -0.5f;
        }
        else if (AimUtil.mc.thePlayer.movementInput.moveForward > 0.0f) {
            llllllllllllllllllllIIlIIIIllIll = 0.5f;
        }
        if (AimUtil.mc.thePlayer.movementInput.moveStrafe > 0.0f) {
            llllllllllllllllllllIIlIIIIlllII -= 90.0f * llllllllllllllllllllIIlIIIIllIll;
        }
        if (AimUtil.mc.thePlayer.movementInput.moveStrafe < 0.0f) {
            llllllllllllllllllllIIlIIIIlllII += 90.0f * llllllllllllllllllllIIlIIIIllIll;
        }
        return Math.toRadians(llllllllllllllllllllIIlIIIIlllII);
    }
}
