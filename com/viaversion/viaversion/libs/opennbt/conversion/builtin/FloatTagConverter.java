package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class FloatTagConverter implements TagConverter<FloatTag, Float>
{
    @Override
    public Float convert(final FloatTag tag) {
        return tag.getValue();
    }
    
    @Override
    public FloatTag convert(final Float value) {
        return new FloatTag(value);
    }
}
