package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.bungee.commands.subs.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;

public class BungeeCommandHandler extends ViaCommandHandler
{
    public BungeeCommandHandler() {
        try {
            this.registerSubCommand(new ProbeSubCmd());
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Failed to register Bungee subcommands");
            e.printStackTrace();
        }
    }
}
