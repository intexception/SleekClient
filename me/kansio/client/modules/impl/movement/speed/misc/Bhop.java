package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Bhop extends SpeedMode
{
    @Override
    public void onUpdate(final UpdateEvent lIIllIIIIllllI) {
        if (Bhop.mc.thePlayer.onGround) {
            this.getSpeed().getHDist().set((double)this.getSpeed().getSpeed().getValue());
        }
        if (Bhop.mc.thePlayer.isMovingOnGround()) {
            Bhop.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f);
        }
        PlayerUtil.setMotion(this.getSpeed().handleFriction(this.getSpeed().getHDist()));
    }
    
    public Bhop() {
        super("Bhop");
    }
}
