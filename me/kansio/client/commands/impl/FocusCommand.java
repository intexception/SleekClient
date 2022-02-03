package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.*;
import me.kansio.client.utils.chat.*;

@CommandData(name = "focus", description = "Focuses a mentioned player")
public class FocusCommand extends Command
{
    @Override
    public void run(final String[] lllIlIllIl) {
        if (lllIlIllIl.length > 0) {
            Client.getInstance().getTargetManager().getTarget().add(lllIlIllIl[0]);
            ChatUtil.log("Added them as a target.");
        }
    }
}
