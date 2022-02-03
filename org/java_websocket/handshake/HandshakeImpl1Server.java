package org.java_websocket.handshake;

public class HandshakeImpl1Server extends HandshakedataImpl1 implements ServerHandshakeBuilder
{
    private short httpstatus;
    private String httpstatusmessage;
    
    @Override
    public String getHttpStatusMessage() {
        return this.httpstatusmessage;
    }
    
    @Override
    public short getHttpStatus() {
        return this.httpstatus;
    }
    
    @Override
    public void setHttpStatusMessage(final String message) {
        this.httpstatusmessage = message;
    }
    
    @Override
    public void setHttpStatus(final short status) {
        this.httpstatus = status;
    }
}
