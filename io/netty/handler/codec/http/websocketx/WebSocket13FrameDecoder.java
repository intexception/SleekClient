package io.netty.handler.codec.http.websocketx;

public class WebSocket13FrameDecoder extends WebSocket08FrameDecoder
{
    public WebSocket13FrameDecoder(final boolean maskedPayload, final boolean allowExtensions, final int maxFramePayloadLength) {
        super(maskedPayload, allowExtensions, maxFramePayloadLength);
    }
}
