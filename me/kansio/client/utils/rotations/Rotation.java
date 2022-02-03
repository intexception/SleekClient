package me.kansio.client.utils.rotations;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public final class Rotation
{
    private final /* synthetic */ float rotationYaw;
    private final /* synthetic */ float rotationPitch;
    
    public Rotation(final float lIlIIIIIlllIll, final float lIlIIIIIlllIlI) {
        this.rotationYaw = lIlIIIIIlllIll;
        this.rotationPitch = lIlIIIIIlllIlI;
    }
    
    public static Rotation fromFacing(final EntityLivingBase lIlIIIIIllIlll) {
        final Rotation lIlIIIIIllIllI = AimUtil.getRotationsRandom(lIlIIIIIllIlll);
        return new Rotation((float)MathHelper.floor_double(lIlIIIIIllIllI.getRotationYaw()), lIlIIIIIllIllI.getRotationPitch());
    }
    
    public float getRotationPitch() {
        return this.rotationPitch;
    }
    
    public float getRotationYaw() {
        return this.rotationYaw;
    }
}
