package org.java_websocket.exceptions;

public class NotSendableException extends RuntimeException
{
    private static final long serialVersionUID = -6468967874576651628L;
    
    public NotSendableException(final String s) {
        super(s);
    }
    
    public NotSendableException(final Throwable t) {
        super(t);
    }
    
    public NotSendableException(final String s, final Throwable t) {
        super(s, t);
    }
}
