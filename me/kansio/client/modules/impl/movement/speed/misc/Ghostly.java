package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;

public class Ghostly extends SpeedMode
{
    public Ghostly() {
        super("Ghostly");
    }
    
    @Override
    public void onMove(final MoveEvent llIIIlIllllll) {
        final double llIIIlIlllllI = Math.toRadians(Ghostly.mc.thePlayer.rotationYaw);
        final double llIIIlIllllIl = -Math.sin(llIIIlIlllllI) * this.getSpeed().getSpeed().getValue();
        final double llIIIlIllllII = Math.cos(llIIIlIlllllI) * this.getSpeed().getSpeed().getValue();
        if (!Ghostly.mc.thePlayer.isMoving()) {
            return;
        }
        if (Ghostly.mc.thePlayer.ticksExisted % 5 == 0) {
            Ghostly.mc.thePlayer.setPosition(Ghostly.mc.thePlayer.posX + llIIIlIllllIl, Ghostly.mc.thePlayer.posY, Ghostly.mc.thePlayer.posZ + llIIIlIllllII);
        }
    }
}
