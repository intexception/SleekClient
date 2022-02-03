package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.command.*;
import com.viaversion.viaversion.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.spongepowered.api.text.serializer.*;
import java.util.*;
import org.spongepowered.api.util.*;

public class SpongeCommandSender implements ViaCommandSender
{
    private final CommandSource source;
    
    public SpongeCommandSender(final CommandSource source) {
        this.source = source;
    }
    
    @Override
    public boolean hasPermission(final String permission) {
        return this.source.hasPermission(permission);
    }
    
    @Override
    public void sendMessage(final String msg) {
        final String serialized = SpongePlugin.COMPONENT_SERIALIZER.serialize((Component)SpongePlugin.COMPONENT_SERIALIZER.deserialize(msg));
        this.source.sendMessage(TextSerializers.JSON.deserialize(serialized));
    }
    
    @Override
    public UUID getUUID() {
        if (this.source instanceof Identifiable) {
            return ((Identifiable)this.source).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }
    
    @Override
    public String getName() {
        return this.source.getName();
    }
}
