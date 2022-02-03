package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.utils.render.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.handshake.client.*;

@ModuleData(name = "Playtime", category = ModuleCategory.VISUALS, description = "Shows how long you've been on for")
public class Playtime extends Module
{
    public /* synthetic */ long currentTime;
    
    @Override
    public void onEnable() {
        this.currentTime = System.currentTimeMillis();
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent lIlllIIIllIl) {
        if (Playtime.mc.currentScreen != null) {
            return;
        }
        final long lIlllIIIllII = System.currentTimeMillis() - this.currentTime;
        final int lIlllIIIlIll = (int)(lIlllIIIllII / 1000L) % 60;
        final int lIlllIIIlIlI = (int)(lIlllIIIllII / 60000L % 60L);
        final int lIlllIIIlIIl = (int)(lIlllIIIllII / 3600000L % 24L);
        Playtime.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append(lIlllIIIlIIl).append("h ").append(lIlllIIIlIlI).append("m, ").append(lIlllIIIlIll).append("s")), (float)(RenderUtils.getResolution().getScaledWidth() / 2 - 20), 20.0f, -1);
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIlllIIlIllI) {
        if (lIlllIIlIllI.getPacket() instanceof C00Handshake) {
            this.currentTime = System.currentTimeMillis();
        }
    }
}
