package com.viaversion.viaversion.velocity.command;

import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.velocity.command.subs.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;
import com.velocitypowered.api.command.*;

public class VelocityCommandHandler extends ViaCommandHandler implements SimpleCommand
{
    public VelocityCommandHandler() {
        try {
            this.registerSubCommand(new ProbeSubCmd());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void execute(final SimpleCommand.Invocation invocation) {
        this.onCommand(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }
    
    public List<String> suggest(final SimpleCommand.Invocation invocation) {
        return this.onTabComplete(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }
}
