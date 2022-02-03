package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VarLongType extends Type<Long> implements TypeConverter<Long>
{
    public VarLongType() {
        super("VarLong", Long.class);
    }
    
    public long readPrimitive(final ByteBuf buffer) {
        long out = 0L;
        int bytes = 0;
        byte in;
        do {
            in = buffer.readByte();
            out |= (long)(in & 0x7F) << bytes++ * 7;
            if (bytes > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((in & 0x80) == 0x80);
        return out;
    }
    
    public void writePrimitive(final ByteBuf buffer, long object) {
        do {
            int part = (int)(object & 0x7FL);
            object >>>= 7;
            if (object != 0L) {
                part |= 0x80;
            }
            buffer.writeByte(part);
        } while (object != 0L);
    }
    
    @Deprecated
    @Override
    public Long read(final ByteBuf buffer) {
        return this.readPrimitive(buffer);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf buffer, final Long object) {
        this.writePrimitive(buffer, object);
    }
    
    @Override
    public Long from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).longValue();
        }
        if (o instanceof Boolean) {
            return (long)(((boolean)o) ? 1 : 0);
        }
        return (Long)o;
    }
}
