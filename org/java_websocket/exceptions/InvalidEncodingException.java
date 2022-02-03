package org.java_websocket.exceptions;

import java.io.*;

public class InvalidEncodingException extends RuntimeException
{
    private final UnsupportedEncodingException encodingException;
    
    public InvalidEncodingException(final UnsupportedEncodingException encodingException) {
        if (encodingException == null) {
            throw new IllegalArgumentException();
        }
        this.encodingException = encodingException;
    }
    
    public UnsupportedEncodingException getEncodingException() {
        return this.encodingException;
    }
}
