package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.client.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Auto Settings", category = ModuleCategory.PLAYER, description = "Attempts to set up good settings for the client")
public class AutoSettings extends Module
{
    private /* synthetic */ BooleanValue debugC0F;
    private /* synthetic */ long currTime;
    
    @Subscribe
    public void onPacket(final PacketEvent llIlIIIlllIIl) {
        if (llIlIIIlllIIl.getPacket() instanceof C0FPacketConfirmTransaction) {}
    }
    
    public AutoSettings() {
        this.currTime = System.currentTimeMillis();
        this.debugC0F = new BooleanValue("Test C0F", this, true);
    }
}
