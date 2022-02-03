package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;

@Deprecated
public enum MetaType1_13 implements MetaType
{
    Byte(0, (Type)Type.BYTE), 
    VarInt(1, (Type)Type.VAR_INT), 
    Float(2, (Type)Type.FLOAT), 
    String(3, (Type)Type.STRING), 
    Chat(4, (Type)Type.COMPONENT), 
    OptChat(5, (Type)Type.OPTIONAL_COMPONENT), 
    Slot(6, (Type)Type.FLAT_ITEM), 
    Boolean(7, (Type)Type.BOOLEAN), 
    Vector3F(8, (Type)Type.ROTATION), 
    Position(9, (Type)Type.POSITION), 
    OptPosition(10, (Type)Type.OPTIONAL_POSITION), 
    Direction(11, (Type)Type.VAR_INT), 
    OptUUID(12, (Type)Type.OPTIONAL_UUID), 
    BlockID(13, (Type)Type.VAR_INT), 
    NBTTag(14, (Type)Type.NBT), 
    PARTICLE(15, (Type)Types1_13.PARTICLE);
    
    private final int typeID;
    private final Type type;
    
    private MetaType1_13(final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_13 byId(final int id) {
        return values()[id];
    }
    
    @Override
    public int typeId() {
        return this.typeID;
    }
    
    @Override
    public Type type() {
        return this.type;
    }
}
