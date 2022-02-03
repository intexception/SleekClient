package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class VectorType extends Type<Vector>
{
    public VectorType() {
        super(Vector.class);
    }
    
    @Override
    public Vector read(final ByteBuf buffer) throws Exception {
        final int x = Type.INT.read(buffer);
        final int y = Type.INT.read(buffer);
        final int z = Type.INT.read(buffer);
        return new Vector(x, y, z);
    }
    
    @Override
    public void write(final ByteBuf buffer, final Vector object) throws Exception {
        Type.INT.write(buffer, object.blockX());
        Type.INT.write(buffer, object.blockY());
        Type.INT.write(buffer, object.blockZ());
    }
}
