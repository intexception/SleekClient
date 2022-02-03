package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class UUIDIntArrayType extends Type<UUID>
{
    public UUIDIntArrayType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf buffer) {
        final int[] ints = { buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt() };
        return uuidFromIntArray(ints);
    }
    
    @Override
    public void write(final ByteBuf buffer, final UUID object) {
        final int[] ints = uuidToIntArray(object);
        buffer.writeInt(ints[0]);
        buffer.writeInt(ints[1]);
        buffer.writeInt(ints[2]);
        buffer.writeInt(ints[3]);
    }
    
    public static UUID uuidFromIntArray(final int[] ints) {
        return new UUID((long)ints[0] << 32 | ((long)ints[1] & 0xFFFFFFFFL), (long)ints[2] << 32 | ((long)ints[3] & 0xFFFFFFFFL));
    }
    
    public static int[] uuidToIntArray(final UUID uuid) {
        return bitsToIntArray(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }
    
    public static int[] bitsToIntArray(final long long1, final long long2) {
        return new int[] { (int)(long1 >> 32), (int)long1, (int)(long2 >> 32), (int)long2 };
    }
}
