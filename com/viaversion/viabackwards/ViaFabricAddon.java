package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.*;
import java.util.logging.*;
import java.io.*;
import org.apache.logging.log4j.*;
import com.viaversion.viabackwards.fabric.util.*;
import net.fabricmc.loader.api.*;
import java.nio.file.*;

public class ViaFabricAddon implements ViaBackwardsPlatform, Runnable
{
    private final Logger logger;
    private File configDir;
    
    public ViaFabricAddon() {
        this.logger = new LoggerWrapper(LogManager.getLogger("ViaBackwards"));
    }
    
    @Override
    public void run() {
        final Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaBackwards");
        this.configDir = configDirPath.toFile();
        this.init(this.getDataFolder());
    }
    
    @Override
    public void disable() {
    }
    
    @Override
    public File getDataFolder() {
        return this.configDir;
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
