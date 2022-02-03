package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ShortTagConverter implements TagConverter<ShortTag, Short>
{
    @Override
    public Short convert(final ShortTag tag) {
        return tag.getValue();
    }
    
    @Override
    public ShortTag convert(final Short value) {
        return new ShortTag(value);
    }
}
