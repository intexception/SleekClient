package com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1;

import com.viaversion.viaversion.bukkit.providers.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class BukkitInventoryUpdateTask implements Runnable
{
    private final BukkitInventoryQuickMoveProvider provider;
    private final UUID uuid;
    private final List<ItemTransaction> items;
    
    public BukkitInventoryUpdateTask(final BukkitInventoryQuickMoveProvider provider, final UUID uuid) {
        this.provider = provider;
        this.uuid = uuid;
        this.items = Collections.synchronizedList(new ArrayList<ItemTransaction>());
    }
    
    public void addItem(final short windowId, final short slotId, final short actionId) {
        final ItemTransaction storage = new ItemTransaction(windowId, slotId, actionId);
        this.items.add(storage);
    }
    
    @Override
    public void run() {
        final Player p = Bukkit.getServer().getPlayer(this.uuid);
        if (p == null) {
            this.provider.onTaskExecuted(this.uuid);
            return;
        }
        try {
            synchronized (this.items) {
                for (final ItemTransaction storage : this.items) {
                    final Object packet = this.provider.buildWindowClickPacket(p, storage);
                    final boolean result = this.provider.sendPacketToServer(p, packet);
                    if (!result) {
                        break;
                    }
                }
                this.items.clear();
            }
        }
        finally {
            this.provider.onTaskExecuted(this.uuid);
        }
    }
}
