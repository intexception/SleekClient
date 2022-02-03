package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class EulerAngleType extends Type<EulerAngle>
{
    public EulerAngleType() {
        super(EulerAngle.class);
    }
    
    @Override
    public EulerAngle read(final ByteBuf buffer) throws Exception {
        final float x = Type.FLOAT.readPrimitive(buffer);
        final float y = Type.FLOAT.readPrimitive(buffer);
        final float z = Type.FLOAT.readPrimitive(buffer);
        return new EulerAngle(x, y, z);
    }
    
    @Override
    public void write(final ByteBuf buffer, final EulerAngle object) throws Exception {
        Type.FLOAT.writePrimitive(buffer, object.x());
        Type.FLOAT.writePrimitive(buffer, object.y());
        Type.FLOAT.writePrimitive(buffer, object.z());
    }
}
