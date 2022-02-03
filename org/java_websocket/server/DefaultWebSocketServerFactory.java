package org.java_websocket.server;

import org.java_websocket.drafts.*;
import java.util.*;
import java.nio.channels.*;
import java.io.*;
import org.java_websocket.*;

public class DefaultWebSocketServerFactory implements WebSocketServerFactory
{
    @Override
    public WebSocketImpl createWebSocket(final WebSocketAdapter a, final Draft d) {
        return new WebSocketImpl(a, d);
    }
    
    @Override
    public WebSocketImpl createWebSocket(final WebSocketAdapter a, final List<Draft> d) {
        return new WebSocketImpl(a, d);
    }
    
    @Override
    public SocketChannel wrapChannel(final SocketChannel channel, final SelectionKey key) {
        return channel;
    }
    
    @Override
    public void close() {
    }
}
