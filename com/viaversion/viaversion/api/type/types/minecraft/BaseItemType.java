package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public abstract class BaseItemType extends Type<Item>
{
    protected BaseItemType() {
        super(Item.class);
    }
    
    protected BaseItemType(final String typeName) {
        super(typeName, Item.class);
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseItemType.class;
    }
}
