package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class UUIDType extends Type<UUID>
{
    public UUIDType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }
    
    @Override
    public void write(final ByteBuf buffer, final UUID object) {
        buffer.writeLong(object.getMostSignificantBits());
        buffer.writeLong(object.getLeastSignificantBits());
    }
}
