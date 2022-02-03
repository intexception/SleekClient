package com.viaversion.viaversion;

import org.bukkit.plugin.java.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.bukkit.classgenerator.*;
import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.bukkit.platform.*;
import org.bukkit.scheduler.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.bukkit.commands.*;
import org.bukkit.command.*;
import org.bukkit.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.unsupported.*;
import java.util.*;
import com.viaversion.viaversion.api.configuration.*;

public class ViaVersionPlugin extends JavaPlugin implements ViaPlatform<Player>
{
    private static ViaVersionPlugin instance;
    private final BukkitCommandHandler commandHandler;
    private final BukkitViaConfig conf;
    private final ViaAPI<Player> api;
    private final List<Runnable> queuedTasks;
    private final List<Runnable> asyncQueuedTasks;
    private final boolean protocolSupport;
    private boolean compatSpigotBuild;
    private boolean spigot;
    private boolean lateBind;
    
    public ViaVersionPlugin() {
        this.api = new BukkitViaAPI(this);
        this.queuedTasks = new ArrayList<Runnable>();
        this.asyncQueuedTasks = new ArrayList<Runnable>();
        this.spigot = true;
        ViaVersionPlugin.instance = this;
        this.commandHandler = new BukkitCommandHandler();
        final BukkitViaInjector injector = new BukkitViaInjector();
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(this.commandHandler).injector(injector).loader(new BukkitViaLoader(this)).build());
        this.conf = new BukkitViaConfig();
        this.protocolSupport = (Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null);
    }
    
    public void onLoad() {
        final boolean hasProtocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib") != null;
        ((BukkitViaInjector)Via.getManager().getInjector()).setProtocolLib(hasProtocolLib);
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        }
        catch (ClassNotFoundException e) {
            this.spigot = false;
        }
        try {
            NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder").getDeclaredField("version");
            this.compatSpigotBuild = true;
        }
        catch (Exception e2) {
            this.compatSpigotBuild = false;
        }
        if (this.getServer().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        ClassGenerator.generate();
        this.lateBind = !((BukkitViaInjector)Via.getManager().getInjector()).isBinded();
        this.getLogger().info("ViaVersion " + this.getDescription().getVersion() + (this.compatSpigotBuild ? "compat" : "") + " is now loaded" + (this.lateBind ? ", waiting for boot. (late-bind)" : ", injecting!"));
        if (!this.lateBind) {
            ((ViaManagerImpl)Via.getManager()).init();
        }
    }
    
    public void onEnable() {
        if (this.lateBind) {
            ((ViaManagerImpl)Via.getManager()).init();
        }
        this.getCommand("viaversion").setExecutor((CommandExecutor)this.commandHandler);
        this.getCommand("viaversion").setTabCompleter((TabCompleter)this.commandHandler);
        this.getServer().getPluginManager().registerEvents((Listener)new ProtocolLibEnableListener(), (Plugin)this);
        if (this.conf.isAntiXRay() && !this.spigot) {
            this.getLogger().info("You have anti-xray on in your config, since you're not using spigot it won't fix xray!");
        }
        for (final Runnable r : this.queuedTasks) {
            Bukkit.getScheduler().runTask((Plugin)this, r);
        }
        this.queuedTasks.clear();
        for (final Runnable r : this.asyncQueuedTasks) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)this, r);
        }
        this.asyncQueuedTasks.clear();
    }
    
    public void onDisable() {
        ((ViaManagerImpl)Via.getManager()).destroy();
    }
    
    public String getPlatformName() {
        return Bukkit.getServer().getName();
    }
    
    public String getPlatformVersion() {
        return Bukkit.getServer().getVersion();
    }
    
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }
    
    public PlatformTask runAsync(final Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitViaTask(this.getServer().getScheduler().runTaskAsynchronously((Plugin)this, runnable));
        }
        this.asyncQueuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }
    
    public PlatformTask runSync(final Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitViaTask(this.getServer().getScheduler().runTask((Plugin)this, runnable));
        }
        this.queuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }
    
    public PlatformTask runSync(final Runnable runnable, final long ticks) {
        return new BukkitViaTask(this.getServer().getScheduler().runTaskLater((Plugin)this, runnable, ticks));
    }
    
    public PlatformTask runRepeatingSync(final Runnable runnable, final long ticks) {
        return new BukkitViaTask(this.getServer().getScheduler().runTaskTimer((Plugin)this, runnable, 0L, ticks));
    }
    
    public ViaCommandSender[] getOnlinePlayers() {
        final ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
        int i = 0;
        for (final Player player : Bukkit.getOnlinePlayers()) {
            array[i++] = new BukkitCommandSender((CommandSender)player);
        }
        return array;
    }
    
    public void sendMessage(final UUID uuid, final String message) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage(message);
        }
    }
    
    public boolean kickPlayer(final UUID uuid, final String message) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.kickPlayer(message);
            return true;
        }
        return false;
    }
    
    public boolean isPluginEnabled() {
        return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
    }
    
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }
    
    public void onReload() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");
            for (final Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', this.conf.getReloadDisconnectMsg()));
            }
        }
        else {
            this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
        }
    }
    
    public JsonObject getDump() {
        final JsonObject platformSpecific = new JsonObject();
        final List<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (final Plugin p : Bukkit.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(p.isEnabled(), p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), p.getDescription().getAuthors()));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        return platformSpecific;
    }
    
    public boolean isOldClientsAllowed() {
        return !this.protocolSupport;
    }
    
    public BukkitViaConfig getConf() {
        return this.conf;
    }
    
    public ViaAPI<Player> getApi() {
        return this.api;
    }
    
    public final Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        final List<UnsupportedSoftware> list = new ArrayList<UnsupportedSoftware>(super.getUnsupportedSoftwareClasses());
        list.add(new UnsupportedSoftwareImpl.Builder().name("Yatopia").reason("You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.").addClassName("org.yatopiamc.yatopia.server.YatopiaConfig").addClassName("net.yatopia.api.event.PlayerAttackEntityEvent").addClassName("yatopiamc.org.yatopia.server.YatopiaConfig").addMethod("org.bukkit.Server", "getLastTickTime").build());
        return (Collection<UnsupportedSoftware>)Collections.unmodifiableList((List<?>)list);
    }
    
    public boolean isLateBind() {
        return this.lateBind;
    }
    
    public boolean isCompatSpigotBuild() {
        return this.compatSpigotBuild;
    }
    
    public boolean isSpigot() {
        return this.spigot;
    }
    
    public boolean isProtocolSupport() {
        return this.protocolSupport;
    }
    
    public static ViaVersionPlugin getInstance() {
        return ViaVersionPlugin.instance;
    }
}
