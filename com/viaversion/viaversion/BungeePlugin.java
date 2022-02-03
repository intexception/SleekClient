package com.viaversion.viaversion;

import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.protocol.*;
import net.md_5.bungee.api.plugin.*;
import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.bungee.platform.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.bungee.commands.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.bungee.service.*;
import java.util.*;
import com.viaversion.viaversion.api.configuration.*;

public class BungeePlugin extends Plugin implements ViaPlatform<ProxiedPlayer>, Listener
{
    private BungeeViaAPI api;
    private BungeeViaConfig config;
    
    public void onLoad() {
        try {
            ProtocolConstants.class.getField("MINECRAFT_1_18");
        }
        catch (NoSuchFieldException e) {
            this.getLogger().warning("      / \\");
            this.getLogger().warning("     /   \\");
            this.getLogger().warning("    /  |  \\");
            this.getLogger().warning("   /   |   \\         BUNGEECORD IS OUTDATED");
            this.getLogger().warning("  /         \\   VIAVERSION MAY NOT WORK AS INTENDED");
            this.getLogger().warning(" /     o     \\");
            this.getLogger().warning("/_____________\\");
        }
        this.api = new BungeeViaAPI();
        this.config = new BungeeViaConfig(this.getDataFolder());
        final BungeeCommandHandler commandHandler = new BungeeCommandHandler();
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new BungeeCommand(commandHandler));
        Via.init(ViaManagerImpl.builder().platform(this).injector(new BungeeViaInjector()).loader(new BungeeViaLoader(this)).commandHandler(commandHandler).build());
    }
    
    public void onEnable() {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        ((ViaManagerImpl)Via.getManager()).init();
    }
    
    public String getPlatformName() {
        return this.getProxy().getName();
    }
    
    public String getPlatformVersion() {
        return this.getProxy().getVersion();
    }
    
    public boolean isProxy() {
        return true;
    }
    
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }
    
    public PlatformTask runAsync(final Runnable runnable) {
        return new BungeeViaTask(this.getProxy().getScheduler().runAsync((Plugin)this, runnable));
    }
    
    public PlatformTask runSync(final Runnable runnable) {
        return this.runAsync(runnable);
    }
    
    public PlatformTask runSync(final Runnable runnable, final long ticks) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, ticks * 50L, TimeUnit.MILLISECONDS));
    }
    
    public PlatformTask runRepeatingSync(final Runnable runnable, final long ticks) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS));
    }
    
    public ViaCommandSender[] getOnlinePlayers() {
        final Collection<ProxiedPlayer> players = (Collection<ProxiedPlayer>)this.getProxy().getPlayers();
        final ViaCommandSender[] array = new ViaCommandSender[players.size()];
        int i = 0;
        for (final ProxiedPlayer player : players) {
            array[i++] = new BungeeCommandSender((CommandSender)player);
        }
        return array;
    }
    
    public void sendMessage(final UUID uuid, final String message) {
        this.getProxy().getPlayer(uuid).sendMessage(message);
    }
    
    public boolean kickPlayer(final UUID uuid, final String message) {
        final ProxiedPlayer player = this.getProxy().getPlayer(uuid);
        if (player != null) {
            player.disconnect(message);
            return true;
        }
        return false;
    }
    
    public boolean isPluginEnabled() {
        return true;
    }
    
    public ViaAPI<ProxiedPlayer> getApi() {
        return this.api;
    }
    
    public BungeeViaConfig getConf() {
        return this.config;
    }
    
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }
    
    public void onReload() {
    }
    
    public JsonObject getDump() {
        final JsonObject platformSpecific = new JsonObject();
        final List<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (final Plugin p : ProxyServer.getInstance().getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), Collections.singletonList(p.getDescription().getAuthor())));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        platformSpecific.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return platformSpecific;
    }
    
    public boolean isOldClientsAllowed() {
        return true;
    }
}
