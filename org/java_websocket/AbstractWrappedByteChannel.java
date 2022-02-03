package org.java_websocket;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;

@Deprecated
public class AbstractWrappedByteChannel implements WrappedByteChannel
{
    private final ByteChannel channel;
    
    @Deprecated
    public AbstractWrappedByteChannel(final ByteChannel towrap) {
        this.channel = towrap;
    }
    
    @Deprecated
    public AbstractWrappedByteChannel(final WrappedByteChannel towrap) {
        this.channel = towrap;
    }
    
    @Override
    public int read(final ByteBuffer dst) throws IOException {
        return this.channel.read(dst);
    }
    
    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }
    
    @Override
    public void close() throws IOException {
        this.channel.close();
    }
    
    @Override
    public int write(final ByteBuffer src) throws IOException {
        return this.channel.write(src);
    }
    
    @Override
    public boolean isNeedWrite() {
        return this.channel instanceof WrappedByteChannel && ((WrappedByteChannel)this.channel).isNeedWrite();
    }
    
    @Override
    public void writeMore() throws IOException {
        if (this.channel instanceof WrappedByteChannel) {
            ((WrappedByteChannel)this.channel).writeMore();
        }
    }
    
    @Override
    public boolean isNeedRead() {
        return this.channel instanceof WrappedByteChannel && ((WrappedByteChannel)this.channel).isNeedRead();
    }
    
    @Override
    public int readMore(final ByteBuffer dst) throws IOException {
        return (this.channel instanceof WrappedByteChannel) ? ((WrappedByteChannel)this.channel).readMore(dst) : 0;
    }
    
    @Override
    public boolean isBlocking() {
        if (this.channel instanceof SocketChannel) {
            return ((SocketChannel)this.channel).isBlocking();
        }
        return this.channel instanceof WrappedByteChannel && ((WrappedByteChannel)this.channel).isBlocking();
    }
}
