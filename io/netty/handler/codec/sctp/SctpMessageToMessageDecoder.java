package io.netty.handler.codec.sctp;

import io.netty.channel.sctp.*;
import io.netty.handler.codec.*;

public abstract class SctpMessageToMessageDecoder extends MessageToMessageDecoder<SctpMessage>
{
    @Override
    public boolean acceptInboundMessage(final Object msg) throws Exception {
        if (!(msg instanceof SctpMessage)) {
            return false;
        }
        final SctpMessage sctpMsg = (SctpMessage)msg;
        if (sctpMsg.isComplete()) {
            return true;
        }
        throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", SctpMessageCompletionHandler.class.getSimpleName()));
    }
}
