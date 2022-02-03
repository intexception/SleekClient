package com.viaversion.viaversion.bukkit.platform;

import org.bukkit.entity.*;
import com.viaversion.viaversion.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import org.bukkit.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;

public class BukkitViaAPI extends ViaAPIBase<Player>
{
    private final ViaVersionPlugin plugin;
    
    public BukkitViaAPI(final ViaVersionPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public int getPlayerVersion(final Player player) {
        return this.getPlayerVersion(player.getUniqueId());
    }
    
    @Override
    public int getPlayerVersion(final UUID uuid) {
        final UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connection != null) {
            return connection.getProtocolInfo().getProtocolVersion();
        }
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null && this.isProtocolSupport()) {
            return ProtocolSupportUtil.getProtocolVersion(player);
        }
        return -1;
    }
    
    @Override
    public void sendRawPacket(final Player player, final ByteBuf packet) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), packet);
    }
    
    public boolean isCompatSpigotBuild() {
        return this.plugin.isCompatSpigotBuild();
    }
    
    public boolean isProtocolSupport() {
        return this.plugin.isProtocolSupport();
    }
}
