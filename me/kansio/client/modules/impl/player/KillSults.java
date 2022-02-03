package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import com.google.common.eventbus.*;
import me.kansio.client.*;
import org.apache.commons.lang3.*;
import java.util.*;
import me.kansio.client.value.*;

@ModuleData(name = "Kill Insults", category = ModuleCategory.PLAYER, description = "Test Module...")
public class KillSults extends Module
{
    private final /* synthetic */ ModeValue modeValue;
    
    public KillSults() {
        this.modeValue = new ModeValue("Mode", this, new String[] { "BlocksMC" });
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIlIlIIIIlIIlI) {
        if (lIlIlIIIIlIIlI.getPacket() instanceof S02PacketChat) {
            final S02PacketChat lIlIlIIIIlIlll = lIlIlIIIIlIIlI.getPacket();
            final String lIlIlIIIIlIllI = lIlIlIIIIlIlll.getChatComponent().getFormattedText();
            final short lIlIlIIIIIllll = ((Value<Short>)this.modeValue).getValue();
            short lIlIlIIIIIlllI = -1;
            switch (((String)lIlIlIIIIIllll).hashCode()) {
                case -599920196: {
                    if (((String)lIlIlIIIIIllll).equals("BlocksMC")) {
                        lIlIlIIIIIlllI = 0;
                        break;
                    }
                    break;
                }
            }
            switch (lIlIlIIIIIlllI) {
                case 0: {
                    if (lIlIlIIIIlIllI.contains("for killing")) {
                        this.sendKillSult(lIlIlIIIIlIllI.split(" ")[11]);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void sendKillSult(final String lIlIlIIIIIlIII) {
        final List<String> lIlIlIIIIIlIIl = Arrays.asList("You got sleeked L", "Sleek is just better...", "Verus got killed by Sleek", "You just got absolutely raped by Sleek :)", "Sleek too op I guess", String.valueOf(new StringBuilder().append("You got killed by ").append(Client.getInstance().getUsername()).append("(uid: ").append(Client.getInstance().getUid()).append(") using Sleek hake")), "We do be doing slight amounts of trolling using Sleek", "me and da sleek bois destroying blocksmc", "sussy among us sleek hack???", String.valueOf(new StringBuilder().append("mad? rage at me on discord: ").append(Client.getInstance().getDiscordTag())), String.valueOf(new StringBuilder().append("got angry? rage at me on discord: ").append(Client.getInstance().getDiscordTag())), String.valueOf(new StringBuilder().append("rage at me on discord: ").append(Client.getInstance().getDiscordTag())), String.valueOf(new StringBuilder().append("mad? rage at me on discord: ").append(Client.getInstance().getDiscordTag()).append(" :troll:")), "like da hack? https://discord.gg/GUauVwtFKj", "hack too good? get it here: https://discord.gg/GUauVwtFKj");
        KillSults.mc.thePlayer.sendChatMessage(lIlIlIIIIIlIIl.get(RandomUtils.nextInt(0, lIlIlIIIIIlIIl.size() - 1)).replaceAll("%name%", lIlIlIIIIIlIII));
    }
}
