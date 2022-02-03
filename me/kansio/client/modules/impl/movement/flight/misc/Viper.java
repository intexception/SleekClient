package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Viper extends FlightMode
{
    public Viper() {
        super("Viper");
    }
    
    @Override
    public void onMove(final MoveEvent lIlIlllIllIllI) {
        if (!Viper.mc.thePlayer.isMovingOnGround()) {
            TimerUtil.Reset();
            return;
        }
        if (Viper.mc.thePlayer.isMoving()) {
            TimerUtil.setTimer(0.3f);
            for (int lIlIlllIlllIII = 0; lIlIlllIlllIII < 17; ++lIlIlllIlllIII) {
                PlayerUtil.TPGROUND(lIlIlllIllIllI, 0.06, 0.0);
            }
        }
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lIlIlllIlIlIlI) {
        if (lIlIlllIlIlIlI.getBlock() instanceof BlockAir) {
            if (Viper.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lIlIlllIlIllll = lIlIlllIlIlIlI.getX();
            final double lIlIlllIlIlllI = lIlIlllIlIlIlI.getY();
            final double lIlIlllIlIllIl = lIlIlllIlIlIlI.getZ();
            if (lIlIlllIlIlllI < Viper.mc.thePlayer.posY) {
                lIlIlllIlIlIlI.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIlIlllIlIllll, lIlIlllIlIlllI, lIlIlllIlIllIl));
            }
        }
    }
}
