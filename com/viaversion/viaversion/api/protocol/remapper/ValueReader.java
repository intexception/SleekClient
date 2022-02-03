package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.*;

@FunctionalInterface
public interface ValueReader<T>
{
    T read(final PacketWrapper p0) throws Exception;
}
