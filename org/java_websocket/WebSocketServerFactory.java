package org.java_websocket;

import org.java_websocket.drafts.*;
import java.util.*;
import java.nio.channels.*;
import java.io.*;

public interface WebSocketServerFactory extends WebSocketFactory
{
    WebSocketImpl createWebSocket(final WebSocketAdapter p0, final Draft p1);
    
    WebSocketImpl createWebSocket(final WebSocketAdapter p0, final List<Draft> p1);
    
    ByteChannel wrapChannel(final SocketChannel p0, final SelectionKey p1) throws IOException;
    
    void close();
}
