package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.chat.*;
import java.awt.*;
import java.awt.datatransfer.*;

@CommandData(name = "name", description = "Copies the current account name")
public class NameCommand extends Command
{
    @Override
    public void run(final String[] lIllIlllIIllII) {
        final String lIllIlllIIlIll = NameCommand.mc.thePlayer.getName();
        ChatUtil.log(String.valueOf(new StringBuilder().append("Your Name Is: ").append(lIllIlllIIlIll).append(", And Has Been Copied To Your Clipboard")));
        final StringSelection lIllIlllIIlIlI = new StringSelection(lIllIlllIIlIll);
        final Clipboard lIllIlllIIlIIl = Toolkit.getDefaultToolkit().getSystemClipboard();
        lIllIlllIIlIIl.setContents(lIllIlllIIlIlI, lIllIlllIIlIlI);
    }
}
