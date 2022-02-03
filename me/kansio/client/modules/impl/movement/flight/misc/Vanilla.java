package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Vanilla extends FlightMode
{
    @Override
    public void onUpdate(final UpdateEvent lIllIlIlIIIIIl) {
        double lIllIlIlIIIIII = 0.0;
        if (Vanilla.mc.gameSettings.keyBindJump.isKeyDown()) {
            lIllIlIlIIIIII = this.getFlight().getSpeed().getValue() / 2.0;
        }
        if (Vanilla.mc.gameSettings.keyBindSneak.isKeyDown()) {
            lIllIlIlIIIIII = -(this.getFlight().getSpeed().getValue() / 2.0);
        }
        Vanilla.mc.thePlayer.motionY = lIllIlIlIIIIII;
        PlayerUtil.setMotion(this.getFlight().getSpeed().getValue().floatValue());
    }
    
    public Vanilla() {
        super("Vanilla");
    }
}
