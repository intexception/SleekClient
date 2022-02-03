package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data;

import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public final class MappingData extends MappingDataBase
{
    private final Object2IntMap<String> blockEntityIds;
    
    public MappingData() {
        super("1.17", "1.18", true);
        (this.blockEntityIds = new Object2IntOpenHashMap<String>()).defaultReturnValue(-1);
    }
    
    @Override
    protected void loadExtras(final JsonObject oldMappings, final JsonObject newMappings, final JsonObject diffMappings) {
        int i = 0;
        for (final JsonElement element : newMappings.getAsJsonArray("blockentities")) {
            final String id = element.getAsString();
            this.blockEntityIds.put(id, i++);
        }
    }
    
    public Object2IntMap<String> blockEntityIds() {
        return this.blockEntityIds;
    }
}
