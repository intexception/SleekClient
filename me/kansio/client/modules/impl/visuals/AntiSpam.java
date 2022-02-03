package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import java.util.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Anti Spammer", category = ModuleCategory.VISUALS, description = "Hides known spammer messages")
public class AntiSpam extends Module
{
    private /* synthetic */ List<String> spammers;
    
    public AntiSpam() {
        this.spammers = Arrays.asList("FDPClient", "moonclient.xyz", "Go And play with tired-client.de");
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIIIIllIllIIll) {
        if (lIIIIllIllIIll.getPacket() instanceof S02PacketChat) {
            final S02PacketChat lIIIIllIllIllI = lIIIIllIllIIll.getPacket();
            final String lIIIIllIllIlIl = lIIIIllIllIllI.getChatComponent().getFormattedText();
            if (lIIIIllIllIlIl.contains("Buy") && lIIIIllIllIlIl.contains("stop") && lIIIIllIllIlIl.contains("using")) {
                lIIIIllIllIIll.setCancelled(true);
                return;
            }
            for (final String lIIIIllIllIlll : this.spammers) {
                if (lIIIIllIllIlIl.toLowerCase().contains(lIIIIllIllIlll.toLowerCase())) {
                    System.out.println(String.valueOf(new StringBuilder().append("[Anti Spammer] Hid the message '").append(lIIIIllIllIlIl).append("'.")));
                    lIIIIllIllIIll.setCancelled(true);
                }
            }
        }
    }
}
