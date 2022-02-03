package me.kansio.client.modules.impl.movement.speed.verus;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import net.minecraft.potion.*;
import me.kansio.client.utils.player.*;

public class Verus extends SpeedMode
{
    public Verus() {
        super("Verus");
    }
    
    @Override
    public void onMove(final MoveEvent llllllIllIIII) {
        if (Verus.mc.thePlayer.isMoving()) {
            float llllllIllIIlI = (float)(Verus.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.365 : 0.355);
            if (Verus.mc.thePlayer.onGround) {
                llllllIllIIII.setMotionY(Verus.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
            }
            if (Verus.mc.thePlayer.hurtTime >= 1) {
                llllllIllIIlI = this.getSpeed().getSpeed().getValue().floatValue();
            }
            PlayerUtil.setMotion(llllllIllIIII, llllllIllIIlI);
        }
    }
}
