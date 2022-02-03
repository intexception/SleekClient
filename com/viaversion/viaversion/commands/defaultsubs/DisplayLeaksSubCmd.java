package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import io.netty.util.*;

public class DisplayLeaksSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "displayleaks";
    }
    
    @Override
    public String description() {
        return "Try to hunt memory leaks!";
    }
    
    @Override
    public boolean execute(final ViaCommandSender sender, final String[] args) {
        if (ResourceLeakDetector.getLevel() != ResourceLeakDetector.Level.PARANOID) {
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        }
        else {
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
        }
        ViaSubCommand.sendMessage(sender, "&6Leak detector is now %s", (ResourceLeakDetector.getLevel() == ResourceLeakDetector.Level.PARANOID) ? "&aenabled" : "&cdisabled");
        return true;
    }
}
