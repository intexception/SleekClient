package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;
import me.kansio.client.utils.chat.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Auto Register", description = "Automatically registers you", category = ModuleCategory.PLAYER)
public class AutoRegister extends Module
{
    @Subscribe
    public void onChat(final PacketEvent lIIlIIIIIlIIl) {
        if (lIIlIIIIIlIIl.getPacket() instanceof S02PacketChat) {
            final S02PacketChat lIIlIIIIIllII = lIIlIIIIIlIIl.getPacket();
            if (lIIlIIIIIllII.getChatComponent().getUnformattedText().contains("/register <password> <repeat password>")) {
                PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/register SleekCheat SleekCheat"));
                ChatUtil.log("Automatically registered with password 'SleekCheat'");
            }
        }
    }
}
