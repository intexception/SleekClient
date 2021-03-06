package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Types1_17
{
    public static final ParticleType PARTICLE;
    public static final MetaTypes1_14 META_TYPES;
    public static final Type<Metadata> METADATA;
    public static final Type<List<Metadata>> METADATA_LIST;
    
    static {
        PARTICLE = new ParticleType();
        META_TYPES = new MetaTypes1_14(Types1_17.PARTICLE);
        METADATA = new MetadataType(Types1_17.META_TYPES);
        METADATA_LIST = new MetaListType(Types1_17.METADATA);
    }
}
