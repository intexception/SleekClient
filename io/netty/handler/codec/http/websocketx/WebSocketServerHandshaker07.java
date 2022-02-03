package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.*;
import io.netty.util.*;

public class WebSocketServerHandshaker07 extends WebSocketServerHandshaker
{
    public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private final boolean allowExtensions;
    
    public WebSocketServerHandshaker07(final String webSocketURL, final String subprotocols, final boolean allowExtensions, final int maxFramePayloadLength) {
        super(WebSocketVersion.V07, webSocketURL, subprotocols, maxFramePayloadLength);
        this.allowExtensions = allowExtensions;
    }
    
    @Override
    protected FullHttpResponse newHandshakeResponse(final FullHttpRequest req, final HttpHeaders headers) {
        final FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
        if (headers != null) {
            res.headers().add(headers);
        }
        final String key = req.headers().get("Sec-WebSocket-Key");
        if (key == null) {
            throw new WebSocketHandshakeException("not a WebSocket request: missing key");
        }
        final String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        final byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
        final String accept = WebSocketUtil.base64(sha1);
        if (WebSocketServerHandshaker07.logger.isDebugEnabled()) {
            WebSocketServerHandshaker07.logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", key, accept);
        }
        res.headers().add("Upgrade", "WebSocket".toLowerCase());
        res.headers().add("Connection", "Upgrade");
        res.headers().add("Sec-WebSocket-Accept", accept);
        final String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
        if (subprotocols != null) {
            final String selectedSubprotocol = this.selectSubprotocol(subprotocols);
            if (selectedSubprotocol == null) {
                if (WebSocketServerHandshaker07.logger.isDebugEnabled()) {
                    WebSocketServerHandshaker07.logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
                }
            }
            else {
                res.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
            }
        }
        return res;
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket07FrameDecoder(true, this.allowExtensions, this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket07FrameEncoder(false);
    }
}
