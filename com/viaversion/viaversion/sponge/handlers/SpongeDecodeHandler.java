package com.viaversion.viaversion.sponge.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.function.*;
import com.viaversion.viaversion.util.*;
import java.util.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.exception.*;

public class SpongeDecodeHandler extends ByteToMessageDecoder
{
    private final ByteToMessageDecoder minecraftDecoder;
    private final UserConnection info;
    
    public SpongeDecodeHandler(final UserConnection info, final ByteToMessageDecoder minecraftDecoder) {
        this.info = info;
        this.minecraftDecoder = minecraftDecoder;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> list) throws Exception {
        if (!this.info.checkServerboundPacket()) {
            bytebuf.clear();
            throw CancelDecoderException.generate(null);
        }
        ByteBuf transformedBuf = null;
        try {
            if (this.info.shouldTransformPacket()) {
                transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
                this.info.transformServerbound(transformedBuf, (Function<Throwable, Exception>)CancelDecoderException::generate);
            }
            try {
                list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, ctx, (transformedBuf == null) ? bytebuf : transformedBuf));
            }
            catch (InvocationTargetException e) {
                if (e.getCause() instanceof Exception) {
                    throw (Exception)e.getCause();
                }
                if (e.getCause() instanceof Error) {
                    throw (Error)e.getCause();
                }
            }
        }
        finally {
            if (transformedBuf != null) {
                transformedBuf.release();
            }
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
