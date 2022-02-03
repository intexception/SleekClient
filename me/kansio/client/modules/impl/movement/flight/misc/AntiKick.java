package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class AntiKick extends FlightMode
{
    @Override
    public void onUpdate(final UpdateEvent lIlIlIIlIIIII) {
        double lIlIlIIIlllll = 0.0;
        if (AntiKick.mc.gameSettings.keyBindJump.isKeyDown()) {
            lIlIlIIIlllll = this.getFlight().getSpeed().getValue() / 2.0;
        }
        else if (AntiKick.mc.gameSettings.keyBindSneak.isKeyDown()) {
            lIlIlIIIlllll = -(this.getFlight().getSpeed().getValue() / 2.0);
        }
        else if (AntiKick.mc.thePlayer.ticksExisted % 20 == 0) {
            lIlIlIIIlllll = 2.0;
        }
        else {
            lIlIlIIIlllll = -0.1;
        }
        AntiKick.mc.thePlayer.motionY = lIlIlIIIlllll;
        PlayerUtil.setMotion(this.getFlight().getSpeed().getValue());
    }
    
    public AntiKick() {
        super("AntiKick");
    }
}
