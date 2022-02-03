package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.*;
import org.spongepowered.api.entity.living.player.*;
import io.netty.buffer.*;

public class SpongeViaAPI extends ViaAPIBase<Player>
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
