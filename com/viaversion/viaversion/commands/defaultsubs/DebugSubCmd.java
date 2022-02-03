package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.debug.*;
import java.util.*;

public class DebugSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "debug";
    }
    
    @Override
    public String description() {
        return "Toggle debug mode";
    }
    
    @Override
    public boolean execute(final ViaCommandSender sender, final String[] args) {
        final DebugHandler debug = Via.getManager().debugHandler();
        if (args.length == 0) {
            Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
            ViaSubCommand.sendMessage(sender, "&6Debug mode is now %s", Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                debug.clearPacketTypesToLog();
                ViaSubCommand.sendMessage(sender, "&6Cleared packet types to log", new Object[0]);
                return true;
            }
            if (args[0].equalsIgnoreCase("logposttransform")) {
                debug.setLogPostPacketTransform(!debug.logPostPacketTransform());
                ViaSubCommand.sendMessage(sender, "&6Post transform packet logging is now %s", debug.logPostPacketTransform() ? "&aenabled" : "&cdisabled");
                return true;
            }
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                debug.addPacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
                ViaSubCommand.sendMessage(sender, "&6Added packet type %s to debug logging", args[1]);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                debug.removePacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
                ViaSubCommand.sendMessage(sender, "&6Removed packet type %s from debug logging", args[1]);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public List<String> onTabComplete(final ViaCommandSender sender, final String[] args) {
        if (args.length == 1) {
            return Arrays.asList("clear", "logposttransform", "add", "remove");
        }
        return Collections.emptyList();
    }
}
