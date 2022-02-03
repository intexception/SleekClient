package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder<ByteBuf>
{
    private final NettyEncryptionTranslator encryptionCodec;
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException, Exception {
        this.encryptionCodec.cipher(p_encode_2_, p_encode_3_);
    }
}
