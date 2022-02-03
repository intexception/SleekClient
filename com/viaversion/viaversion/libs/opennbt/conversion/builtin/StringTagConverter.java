package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class StringTagConverter implements TagConverter<StringTag, String>
{
    @Override
    public String convert(final StringTag tag) {
        return tag.getValue();
    }
    
    @Override
    public StringTag convert(final String value) {
        return new StringTag(value);
    }
}
