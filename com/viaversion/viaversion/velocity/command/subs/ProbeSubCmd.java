package com.viaversion.viaversion.velocity.command.subs;

import com.viaversion.viaversion.velocity.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.velocity.service.*;

public class ProbeSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "probe";
    }
    
    @Override
    public String description() {
        return "Forces ViaVersion to scan server protocol versions " + ((((VelocityViaConfig)Via.getConfig()).getVelocityPingInterval() == -1) ? "" : "(Also happens at an interval)");
    }
    
    @Override
    public boolean execute(final ViaCommandSender sender, final String[] args) {
        ProtocolDetectorService.getInstance().run();
        ViaSubCommand.sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
        return true;
    }
}
