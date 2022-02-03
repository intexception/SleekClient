package org.java_websocket.extensions;

import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;

public class DefaultExtension implements IExtension
{
    @Override
    public void decodeFrame(final Framedata inputFrame) throws InvalidDataException {
    }
    
    @Override
    public void encodeFrame(final Framedata inputFrame) {
    }
    
    @Override
    public boolean acceptProvidedExtensionAsServer(final String inputExtension) {
        return true;
    }
    
    @Override
    public boolean acceptProvidedExtensionAsClient(final String inputExtension) {
        return true;
    }
    
    @Override
    public void isFrameValid(final Framedata inputFrame) throws InvalidDataException {
        if (inputFrame.isRSV1() || inputFrame.isRSV2() || inputFrame.isRSV3()) {
            throw new InvalidFrameException("bad rsv RSV1: " + inputFrame.isRSV1() + " RSV2: " + inputFrame.isRSV2() + " RSV3: " + inputFrame.isRSV3());
        }
    }
    
    @Override
    public String getProvidedExtensionAsClient() {
        return "";
    }
    
    @Override
    public String getProvidedExtensionAsServer() {
        return "";
    }
    
    @Override
    public IExtension copyInstance() {
        return new DefaultExtension();
    }
    
    @Override
    public void reset() {
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass());
    }
}
