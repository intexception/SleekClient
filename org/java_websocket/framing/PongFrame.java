package org.java_websocket.framing;

import org.java_websocket.enums.*;

public class PongFrame extends ControlFrame
{
    public PongFrame() {
        super(Opcode.PONG);
    }
    
    public PongFrame(final PingFrame pingFrame) {
        super(Opcode.PONG);
        this.setPayload(pingFrame.getPayloadData());
    }
}
