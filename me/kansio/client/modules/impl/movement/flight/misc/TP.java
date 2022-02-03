package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class TP extends FlightMode
{
    @Override
    public void onUpdate(final UpdateEvent lIllIllIIlIlIl) {
        TP.mc.thePlayer.motionY = 0.0;
        if (TP.mc.thePlayer.ticksExisted % 5 == 0) {
            final double[] lIllIllIIlIlll = PlayerUtil.teleportForward(this.getFlight().getSpeed().getValue());
            if (!TP.mc.thePlayer.isMoving()) {
                return;
            }
            TP.mc.thePlayer.setPosition(TP.mc.thePlayer.posX + lIllIllIIlIlll[0], TP.mc.thePlayer.posY, TP.mc.thePlayer.posZ + lIllIllIIlIlll[1]);
        }
    }
    
    public TP() {
        super("TP");
    }
}
