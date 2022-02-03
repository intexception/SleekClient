package me.kansio.client.utils.chat;

import me.kansio.client.utils.*;
import net.minecraft.util.*;

public class ChatUtil extends Util
{
    public static void log(final String lIlIlIlIIlII) {
        ChatUtil.mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(new StringBuilder().append("§bSleek §7» §f").append(lIlIlIlIIlII))));
    }
    
    public static String translateColorCodes(final String lIlIlIIIllll) {
        return lIlIlIIIllll.replaceAll("&", "§");
    }
    
    public static void logNoPrefix(final String lIlIlIlIIIIl) {
        ChatUtil.mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(new StringBuilder().append("§f").append(lIlIlIlIIIIl))));
    }
    
    public static void logVerusCheater(final String lIlIlIIlIlII, final String lIlIlIIlIIll, final String lIlIlIIlIIlI) {
        ChatUtil.mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(new StringBuilder().append("§9§lVerus §8§l> §f").append(lIlIlIIlIlII).append(" §7failed §f ").append(lIlIlIIlIIll).append(" §7VL[§9").append(lIlIlIIlIIlI).append("§7]"))));
    }
    
    public static void logSleekCheater(final String lIlIlIIlllII, final String lIlIlIIllIll) {
        ChatUtil.mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(new StringBuilder().append("§7[§b§lSleekAC§7] §f").append(lIlIlIIlllII).append(" §7might be using §b").append(lIlIlIIllIll))));
    }
}
