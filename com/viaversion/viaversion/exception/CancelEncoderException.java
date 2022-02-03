package com.viaversion.viaversion.exception;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.*;

public class CancelEncoderException extends EncoderException implements CancelCodecException
{
    public static final CancelEncoderException CACHED;
    
    public CancelEncoderException() {
    }
    
    public CancelEncoderException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CancelEncoderException(final String message) {
        super(message);
    }
    
    public CancelEncoderException(final Throwable cause) {
        super(cause);
    }
    
    public static CancelEncoderException generate(final Throwable cause) {
        return Via.getManager().isDebug() ? new CancelEncoderException(cause) : CancelEncoderException.CACHED;
    }
    
    static {
        CACHED = new CancelEncoderException() {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
