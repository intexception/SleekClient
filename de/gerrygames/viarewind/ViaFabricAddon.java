package de.gerrygames.viarewind;

import java.util.logging.*;
import org.apache.logging.log4j.*;
import de.gerrygames.viarewind.fabric.util.*;
import net.fabricmc.loader.api.*;
import de.gerrygames.viarewind.api.*;

public class ViaFabricAddon implements ViaRewindPlatform, Runnable
{
    private final Logger logger;
    
    public ViaFabricAddon() {
        this.logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));
    }
    
    @Override
    public void run() {
        final ViaRewindConfigImpl conf = new ViaRewindConfigImpl(FabricLoader.getInstance().getConfigDirectory().toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        this.init(conf);
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
