package com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.sponge.listeners.*;
import com.viaversion.viaversion.*;
import com.viaversion.viaversion.api.protocol.*;
import org.spongepowered.api.event.entity.*;
import org.spongepowered.api.entity.living.player.*;
import com.viaversion.viaversion.api.*;
import org.spongepowered.api.event.*;
import org.spongepowered.api.world.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;

public class DeathListener extends ViaSpongeListener
{
    public DeathListener(final SpongePlugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @Listener(order = Order.LAST)
    public void onDeath(final DestructEntityEvent.Death e) {
        if (!(e.getTargetEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getTargetEntity();
        if (this.isOnPipe(p.getUniqueId()) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(p.getWorld())) {
            this.sendPacket(p, e.getMessage().toPlain());
        }
    }
    
    public boolean checkGamerule(final World w) {
        final Optional<String> gamerule = (Optional<String>)w.getGameRule("showDeathMessages");
        if (gamerule.isPresent()) {
            try {
                return Boolean.parseBoolean(gamerule.get());
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    
    private void sendPacket(final Player p, final String msg) {
        Via.getPlatform().runSync(new Runnable() {
            @Override
            public void run() {
                final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.COMBAT_EVENT, null, ViaListener.this.getUserConnection(p.getUniqueId()));
                try {
                    final int entityId = ViaSpongeListener.this.getEntityId(p);
                    wrapper.write(Type.VAR_INT, 2);
                    wrapper.write(Type.VAR_INT, entityId);
                    wrapper.write(Type.INT, entityId);
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, msg);
                    wrapper.scheduleSend(Protocol1_9To1_8.class);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
