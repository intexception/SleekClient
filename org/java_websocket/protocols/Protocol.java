package org.java_websocket.protocols;

import java.util.regex.*;

public class Protocol implements IProtocol
{
    private static final Pattern patternSpace;
    private static final Pattern patternComma;
    private final String providedProtocol;
    
    public Protocol(final String providedProtocol) {
        if (providedProtocol == null) {
            throw new IllegalArgumentException();
        }
        this.providedProtocol = providedProtocol;
    }
    
    @Override
    public boolean acceptProvidedProtocol(final String inputProtocolHeader) {
        if ("".equals(this.providedProtocol)) {
            return true;
        }
        final String protocolHeader = Protocol.patternSpace.matcher(inputProtocolHeader).replaceAll("");
        final String[] split;
        final String[] headers = split = Protocol.patternComma.split(protocolHeader);
        for (final String header : split) {
            if (this.providedProtocol.equals(header)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getProvidedProtocol() {
        return this.providedProtocol;
    }
    
    @Override
    public IProtocol copyInstance() {
        return new Protocol(this.getProvidedProtocol());
    }
    
    @Override
    public String toString() {
        return this.getProvidedProtocol();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Protocol protocol = (Protocol)o;
        return this.providedProtocol.equals(protocol.providedProtocol);
    }
    
    @Override
    public int hashCode() {
        return this.providedProtocol.hashCode();
    }
    
    static {
        patternSpace = Pattern.compile(" ");
        patternComma = Pattern.compile(",");
    }
}
