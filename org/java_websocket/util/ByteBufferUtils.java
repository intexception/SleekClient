package org.java_websocket.util;

import java.nio.*;

public class ByteBufferUtils
{
    private ByteBufferUtils() {
    }
    
    public static int transferByteBuffer(final ByteBuffer source, final ByteBuffer dest) {
        if (source == null || dest == null) {
            throw new IllegalArgumentException();
        }
        final int fremain = source.remaining();
        final int toremain = dest.remaining();
        if (fremain > toremain) {
            final int limit = Math.min(fremain, toremain);
            source.limit(limit);
            dest.put(source);
            return limit;
        }
        dest.put(source);
        return fremain;
    }
    
    public static ByteBuffer getEmptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }
}
