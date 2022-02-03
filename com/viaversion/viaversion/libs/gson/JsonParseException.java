package com.viaversion.viaversion.libs.gson;

public class JsonParseException extends RuntimeException
{
    static final long serialVersionUID = -4086729973971783390L;
    
    public JsonParseException(final String msg) {
        super(msg);
    }
    
    public JsonParseException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
    
    public JsonParseException(final Throwable cause) {
        super(cause);
    }
}
