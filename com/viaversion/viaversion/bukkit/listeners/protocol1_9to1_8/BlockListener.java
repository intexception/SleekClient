package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.*;
import org.bukkit.event.block.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class BlockListener extends ViaBukkitListener
{
    public BlockListener(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void placeBlock(final BlockPlaceEvent e) {
        if (this.isOnPipe(e.getPlayer())) {
            final Block b = e.getBlockPlaced();
            final EntityTracker1_9 tracker = this.getUserConnection(e.getPlayer()).getEntityTracker(Protocol1_9To1_8.class);
            tracker.addBlockInteraction(new Position(b.getX(), (short)b.getY(), b.getZ()));
        }
    }
}
