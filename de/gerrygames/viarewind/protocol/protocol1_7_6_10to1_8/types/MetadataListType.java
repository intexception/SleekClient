package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public class MetadataListType extends MetaListTypeTemplate
{
    private MetadataType metadataType;
    
    public MetadataListType() {
        this.metadataType = new MetadataType();
    }
    
    @Override
    public List<Metadata> read(final ByteBuf buffer) throws Exception {
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        Metadata m;
        do {
            m = Types1_7_6_10.METADATA.read(buffer);
            if (m != null) {
                list.add(m);
            }
        } while (m != null);
        return list;
    }
    
    @Override
    public void write(final ByteBuf buffer, final List<Metadata> metadata) throws Exception {
        for (final Metadata meta : metadata) {
            Types1_7_6_10.METADATA.write(buffer, meta);
        }
        if (metadata.isEmpty()) {
            Types1_7_6_10.METADATA.write(buffer, new Metadata(0, MetaType1_7_6_10.Byte, 0));
        }
        buffer.writeByte(127);
    }
}
