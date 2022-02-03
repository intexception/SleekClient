package de.gerrygames.viarewind.netty;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class ForwardMessageToByteEncoder extends MessageToByteEncoder<ByteBuf>
{
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final ByteBuf out) throws Exception {
        out.writeBytes(msg);
    }
}
