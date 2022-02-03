package org.java_websocket.exceptions;

import org.java_websocket.*;
import java.io.*;

public class WrappedIOException extends Exception
{
    private final transient WebSocket connection;
    private final IOException ioException;
    
    public WrappedIOException(final WebSocket connection, final IOException ioException) {
        this.connection = connection;
        this.ioException = ioException;
    }
    
    public WebSocket getConnection() {
        return this.connection;
    }
    
    public IOException getIOException() {
        return this.ioException;
    }
}
