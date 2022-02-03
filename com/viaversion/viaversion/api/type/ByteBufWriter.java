package com.viaversion.viaversion.api.type;

import io.netty.buffer.*;

public interface ByteBufWriter<T>
{
    void write(final ByteBuf p0, final T p1) throws Exception;
}
