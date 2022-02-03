package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.util.*;

public final class MetaListType extends MetaListTypeTemplate
{
    private final Type<Metadata> type;
    
    public MetaListType(final Type<Metadata> type) {
        Preconditions.checkNotNull((Object)type);
        this.type = type;
    }
    
    @Override
    public List<Metadata> read(final ByteBuf buffer) throws Exception {
        final List<Metadata> list = new ArrayList<Metadata>();
        Metadata meta;
        do {
            meta = this.type.read(buffer);
            if (meta != null) {
                list.add(meta);
            }
        } while (meta != null);
        return list;
    }
    
    @Override
    public void write(final ByteBuf buffer, final List<Metadata> object) throws Exception {
        for (final Metadata metadata : object) {
            this.type.write(buffer, metadata);
        }
        this.type.write(buffer, null);
    }
}
