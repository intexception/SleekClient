package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.commands.*;
import org.bukkit.command.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;

public class BukkitCommandHandler extends ViaCommandHandler implements CommandExecutor, TabExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return this.onCommand(new BukkitCommandSender(sender), args);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return this.onTabComplete(new BukkitCommandSender(sender), args);
    }
}
