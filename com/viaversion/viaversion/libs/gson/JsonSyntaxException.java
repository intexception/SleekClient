package com.viaversion.viaversion.libs.gson;

public final class JsonSyntaxException extends JsonParseException
{
    private static final long serialVersionUID = 1L;
    
    public JsonSyntaxException(final String msg) {
        super(msg);
    }
    
    public JsonSyntaxException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
    
    public JsonSyntaxException(final Throwable cause) {
        super(cause);
    }
}
