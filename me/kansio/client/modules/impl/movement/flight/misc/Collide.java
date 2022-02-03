package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Collide extends FlightMode
{
    @Override
    public void onCollide(final BlockCollisionEvent lIlIIlIIllII) {
        if (lIlIIlIIllII.getBlock() instanceof BlockAir) {
            if (Collide.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lIlIIlIlIIIl = lIlIIlIIllII.getX();
            final double lIlIIlIlIIII = lIlIIlIIllII.getY();
            final double lIlIIlIIllll = lIlIIlIIllII.getZ();
            if (lIlIIlIlIIII < Collide.mc.thePlayer.posY) {
                lIlIIlIIllII.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIlIIlIlIIIl, lIlIIlIlIIII, lIlIIlIIllll));
            }
        }
    }
    
    public Collide() {
        super("Collide");
    }
}
