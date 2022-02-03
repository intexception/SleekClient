package me.kansio.client.modules.impl.movement.speed.viper;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class ViperGround extends SpeedMode
{
    public ViperGround() {
        super("Viper Ground");
    }
    
    @Override
    public void onMove(final MoveEvent lIllIlIIIll) {
        if (!ViperGround.mc.thePlayer.isMovingOnGround()) {
            TimerUtil.setTimer(1.0f);
            ViperGround.mc.thePlayer.motionY = -5.0;
            return;
        }
        if (ViperGround.mc.thePlayer.isMoving()) {
            TimerUtil.setTimer(0.3f);
            for (int lIllIlIIllI = 0; lIllIlIIllI < 17; ++lIllIlIIllI) {
                PlayerUtil.TP(lIllIlIIIll, 0.22, 0.0);
            }
        }
    }
}
