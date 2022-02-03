package me.kansio.client.modules.impl.player.hackerdetect.checks.chat;

import me.kansio.client.modules.impl.player.hackerdetect.checks.*;
import java.util.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import me.kansio.client.*;

public class ChatA extends Check
{
    private /* synthetic */ List<String> flagged;
    
    public ChatA() {
        this.flagged = Arrays.asList("[FDPClient]");
    }
    
    @Override
    public void onPacket(final PacketEvent lIlIllIIllIll) {
        if (lIlIllIIllIll.getPacket() instanceof S02PacketChat) {
            final String lIlIllIIlllIl = lIlIllIIllIll.getPacket().getChatComponent().getFormattedText();
            for (final String lIlIllIIllllI : this.flagged) {
                if (lIlIllIIlllIl.contains(lIlIllIIllllI)) {
                    final String lIlIllIIlllll = lIlIllIIlllIl.split(" ")[0].replace("§r§9", "").replace("§r§8", "");
                    if (!Client.getInstance().getTargetManager().isTarget(lIlIllIIlllll)) {
                        Client.getInstance().getTargetManager().getTarget().add(lIlIllIIlllll);
                    }
                }
            }
        }
    }
    
    @Override
    public String name() {
        return "Chat (Type A)";
    }
}
