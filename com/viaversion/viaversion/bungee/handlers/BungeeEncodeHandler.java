package com.viaversion.viaversion.bungee.handlers;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.bungee.util.*;
import com.viaversion.viaversion.exception.*;

@ChannelHandler.Sharable
public class BungeeEncodeHandler extends MessageToMessageEncoder<ByteBuf>
{
    private final UserConnection info;
    private boolean handledCompression;
    
    public BungeeEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            final boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformClientbound(transformedBuf, (Function<Throwable, Exception>)CancelEncoderException::generate);
            if (needsCompress) {
                this.recompress(ctx, transformedBuf);
            }
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }
    
    private boolean handleCompressionOrder(final ChannelHandlerContext ctx, final ByteBuf buf) {
        boolean needsCompress = false;
        if (!this.handledCompression && ctx.pipeline().names().indexOf("compress") > ctx.pipeline().names().indexOf("via-encoder")) {
            final ByteBuf decompressed = BungeePipelineUtil.decompress(ctx, buf);
            if (buf != decompressed) {
                try {
                    buf.clear().writeBytes(decompressed);
                }
                finally {
                    decompressed.release();
                }
            }
            final ChannelHandler dec = ctx.pipeline().get("via-decoder");
            final ChannelHandler enc = ctx.pipeline().get("via-encoder");
            ctx.pipeline().remove(dec);
            ctx.pipeline().remove(enc);
            ctx.pipeline().addAfter("decompress", "via-decoder", dec);
            ctx.pipeline().addAfter("compress", "via-encoder", enc);
            needsCompress = true;
            this.handledCompression = true;
        }
        return needsCompress;
    }
    
    private void recompress(final ChannelHandlerContext ctx, final ByteBuf buf) {
        final ByteBuf compressed = BungeePipelineUtil.compress(ctx, buf);
        try {
            buf.clear().writeBytes(compressed);
        }
        finally {
            compressed.release();
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
