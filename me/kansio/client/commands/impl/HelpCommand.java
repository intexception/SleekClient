package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.*;
import java.util.*;
import java.text.*;
import me.kansio.client.utils.chat.*;

@CommandData(name = "help", description = "Displays this message", aliases = { "h" })
public class HelpCommand extends Command
{
    @Override
    public void run(final String[] llllllllllllllllllllllIIIlIllllI) {
        for (final Command llllllllllllllllllllllIIIllIIIII : Client.getInstance().getCommandManager().getCommands()) {
            final CommandData llllllllllllllllllllllIIIllIIlIl = llllllllllllllllllllllIIIllIIIII.getClass().getAnnotation(CommandData.class);
            final String llllllllllllllllllllllIIIllIIlII = llllllllllllllllllllllIIIllIIlIl.name();
            final String llllllllllllllllllllllIIIllIIIll = llllllllllllllllllllllIIIllIIlIl.description();
            final StringBuilder llllllllllllllllllllllIIIllIIIIl = new StringBuilder();
            final int llllllllllllllllllllllIIIlIlIllI = (Object)llllllllllllllllllllllIIIllIIlIl.aliases();
            final long llllllllllllllllllllllIIIlIlIlIl = llllllllllllllllllllllIIIlIlIllI.length;
            for (final String llllllllllllllllllllllIIIllIIllI : llllllllllllllllllllllIIIlIlIllI) {
                llllllllllllllllllllllIIIllIIIIl.append(llllllllllllllllllllllIIIllIIllI);
                if (Arrays.asList(llllllllllllllllllllllIIIllIIlIl.aliases()).indexOf(llllllllllllllllllllllIIIllIIllI) != llllllllllllllllllllllIIIllIIlIl.aliases().length - 1) {
                    llllllllllllllllllllllIIIllIIIIl.append(",");
                }
            }
            final String llllllllllllllllllllllIIIllIIIlI = String.valueOf(llllllllllllllllllllllIIIllIIIIl);
            ChatUtil.log(MessageFormat.format("{0} - {1} ({2})", llllllllllllllllllllllIIIllIIlII, llllllllllllllllllllllIIIllIIIll, llllllllllllllllllllllIIIllIIIlI));
        }
    }
}
