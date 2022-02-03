package com.viaversion.viaversion.exception;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.*;

public class CancelDecoderException extends DecoderException implements CancelCodecException
{
    public static final CancelDecoderException CACHED;
    
    public CancelDecoderException() {
    }
    
    public CancelDecoderException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CancelDecoderException(final String message) {
        super(message);
    }
    
    public CancelDecoderException(final Throwable cause) {
        super(cause);
    }
    
    public static CancelDecoderException generate(final Throwable cause) {
        return Via.getManager().isDebug() ? new CancelDecoderException(cause) : CancelDecoderException.CACHED;
    }
    
    static {
        CACHED = new CancelDecoderException() {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
