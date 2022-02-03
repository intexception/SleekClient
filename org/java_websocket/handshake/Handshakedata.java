package org.java_websocket.handshake;

import java.util.*;

public interface Handshakedata
{
    Iterator<String> iterateHttpFields();
    
    String getFieldValue(final String p0);
    
    boolean hasFieldValue(final String p0);
    
    byte[] getContent();
}
