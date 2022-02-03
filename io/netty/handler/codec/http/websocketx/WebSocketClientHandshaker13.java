package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.util.*;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.logging.*;

public class WebSocketClientHandshaker13 extends WebSocketClientHandshaker
{
    private static final InternalLogger logger;
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private String expectedChallengeResponseString;
    private final boolean allowExtensions;
    
    public WebSocketClientHandshaker13(final URI webSocketURL, final WebSocketVersion version, final String subprotocol, final boolean allowExtensions, final HttpHeaders customHeaders, final int maxFramePayloadLength) {
        super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength);
        this.allowExtensions = allowExtensions;
    }
    
    @Override
    protected FullHttpRequest newHandshakeRequest() {
        final URI wsURL = this.uri();
        String path = wsURL.getPath();
        if (wsURL.getQuery() != null && !wsURL.getQuery().isEmpty()) {
            path = wsURL.getPath() + '?' + wsURL.getQuery();
        }
        if (path == null || path.isEmpty()) {
            path = "/";
        }
        final byte[] nonce = WebSocketUtil.randomBytes(16);
        final String key = WebSocketUtil.base64(nonce);
        final String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        final byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
        this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
        if (WebSocketClientHandshaker13.logger.isDebugEnabled()) {
            WebSocketClientHandshaker13.logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
        }
        int wsPort = wsURL.getPort();
        if (wsPort == -1) {
            if ("wss".equals(wsURL.getScheme())) {
                wsPort = 443;
            }
            else {
                wsPort = 80;
            }
        }
        final FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
        final HttpHeaders headers = request.headers();
        headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", key).add("Host", wsURL.getHost() + ':' + wsPort);
        String originValue = "http://" + wsURL.getHost();
        if (wsPort != 80 && wsPort != 443) {
            originValue = originValue + ':' + wsPort;
        }
        headers.add("Sec-WebSocket-Origin", originValue);
        final String expectedSubprotocol = this.expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
        }
        headers.add("Sec-WebSocket-Version", "13");
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        return request;
    }
    
    @Override
    protected void verify(final FullHttpResponse response) {
        final HttpResponseStatus status = HttpResponseStatus.SWITCHING_PROTOCOLS;
        final HttpHeaders headers = response.headers();
        if (!response.getStatus().equals(status)) {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.getStatus());
        }
        final String upgrade = headers.get("Upgrade");
        if (!"WebSocket".equalsIgnoreCase(upgrade)) {
            throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
        }
        final String connection = headers.get("Connection");
        if (!"Upgrade".equalsIgnoreCase(connection)) {
            throw new WebSocketHandshakeException("Invalid handshake response connection: " + connection);
        }
        final String accept = headers.get("Sec-WebSocket-Accept");
        if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
            throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", accept, this.expectedChallengeResponseString));
        }
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket13FrameDecoder(false, this.allowExtensions, this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket13FrameEncoder(true);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
    }
}
