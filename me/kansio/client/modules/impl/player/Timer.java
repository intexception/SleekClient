package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Timer", category = ModuleCategory.PLAYER, description = "Changes your game speed")
public class Timer extends Module
{
    private final /* synthetic */ BooleanValue tick;
    private /* synthetic */ NumberValue<Integer> tickspeed;
    private /* synthetic */ NumberValue<Float> speed;
    
    @Subscribe
    public void onUpdate(final UpdateEvent lllllllllllllllllllIllllllIllIlI) {
        if (this.tick.getValue()) {
            TimerUtil.setTimer(this.speed.getValue(), this.tickspeed.getValue());
        }
        else {
            TimerUtil.setTimer(this.speed.getValue());
        }
    }
    
    @Override
    public void onDisable() {
        TimerUtil.Reset();
    }
    
    public Timer() {
        this.tick = new BooleanValue("Tick Timer", this, false);
        this.speed = new NumberValue<Float>("Speed", this, 1.0f, 0.05f, 10.0f, 0.1f);
        this.tickspeed = new NumberValue<Integer>("Ticks", this, (Integer)(Object)Double.valueOf(1.0), (Integer)(Object)Double.valueOf(1.0), (Integer)(Object)Double.valueOf(20.0), (Integer)(Object)Double.valueOf(1.0), this.tick);
    }
}
