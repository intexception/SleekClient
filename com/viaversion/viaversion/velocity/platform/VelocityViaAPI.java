package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.*;
import com.velocitypowered.api.proxy.*;
import io.netty.buffer.*;

public class VelocityViaAPI extends ViaAPIBase<Player>
{
    @Override
    public int getPlayerVersion(final Player player) {
        return this.getPlayerVersion(player.getUniqueId());
    }
    
    @Override
    public void sendRawPacket(final Player player, final ByteBuf packet) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), packet);
    }
}
