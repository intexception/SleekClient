package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.utils.chat.*;

@CommandData(name = "hclip", description = "Horizontally clips you a certain amount")
public class HClipCommand extends Command
{
    @Override
    public void run(final String[] llllllIIIlllI) {
        try {
            final double llllllIIlIIll = Double.parseDouble(llllllIIIlllI[0]);
            final double[] llllllIIlIIlI = PlayerUtil.teleportForward(llllllIIlIIll);
            HClipCommand.mc.thePlayer.setPosition(HClipCommand.mc.thePlayer.posX + llllllIIlIIlI[0], HClipCommand.mc.thePlayer.posY, HClipCommand.mc.thePlayer.posZ + llllllIIlIIlI[1]);
            ChatUtil.log(String.valueOf(new StringBuilder().append("Clipped ").append((llllllIIlIIll == 1.0) ? "1 block" : String.valueOf(new StringBuilder().append(llllllIIlIIll).append(" blocks")))));
        }
        catch (Exception llllllIIlIIIl) {
            llllllIIlIIIl.printStackTrace();
            ChatUtil.log(".hclip <amount>");
        }
    }
}
