package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

@Deprecated
public class Metadata1_14Type extends ModernMetaType
{
    @Override
    protected MetaType getType(final int index) {
        return Types1_14.META_TYPES.byId(index);
    }
}
