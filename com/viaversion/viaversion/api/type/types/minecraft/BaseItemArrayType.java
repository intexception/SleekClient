package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public abstract class BaseItemArrayType extends Type<Item[]>
{
    protected BaseItemArrayType() {
        super(Item[].class);
    }
    
    protected BaseItemArrayType(final String typeName) {
        super(typeName, Item[].class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseItemArrayType.class;
    }
}
