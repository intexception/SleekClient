package org.java_websocket;

import org.java_websocket.drafts.*;
import org.java_websocket.exceptions.*;
import java.nio.*;
import org.java_websocket.handshake.*;
import org.java_websocket.framing.*;
import java.net.*;

public interface WebSocketListener
{
    ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(final WebSocket p0, final Draft p1, final ClientHandshake p2) throws InvalidDataException;
    
    void onWebsocketHandshakeReceivedAsClient(final WebSocket p0, final ClientHandshake p1, final ServerHandshake p2) throws InvalidDataException;
    
    void onWebsocketHandshakeSentAsClient(final WebSocket p0, final ClientHandshake p1) throws InvalidDataException;
    
    void onWebsocketMessage(final WebSocket p0, final String p1);
    
    void onWebsocketMessage(final WebSocket p0, final ByteBuffer p1);
    
    void onWebsocketOpen(final WebSocket p0, final Handshakedata p1);
    
    void onWebsocketClose(final WebSocket p0, final int p1, final String p2, final boolean p3);
    
    void onWebsocketClosing(final WebSocket p0, final int p1, final String p2, final boolean p3);
    
    void onWebsocketCloseInitiated(final WebSocket p0, final int p1, final String p2);
    
    void onWebsocketError(final WebSocket p0, final Exception p1);
    
    void onWebsocketPing(final WebSocket p0, final Framedata p1);
    
    PingFrame onPreparePing(final WebSocket p0);
    
    void onWebsocketPong(final WebSocket p0, final Framedata p1);
    
    void onWriteDemand(final WebSocket p0);
    
    InetSocketAddress getLocalSocketAddress(final WebSocket p0);
    
    InetSocketAddress getRemoteSocketAddress(final WebSocket p0);
}
