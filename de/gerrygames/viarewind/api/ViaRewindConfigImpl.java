package de.gerrygames.viarewind.api;

import com.viaversion.viaversion.util.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ViaRewindConfigImpl extends Config implements ViaRewindConfig
{
    public ViaRewindConfigImpl(final File configFile) {
        super(configFile);
        this.reloadConfig();
    }
    
    @Override
    public CooldownIndicator getCooldownIndicator() {
        return CooldownIndicator.valueOf(this.getString("cooldown-indicator", "TITLE").toUpperCase());
    }
    
    @Override
    public boolean isReplaceAdventureMode() {
        return this.getBoolean("replace-adventure", false);
    }
    
    @Override
    public boolean isReplaceParticles() {
        return this.getBoolean("replace-particles", false);
    }
    
    @Override
    public int getMaxBookPages() {
        return this.getInt("max-book-pages", 100);
    }
    
    @Override
    public int getMaxBookPageSize() {
        return this.getInt("max-book-page-length", 5000);
    }
    
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viarewind/config.yml");
    }
    
    @Override
    protected void handleConfig(final Map<String, Object> map) {
    }
    
    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
