package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import java.util.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import com.viaversion.viaversion.api.*;

public class ArmorListener extends ViaBukkitListener
{
    private static final UUID ARMOR_ATTRIBUTE;
    
    public ArmorListener(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    public void sendArmorUpdate(final Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        int armor = 0;
        for (final ItemStack stack : player.getInventory().getArmorContents()) {
            armor += ArmorType.findById(stack.getTypeId()).getArmorPoints();
        }
        final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_PROPERTIES, null, this.getUserConnection(player));
        try {
            wrapper.write(Type.VAR_INT, player.getEntityId());
            wrapper.write(Type.INT, 1);
            wrapper.write(Type.STRING, "generic.armor");
            wrapper.write(Type.DOUBLE, 0.0);
            wrapper.write(Type.VAR_INT, 1);
            wrapper.write(Type.UUID, ArmorListener.ARMOR_ATTRIBUTE);
            wrapper.write(Type.DOUBLE, (double)armor);
            wrapper.write(Type.BYTE, (Byte)0);
            wrapper.scheduleSend(Protocol1_9To1_8.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent e) {
        final HumanEntity human = e.getWhoClicked();
        if (human instanceof Player && e.getInventory() instanceof CraftingInventory) {
            final Player player = (Player)human;
            if (e.getCurrentItem() != null && ArmorType.isArmor(e.getCurrentItem().getTypeId())) {
                this.sendDelayedArmorUpdate(player);
                return;
            }
            if (e.getRawSlot() >= 5 && e.getRawSlot() <= 8) {
                this.sendDelayedArmorUpdate(player);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            final Player player = e.getPlayer();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.getPlugin(), () -> this.sendArmorUpdate(player), 3L);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemBreak(final PlayerItemBreakEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRespawn(final PlayerRespawnEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        this.sendArmorUpdate(e.getPlayer());
    }
    
    public void sendDelayedArmorUpdate(final Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        Via.getPlatform().runSync(() -> this.sendArmorUpdate(player));
    }
    
    static {
        ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
    }
}
