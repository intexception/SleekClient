package org.java_websocket.server;

import java.util.concurrent.*;
import java.nio.channels.*;
import org.java_websocket.*;
import javax.net.ssl.*;
import java.io.*;

public class CustomSSLWebSocketServerFactory extends DefaultSSLWebSocketServerFactory
{
    private final String[] enabledProtocols;
    private final String[] enabledCiphersuites;
    
    public CustomSSLWebSocketServerFactory(final SSLContext sslContext, final String[] enabledProtocols, final String[] enabledCiphersuites) {
        this(sslContext, Executors.newSingleThreadScheduledExecutor(), enabledProtocols, enabledCiphersuites);
    }
    
    public CustomSSLWebSocketServerFactory(final SSLContext sslContext, final ExecutorService executerService, final String[] enabledProtocols, final String[] enabledCiphersuites) {
        super(sslContext, executerService);
        this.enabledProtocols = enabledProtocols;
        this.enabledCiphersuites = enabledCiphersuites;
    }
    
    @Override
    public ByteChannel wrapChannel(final SocketChannel channel, final SelectionKey key) throws IOException {
        final SSLEngine e = this.sslcontext.createSSLEngine();
        if (this.enabledProtocols != null) {
            e.setEnabledProtocols(this.enabledProtocols);
        }
        if (this.enabledCiphersuites != null) {
            e.setEnabledCipherSuites(this.enabledCiphersuites);
        }
        e.setUseClientMode(false);
        return new SSLSocketChannel2(channel, e, this.exec, key);
    }
}
