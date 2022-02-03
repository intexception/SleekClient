package me.kansio.client.utils.rotations;

import me.kansio.client.utils.player.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import javax.vecmath.*;
import net.minecraft.util.*;

public class RotationUtil
{
    private static final /* synthetic */ Minecraft minecraft;
    
    public static Vector2f getNormalRotations(final Entity lIllllIlIIllll) {
        return getNormalRotations(RotationUtil.minecraft.thePlayer.getPositionVector().addVector(0.0, RotationUtil.minecraft.thePlayer.getEyeHeight(), 0.0), lIllllIlIIllll.getPositionVector().addVector(0.0, lIllllIlIIllll.getEyeHeight() / 2.0f, 0.0));
    }
    
    public static Vector2f clampRotation(final Vector2f lIllllIIIlIlll) {
        final float lIllllIIIllIIl = RotationUtil.minecraft.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float lIllllIIIllIII = lIllllIIIllIIl * lIllllIIIllIIl * lIllllIIIllIIl * 1.2f;
        return new Vector2f(lIllllIIIlIlll.x - lIllllIIIlIlll.x % lIllllIIIllIII, lIllllIIIlIlll.y - lIllllIIIlIlll.y % lIllllIIIllIII);
    }
    
    public static Vector2f getRotations(final Entity lIllllIIlIIIIl) {
        return getRotations(RotationUtil.minecraft.thePlayer.getPositionVector().addVector(0.0, RotationUtil.minecraft.thePlayer.getEyeHeight(), 0.0), lIllllIIlIIIIl.getPositionVector().addVector(0.0, lIllllIIlIIIIl.getEyeHeight() / 2.0f, 0.0));
    }
    
    public static Vector2f getNormalRotations(final Vec3 lIllllIlIIIllI, final Vec3 lIllllIIlllllI) {
        final Vec3 lIllllIlIIIlII = new Vec3(lIllllIlIIIllI.xCoord, lIllllIlIIIllI.yCoord, lIllllIlIIIllI.zCoord);
        final Vec3 lIllllIlIIIIll = lIllllIIlllllI.subtract(lIllllIlIIIlII);
        final double lIllllIlIIIIlI = lIllllIlIIIIll.flat().lengthVector();
        final float lIllllIlIIIIIl = (float)Math.toDegrees(Math.atan2(lIllllIlIIIIll.zCoord, lIllllIlIIIIll.xCoord)) - 90.0f;
        final float lIllllIlIIIIII = (float)(-Math.toDegrees(Math.atan2(lIllllIlIIIIll.yCoord, lIllllIlIIIIlI)));
        return new Vector2f(lIllllIlIIIIIl, lIllllIlIIIIII);
    }
    
    public static float updateYawRotation(final float lIllllIIIlIIII, final float lIllllIIIIlIll, final float lIllllIIIIlllI) {
        float lIllllIIIIllIl = MathHelper.wrapAngleTo180_float(lIllllIIIIlIll - lIllllIIIlIIII);
        if (lIllllIIIIllIl > lIllllIIIIlllI) {
            lIllllIIIIllIl = lIllllIIIIlllI;
        }
        if (lIllllIIIIllIl < -lIllllIIIIlllI) {
            lIllllIIIIllIl = -lIllllIIIIlllI;
        }
        return lIllllIIIlIIII + lIllllIIIIllIl;
    }
    
    public static Vector2f getRotations(final Vec3 lIllllIIllIIIl, final Vec3 lIllllIIlIlIIl) {
        final Vec3 lIllllIIlIllll = new Vec3(lIllllIIllIIIl.xCoord, lIllllIIllIIIl.yCoord, lIllllIIllIIIl.zCoord);
        final Vec3 lIllllIIlIlllI = lIllllIIlIlIIl.subtract(lIllllIIlIllll);
        final double lIllllIIlIllIl = lIllllIIlIlllI.flat().lengthVector();
        final float lIllllIIlIllII = (float)Math.toDegrees(Math.atan2(lIllllIIlIlllI.zCoord, lIllllIIlIlllI.xCoord)) - 90.0f;
        final float lIllllIIlIlIll = (float)(-Math.toDegrees(Math.atan2(lIllllIIlIlllI.yCoord, lIllllIIlIllIl)));
        return new Vector2f(lIllllIIlIllII, lIllllIIlIlIll);
    }
    
    public static Vector2f getRotations(final Vec3 lIllllIIIllllI) {
        return getRotations(RotationUtil.minecraft.thePlayer.getPositionVector().addVector(0.0, RotationUtil.minecraft.thePlayer.getEyeHeight(), 0.0), lIllllIIIllllI);
    }
    
    public static float updatePitchRotation(final float lIllllIIIIIIII, final float lIlllIllllllll, final float lIlllIlllllllI) {
        float lIllllIIIIIIIl = MathHelper.wrapAngleTo180_float(lIlllIllllllll - lIllllIIIIIIII);
        if (lIllllIIIIIIIl > lIlllIlllllllI) {
            lIllllIIIIIIIl = lIlllIlllllllI;
        }
        if (lIllllIIIIIIIl < -lIlllIlllllllI) {
            lIllllIIIIIIIl = -lIlllIlllllllI;
        }
        return lIllllIIIIIIII + lIllllIIIIIIIl;
    }
    
    static {
        minecraft = Minecraft.getMinecraft();
    }
}
