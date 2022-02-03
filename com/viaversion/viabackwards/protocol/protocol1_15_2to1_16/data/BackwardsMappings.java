package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings
{
    private final Map<String, String> attributeMappings;
    
    public BackwardsMappings() {
        super("1.16", "1.15", Protocol1_16To1_15_2.class, true);
        this.attributeMappings = new HashMap<String, String>();
    }
    
    @Override
    protected void loadVBExtras(final JsonObject oldMappings, final JsonObject newMappings) {
        for (final Map.Entry<String, String> entry : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().entrySet()) {
            this.attributeMappings.put(entry.getValue(), entry.getKey());
        }
    }
    
    public Map<String, String> getAttributeMappings() {
        return this.attributeMappings;
    }
}
