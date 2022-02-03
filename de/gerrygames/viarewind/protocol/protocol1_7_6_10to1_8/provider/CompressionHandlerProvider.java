package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import de.gerrygames.viarewind.netty.*;
import io.netty.channel.*;
import java.util.*;
import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import io.netty.handler.codec.*;
import java.util.zip.*;

public class CompressionHandlerProvider implements Provider
{
    public void handleSetCompression(final UserConnection user, final int threshold) {
        final ChannelPipeline pipeline = user.getChannel().pipeline();
        if (user.isClientSide()) {
            pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(threshold));
            pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(threshold));
        }
        else {
            final CompressionSendStorage storage = user.get(CompressionSendStorage.class);
            storage.setRemoveCompression(true);
        }
    }
    
    public void handleTransform(final UserConnection user) {
        final CompressionSendStorage storage = user.get(CompressionSendStorage.class);
        if (storage.isRemoveCompression()) {
            final ChannelPipeline pipeline = user.getChannel().pipeline();
            String compressor = null;
            String decompressor = null;
            if (pipeline.get("compress") != null) {
                compressor = "compress";
                decompressor = "decompress";
            }
            else if (pipeline.get("compression-encoder") != null) {
                compressor = "compression-encoder";
                decompressor = "compression-decoder";
            }
            if (compressor == null) {
                throw new IllegalStateException("Couldn't remove compression for 1.7!");
            }
            pipeline.replace(decompressor, decompressor, new EmptyChannelHandler());
            pipeline.replace(compressor, compressor, new ForwardMessageToByteEncoder());
            storage.setRemoveCompression(false);
        }
    }
    
    protected ChannelHandler getEncoder(final int threshold) {
        return new Compressor(threshold);
    }
    
    protected ChannelHandler getDecoder(final int threshold) {
        return new Decompressor(threshold);
    }
    
    private static class Decompressor extends MessageToMessageDecoder<ByteBuf>
    {
        private final Inflater inflater;
        private final int threshold;
        
        public Decompressor(final int var1) {
            this.threshold = var1;
            this.inflater = new Inflater();
        }
        
        @Override
        protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
            if (!in.isReadable()) {
                return;
            }
            final int outLength = Type.VAR_INT.readPrimitive(in);
            if (outLength == 0) {
                out.add(in.readBytes(in.readableBytes()));
                return;
            }
            if (outLength < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold);
            }
            if (outLength > 2097152) {
                throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of " + 2097152);
            }
            ByteBuf temp = in;
            if (!in.hasArray()) {
                temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            }
            else {
                in.retain();
            }
            final ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer(outLength, outLength);
            try {
                this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                output.writerIndex(output.writerIndex() + this.inflater.inflate(output.array(), output.arrayOffset(), outLength));
                out.add(output.retain());
            }
            finally {
                output.release();
                temp.release();
                this.inflater.reset();
            }
        }
    }
    
    private static class Compressor extends MessageToByteEncoder<ByteBuf>
    {
        private final Deflater deflater;
        private final int threshold;
        
        public Compressor(final int var1) {
            this.threshold = var1;
            this.deflater = new Deflater();
        }
        
        @Override
        protected void encode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
            final int frameLength = in.readableBytes();
            if (frameLength < this.threshold) {
                out.writeByte(0);
                out.writeBytes(in);
                return;
            }
            Type.VAR_INT.writePrimitive(out, frameLength);
            ByteBuf temp = in;
            if (!in.hasArray()) {
                temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            }
            else {
                in.retain();
            }
            final ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer();
            try {
                this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    output.ensureWritable(4096);
                    output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output.arrayOffset() + output.writerIndex(), output.writableBytes()));
                }
                out.writeBytes(output);
            }
            finally {
                output.release();
                temp.release();
                this.deflater.reset();
            }
        }
    }
}
