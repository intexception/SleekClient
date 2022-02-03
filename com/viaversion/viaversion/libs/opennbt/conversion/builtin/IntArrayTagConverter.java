package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class IntArrayTagConverter implements TagConverter<IntArrayTag, int[]>
{
    @Override
    public int[] convert(final IntArrayTag tag) {
        return tag.getValue();
    }
    
    @Override
    public IntArrayTag convert(final int[] value) {
        return new IntArrayTag(value);
    }
}
