package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Ghostly extends FlightMode
{
    public Ghostly() {
        super("Ghostly");
    }
    
    @Override
    public void onMove(final MoveEvent lIlIlIIlIIlIlI) {
        double lIlIlIIlIIlIIl = 0.0;
        if (Ghostly.mc.gameSettings.keyBindJump.isKeyDown()) {
            lIlIlIIlIIlIIl = this.getFlight().getSpeed().getValue() / 2.0;
        }
        if (Ghostly.mc.gameSettings.keyBindSneak.isKeyDown()) {
            lIlIlIIlIIlIIl = -(this.getFlight().getSpeed().getValue() / 2.0);
        }
        Ghostly.mc.thePlayer.motionY = -1.21E-10;
        final double lIlIlIIlIIlIII = Math.toRadians(Ghostly.mc.thePlayer.rotationYaw);
        final double lIlIlIIlIIIlll = -Math.sin(lIlIlIIlIIlIII) * this.getFlight().getSpeed().getValue();
        final double lIlIlIIlIIIllI = Math.cos(lIlIlIIlIIlIII) * this.getFlight().getSpeed().getValue();
        if (!Ghostly.mc.thePlayer.isMoving()) {
            return;
        }
        if (Ghostly.mc.thePlayer.ticksExisted % 5 == 0) {
            Ghostly.mc.thePlayer.setPosition(Ghostly.mc.thePlayer.posX + lIlIlIIlIIIlll, Ghostly.mc.thePlayer.posY, Ghostly.mc.thePlayer.posZ + lIlIlIIlIIIllI);
        }
    }
    
    @Override
    public void onEnable() {
        PlayerUtil.damageVerus();
    }
}
