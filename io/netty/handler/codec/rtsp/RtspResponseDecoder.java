package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;

public class RtspResponseDecoder extends RtspObjectDecoder
{
    private static final HttpResponseStatus UNKNOWN_STATUS;
    
    public RtspResponseDecoder() {
    }
    
    public RtspResponseDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxContentLength) {
        super(maxInitialLineLength, maxHeaderSize, maxContentLength);
    }
    
    public RtspResponseDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxContentLength, final boolean validateHeaders) {
        super(maxInitialLineLength, maxHeaderSize, maxContentLength, validateHeaders);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] initialLine) throws Exception {
        return new DefaultHttpResponse(RtspVersions.valueOf(initialLine[0]), new HttpResponseStatus(Integer.parseInt(initialLine[1]), initialLine[2]), this.validateHeaders);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpResponse(RtspVersions.RTSP_1_0, RtspResponseDecoder.UNKNOWN_STATUS, this.validateHeaders);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }
    
    static {
        UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
    }
}
