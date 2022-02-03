package com.viaversion.viaversion;

import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;

public abstract class ViaListener
{
    private final Class<? extends Protocol> requiredPipeline;
    private boolean registered;
    
    protected ViaListener(final Class<? extends Protocol> requiredPipeline) {
        this.requiredPipeline = requiredPipeline;
    }
    
    protected UserConnection getUserConnection(final UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }
    
    protected boolean isOnPipe(final UUID uuid) {
        final UserConnection userConnection = this.getUserConnection(uuid);
        return userConnection != null && (this.requiredPipeline == null || userConnection.getProtocolInfo().getPipeline().contains(this.requiredPipeline));
    }
    
    public abstract void register();
    
    protected Class<? extends Protocol> getRequiredPipeline() {
        return this.requiredPipeline;
    }
    
    protected boolean isRegistered() {
        return this.registered;
    }
    
    protected void setRegistered(final boolean registered) {
        this.registered = registered;
    }
}
