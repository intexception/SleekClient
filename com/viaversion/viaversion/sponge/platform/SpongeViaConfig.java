package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.configuration.*;
import org.spongepowered.api.plugin.*;
import java.io.*;
import java.net.*;
import org.spongepowered.api.asset.*;
import java.util.*;

public class SpongeViaConfig extends AbstractViaConfig
{
    private static final List<String> UNSUPPORTED;
    private final PluginContainer pluginContainer;
    
    public SpongeViaConfig(final PluginContainer pluginContainer, final File configFile) {
        super(new File(configFile, "config.yml"));
        this.pluginContainer = pluginContainer;
        this.reloadConfig();
    }
    
    @Override
    public URL getDefaultConfigURL() {
        final Optional<Asset> config = (Optional<Asset>)this.pluginContainer.getAsset("config.yml");
        if (!config.isPresent()) {
            throw new IllegalArgumentException("Default config is missing from jar");
        }
        return config.get().getUrl();
    }
    
    @Override
    protected void handleConfig(final Map<String, Object> config) {
    }
    
    @Override
    public List<String> getUnsupportedOptions() {
        return SpongeViaConfig.UNSUPPORTED;
    }
    
    static {
        UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method");
    }
}
