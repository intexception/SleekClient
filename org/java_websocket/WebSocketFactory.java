package org.java_websocket;

import org.java_websocket.drafts.*;
import java.util.*;

public interface WebSocketFactory
{
    WebSocket createWebSocket(final WebSocketAdapter p0, final Draft p1);
    
    WebSocket createWebSocket(final WebSocketAdapter p0, final List<Draft> p1);
}
