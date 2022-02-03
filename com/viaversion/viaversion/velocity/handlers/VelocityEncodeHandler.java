package com.viaversion.viaversion.velocity.handlers;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;

@ChannelHandler.Sharable
public class VelocityEncodeHandler extends MessageToMessageEncoder<ByteBuf>
{
    private final UserConnection info;
    
    public VelocityEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        if (!this.info.checkOutgoingPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformOutgoing(transformedBuf, (Function<Throwable, Exception>)CancelEncoderException::generate);
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
}
