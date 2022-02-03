package org.java_websocket;

import org.java_websocket.drafts.*;
import org.java_websocket.exceptions.*;
import org.java_websocket.handshake.*;
import org.java_websocket.framing.*;

public abstract class WebSocketAdapter implements WebSocketListener
{
    private PingFrame pingFrame;
    
    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(final WebSocket conn, final Draft draft, final ClientHandshake request) throws InvalidDataException {
        return new HandshakeImpl1Server();
    }
    
    @Override
    public void onWebsocketHandshakeReceivedAsClient(final WebSocket conn, final ClientHandshake request, final ServerHandshake response) throws InvalidDataException {
    }
    
    @Override
    public void onWebsocketHandshakeSentAsClient(final WebSocket conn, final ClientHandshake request) throws InvalidDataException {
    }
    
    @Override
    public void onWebsocketPing(final WebSocket conn, final Framedata f) {
        conn.sendFrame(new PongFrame((PingFrame)f));
    }
    
    @Override
    public void onWebsocketPong(final WebSocket conn, final Framedata f) {
    }
    
    @Override
    public PingFrame onPreparePing(final WebSocket conn) {
        if (this.pingFrame == null) {
            this.pingFrame = new PingFrame();
        }
        return this.pingFrame;
    }
}
