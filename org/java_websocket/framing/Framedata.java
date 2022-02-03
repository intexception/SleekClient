package org.java_websocket.framing;

import org.java_websocket.enums.*;
import java.nio.*;

public interface Framedata
{
    boolean isFin();
    
    boolean isRSV1();
    
    boolean isRSV2();
    
    boolean isRSV3();
    
    boolean getTransfereMasked();
    
    Opcode getOpcode();
    
    ByteBuffer getPayloadData();
    
    void append(final Framedata p0);
}
