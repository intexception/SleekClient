package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class IntTagConverter implements TagConverter<IntTag, Integer>
{
    @Override
    public Integer convert(final IntTag tag) {
        return tag.getValue();
    }
    
    @Override
    public IntTag convert(final Integer value) {
        return new IntTag(value);
    }
}
