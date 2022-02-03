package org.java_websocket.framing;

import org.java_websocket.enums.*;
import org.java_websocket.util.*;
import org.java_websocket.exceptions.*;

public class TextFrame extends DataFrame
{
    public TextFrame() {
        super(Opcode.TEXT);
    }
    
    @Override
    public void isValid() throws InvalidDataException {
        super.isValid();
        if (!Charsetfunctions.isValidUTF8(this.getPayloadData())) {
            throw new InvalidDataException(1007, "Received text is no valid utf8 string!");
        }
    }
}
