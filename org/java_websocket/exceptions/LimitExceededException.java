package org.java_websocket.exceptions;

public class LimitExceededException extends InvalidDataException
{
    private static final long serialVersionUID = 6908339749836826785L;
    private final int limit;
    
    public LimitExceededException() {
        this(Integer.MAX_VALUE);
    }
    
    public LimitExceededException(final int limit) {
        super(1009);
        this.limit = limit;
    }
    
    public LimitExceededException(final String s, final int limit) {
        super(1009, s);
        this.limit = limit;
    }
    
    public LimitExceededException(final String s) {
        this(s, Integer.MAX_VALUE);
    }
    
    public int getLimit() {
        return this.limit;
    }
}
