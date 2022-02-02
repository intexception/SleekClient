package com.viaversion.viaversion.bungee.handlers;

import net.md_5.bungee.api.plugin.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bungee.storage.*;
import com.viaversion.viaversion.bungee.service.*;
import java.lang.reflect.*;
import net.md_5.bungee.event.*;
import net.md_5.bungee.api.event.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import net.md_5.bungee.protocol.packet.*;
import net.md_5.bungee.api.score.*;
import net.md_5.bungee.api.connection.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.*;

public class BungeeServerHandler implements Listener
{
    private static Method getHandshake;
    private static Method getRegisteredChannels;
    private static Method getBrandMessage;
    private static Method setProtocol;
    private static Method getEntityMap;
    private static Method setVersion;
    private static Field entityRewrite;
    private static Field channelWrapper;
    
    @EventHandler(priority = 120)
    public void onServerConnect(final ServerConnectEvent e) {
        if (e.isCancelled()) {
            return;
        }
        final UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId());
        if (user == null) {
            return;
        }
        if (!user.has(BungeeStorage.class)) {
            user.put(new BungeeStorage(e.getPlayer()));
        }
        final int protocolId = ProtocolDetectorService.getProtocolId(e.getTarget().getName());
        final List<ProtocolPathEntry> protocols = Via.getManager().getProtocolManager().getProtocolPath(user.getProtocolInfo().getProtocolVersion(), protocolId);
        try {
            final Object handshake = BungeeServerHandler.getHandshake.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
            BungeeServerHandler.setProtocol.invoke(handshake, (protocols == null) ? user.getProtocolInfo().getProtocolVersion() : protocolId);
        }
        catch (InvocationTargetException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e2 = ex;
            e2.printStackTrace();
        }
    }
    
    @EventHandler(priority = -120)
    public void onServerConnected(final ServerConnectedEvent e) {
        try {
            this.checkServerChange(e, Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId()));
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    @EventHandler(priority = -120)
    public void onServerSwitch(final ServerSwitchEvent e) {
        final UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        int playerId;
        try {
            playerId = Via.getManager().getProviders().get(EntityIdProvider.class).getEntityId(userConnection);
        }
        catch (Exception ex) {
            return;
        }
        for (final EntityTracker tracker : userConnection.getEntityTrackers()) {
            tracker.setClientEntityId(playerId);
        }
        for (final StorableObject object : userConnection.getStoredObjects().values()) {
            if (object instanceof ClientEntityIdChangeListener) {
                ((ClientEntityIdChangeListener)object).setClientEntityId(playerId);
            }
        }
    }
    
    public void checkServerChange(final ServerConnectedEvent e, final UserConnection user) throws Exception {
        if (user == null) {
            return;
        }
        if (user.has(BungeeStorage.class)) {
            final BungeeStorage storage = user.get(BungeeStorage.class);
            final ProxiedPlayer player = storage.getPlayer();
            if (e.getServer() != null && !e.getServer().getInfo().getName().equals(storage.getCurrentServer())) {
                final EntityTracker1_9 oldEntityTracker = user.getEntityTracker(Protocol1_9To1_8.class);
                if (oldEntityTracker != null && oldEntityTracker.isAutoTeam() && oldEntityTracker.isTeamExists()) {
                    oldEntityTracker.sendTeamPacket(false, true);
                }
                final String serverName = e.getServer().getInfo().getName();
                storage.setCurrentServer(serverName);
                int protocolId = ProtocolDetectorService.getProtocolId(serverName);
                if (protocolId <= ProtocolVersion.v1_8.getVersion() && storage.getBossbar() != null) {
                    if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                        for (final UUID uuid : storage.getBossbar()) {
                            final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, user);
                            wrapper.write(Type.UUID, uuid);
                            wrapper.write(Type.VAR_INT, 1);
                            wrapper.send(Protocol1_9To1_8.class);
                        }
                    }
                    storage.getBossbar().clear();
                }
                final ProtocolInfo info = user.getProtocolInfo();
                final int previousServerProtocol = info.getServerProtocolVersion();
                final List<ProtocolPathEntry> protocolPath = Via.getManager().getProtocolManager().getProtocolPath(info.getProtocolVersion(), protocolId);
                final ProtocolPipeline pipeline = user.getProtocolInfo().getPipeline();
                user.clearStoredObjects();
                pipeline.cleanPipes();
                if (protocolPath == null) {
                    protocolId = info.getProtocolVersion();
                }
                else {
                    final List<Protocol> protocols = new ArrayList<Protocol>(protocolPath.size());
                    for (final ProtocolPathEntry entry : protocolPath) {
                        protocols.add(entry.protocol());
                    }
                    pipeline.add(protocols);
                }
                info.setServerProtocolVersion(protocolId);
                pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(protocolId));
                final int id1_13 = ProtocolVersion.v1_13.getVersion();
                final boolean toNewId = previousServerProtocol < id1_13 && protocolId >= id1_13;
                final boolean toOldId = previousServerProtocol >= id1_13 && protocolId < id1_13;
                if (previousServerProtocol != -1 && (toNewId || toOldId)) {
                    final Collection<String> registeredChannels = (Collection<String>)BungeeServerHandler.getRegisteredChannels.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
                    if (!registeredChannels.isEmpty()) {
                        final Collection<String> newChannels = new HashSet<String>();
                        final Iterator<String> iterator = registeredChannels.iterator();
                        while (iterator.hasNext()) {
                            final String oldChannel;
                            String channel = oldChannel = iterator.next();
                            if (toNewId) {
                                channel = InventoryPackets.getNewPluginChannelId(channel);
                            }
                            else {
                                channel = InventoryPackets.getOldPluginChannelId(channel);
                            }
                            if (channel == null) {
                                iterator.remove();
                            }
                            else {
                                if (oldChannel.equals(channel)) {
                                    continue;
                                }
                                iterator.remove();
                                newChannels.add(channel);
                            }
                        }
                        registeredChannels.addAll(newChannels);
                    }
                    final PluginMessage brandMessage = (PluginMessage)BungeeServerHandler.getBrandMessage.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
                    if (brandMessage != null) {
                        String channel2 = brandMessage.getTag();
                        if (toNewId) {
                            channel2 = InventoryPackets.getNewPluginChannelId(channel2);
                        }
                        else {
                            channel2 = InventoryPackets.getOldPluginChannelId(channel2);
                        }
                        if (channel2 != null) {
                            brandMessage.setTag(channel2);
                        }
                    }
                }
                user.put(storage);
                user.setActive(protocolPath != null);
                for (final Protocol protocol : pipeline.pipes()) {
                    protocol.init(user);
                }
                final EntityTracker1_9 newTracker = user.getEntityTracker(Protocol1_9To1_8.class);
                if (newTracker != null && Via.getConfig().isAutoTeam()) {
                    String currentTeam = null;
                    for (final Team team : player.getScoreboard().getTeams()) {
                        if (team.getPlayers().contains(info.getUsername())) {
                            currentTeam = team.getName();
                        }
                    }
                    newTracker.setAutoTeam(true);
                    if (currentTeam == null) {
                        newTracker.sendTeamPacket(true, true);
                        newTracker.setCurrentTeam("viaversion");
                    }
                    else {
                        newTracker.setAutoTeam(Via.getConfig().isAutoTeam());
                        newTracker.setCurrentTeam(currentTeam);
                    }
                }
                final Object wrapper2 = BungeeServerHandler.channelWrapper.get(player);
                BungeeServerHandler.setVersion.invoke(wrapper2, protocolId);
                final Object entityMap = BungeeServerHandler.getEntityMap.invoke(null, protocolId);
                BungeeServerHandler.entityRewrite.set(player, entityMap);
            }
        }
    }
    
    static {
        BungeeServerHandler.getEntityMap = null;
        BungeeServerHandler.setVersion = null;
        BungeeServerHandler.entityRewrite = null;
        BungeeServerHandler.channelWrapper = null;
        try {
            BungeeServerHandler.getHandshake = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getHandshake", (Class<?>[])new Class[0]);
            BungeeServerHandler.getRegisteredChannels = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRegisteredChannels", (Class<?>[])new Class[0]);
            BungeeServerHandler.getBrandMessage = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getBrandMessage", (Class<?>[])new Class[0]);
            BungeeServerHandler.setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", Integer.TYPE);
            BungeeServerHandler.getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", Integer.TYPE);
            BungeeServerHandler.setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", Integer.TYPE);
            (BungeeServerHandler.channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch")).setAccessible(true);
            (BungeeServerHandler.entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite")).setAccessible(true);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Error initializing BungeeServerHandler, try updating BungeeCord or ViaVersion!");
            e.printStackTrace();
        }
    }
}
