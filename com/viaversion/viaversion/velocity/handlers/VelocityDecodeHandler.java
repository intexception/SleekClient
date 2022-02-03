package com.viaversion.viaversion.velocity.handlers;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class VelocityDecodeHandler extends MessageToMessageDecoder<ByteBuf>
{
    private final UserConnection info;
    
    public VelocityDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformIncoming(transformedBuf, (Function<Throwable, Exception>)CancelDecoderException::generate);
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object event) throws Exception {
        if (event != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
            super.userEventTriggered(ctx, event);
            return;
        }
        final ChannelPipeline pipeline = ctx.pipeline();
        final ChannelHandler encoder = pipeline.get("via-encoder");
        pipeline.remove(encoder);
        pipeline.addBefore("minecraft-encoder", "via-encoder", encoder);
        final ChannelHandler decoder = pipeline.get("via-decoder");
        pipeline.remove(decoder);
        pipeline.addBefore("minecraft-decoder", "via-decoder", decoder);
        super.userEventTriggered(ctx, event);
    }
}
