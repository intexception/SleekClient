package org.java_websocket.framing;

import org.java_websocket.enums.*;

public class PingFrame extends ControlFrame
{
    public PingFrame() {
        super(Opcode.PING);
    }
}
