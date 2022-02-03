package com.viaversion.viaversion.velocity.command;

import com.viaversion.viaversion.api.command.*;
import com.velocitypowered.api.command.*;
import com.viaversion.viaversion.*;
import net.kyori.adventure.text.*;
import java.util.*;
import com.velocitypowered.api.proxy.*;

public class VelocityCommandSender implements ViaCommandSender
{
    private final CommandSource source;
    
    public VelocityCommandSender(final CommandSource source) {
        this.source = source;
    }
    
    @Override
    public boolean hasPermission(final String permission) {
        return this.source.hasPermission(permission);
    }
    
    @Override
    public void sendMessage(final String msg) {
        this.source.sendMessage((Component)VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
    }
    
    @Override
    public UUID getUUID() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }
    
    @Override
    public String getName() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUsername();
        }
        return "?";
    }
}
