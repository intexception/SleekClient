package me.kansio.client.modules.impl.movement.flight.verus;

import me.kansio.client.modules.impl.movement.flight.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class VerusGlide extends FlightMode
{
    @Override
    public void onCollide(final BlockCollisionEvent lIlIllIlllIllI) {
        if (lIlIllIlllIllI.getBlock() instanceof BlockAir) {
            if (VerusGlide.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lIlIllIllllIll = lIlIllIlllIllI.getX();
            final double lIlIllIllllIlI = lIlIllIlllIllI.getY();
            final double lIlIllIllllIIl = lIlIllIlllIllI.getZ();
            if (lIlIllIllllIlI < VerusGlide.mc.thePlayer.posY && VerusGlide.mc.thePlayer.ticksExisted % 5 == 0) {
                lIlIllIlllIllI.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIlIllIllllIll, lIlIllIllllIlI, lIlIllIllllIIl));
            }
        }
    }
    
    @Override
    public void onMove(final MoveEvent lIlIlllIIIIIII) {
        if (VerusGlide.mc.thePlayer.ticksExisted % 4 == 0) {
            VerusGlide.mc.thePlayer.motionY = 0.0;
            PlayerUtil.setMotion(0.4000000059604645);
        }
        else {
            PlayerUtil.setMotion(0.10000000149011612);
        }
    }
    
    public VerusGlide() {
        super("Verus Glide");
    }
}
