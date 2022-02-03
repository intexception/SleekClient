package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class VillagerDataType extends Type<VillagerData>
{
    public VillagerDataType() {
        super(VillagerData.class);
    }
    
    @Override
    public VillagerData read(final ByteBuf buffer) throws Exception {
        return new VillagerData(Type.VAR_INT.readPrimitive(buffer), Type.VAR_INT.readPrimitive(buffer), Type.VAR_INT.readPrimitive(buffer));
    }
    
    @Override
    public void write(final ByteBuf buffer, final VillagerData object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.type());
        Type.VAR_INT.writePrimitive(buffer, object.profession());
        Type.VAR_INT.writePrimitive(buffer, object.level());
    }
}
