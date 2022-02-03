package me.kansio.client.modules.impl.movement.flight.bridgerland;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Timer extends FlightMode
{
    public Timer() {
        super("BridgerLand (Timer)");
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIIllIIIIIlIII) {
        double lIIllIIIIIIlll = 0.0;
        if (Timer.mc.gameSettings.keyBindJump.isKeyDown()) {
            lIIllIIIIIIlll = 1.0;
        }
        if (Timer.mc.gameSettings.keyBindSneak.isKeyDown()) {
            lIIllIIIIIIlll = -1.0;
        }
        Timer.mc.thePlayer.motionY = lIIllIIIIIIlll;
        TimerUtil.setTimer(0.1f, 5);
        if (Timer.mc.thePlayer.ticksExisted % 4 == 0) {
            PlayerUtil.setMotion(4.0);
        }
        else {
            PlayerUtil.setMotion(0.0);
            Timer.mc.thePlayer.motionY = 0.0;
        }
    }
}
