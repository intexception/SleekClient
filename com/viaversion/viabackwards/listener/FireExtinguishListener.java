package com.viaversion.viabackwards.listener;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.protocol.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class FireExtinguishListener extends ViaBukkitListener
{
    public FireExtinguishListener(final BukkitPlugin plugin) {
        super((Plugin)plugin, Protocol1_15_2To1_16.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFireExtinguish(final PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        final Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        final Player player = event.getPlayer();
        if (!this.isOnPipe(player)) {
            return;
        }
        final Block relative = block.getRelative(event.getBlockFace());
        if (relative.getType() == Material.FIRE) {
            event.setCancelled(true);
            relative.setType(Material.AIR);
        }
    }
}
