package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Types1_9
{
    public static final Type<Metadata> METADATA;
    public static final Type<List<Metadata>> METADATA_LIST;
    public static final Type<ChunkSection> CHUNK_SECTION;
    
    static {
        METADATA = new Metadata1_9Type();
        METADATA_LIST = new MetaListType(Types1_9.METADATA);
        CHUNK_SECTION = new ChunkSectionType1_9();
    }
}
