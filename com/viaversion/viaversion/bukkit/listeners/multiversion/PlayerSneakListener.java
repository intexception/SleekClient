package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viaversion.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import java.lang.reflect.*;

public class PlayerSneakListener extends ViaBukkitListener
{
    private static final float STANDING_HEIGHT = 1.8f;
    private static final float HEIGHT_1_14 = 1.5f;
    private static final float HEIGHT_1_9 = 1.6f;
    private static final float DEFAULT_WIDTH = 0.6f;
    private final boolean is1_9Fix;
    private final boolean is1_14Fix;
    private Map<Player, Boolean> sneaking;
    private Set<UUID> sneakingUuids;
    private final Method getHandle;
    private Method setSize;
    private boolean useCache;
    
    public PlayerSneakListener(final ViaVersionPlugin plugin, final boolean is1_9Fix, final boolean is1_14Fix) throws ReflectiveOperationException {
        super((Plugin)plugin, null);
        this.is1_9Fix = is1_9Fix;
        this.is1_14Fix = is1_14Fix;
        final String packageName = plugin.getServer().getClass().getPackage().getName();
        this.getHandle = Class.forName(packageName + ".entity.CraftPlayer").getMethod("getHandle", (Class<?>[])new Class[0]);
        final Class<?> entityPlayerClass = Class.forName(packageName.replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer");
        try {
            this.setSize = entityPlayerClass.getMethod("setSize", Float.TYPE, Float.TYPE);
        }
        catch (NoSuchMethodException e) {
            this.setSize = entityPlayerClass.getMethod("a", Float.TYPE, Float.TYPE);
        }
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
            this.sneaking = new WeakHashMap<Player, Boolean>();
            this.useCache = true;
            plugin.getServer().getScheduler().runTaskTimer((Plugin)plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    for (final Map.Entry<Player, Boolean> entry : PlayerSneakListener.this.sneaking.entrySet()) {
                        PlayerSneakListener.this.setHeight(entry.getKey(), ((boolean)entry.getValue()) ? 1.5f : 1.6f);
                    }
                }
            }, 1L, 1L);
        }
        if (is1_14Fix) {
            this.sneakingUuids = new HashSet<UUID>();
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerToggleSneak(final PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        final UserConnection userConnection = this.getUserConnection(player);
        if (userConnection == null) {
            return;
        }
        final ProtocolInfo info = userConnection.getProtocolInfo();
        if (info == null) {
            return;
        }
        final int protocolVersion = info.getProtocolVersion();
        if (this.is1_14Fix && protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
            this.setHeight(player, event.isSneaking() ? 1.5f : 1.8f);
            if (event.isSneaking()) {
                this.sneakingUuids.add(player.getUniqueId());
            }
            else {
                this.sneakingUuids.remove(player.getUniqueId());
            }
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, true);
            }
            else {
                this.sneaking.remove(player);
            }
        }
        else if (this.is1_9Fix && protocolVersion >= ProtocolVersion.v1_9.getVersion()) {
            this.setHeight(player, event.isSneaking() ? 1.6f : 1.8f);
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, false);
            }
            else {
                this.sneaking.remove(player);
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerDamage(final EntityDamageEvent event) {
        if (!this.is1_14Fix) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.SUFFOCATION) {
            return;
        }
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        final Player player = (Player)event.getEntity();
        if (!this.sneakingUuids.contains(player.getUniqueId())) {
            return;
        }
        double y = player.getEyeLocation().getY() + 0.045;
        y -= (int)y;
        if (y < 0.09) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void playerQuit(final PlayerQuitEvent event) {
        if (this.sneaking != null) {
            this.sneaking.remove(event.getPlayer());
        }
        if (this.sneakingUuids != null) {
            this.sneakingUuids.remove(event.getPlayer().getUniqueId());
        }
    }
    
    private void setHeight(final Player player, final float height) {
        try {
            this.setSize.invoke(this.getHandle.invoke(player, new Object[0]), 0.6f, height);
        }
        catch (IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
}
