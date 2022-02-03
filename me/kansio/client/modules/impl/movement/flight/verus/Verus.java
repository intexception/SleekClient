package me.kansio.client.modules.impl.movement.flight.verus;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Verus extends FlightMode
{
    @Override
    public void onMove(final MoveEvent llIlIIlllIl) {
        if (!Verus.mc.thePlayer.isInLava() && !Verus.mc.thePlayer.isInWater() && !Verus.mc.thePlayer.isOnLadder() && Verus.mc.thePlayer.ridingEntity == null && Verus.mc.thePlayer.hurtTime < 1) {
            Verus.mc.gameSettings.keyBindJump.pressed = false;
            if (Verus.mc.thePlayer.onGround) {
                Verus.mc.thePlayer.jump();
                Verus.mc.thePlayer.motionY = 0.0;
                if (Verus.mc.thePlayer.isMoving()) {
                    PlayerUtil.strafe(0.61f);
                }
                llIlIIlllIl.setMotionY(0.41999998688698);
            }
            PlayerUtil.strafe();
        }
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent llIlIIlIIlI) {
        if (llIlIIlIIlI.getBlock() instanceof BlockAir) {
            if (Verus.mc.thePlayer.isSneaking()) {
                return;
            }
            final double llIlIIlIlll = llIlIIlIIlI.getX();
            final double llIlIIlIllI = llIlIIlIIlI.getY();
            final double llIlIIlIlIl = llIlIIlIIlI.getZ();
            if (llIlIIlIllI < Verus.mc.thePlayer.posY) {
                llIlIIlIIlI.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(llIlIIlIlll, llIlIIlIllI, llIlIIlIlIl));
            }
        }
    }
    
    public Verus() {
        super("Verus");
    }
}
