package org.java_websocket;

import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import org.java_websocket.enums.*;

public class SocketChannelIOHelper
{
    private SocketChannelIOHelper() {
        throw new IllegalStateException("Utility class");
    }
    
    public static boolean read(final ByteBuffer buf, final WebSocketImpl ws, final ByteChannel channel) throws IOException {
        buf.clear();
        final int read = channel.read(buf);
        buf.flip();
        if (read == -1) {
            ws.eot();
            return false;
        }
        return read != 0;
    }
    
    public static boolean readMore(final ByteBuffer buf, final WebSocketImpl ws, final WrappedByteChannel channel) throws IOException {
        buf.clear();
        final int read = channel.readMore(buf);
        buf.flip();
        if (read == -1) {
            ws.eot();
            return false;
        }
        return channel.isNeedRead();
    }
    
    public static boolean batch(final WebSocketImpl ws, final ByteChannel sockchannel) throws IOException {
        if (ws == null) {
            return false;
        }
        ByteBuffer buffer = ws.outQueue.peek();
        WrappedByteChannel c = null;
        if (buffer == null) {
            if (sockchannel instanceof WrappedByteChannel) {
                c = (WrappedByteChannel)sockchannel;
                if (c.isNeedWrite()) {
                    c.writeMore();
                }
            }
        }
        else {
            do {
                sockchannel.write(buffer);
                if (buffer.remaining() > 0) {
                    return false;
                }
                ws.outQueue.poll();
                buffer = ws.outQueue.peek();
            } while (buffer != null);
        }
        if (ws.outQueue.isEmpty() && ws.isFlushAndClose() && ws.getDraft() != null && ws.getDraft().getRole() != null && ws.getDraft().getRole() == Role.SERVER) {
            ws.closeConnection();
        }
        return c == null || !((WrappedByteChannel)sockchannel).isNeedWrite();
    }
}
