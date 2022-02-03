package com.viaversion.viaversion;

import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.*;
import com.google.inject.*;
import org.spongepowered.api.plugin.*;
import java.io.*;
import org.spongepowered.api.config.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import java.util.logging.*;
import com.viaversion.viaversion.sponge.util.*;
import com.viaversion.viaversion.commands.*;
import org.spongepowered.api.event.*;
import com.viaversion.viaversion.api.data.*;
import org.spongepowered.api.event.game.state.*;
import com.viaversion.viaversion.api.platform.*;
import org.spongepowered.api.scheduler.*;
import com.viaversion.viaversion.sponge.platform.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.sponge.commands.*;
import org.spongepowered.api.command.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.spongepowered.api.text.serializer.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.configuration.*;
import com.viaversion.viaversion.api.*;

@Plugin(id = "viaversion", name = "ViaVersion", version = "4.1.2-SNAPSHOT", authors = { "_MylesC", "creeper123123321", "Gerrygames", "kennytv", "Matsv" }, description = "Allow newer Minecraft versions to connect to an older server version.")
public class SpongePlugin implements ViaPlatform<Player>
{
    @Inject
    private Game game;
    @Inject
    private PluginContainer container;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private File spongeConfig;
    public static final LegacyComponentSerializer COMPONENT_SERIALIZER;
    private final SpongeViaAPI api;
    private SpongeViaConfig conf;
    private Logger logger;
    
    public SpongePlugin() {
        this.api = new SpongeViaAPI();
    }
    
    @Listener
    public void onGameStart(final GameInitializationEvent event) {
        this.logger = new LoggerWrapper(this.container.getLogger());
        this.conf = new SpongeViaConfig(this.container, this.spongeConfig.getParentFile());
        final SpongeCommandHandler commandHandler = new SpongeCommandHandler();
        this.game.getCommandManager().register((Object)this, (CommandCallable)commandHandler, new String[] { "viaversion", "viaver", "vvsponge" });
        this.logger.info("ViaVersion " + this.getPluginVersion() + " is now loaded!");
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(commandHandler).injector(new SpongeViaInjector()).loader(new SpongeViaLoader(this)).build());
    }
    
    @Listener
    public void onServerStart(final GameAboutToStartServerEvent event) {
        if (this.game.getPluginManager().getPlugin("viabackwards").isPresent()) {
            MappingDataLoader.enableMappingsCache();
        }
        this.logger.info("ViaVersion is injecting!");
        ((ViaManagerImpl)Via.getManager()).init();
    }
    
    @Listener
    public void onServerStop(final GameStoppingServerEvent event) {
        ((ViaManagerImpl)Via.getManager()).destroy();
    }
    
    @Override
    public String getPlatformName() {
        return this.game.getPlatform().getImplementation().getName();
    }
    
    @Override
    public String getPlatformVersion() {
        return this.game.getPlatform().getImplementation().getVersion().orElse("Unknown Version");
    }
    
    @Override
    public String getPluginVersion() {
        return this.container.getVersion().orElse("Unknown Version");
    }
    
    @Override
    public PlatformTask runAsync(final Runnable runnable) {
        return new SpongeViaTask(Task.builder().execute(runnable).async().submit((Object)this));
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable) {
        return new SpongeViaTask(Task.builder().execute(runnable).submit((Object)this));
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable, final long ticks) {
        return new SpongeViaTask(Task.builder().execute(runnable).delayTicks(ticks).submit((Object)this));
    }
    
    @Override
    public PlatformTask runRepeatingSync(final Runnable runnable, final long ticks) {
        return new SpongeViaTask(Task.builder().execute(runnable).intervalTicks(ticks).submit((Object)this));
    }
    
    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        final ViaCommandSender[] array = new ViaCommandSender[this.game.getServer().getOnlinePlayers().size()];
        int i = 0;
        for (final Player player : this.game.getServer().getOnlinePlayers()) {
            array[i++] = new SpongeCommandSender((CommandSource)player);
        }
        return array;
    }
    
    @Override
    public void sendMessage(final UUID uuid, final String message) {
        final String serialized = SpongePlugin.COMPONENT_SERIALIZER.serialize((Component)SpongePlugin.COMPONENT_SERIALIZER.deserialize(message));
        this.game.getServer().getPlayer(uuid).ifPresent(player -> player.sendMessage(TextSerializers.JSON.deserialize(serialized)));
    }
    
    @Override
    public boolean kickPlayer(final UUID uuid, final String message) {
        return this.game.getServer().getPlayer(uuid).map(player -> {
            player.kick(TextSerializers.formattingCode('§').deserialize(message));
            return true;
        }).orElse(false);
    }
    
    @Override
    public boolean isPluginEnabled() {
        return true;
    }
    
    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }
    
    @Override
    public File getDataFolder() {
        return this.spongeConfig.getParentFile();
    }
    
    @Override
    public void onReload() {
        this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject platformSpecific = new JsonObject();
        final List<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (final PluginContainer p : this.game.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, p.getName(), p.getVersion().orElse("Unknown Version"), p.getInstance().isPresent() ? p.getInstance().get().getClass().getCanonicalName() : "Unknown", p.getAuthors()));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        return platformSpecific;
    }
    
    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    @Override
    public SpongeViaAPI getApi() {
        return this.api;
    }
    
    @Override
    public SpongeViaConfig getConf() {
        return this.conf;
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
    
    static {
        COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character('§').extractUrls().build();
    }
}
