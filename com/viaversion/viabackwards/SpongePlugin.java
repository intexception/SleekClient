package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.*;
import org.spongepowered.api.plugin.*;
import java.util.logging.*;
import com.google.inject.*;
import java.nio.file.*;
import org.spongepowered.api.config.*;
import org.spongepowered.api.event.game.state.*;
import com.viaversion.viaversion.sponge.util.*;
import com.viaversion.viaversion.api.*;
import org.spongepowered.api.event.*;
import java.io.*;

@Plugin(id = "viabackwards", name = "ViaBackwards", version = "4.1.2-SNAPSHOT", authors = { "Matsv", "kennytv", "Gerrygames", "creeper123123321", "ForceUpdate1" }, description = "Allow older Minecraft versions to connect to a newer server version.", dependencies = { @Dependency(id = "viaversion") })
public class SpongePlugin implements ViaBackwardsPlatform
{
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;
    
    @Listener(order = Order.LATE)
    public void onGameStart(final GameInitializationEvent e) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        Via.getManager().addEnableListener(() -> this.init(this.configPath.resolve("config.yml").toFile()));
    }
    
    @Override
    public void disable() {
    }
    
    @Override
    public File getDataFolder() {
        return this.configPath.toFile();
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
