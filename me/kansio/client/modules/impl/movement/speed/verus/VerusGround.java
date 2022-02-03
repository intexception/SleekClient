package me.kansio.client.modules.impl.movement.speed.verus;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class VerusGround extends SpeedMode
{
    public VerusGround() {
        super("Verus Port");
    }
    
    @Override
    public void onMove(final MoveEvent llllllllllllllllllllllllIlllIIlI) {
        if (!VerusGround.mc.thePlayer.isInLava() && !VerusGround.mc.thePlayer.isInWater() && !VerusGround.mc.thePlayer.isOnLadder() && VerusGround.mc.thePlayer.ridingEntity == null && VerusGround.mc.thePlayer.isMoving()) {
            VerusGround.mc.gameSettings.keyBindJump.pressed = false;
            if (VerusGround.mc.thePlayer.onGround) {
                VerusGround.mc.thePlayer.jump();
                VerusGround.mc.thePlayer.motionY = 0.0;
                PlayerUtil.setMotion(0.6100000143051147);
                llllllllllllllllllllllllIlllIIlI.setMotionY(0.41999998688698);
            }
            PlayerUtil.strafe();
        }
    }
}
