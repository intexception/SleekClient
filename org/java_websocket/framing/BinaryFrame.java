package org.java_websocket.framing;

import org.java_websocket.enums.*;

public class BinaryFrame extends DataFrame
{
    public BinaryFrame() {
        super(Opcode.BINARY);
    }
}
