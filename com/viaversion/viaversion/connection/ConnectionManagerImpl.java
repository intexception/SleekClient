package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import io.netty.channel.*;

public class ConnectionManagerImpl implements ConnectionManager
{
    protected final Map<UUID, UserConnection> clients;
    protected final Set<UserConnection> connections;
    
    public ConnectionManagerImpl() {
        this.clients = new ConcurrentHashMap<UUID, UserConnection>();
        this.connections = Collections.newSetFromMap(new ConcurrentHashMap<UserConnection, Boolean>());
    }
    
    @Override
    public void onLoginSuccess(final UserConnection connection) {
        Objects.requireNonNull(connection, "connection is null!");
        this.connections.add(connection);
        if (this.isFrontEnd(connection)) {
            final UUID id = connection.getProtocolInfo().getUuid();
            if (this.clients.put(id, connection) != null) {
                Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + id + ")");
            }
        }
        if (connection.getChannel() != null) {
            connection.getChannel().closeFuture().addListener(future -> this.onDisconnect(connection));
        }
    }
    
    @Override
    public void onDisconnect(final UserConnection connection) {
        Objects.requireNonNull(connection, "connection is null!");
        this.connections.remove(connection);
        if (this.isFrontEnd(connection)) {
            final UUID id = connection.getProtocolInfo().getUuid();
            this.clients.remove(id);
        }
    }
    
    @Override
    public Map<UUID, UserConnection> getConnectedClients() {
        return Collections.unmodifiableMap((Map<? extends UUID, ? extends UserConnection>)this.clients);
    }
    
    @Override
    public UserConnection getConnectedClient(final UUID clientIdentifier) {
        return this.clients.get(clientIdentifier);
    }
    
    @Override
    public UUID getConnectedClientId(final UserConnection connection) {
        if (connection.getProtocolInfo() == null) {
            return null;
        }
        final UUID uuid = connection.getProtocolInfo().getUuid();
        final UserConnection client = this.clients.get(uuid);
        if (connection.equals(client)) {
            return uuid;
        }
        return null;
    }
    
    @Override
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet((Set<? extends UserConnection>)this.connections);
    }
    
    @Override
    public boolean isClientConnected(final UUID playerId) {
        return this.clients.containsKey(playerId);
    }
}
