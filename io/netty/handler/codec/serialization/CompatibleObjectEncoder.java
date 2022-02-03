package io.netty.handler.codec.serialization;

import io.netty.handler.codec.*;
import java.io.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.*;

public class CompatibleObjectEncoder extends MessageToByteEncoder<Serializable>
{
    private static final AttributeKey<ObjectOutputStream> OOS;
    private final int resetInterval;
    private int writtenObjects;
    
    public CompatibleObjectEncoder() {
        this(16);
    }
    
    public CompatibleObjectEncoder(final int resetInterval) {
        if (resetInterval < 0) {
            throw new IllegalArgumentException("resetInterval: " + resetInterval);
        }
        this.resetInterval = resetInterval;
    }
    
    protected ObjectOutputStream newObjectOutputStream(final OutputStream out) throws Exception {
        return new ObjectOutputStream(out);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final Serializable msg, final ByteBuf out) throws Exception {
        final Attribute<ObjectOutputStream> oosAttr = ctx.attr(CompatibleObjectEncoder.OOS);
        ObjectOutputStream oos = oosAttr.get();
        if (oos == null) {
            oos = this.newObjectOutputStream(new ByteBufOutputStream(out));
            final ObjectOutputStream newOos = oosAttr.setIfAbsent(oos);
            if (newOos != null) {
                oos = newOos;
            }
        }
        synchronized (oos) {
            if (this.resetInterval != 0) {
                ++this.writtenObjects;
                if (this.writtenObjects % this.resetInterval == 0) {
                    oos.reset();
                }
            }
            oos.writeObject(msg);
            oos.flush();
        }
    }
    
    static {
        OOS = AttributeKey.valueOf(CompatibleObjectEncoder.class.getName() + ".OOS");
    }
}
