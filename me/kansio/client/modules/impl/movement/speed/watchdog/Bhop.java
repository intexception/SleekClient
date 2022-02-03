package me.kansio.client.modules.impl.movement.speed.watchdog;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Bhop extends SpeedMode
{
    @Override
    public void onUpdate(final UpdateEvent lllllllllllllllllllIllllIlllIIIl) {
        if (Bhop.mc.thePlayer.onGround && Bhop.mc.thePlayer.isMoving()) {
            Bhop.mc.thePlayer.speedInAir = 0.0204f;
            TimerUtil.setTimer(0.35f);
            Bhop.mc.gameSettings.keyBindJump.pressed = true;
        }
        else {
            TimerUtil.Reset();
            Bhop.mc.gameSettings.keyBindJump.pressed = false;
        }
        if (Bhop.mc.thePlayer.isMoving()) {
            if (Bhop.mc.thePlayer.fallDistance < 0.1) {
                TimerUtil.setTimer(1.75f);
            }
            if (Bhop.mc.thePlayer.fallDistance > 0.2) {
                TimerUtil.setTimer(0.42f);
            }
            if (Bhop.mc.thePlayer.fallDistance > 0.6) {
                TimerUtil.setTimer(1.05f);
                Bhop.mc.thePlayer.speedInAir = 0.02019f;
            }
        }
        if (Bhop.mc.thePlayer.fallDistance > 1.0f) {
            TimerUtil.Reset();
            Bhop.mc.thePlayer.speedInAir = 0.02f;
        }
    }
    
    public Bhop() {
        super("Watchdog (Hop)");
    }
}
