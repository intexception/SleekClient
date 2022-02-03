package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.*;
import me.kansio.client.utils.chat.*;

@CommandData(name = "reload", description = "Used for reloading some of the client. used for debugging")
public class ReloadCommand extends Command
{
    @Override
    public void run(final String[] lIIIIIIllIlIIl) {
        Client.getInstance().getModuleManager().reloadModules();
        Client.getInstance().getKeybindManager().load();
        ChatUtil.log("Reloaded.");
        if (lIIIIIIllIlIIl.length == 1 && lIIIIIIllIlIIl[0].equalsIgnoreCase("all")) {
            Client.getInstance().getCommandManager().clearCommands();
            Client.getInstance().getCommandManager().registerCommands();
        }
    }
}
