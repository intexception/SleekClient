package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import java.util.*;

public class ListTagConverter implements TagConverter<ListTag, List>
{
    @Override
    public List convert(final ListTag tag) {
        final List<Object> ret = new ArrayList<Object>();
        final List<? extends Tag> tags = tag.getValue();
        for (final Tag t : tags) {
            ret.add(ConverterRegistry.convertToValue(t));
        }
        return ret;
    }
    
    @Override
    public ListTag convert(final List value) {
        final List<Tag> tags = new ArrayList<Tag>();
        for (final Object o : value) {
            tags.add(ConverterRegistry.convertToTag(o));
        }
        return new ListTag(tags);
    }
}
