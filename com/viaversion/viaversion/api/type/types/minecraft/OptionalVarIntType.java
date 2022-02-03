package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class OptionalVarIntType extends Type<Integer>
{
    public OptionalVarIntType() {
        super(Integer.class);
    }
    
    @Override
    public Integer read(final ByteBuf buffer) throws Exception {
        final int read = Type.VAR_INT.readPrimitive(buffer);
        if (read == 0) {
            return null;
        }
        return read - 1;
    }
    
    @Override
    public void write(final ByteBuf buffer, final Integer object) throws Exception {
        if (object == null) {
            Type.VAR_INT.writePrimitive(buffer, 0);
        }
        else {
            Type.VAR_INT.writePrimitive(buffer, object + 1);
        }
    }
}
