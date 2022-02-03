package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Friction extends SpeedMode
{
    @Override
    public void onUpdate(final UpdateEvent lIlIlIIIIIllI) {
        if (Friction.mc.thePlayer.onGround) {
            this.getSpeed().getHDist().set(this.getSpeed().getHDist().get() + this.getSpeed().getSpeed().getValue());
        }
        if (Friction.mc.thePlayer.isCollidedHorizontally) {
            this.getSpeed().getHDist().set(0.0);
        }
    }
    
    public Friction() {
        super("Friction Abuse");
    }
    
    @Override
    public void onMove(final MoveEvent lIlIIllllllll) {
        if (Friction.mc.thePlayer.isMovingOnGround()) {
            lIlIIllllllll.setMotionY(Friction.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
        }
        PlayerUtil.setMotion(this.getSpeed().handleFriction(this.getSpeed().getHDist()));
    }
}
