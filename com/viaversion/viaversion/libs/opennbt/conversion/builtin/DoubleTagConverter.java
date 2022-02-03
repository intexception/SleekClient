package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class DoubleTagConverter implements TagConverter<DoubleTag, Double>
{
    @Override
    public Double convert(final DoubleTag tag) {
        return tag.getValue();
    }
    
    @Override
    public DoubleTag convert(final Double value) {
        return new DoubleTag(value);
    }
}
