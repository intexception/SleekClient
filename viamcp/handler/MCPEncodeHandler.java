package viamcp.handler;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.exception.*;
import com.viaversion.viaversion.util.*;

@ChannelHandler.Sharable
public class MCPEncodeHandler extends MessageToMessageEncoder<ByteBuf>
{
    private final UserConnection info;
    private boolean handledCompression;
    
    public MCPEncodeHandler(final UserConnection info) {
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
            final boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformOutgoing(transformedBuf, (Function<Throwable, Exception>)CancelEncoderException::generate);
            if (needsCompress) {
                CommonTransformer.compress(ctx, transformedBuf);
            }
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }
    
    private boolean handleCompressionOrder(final ChannelHandlerContext ctx, final ByteBuf buf) throws InvocationTargetException {
        if (this.handledCompression) {
            return false;
        }
        final int encoderIndex = ctx.pipeline().names().indexOf("compress");
        if (encoderIndex == -1) {
            return false;
        }
        this.handledCompression = true;
        if (encoderIndex > ctx.pipeline().names().indexOf("via-encoder")) {
            CommonTransformer.decompress(ctx, buf);
            final ChannelHandler encoder = ctx.pipeline().get("via-encoder");
            final ChannelHandler decoder = ctx.pipeline().get("via-decoder");
            ctx.pipeline().remove(encoder);
            ctx.pipeline().remove(decoder);
            ctx.pipeline().addAfter("compress", "via-encoder", encoder);
            ctx.pipeline().addAfter("decompress", "via-decoder", decoder);
            return true;
        }
        return false;
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
