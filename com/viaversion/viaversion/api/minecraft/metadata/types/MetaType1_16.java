package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;

@Deprecated
public enum MetaType1_16 implements MetaType
{
    BYTE(0, (Type)Type.BYTE), 
    VAR_INT(1, (Type)Type.VAR_INT), 
    FLOAT(2, (Type)Type.FLOAT), 
    STRING(3, (Type)Type.STRING), 
    COMPONENT(4, (Type)Type.COMPONENT), 
    OPT_COMPONENT(5, (Type)Type.OPTIONAL_COMPONENT), 
    ITEM(6, (Type)Type.FLAT_VAR_INT_ITEM), 
    BOOLEAN(7, (Type)Type.BOOLEAN), 
    ROTATION(8, (Type)Type.ROTATION), 
    POSITION(9, (Type)Type.POSITION1_14), 
    OPT_POSITION(10, (Type)Type.OPTIONAL_POSITION_1_14), 
    DIRECTION(11, (Type)Type.VAR_INT), 
    OPT_UUID(12, (Type)Type.OPTIONAL_UUID), 
    BLOCK_STATE(13, (Type)Type.VAR_INT), 
    NBT(14, (Type)Type.NBT), 
    PARTICLE(15, (Type)Types1_16.PARTICLE), 
    VILLAGER_DATA(16, (Type)Type.VILLAGER_DATA), 
    OPT_VAR_INT(17, (Type)Type.OPTIONAL_VAR_INT), 
    POSE(18, (Type)Type.VAR_INT);
    
    private static final MetaType1_16[] VALUES;
    private final int typeId;
    private final Type type;
    
    private MetaType1_16(final int typeId, final Type type) {
        this.typeId = typeId;
        this.type = type;
    }
    
    public static MetaType1_16 byId(final int id) {
        return MetaType1_16.VALUES[id];
    }
    
    @Override
    public int typeId() {
        return this.typeId;
    }
    
    @Override
    public Type type() {
        return this.type;
    }
    
    static {
        VALUES = values();
    }
}
