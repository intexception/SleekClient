package org.java_websocket.framing;

import org.java_websocket.enums.*;

public class ContinuousFrame extends DataFrame
{
    public ContinuousFrame() {
        super(Opcode.CONTINUOUS);
    }
}
