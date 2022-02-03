package org.java_websocket.server;

import java.util.concurrent.*;
import java.nio.channels.*;
import org.java_websocket.*;
import javax.net.ssl.*;
import java.io.*;

public class SSLParametersWebSocketServerFactory extends DefaultSSLWebSocketServerFactory
{
    private final SSLParameters sslParameters;
    
    public SSLParametersWebSocketServerFactory(final SSLContext sslContext, final SSLParameters sslParameters) {
        this(sslContext, Executors.newSingleThreadScheduledExecutor(), sslParameters);
    }
    
    public SSLParametersWebSocketServerFactory(final SSLContext sslContext, final ExecutorService executerService, final SSLParameters sslParameters) {
        super(sslContext, executerService);
        if (sslParameters == null) {
            throw new IllegalArgumentException();
        }
        this.sslParameters = sslParameters;
    }
    
    @Override
    public ByteChannel wrapChannel(final SocketChannel channel, final SelectionKey key) throws IOException {
        final SSLEngine e = this.sslcontext.createSSLEngine();
        e.setUseClientMode(false);
        e.setSSLParameters(this.sslParameters);
        return new SSLSocketChannel2(channel, e, this.exec, key);
    }
}
