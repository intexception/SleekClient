package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class LongArrayTagConverter implements TagConverter<LongArrayTag, long[]>
{
    @Override
    public long[] convert(final LongArrayTag tag) {
        return tag.getValue();
    }
    
    @Override
    public LongArrayTag convert(final long[] value) {
        return new LongArrayTag(value);
    }
}
