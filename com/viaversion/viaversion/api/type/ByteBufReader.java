package com.viaversion.viaversion.api.type;

import io.netty.buffer.*;

public interface ByteBufReader<T>
{
    T read(final ByteBuf p0) throws Exception;
}
