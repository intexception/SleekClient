package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.entity.player.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.event.impl.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Auto Report", description = "Creates a lot of false reports on hypixel :troll:", category = ModuleCategory.PLAYER)
public class AutoReport extends Module
{
    private /* synthetic */ NumberValue delay;
    private /* synthetic */ Stopwatch timer;
    
    private void reportPlayer() {
        final EntityPlayer llIIllIIIII = AutoReport.mc.theWorld.playerEntities.get(MathUtil.getRandomInRange(0, AutoReport.mc.theWorld.playerEntities.size() - 1));
        AutoReport.mc.thePlayer.sendChatMessage(String.valueOf(new StringBuilder().append("/report ").append(llIIllIIIII.getName()).append(" bhop ka")));
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llIIllIIlII) {
        if (this.timer.timeElapsed(this.delay.getValue().longValue())) {
            this.reportPlayer();
            this.timer.resetTime();
        }
    }
    
    public AutoReport() {
        this.delay = new NumberValue("Delay (ms)", this, (T)100, (T)0, (T)100000, (T)1);
        this.timer = new Stopwatch();
    }
}
