package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class MetaTypeTemplate extends Type<Metadata>
{
    protected MetaTypeTemplate() {
        super("Metadata type", Metadata.class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return MetaTypeTemplate.class;
    }
}
