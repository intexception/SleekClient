package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.irc.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.event.impl.*;
import com.google.common.eventbus.*;
import java.net.*;

@ModuleData(name = "IRC", category = ModuleCategory.PLAYER, description = "Let's you chat with other client users")
public class IRC extends Module
{
    private /* synthetic */ IRCClient client;
    /* synthetic */ Stopwatch time;
    
    @Subscribe
    public void onChat(final ChatEvent lllllllllllllllllllllIllIIlIllll) {
        final String lllllllllllllllllllllIllIIlIlllI = lllllllllllllllllllllIllIIlIllll.getMessage();
        if (lllllllllllllllllllllIllIIlIlllI.startsWith("-") || lllllllllllllllllllllIllIIlIlllI.startsWith("- ")) {
            lllllllllllllllllllllIllIIlIllll.setCancelled(true);
            this.client.send(lllllllllllllllllllllIllIIlIllll.getMessage().replace("-", ""));
        }
    }
    
    public IRC() {
        super("IRC", ModuleCategory.PLAYER);
        this.time = new Stopwatch();
    }
    
    @Override
    public void onEnable() {
        this.time.resetTime();
        try {
            this.client = new IRCClient();
            this.client.connectBlocking();
        }
        catch (URISyntaxException | InterruptedException ex2) {
            final Exception ex;
            final Exception lllllllllllllllllllllIllIIlllIlI = ex;
            lllllllllllllllllllllIllIIlllIlI.printStackTrace();
        }
    }
    
    @Override
    public void onDisable() {
        this.client.close();
        this.client = null;
    }
}
