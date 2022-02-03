package com.viaversion.viabackwards.listener;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.protocol.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class LecternInteractListener extends ViaBukkitListener
{
    public LecternInteractListener(final BukkitPlugin plugin) {
        super((Plugin)plugin, Protocol1_13_2To1_14.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLecternInteract(final PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.LECTERN) {
            return;
        }
        final Player player = event.getPlayer();
        if (!this.isOnPipe(player)) {
            return;
        }
        final Lectern lectern = (Lectern)block.getState();
        final ItemStack book = lectern.getInventory().getItem(0);
        if (book == null) {
            return;
        }
        final BookMeta meta = (BookMeta)book.getItemMeta();
        final ItemStack newBook = new ItemStack(Material.WRITTEN_BOOK);
        final BookMeta newBookMeta = (BookMeta)newBook.getItemMeta();
        newBookMeta.setPages(meta.getPages());
        newBookMeta.setAuthor("an upsidedown person");
        newBookMeta.setTitle("buk");
        newBook.setItemMeta((ItemMeta)newBookMeta);
        player.openBook(newBook);
        event.setCancelled(true);
    }
}
