package org.java_websocket.server;

import java.util.concurrent.*;
import java.nio.channels.*;
import javax.net.ssl.*;
import java.util.*;
import java.io.*;
import org.java_websocket.drafts.*;
import org.java_websocket.*;

public class DefaultSSLWebSocketServerFactory implements WebSocketServerFactory
{
    protected SSLContext sslcontext;
    protected ExecutorService exec;
    
    public DefaultSSLWebSocketServerFactory(final SSLContext sslContext) {
        this(sslContext, Executors.newSingleThreadScheduledExecutor());
    }
    
    public DefaultSSLWebSocketServerFactory(final SSLContext sslContext, final ExecutorService exec) {
        if (sslContext == null || exec == null) {
            throw new IllegalArgumentException();
        }
        this.sslcontext = sslContext;
        this.exec = exec;
    }
    
    @Override
    public ByteChannel wrapChannel(final SocketChannel channel, final SelectionKey key) throws IOException {
        final SSLEngine e = this.sslcontext.createSSLEngine();
        final List<String> ciphers = new ArrayList<String>(Arrays.asList(e.getEnabledCipherSuites()));
        ciphers.remove("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        e.setEnabledCipherSuites(ciphers.toArray(new String[ciphers.size()]));
        e.setUseClientMode(false);
        return new SSLSocketChannel2(channel, e, this.exec, key);
    }
    
    @Override
    public WebSocketImpl createWebSocket(final WebSocketAdapter a, final Draft d) {
        return new WebSocketImpl(a, d);
    }
    
    @Override
    public WebSocketImpl createWebSocket(final WebSocketAdapter a, final List<Draft> d) {
        return new WebSocketImpl(a, d);
    }
    
    @Override
    public void close() {
        this.exec.shutdown();
    }
}
