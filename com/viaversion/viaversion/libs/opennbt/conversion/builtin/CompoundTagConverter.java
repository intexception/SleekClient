package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import java.util.*;

public class CompoundTagConverter implements TagConverter<CompoundTag, Map>
{
    @Override
    public Map convert(final CompoundTag tag) {
        final Map<String, Object> ret = new HashMap<String, Object>();
        final Map<String, Tag> tags = tag.getValue();
        for (final Map.Entry<String, Tag> entry : tags.entrySet()) {
            ret.put(entry.getKey(), ConverterRegistry.convertToValue(entry.getValue()));
        }
        return ret;
    }
    
    @Override
    public CompoundTag convert(final Map value) {
        final Map<String, Tag> tags = new HashMap<String, Tag>();
        for (final Object na : value.keySet()) {
            final String n = (String)na;
            tags.put(n, ConverterRegistry.convertToTag(value.get(n)));
        }
        return new CompoundTag(tags);
    }
}
