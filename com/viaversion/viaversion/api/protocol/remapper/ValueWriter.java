package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.*;

@FunctionalInterface
public interface ValueWriter<T>
{
    void write(final PacketWrapper p0, final T p1) throws Exception;
}
