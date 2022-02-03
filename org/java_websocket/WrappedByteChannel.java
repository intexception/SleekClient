package org.java_websocket;

import java.nio.channels.*;
import java.io.*;
import java.nio.*;

public interface WrappedByteChannel extends ByteChannel
{
    boolean isNeedWrite();
    
    void writeMore() throws IOException;
    
    boolean isNeedRead();
    
    int readMore(final ByteBuffer p0) throws IOException;
    
    boolean isBlocking();
}
