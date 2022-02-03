package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.chat.*;

@CommandData(name = "vclip", description = "Vertically clips you a certain amount")
public class VClipCommand extends Command
{
    @Override
    public void run(final String[] llIIllIllllIII) {
        if (llIIllIllllIII.length > 0) {
            ChatUtil.log(String.valueOf(new StringBuilder().append("Vclipped ").append(Integer.parseInt(llIIllIllllIII[0])).append(" Blocks")));
            VClipCommand.mc.thePlayer.setPosition(VClipCommand.mc.thePlayer.posX, VClipCommand.mc.thePlayer.posY + Integer.parseInt(llIIllIllllIII[0]), VClipCommand.mc.thePlayer.posZ);
        }
        else {
            ChatUtil.log(".vclip <amount>");
        }
    }
}
