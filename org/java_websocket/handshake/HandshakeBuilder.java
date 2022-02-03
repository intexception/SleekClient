package org.java_websocket.handshake;

public interface HandshakeBuilder extends Handshakedata
{
    void setContent(final byte[] p0);
    
    void put(final String p0, final String p1);
}
