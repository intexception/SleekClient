package com.viaversion.viaversion;

import com.viaversion.viaversion.legacy.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.legacy.*;

public abstract class ViaAPIBase<T> implements ViaAPI<T>
{
    private final LegacyAPI<T> legacy;
    
    public ViaAPIBase() {
        this.legacy = new LegacyAPI<T>();
    }
    
    @Override
    public ServerProtocolVersion getServerVersion() {
        return Via.getManager().getProtocolManager().getServerProtocolVersion();
    }
    
    @Override
    public int getPlayerVersion(final UUID uuid) {
        final UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        return (connection != null) ? connection.getProtocolInfo().getProtocolVersion() : -1;
    }
    
    @Override
    public String getVersion() {
        return Via.getPlatform().getPluginVersion();
    }
    
    @Override
    public boolean isInjected(final UUID uuid) {
        return Via.getManager().getConnectionManager().isClientConnected(uuid);
    }
    
    @Override
    public UserConnection getConnection(final UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }
    
    @Override
    public void sendRawPacket(final UUID uuid, final ByteBuf packet) throws IllegalArgumentException {
        if (!this.isInjected(uuid)) {
            throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
        }
        final UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        user.scheduleSendRawPacket(packet);
    }
    
    @Override
    public SortedSet<Integer> getSupportedVersions() {
        final SortedSet<Integer> outputSet = new TreeSet<Integer>(Via.getManager().getProtocolManager().getSupportedVersions());
        final BlockedProtocolVersions blockedVersions = Via.getPlatform().getConf().blockedProtocolVersions();
        final SortedSet<Integer> set = outputSet;
        final BlockedProtocolVersions blockedProtocolVersions = blockedVersions;
        Objects.requireNonNull(blockedProtocolVersions);
        set.removeIf(blockedProtocolVersions::contains);
        return outputSet;
    }
    
    @Override
    public SortedSet<Integer> getFullSupportedVersions() {
        return Via.getManager().getProtocolManager().getSupportedVersions();
    }
    
    @Override
    public LegacyViaAPI<T> legacyAPI() {
        return this.legacy;
    }
}
