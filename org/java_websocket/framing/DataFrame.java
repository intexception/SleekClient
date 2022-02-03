package org.java_websocket.framing;

import org.java_websocket.enums.*;
import org.java_websocket.exceptions.*;

public abstract class DataFrame extends FramedataImpl1
{
    public DataFrame(final Opcode opcode) {
        super(opcode);
    }
    
    @Override
    public void isValid() throws InvalidDataException {
    }
}
