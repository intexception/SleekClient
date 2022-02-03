package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Types1_18
{
    public static final Type<BlockEntity> BLOCK_ENTITY;
    public static final ParticleType PARTICLE;
    public static final MetaTypes1_14 META_TYPES;
    public static final Type<Metadata> METADATA;
    public static final Type<List<Metadata>> METADATA_LIST;
    
    static {
        BLOCK_ENTITY = new BlockEntityType1_18();
        PARTICLE = new ParticleType();
        META_TYPES = new MetaTypes1_14(Types1_18.PARTICLE);
        METADATA = new MetadataType(Types1_18.META_TYPES);
        METADATA_LIST = new MetaListType(Types1_18.METADATA);
    }
}
