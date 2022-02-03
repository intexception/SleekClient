package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class OptUUIDType extends Type<UUID>
{
    public OptUUIDType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf buffer) {
        final boolean present = buffer.readBoolean();
        if (!present) {
            return null;
        }
        return new UUID(buffer.readLong(), buffer.readLong());
    }
    
    @Override
    public void write(final ByteBuf buffer, final UUID object) {
        if (object == null) {
            buffer.writeBoolean(false);
        }
        else {
            buffer.writeBoolean(true);
            buffer.writeLong(object.getMostSignificantBits());
            buffer.writeLong(object.getLeastSignificantBits());
        }
    }
}
