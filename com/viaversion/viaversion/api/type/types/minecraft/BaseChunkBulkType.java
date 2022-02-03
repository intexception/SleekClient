package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public abstract class BaseChunkBulkType extends Type<Chunk[]>
{
    protected BaseChunkBulkType() {
        super(Chunk[].class);
    }
    
    protected BaseChunkBulkType(final String typeName) {
        super(typeName, Chunk[].class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkBulkType.class;
    }
}
