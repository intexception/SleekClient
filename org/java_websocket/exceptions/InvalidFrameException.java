package org.java_websocket.exceptions;

public class InvalidFrameException extends InvalidDataException
{
    private static final long serialVersionUID = -9016496369828887591L;
    
    public InvalidFrameException() {
        super(1002);
    }
    
    public InvalidFrameException(final String s) {
        super(1002, s);
    }
    
    public InvalidFrameException(final Throwable t) {
        super(1002, t);
    }
    
    public InvalidFrameException(final String s, final Throwable t) {
        super(1002, s, t);
    }
}
