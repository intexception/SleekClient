package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viaversion.bukkit.classgenerator.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.bukkit.listeners.multiversion.*;
import com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import com.viaversion.viaversion.bukkit.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.*;
import org.bukkit.event.*;
import java.util.*;

public class BukkitViaLoader implements ViaPlatformLoader
{
    private final ViaVersionPlugin plugin;
    private final Set<Listener> listeners;
    private final Set<BukkitTask> tasks;
    private HandItemCache handItemCache;
    
    public BukkitViaLoader(final ViaVersionPlugin plugin) {
        this.listeners = new HashSet<Listener>();
        this.tasks = new HashSet<BukkitTask>();
        this.plugin = plugin;
    }
    
    public void registerListener(final Listener listener) {
        Bukkit.getPluginManager().registerEvents(this.storeListener(listener), (Plugin)this.plugin);
    }
    
    public <T extends Listener> T storeListener(final T listener) {
        this.listeners.add(listener);
        return listener;
    }
    
    @Override
    public void load() {
        this.registerListener((Listener)new UpdateListener());
        final ViaVersionPlugin plugin = (ViaVersionPlugin)Bukkit.getPluginManager().getPlugin("ViaVersion");
        ClassGenerator.registerPSConnectListener(plugin);
        final int serverProtocolVersion = Via.getAPI().getServerVersion().lowestSupportedVersion();
        if (serverProtocolVersion < ProtocolVersion.v1_9.getVersion()) {
            this.storeListener(new ArmorListener((Plugin)plugin)).register();
            this.storeListener(new DeathListener((Plugin)plugin)).register();
            this.storeListener(new BlockListener((Plugin)plugin)).register();
            if (plugin.getConf().isItemCache()) {
                this.handItemCache = new HandItemCache();
                this.tasks.add(this.handItemCache.runTaskTimerAsynchronously((Plugin)plugin, 1L, 1L));
            }
        }
        Label_0247: {
            if (serverProtocolVersion < ProtocolVersion.v1_14.getVersion()) {
                final boolean use1_9Fix = plugin.getConf().is1_9HitboxFix() && serverProtocolVersion < ProtocolVersion.v1_9.getVersion();
                if (!use1_9Fix) {
                    if (!plugin.getConf().is1_14HitboxFix()) {
                        break Label_0247;
                    }
                }
                try {
                    this.storeListener(new PlayerSneakListener(plugin, use1_9Fix, plugin.getConf().is1_14HitboxFix())).register();
                }
                catch (ReflectiveOperationException e) {
                    Via.getPlatform().getLogger().warning("Could not load hitbox fix - please report this on our GitHub");
                    e.printStackTrace();
                }
            }
        }
        if (serverProtocolVersion < ProtocolVersion.v1_15.getVersion()) {
            try {
                Class.forName("org.bukkit.event.entity.EntityToggleGlideEvent");
                this.storeListener(new EntityToggleGlideListener(plugin)).register();
            }
            catch (ClassNotFoundException ex) {}
        }
        if (serverProtocolVersion < ProtocolVersion.v1_12.getVersion() && !Boolean.getBoolean("com.viaversion.ignorePaperBlockPlacePatch")) {
            boolean paper = true;
            try {
                Class.forName("org.github.paperspigot.PaperSpigotConfig");
            }
            catch (ClassNotFoundException ignored) {
                try {
                    Class.forName("com.destroystokyo.paper.PaperConfig");
                }
                catch (ClassNotFoundException alsoIgnored) {
                    paper = false;
                }
            }
            if (paper) {
                this.storeListener(new PaperPatch((Plugin)plugin)).register();
            }
        }
        if (serverProtocolVersion < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use((Class<BukkitViaMovementTransmitter>)MovementTransmitterProvider.class, new BukkitViaMovementTransmitter());
            Via.getManager().getProviders().use((Class<BukkitViaLoader$1>)HandItemProvider.class, new HandItemProvider() {
                @Override
                public Item getHandItem(final UserConnection info) {
                    if (BukkitViaLoader.this.handItemCache != null) {
                        return BukkitViaLoader.this.handItemCache.getHandItem(info.getProtocolInfo().getUuid());
                    }
                    try {
                        final UUID playerUUID;
                        final Player player;
                        return Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), () -> {
                            playerUUID = info.getProtocolInfo().getUuid();
                            player = Bukkit.getPlayer(playerUUID);
                            if (player != null) {
                                return HandItemCache.convert(player.getItemInHand());
                            }
                            else {
                                return null;
                            }
                        }).get(10L, TimeUnit.SECONDS);
                    }
                    catch (Exception e) {
                        Via.getPlatform().getLogger().severe("Error fetching hand item: " + e.getClass().getName());
                        if (Via.getManager().isDebug()) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
            });
        }
        if (serverProtocolVersion < ProtocolVersion.v1_12.getVersion() && plugin.getConf().is1_12QuickMoveActionFix()) {
            Via.getManager().getProviders().use((Class<BukkitInventoryQuickMoveProvider>)InventoryQuickMoveProvider.class, new BukkitInventoryQuickMoveProvider());
        }
        if (serverProtocolVersion < ProtocolVersion.v1_13.getVersion() && Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("world")) {
            final BukkitBlockConnectionProvider blockConnectionProvider = new BukkitBlockConnectionProvider();
            Via.getManager().getProviders().use(BlockConnectionProvider.class, blockConnectionProvider);
            ConnectionData.blockConnectionProvider = blockConnectionProvider;
        }
    }
    
    @Override
    public void unload() {
        for (final Listener listener : this.listeners) {
            HandlerList.unregisterAll(listener);
        }
        this.listeners.clear();
        for (final BukkitTask task : this.tasks) {
            task.cancel();
        }
        this.tasks.clear();
    }
}
