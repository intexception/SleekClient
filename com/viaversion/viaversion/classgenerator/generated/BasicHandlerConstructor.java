package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.*;
import io.netty.handler.codec.*;
import com.viaversion.viaversion.bukkit.handlers.*;

public class BasicHandlerConstructor implements HandlerConstructor
{
    @Override
    public BukkitEncodeHandler newEncodeHandler(final UserConnection info, final MessageToByteEncoder minecraftEncoder) {
        return new BukkitEncodeHandler(info, minecraftEncoder);
    }
    
    @Override
    public BukkitDecodeHandler newDecodeHandler(final UserConnection info, final ByteToMessageDecoder minecraftDecoder) {
        return new BukkitDecodeHandler(info, minecraftDecoder);
    }
}
