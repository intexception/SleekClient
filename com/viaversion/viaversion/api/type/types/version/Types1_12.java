package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Types1_12
{
    public static final Type<Metadata> METADATA;
    public static final Type<List<Metadata>> METADATA_LIST;
    
    static {
        METADATA = new Metadata1_12Type();
        METADATA_LIST = new MetaListType(Types1_12.METADATA);
    }
}
