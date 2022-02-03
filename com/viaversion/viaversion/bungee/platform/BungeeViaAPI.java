package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.*;
import net.md_5.bungee.api.connection.*;
import io.netty.buffer.*;
import net.md_5.bungee.api.config.*;
import com.viaversion.viaversion.bungee.service.*;

public class BungeeViaAPI extends ViaAPIBase<ProxiedPlayer>
{
    @Override
    public int getPlayerVersion(final ProxiedPlayer player) {
        return this.getPlayerVersion(player.getUniqueId());
    }
    
    @Override
    public void sendRawPacket(final ProxiedPlayer player, final ByteBuf packet) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), packet);
    }
    
    public void probeServer(final ServerInfo serverInfo) {
        ProtocolDetectorService.probeServer(serverInfo);
    }
}
