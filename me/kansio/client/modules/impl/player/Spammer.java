package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.math.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Spammer", description = "Spams a message with a delay", category = ModuleCategory.PLAYER)
public class Spammer extends Module
{
    private final /* synthetic */ StringValue message;
    private final /* synthetic */ NumberValue delay;
    private /* synthetic */ Stopwatch stopwatch;
    
    public Spammer() {
        this.delay = new NumberValue("Delay", this, (T)3000, (T)0, (T)600000, (T)0.1);
        this.message = new StringValue("Text", this, "sex");
        this.stopwatch = new Stopwatch();
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llIlIIlIlllII) {
        if (this.stopwatch.timeElapsed(this.delay.getValue().longValue() + MathUtil.getRandomInRange(1000, 3000))) {
            Spammer.mc.thePlayer.sendChatMessage(this.message.getValue());
            this.stopwatch.resetTime();
        }
    }
}
