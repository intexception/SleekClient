package com.viaversion.viaversion.bungee.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.command.*;

public class BungeeCommand extends Command implements TabExecutor
{
    private final BungeeCommandHandler handler;
    
    public BungeeCommand(final BungeeCommandHandler handler) {
        super("viaversion", "", new String[] { "viaver", "vvbungee" });
        this.handler = handler;
    }
    
    public void execute(final CommandSender commandSender, final String[] strings) {
        this.handler.onCommand(new BungeeCommandSender(commandSender), strings);
    }
    
    public Iterable<String> onTabComplete(final CommandSender commandSender, final String[] strings) {
        return this.handler.onTabComplete(new BungeeCommandSender(commandSender), strings);
    }
}
