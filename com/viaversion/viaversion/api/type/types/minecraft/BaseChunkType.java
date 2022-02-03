package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public abstract class BaseChunkType extends Type<Chunk>
{
    protected BaseChunkType() {
        super(Chunk.class);
    }
    
    protected BaseChunkType(final String typeName) {
        super(typeName, Chunk.class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
