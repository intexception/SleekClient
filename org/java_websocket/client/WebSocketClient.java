package org.java_websocket.client;

import javax.net.*;
import org.java_websocket.drafts.*;
import org.java_websocket.*;
import java.util.concurrent.*;
import java.net.*;
import java.lang.reflect.*;
import java.nio.*;
import java.io.*;
import java.security.*;
import java.util.*;
import org.java_websocket.exceptions.*;
import org.java_websocket.handshake.*;
import org.java_websocket.enums.*;
import org.java_websocket.framing.*;
import org.java_websocket.protocols.*;
import javax.net.ssl.*;

public abstract class WebSocketClient extends AbstractWebSocket implements Runnable, WebSocket
{
    protected URI uri;
    private WebSocketImpl engine;
    private Socket socket;
    private SocketFactory socketFactory;
    private OutputStream ostream;
    private Proxy proxy;
    private Thread writeThread;
    private Thread connectReadThread;
    private Draft draft;
    private Map<String, String> headers;
    private CountDownLatch connectLatch;
    private CountDownLatch closeLatch;
    private int connectTimeout;
    private DnsResolver dnsResolver;
    
    public WebSocketClient(final URI serverUri) {
        this(serverUri, new Draft_6455());
    }
    
    public WebSocketClient(final URI serverUri, final Draft protocolDraft) {
        this(serverUri, protocolDraft, null, 0);
    }
    
    public WebSocketClient(final URI serverUri, final Map<String, String> httpHeaders) {
        this(serverUri, new Draft_6455(), httpHeaders);
    }
    
    public WebSocketClient(final URI serverUri, final Draft protocolDraft, final Map<String, String> httpHeaders) {
        this(serverUri, protocolDraft, httpHeaders, 0);
    }
    
    public WebSocketClient(final URI serverUri, final Draft protocolDraft, final Map<String, String> httpHeaders, final int connectTimeout) {
        this.uri = null;
        this.engine = null;
        this.socket = null;
        this.socketFactory = null;
        this.proxy = Proxy.NO_PROXY;
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.connectTimeout = 0;
        this.dnsResolver = null;
        if (serverUri == null) {
            throw new IllegalArgumentException();
        }
        if (protocolDraft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        }
        this.uri = serverUri;
        this.draft = protocolDraft;
        this.dnsResolver = new DnsResolver() {
            @Override
            public InetAddress resolve(final URI uri) throws UnknownHostException {
                return InetAddress.getByName(uri.getHost());
            }
        };
        if (httpHeaders != null) {
            (this.headers = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER)).putAll(httpHeaders);
        }
        this.connectTimeout = connectTimeout;
        this.setTcpNoDelay(false);
        this.setReuseAddr(false);
        this.engine = new WebSocketImpl(this, protocolDraft);
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    @Override
    public Draft getDraft() {
        return this.draft;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public void addHeader(final String key, final String value) {
        if (this.headers == null) {
            this.headers = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        }
        this.headers.put(key, value);
    }
    
    public String removeHeader(final String key) {
        if (this.headers == null) {
            return null;
        }
        return this.headers.remove(key);
    }
    
    public void clearHeaders() {
        this.headers = null;
    }
    
    public void setDnsResolver(final DnsResolver dnsResolver) {
        this.dnsResolver = dnsResolver;
    }
    
    public void reconnect() {
        this.reset();
        this.connect();
    }
    
    public boolean reconnectBlocking() throws InterruptedException {
        this.reset();
        return this.connectBlocking();
    }
    
    private void reset() {
        final Thread current = Thread.currentThread();
        if (current == this.writeThread || current == this.connectReadThread) {
            throw new IllegalStateException("You cannot initialize a reconnect out of the websocket thread. Use reconnect in another thread to ensure a successful cleanup.");
        }
        try {
            this.closeBlocking();
            if (this.writeThread != null) {
                this.writeThread.interrupt();
                this.writeThread = null;
            }
            if (this.connectReadThread != null) {
                this.connectReadThread.interrupt();
                this.connectReadThread = null;
            }
            this.draft.reset();
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
        }
        catch (Exception e) {
            this.onError(e);
            this.engine.closeConnection(1006, e.getMessage());
            return;
        }
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.engine = new WebSocketImpl(this, this.draft);
    }
    
    public void connect() {
        if (this.connectReadThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        (this.connectReadThread = new Thread(this)).setName("WebSocketConnectReadThread-" + this.connectReadThread.getId());
        this.connectReadThread.start();
    }
    
    public boolean connectBlocking() throws InterruptedException {
        this.connect();
        this.connectLatch.await();
        return this.engine.isOpen();
    }
    
    public boolean connectBlocking(final long timeout, final TimeUnit timeUnit) throws InterruptedException {
        this.connect();
        return this.connectLatch.await(timeout, timeUnit) && this.engine.isOpen();
    }
    
    @Override
    public void close() {
        if (this.writeThread != null) {
            this.engine.close(1000);
        }
    }
    
    public void closeBlocking() throws InterruptedException {
        this.close();
        this.closeLatch.await();
    }
    
    @Override
    public void send(final String text) {
        this.engine.send(text);
    }
    
    @Override
    public void send(final byte[] data) {
        this.engine.send(data);
    }
    
    @Override
    public <T> T getAttachment() {
        return this.engine.getAttachment();
    }
    
    @Override
    public <T> void setAttachment(final T attachment) {
        this.engine.setAttachment(attachment);
    }
    
    @Override
    protected Collection<WebSocket> getConnections() {
        return (Collection<WebSocket>)Collections.singletonList(this.engine);
    }
    
    @Override
    public void sendPing() {
        this.engine.sendPing();
    }
    
    @Override
    public void run() {
        InputStream istream;
        try {
            final boolean upgradeSocketToSSLSocket = this.prepareSocket();
            this.socket.setTcpNoDelay(this.isTcpNoDelay());
            this.socket.setReuseAddress(this.isReuseAddr());
            if (!this.socket.isConnected()) {
                final InetSocketAddress addr = new InetSocketAddress(this.dnsResolver.resolve(this.uri), this.getPort());
                this.socket.connect(addr, this.connectTimeout);
            }
            if (upgradeSocketToSSLSocket && "wss".equals(this.uri.getScheme())) {
                this.upgradeSocketToSSL();
            }
            if (this.socket instanceof SSLSocket) {
                final SSLSocket sslSocket = (SSLSocket)this.socket;
                final SSLParameters sslParameters = sslSocket.getSSLParameters();
                this.onSetSSLParameters(sslParameters);
                sslSocket.setSSLParameters(sslParameters);
            }
            istream = this.socket.getInputStream();
            this.ostream = this.socket.getOutputStream();
            this.sendHandshake();
        }
        catch (Exception e) {
            this.onWebsocketError(this.engine, e);
            this.engine.closeConnection(-1, e.getMessage());
            return;
        }
        catch (InternalError e2) {
            if (e2.getCause() instanceof InvocationTargetException && e2.getCause().getCause() instanceof IOException) {
                final IOException cause = (IOException)e2.getCause().getCause();
                this.onWebsocketError(this.engine, cause);
                this.engine.closeConnection(-1, cause.getMessage());
                return;
            }
            throw e2;
        }
        (this.writeThread = new Thread(new WebsocketWriteThread(this))).start();
        final byte[] rawbuffer = new byte[16384];
        try {
            int readBytes;
            while (!this.isClosing() && !this.isClosed() && (readBytes = istream.read(rawbuffer)) != -1) {
                this.engine.decode(ByteBuffer.wrap(rawbuffer, 0, readBytes));
            }
            this.engine.eot();
        }
        catch (IOException e3) {
            this.handleIOException(e3);
        }
        catch (RuntimeException e4) {
            this.onError(e4);
            this.engine.closeConnection(1006, e4.getMessage());
        }
        this.connectReadThread = null;
    }
    
    private void upgradeSocketToSSL() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLSocketFactory factory;
        if (this.socketFactory instanceof SSLSocketFactory) {
            factory = (SSLSocketFactory)this.socketFactory;
        }
        else {
            final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            factory = sslContext.getSocketFactory();
        }
        this.socket = factory.createSocket(this.socket, this.uri.getHost(), this.getPort(), true);
    }
    
    private boolean prepareSocket() throws IOException {
        boolean upgradeSocketToSSLSocket = false;
        if (this.proxy != Proxy.NO_PROXY) {
            this.socket = new Socket(this.proxy);
            upgradeSocketToSSLSocket = true;
        }
        else if (this.socketFactory != null) {
            this.socket = this.socketFactory.createSocket();
        }
        else if (this.socket == null) {
            this.socket = new Socket(this.proxy);
            upgradeSocketToSSLSocket = true;
        }
        else if (this.socket.isClosed()) {
            throw new IOException();
        }
        return upgradeSocketToSSLSocket;
    }
    
    protected void onSetSSLParameters(final SSLParameters sslParameters) {
        sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
    }
    
    private int getPort() {
        final int port = this.uri.getPort();
        final String scheme = this.uri.getScheme();
        if ("wss".equals(scheme)) {
            return (port == -1) ? 443 : port;
        }
        if ("ws".equals(scheme)) {
            return (port == -1) ? 80 : port;
        }
        throw new IllegalArgumentException("unknown scheme: " + scheme);
    }
    
    private void sendHandshake() throws InvalidHandshakeException {
        final String part1 = this.uri.getRawPath();
        final String part2 = this.uri.getRawQuery();
        String path;
        if (part1 == null || part1.length() == 0) {
            path = "/";
        }
        else {
            path = part1;
        }
        if (part2 != null) {
            path = path + '?' + part2;
        }
        final int port = this.getPort();
        final String host = this.uri.getHost() + ((port != 80 && port != 443) ? (":" + port) : "");
        final HandshakeImpl1Client handshake = new HandshakeImpl1Client();
        handshake.setResourceDescriptor(path);
        handshake.put("Host", host);
        if (this.headers != null) {
            for (final Map.Entry<String, String> kv : this.headers.entrySet()) {
                handshake.put(kv.getKey(), kv.getValue());
            }
        }
        this.engine.startHandshake(handshake);
    }
    
    @Override
    public ReadyState getReadyState() {
        return this.engine.getReadyState();
    }
    
    @Override
    public final void onWebsocketMessage(final WebSocket conn, final String message) {
        this.onMessage(message);
    }
    
    @Override
    public final void onWebsocketMessage(final WebSocket conn, final ByteBuffer blob) {
        this.onMessage(blob);
    }
    
    @Override
    public final void onWebsocketOpen(final WebSocket conn, final Handshakedata handshake) {
        this.startConnectionLostTimer();
        this.onOpen((ServerHandshake)handshake);
        this.connectLatch.countDown();
    }
    
    @Override
    public final void onWebsocketClose(final WebSocket conn, final int code, final String reason, final boolean remote) {
        this.stopConnectionLostTimer();
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }
        this.onClose(code, reason, remote);
        this.connectLatch.countDown();
        this.closeLatch.countDown();
    }
    
    @Override
    public final void onWebsocketError(final WebSocket conn, final Exception ex) {
        this.onError(ex);
    }
    
    @Override
    public final void onWriteDemand(final WebSocket conn) {
    }
    
    @Override
    public void onWebsocketCloseInitiated(final WebSocket conn, final int code, final String reason) {
        this.onCloseInitiated(code, reason);
    }
    
    @Override
    public void onWebsocketClosing(final WebSocket conn, final int code, final String reason, final boolean remote) {
        this.onClosing(code, reason, remote);
    }
    
    public void onCloseInitiated(final int code, final String reason) {
    }
    
    public void onClosing(final int code, final String reason, final boolean remote) {
    }
    
    public WebSocket getConnection() {
        return this.engine;
    }
    
    @Override
    public InetSocketAddress getLocalSocketAddress(final WebSocket conn) {
        if (this.socket != null) {
            return (InetSocketAddress)this.socket.getLocalSocketAddress();
        }
        return null;
    }
    
    @Override
    public InetSocketAddress getRemoteSocketAddress(final WebSocket conn) {
        if (this.socket != null) {
            return (InetSocketAddress)this.socket.getRemoteSocketAddress();
        }
        return null;
    }
    
    public abstract void onOpen(final ServerHandshake p0);
    
    public abstract void onMessage(final String p0);
    
    public abstract void onClose(final int p0, final String p1, final boolean p2);
    
    public abstract void onError(final Exception p0);
    
    public void onMessage(final ByteBuffer bytes) {
    }
    
    public void setProxy(final Proxy proxy) {
        if (proxy == null) {
            throw new IllegalArgumentException();
        }
        this.proxy = proxy;
    }
    
    @Deprecated
    public void setSocket(final Socket socket) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        }
        this.socket = socket;
    }
    
    public void setSocketFactory(final SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }
    
    @Override
    public void sendFragmentedFrame(final Opcode op, final ByteBuffer buffer, final boolean fin) {
        this.engine.sendFragmentedFrame(op, buffer, fin);
    }
    
    @Override
    public boolean isOpen() {
        return this.engine.isOpen();
    }
    
    @Override
    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }
    
    @Override
    public boolean isClosed() {
        return this.engine.isClosed();
    }
    
    @Override
    public boolean isClosing() {
        return this.engine.isClosing();
    }
    
    @Override
    public boolean hasBufferedData() {
        return this.engine.hasBufferedData();
    }
    
    @Override
    public void close(final int code) {
        this.engine.close(code);
    }
    
    @Override
    public void close(final int code, final String message) {
        this.engine.close(code, message);
    }
    
    @Override
    public void closeConnection(final int code, final String message) {
        this.engine.closeConnection(code, message);
    }
    
    @Override
    public void send(final ByteBuffer bytes) {
        this.engine.send(bytes);
    }
    
    @Override
    public void sendFrame(final Framedata framedata) {
        this.engine.sendFrame(framedata);
    }
    
    @Override
    public void sendFrame(final Collection<Framedata> frames) {
        this.engine.sendFrame(frames);
    }
    
    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }
    
    @Override
    public InetSocketAddress getRemoteSocketAddress() {
        return this.engine.getRemoteSocketAddress();
    }
    
    @Override
    public String getResourceDescriptor() {
        return this.uri.getPath();
    }
    
    @Override
    public boolean hasSSLSupport() {
        return this.engine.hasSSLSupport();
    }
    
    @Override
    public SSLSession getSSLSession() {
        return this.engine.getSSLSession();
    }
    
    @Override
    public IProtocol getProtocol() {
        return this.engine.getProtocol();
    }
    
    private void handleIOException(final IOException e) {
        if (e instanceof SSLException) {
            this.onError(e);
        }
        this.engine.eot();
    }
    
    private class WebsocketWriteThread implements Runnable
    {
        private final WebSocketClient webSocketClient;
        
        WebsocketWriteThread(final WebSocketClient webSocketClient) {
            this.webSocketClient = webSocketClient;
        }
        
        @Override
        public void run() {
            Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());
            try {
                this.runWriteData();
            }
            catch (IOException e) {
                WebSocketClient.this.handleIOException(e);
            }
            finally {
                this.closeSocket();
                WebSocketClient.this.writeThread = null;
            }
        }
        
        private void runWriteData() throws IOException {
            try {
                while (!Thread.interrupted()) {
                    final ByteBuffer buffer = WebSocketClient.this.engine.outQueue.take();
                    WebSocketClient.this.ostream.write(buffer.array(), 0, buffer.limit());
                    WebSocketClient.this.ostream.flush();
                }
            }
            catch (InterruptedException e) {
                for (final ByteBuffer buffer2 : WebSocketClient.this.engine.outQueue) {
                    WebSocketClient.this.ostream.write(buffer2.array(), 0, buffer2.limit());
                    WebSocketClient.this.ostream.flush();
                }
                Thread.currentThread().interrupt();
            }
        }
        
        private void closeSocket() {
            try {
                if (WebSocketClient.this.socket != null) {
                    WebSocketClient.this.socket.close();
                }
            }
            catch (IOException ex) {
                WebSocketClient.this.onWebsocketError(this.webSocketClient, ex);
            }
        }
    }
}
