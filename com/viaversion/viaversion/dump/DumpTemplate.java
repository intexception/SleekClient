package com.viaversion.viaversion.dump;

import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class DumpTemplate
{
    private final VersionInfo versionInfo;
    private final Map<String, Object> configuration;
    private final JsonObject platformDump;
    private final JsonObject injectionDump;
    
    public DumpTemplate(final VersionInfo versionInfo, final Map<String, Object> configuration, final JsonObject platformDump, final JsonObject injectionDump) {
        this.versionInfo = versionInfo;
        this.configuration = configuration;
        this.platformDump = platformDump;
        this.injectionDump = injectionDump;
    }
    
    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }
    
    public Map<String, Object> getConfiguration() {
        return this.configuration;
    }
    
    public JsonObject getPlatformDump() {
        return this.platformDump;
    }
    
    public JsonObject getInjectionDump() {
        return this.injectionDump;
    }
}
