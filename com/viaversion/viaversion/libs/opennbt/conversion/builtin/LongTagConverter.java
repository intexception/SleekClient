package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class LongTagConverter implements TagConverter<LongTag, Long>
{
    @Override
    public Long convert(final LongTag tag) {
        return tag.getValue();
    }
    
    @Override
    public LongTag convert(final Long value) {
        return new LongTag(value);
    }
}
