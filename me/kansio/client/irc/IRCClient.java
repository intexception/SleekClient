package me.kansio.client.irc;

import org.java_websocket.client.*;
import net.minecraft.client.*;
import me.kansio.client.utils.chat.*;
import net.minecraft.util.*;
import org.java_websocket.handshake.*;
import me.kansio.client.modules.impl.player.*;
import me.kansio.client.*;
import java.net.*;

public class IRCClient extends WebSocketClient
{
    public static /* synthetic */ char SPLIT;
    
    @Override
    public void onMessage(final String llllllllllllllllllllllIIIIlIllII) {
        System.out.println(llllllllllllllllllllllIIIIlIllII);
        if (llllllllllllllllllllllIIIIlIllII.contains(Character.toString(IRCClient.SPLIT))) {
            final String[] llllllllllllllllllllllIIIIllIlll = llllllllllllllllllllllIIIIlIllII.split(Character.toString(IRCClient.SPLIT));
            if (llllllllllllllllllllllIIIIllIlll.length != 3) {
                return;
            }
            final String llllllllllllllllllllllIIIIllIlIl = llllllllllllllllllllllIIIIllIlll[0];
            String llllllllllllllllllllllIIIIllIIll = llllllllllllllllllllllIIIIllIlll[1];
            final String llllllllllllllllllllllIIIIllIIIl = llllllllllllllllllllllIIIIllIlll[2];
            llllllllllllllllllllllIIIIllIIll = llllllllllllllllllllllIIIIllIIll.replace("(", "§7(§b").replace(")", "§7)");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatUtil.translateColorCodes(String.valueOf(new StringBuilder().append("§7[§bIRC§7] §b").append(llllllllllllllllllllllIIIIllIlIl).append(llllllllllllllllllllllIIIIllIIll).append(" §f: ").append(llllllllllllllllllllllIIIIllIIIl)))));
        }
        else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(new StringBuilder().append("§7[§bIRC§7] ").append(llllllllllllllllllllllIIIIlIllII))));
        }
    }
    
    @Override
    public void onOpen(final ServerHandshake llllllllllllllllllllllIIIlIIlllI) {
        System.out.println("IRC Connected");
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§bIRC§7] §fConnected"));
    }
    
    @Override
    public void onClose(final int llllllllllllllllllllllIIIIlIIIll, final String llllllllllllllllllllllIIIIlIIIIl, final boolean llllllllllllllllllllllIIIIIlllll) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§bIRC§7] §fDisconnected"));
        final IRC llllllllllllllllllllllIIIIIlllIl = (IRC)Client.getInstance().getModuleManager().getModuleByName("IRC");
        if (llllllllllllllllllllllIIIIIlllIl.isToggled()) {
            llllllllllllllllllllllIIIIIlllIl.toggle();
        }
    }
    
    static {
        IRCClient.SPLIT = '\0';
    }
    
    public IRCClient() throws URISyntaxException {
        super(new URI("ws://zerotwoclient.xyz:1337"));
        this.setAttachment(String.valueOf(new StringBuilder().append(Client.getInstance().getRank().getColor().toString().replace("§", "&")).append(Client.getInstance().getUsername())));
        this.addHeader("name", this.getAttachment());
        this.addHeader("uid", Client.getInstance().getUid());
    }
    
    @Override
    public void onError(final Exception llllllllllllllllllllllIIIIIlIIll) {
        llllllllllllllllllllllIIIIIlIIll.printStackTrace();
    }
}
