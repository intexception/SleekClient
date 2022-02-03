package org.java_websocket.extensions;

import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;

public interface IExtension
{
    void decodeFrame(final Framedata p0) throws InvalidDataException;
    
    void encodeFrame(final Framedata p0);
    
    boolean acceptProvidedExtensionAsServer(final String p0);
    
    boolean acceptProvidedExtensionAsClient(final String p0);
    
    void isFrameValid(final Framedata p0) throws InvalidDataException;
    
    String getProvidedExtensionAsClient();
    
    String getProvidedExtensionAsServer();
    
    IExtension copyInstance();
    
    void reset();
    
    String toString();
}
