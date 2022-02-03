package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class MetaListTypeTemplate extends Type<List<Metadata>>
{
    protected MetaListTypeTemplate() {
        super("MetaData List", List.class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return MetaListTypeTemplate.class;
    }
}
