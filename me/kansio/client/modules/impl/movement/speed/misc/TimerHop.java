package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class TimerHop extends SpeedMode
{
    @Override
    public void onUpdate(final UpdateEvent llIlIllIllI) {
        if (TimerHop.mc.thePlayer.onGround && TimerHop.mc.thePlayer.isMoving()) {
            TimerHop.mc.thePlayer.speedInAir = 0.0204f;
            TimerUtil.setTimer(0.65f);
            TimerHop.mc.gameSettings.keyBindJump.pressed = true;
        }
        else {
            TimerUtil.Reset();
            TimerHop.mc.gameSettings.keyBindJump.pressed = false;
        }
        if (TimerHop.mc.thePlayer.isMoving()) {
            if (TimerHop.mc.thePlayer.fallDistance < 0.1) {
                TimerUtil.setTimer(1.81f);
            }
            if (TimerHop.mc.thePlayer.fallDistance > 0.2) {
                TimerUtil.setTimer(0.42f);
            }
            if (TimerHop.mc.thePlayer.fallDistance > 0.6) {
                TimerUtil.setTimer(1.05f);
                TimerHop.mc.thePlayer.speedInAir = 0.02019f;
            }
        }
        if (TimerHop.mc.thePlayer.fallDistance > 1.0f) {
            TimerUtil.Reset();
            TimerHop.mc.thePlayer.speedInAir = 0.02f;
        }
    }
    
    public TimerHop() {
        super("TimerHop");
    }
}
