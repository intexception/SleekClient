package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.configuration.*;

public class DontBugMeSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "dontbugme";
    }
    
    @Override
    public String description() {
        return "Toggle checking for updates";
    }
    
    @Override
    public boolean execute(final ViaCommandSender sender, final String[] args) {
        final ConfigurationProvider provider = Via.getPlatform().getConfigurationProvider();
        final boolean newValue = !Via.getConfig().isCheckForUpdates();
        Via.getConfig().setCheckForUpdates(newValue);
        provider.saveConfig();
        ViaSubCommand.sendMessage(sender, "&6We will %snotify you about updates.", newValue ? "&a" : "&cnot ");
        return true;
    }
}
