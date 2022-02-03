package org.java_websocket.exceptions;

public class InvalidHandshakeException extends InvalidDataException
{
    private static final long serialVersionUID = -1426533877490484964L;
    
    public InvalidHandshakeException() {
        super(1002);
    }
    
    public InvalidHandshakeException(final String s, final Throwable t) {
        super(1002, s, t);
    }
    
    public InvalidHandshakeException(final String s) {
        super(1002, s);
    }
    
    public InvalidHandshakeException(final Throwable t) {
        super(1002, t);
    }
}
