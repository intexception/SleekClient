package me.kansio.client.modules.impl.movement;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Custom Speed", description = "Gives the ability to customize speed in many ways", category = ModuleCategory.MOVEMENT)
public class CustomSpeed extends Module
{
    private /* synthetic */ NumberValue<Double> motionY;
    private /* synthetic */ NumberValue<Double> groundSpeed;
    
    public CustomSpeed() {
        this.motionY = new NumberValue<Double>("Base MotionY", this, 0.42, 0.05, 5.0, 0.01);
        this.groundSpeed = new NumberValue<Double>("Onground speed", this, 0.4, 0.0, 10.0, 0.01);
    }
    
    @Subscribe
    public void onUpdate(final MoveEvent llllllIIlll) {
        if (CustomSpeed.mc.thePlayer.isMovingOnGround()) {
            llllllIIlll.setMotionY(CustomSpeed.mc.thePlayer.motionY = PlayerUtil.getMotion(this.motionY.getValue().floatValue()));
            PlayerUtil.setMotion(llllllIIlll, this.groundSpeed.getValue());
        }
    }
}
