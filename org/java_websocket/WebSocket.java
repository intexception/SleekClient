package org.java_websocket;

import java.nio.*;
import org.java_websocket.framing.*;
import java.util.*;
import java.net.*;
import org.java_websocket.drafts.*;
import org.java_websocket.enums.*;
import javax.net.ssl.*;
import org.java_websocket.protocols.*;

public interface WebSocket
{
    void close(final int p0, final String p1);
    
    void close(final int p0);
    
    void close();
    
    void closeConnection(final int p0, final String p1);
    
    void send(final String p0);
    
    void send(final ByteBuffer p0);
    
    void send(final byte[] p0);
    
    void sendFrame(final Framedata p0);
    
    void sendFrame(final Collection<Framedata> p0);
    
    void sendPing();
    
    void sendFragmentedFrame(final Opcode p0, final ByteBuffer p1, final boolean p2);
    
    boolean hasBufferedData();
    
    InetSocketAddress getRemoteSocketAddress();
    
    InetSocketAddress getLocalSocketAddress();
    
    boolean isOpen();
    
    boolean isClosing();
    
    boolean isFlushAndClose();
    
    boolean isClosed();
    
    Draft getDraft();
    
    ReadyState getReadyState();
    
    String getResourceDescriptor();
    
     <T> void setAttachment(final T p0);
    
     <T> T getAttachment();
    
    boolean hasSSLSupport();
    
    SSLSession getSSLSession() throws IllegalArgumentException;
    
    IProtocol getProtocol();
}
