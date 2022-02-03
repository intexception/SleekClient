package org.java_websocket.extensions;

import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;

public abstract class CompressionExtension extends DefaultExtension
{
    @Override
    public void isFrameValid(final Framedata inputFrame) throws InvalidDataException {
        if (inputFrame instanceof DataFrame && (inputFrame.isRSV2() || inputFrame.isRSV3())) {
            throw new InvalidFrameException("bad rsv RSV1: " + inputFrame.isRSV1() + " RSV2: " + inputFrame.isRSV2() + " RSV3: " + inputFrame.isRSV3());
        }
        if (inputFrame instanceof ControlFrame && (inputFrame.isRSV1() || inputFrame.isRSV2() || inputFrame.isRSV3())) {
            throw new InvalidFrameException("bad rsv RSV1: " + inputFrame.isRSV1() + " RSV2: " + inputFrame.isRSV2() + " RSV3: " + inputFrame.isRSV3());
        }
    }
}
