package com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.sponge.listeners.*;
import com.viaversion.viaversion.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.*;
import org.spongepowered.api.event.block.*;
import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.event.filter.cause.*;
import org.spongepowered.api.world.*;
import org.spongepowered.api.block.*;
import org.spongepowered.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import org.spongepowered.api.event.*;

public class BlockListener extends ViaSpongeListener
{
    public BlockListener(final SpongePlugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @Listener
    public void placeBlock(final ChangeBlockEvent.Place e, @Root final Player player) {
        if (this.isOnPipe(player.getUniqueId())) {
            final Location loc = ((BlockSnapshot)e.getTransactions().get(0).getFinal()).getLocation().get();
            final EntityTracker1_9 tracker = this.getUserConnection(player.getUniqueId()).getEntityTracker(Protocol1_9To1_8.class);
            tracker.addBlockInteraction(new Position(loc.getBlockX(), (short)loc.getBlockY(), loc.getBlockZ()));
        }
    }
}
