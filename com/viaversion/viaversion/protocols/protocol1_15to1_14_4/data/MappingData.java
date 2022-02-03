package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.data;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.data.*;

public class MappingData extends MappingDataBase
{
    public MappingData() {
        super("1.14", "1.15", true);
    }
    
    @Override
    protected Mappings loadFromArray(final JsonObject oldMappings, final JsonObject newMappings, final JsonObject diffMappings, final String key) {
        if (!key.equals("sounds")) {
            return super.loadFromArray(oldMappings, newMappings, diffMappings, key);
        }
        return IntArrayMappings.builder().warnOnMissing(false).unmapped(oldMappings.getAsJsonArray(key)).mapped(newMappings.getAsJsonArray(key)).build();
    }
}
