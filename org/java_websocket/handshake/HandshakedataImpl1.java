package org.java_websocket.handshake;

import java.util.*;

public class HandshakedataImpl1 implements HandshakeBuilder
{
    private byte[] content;
    private TreeMap<String, String> map;
    
    public HandshakedataImpl1() {
        this.map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
    }
    
    @Override
    public Iterator<String> iterateHttpFields() {
        return Collections.unmodifiableSet((Set<? extends String>)this.map.keySet()).iterator();
    }
    
    @Override
    public String getFieldValue(final String name) {
        final String s = this.map.get(name);
        if (s == null) {
            return "";
        }
        return s;
    }
    
    @Override
    public byte[] getContent() {
        return this.content;
    }
    
    @Override
    public void setContent(final byte[] content) {
        this.content = content;
    }
    
    @Override
    public void put(final String name, final String value) {
        this.map.put(name, value);
    }
    
    @Override
    public boolean hasFieldValue(final String name) {
        return this.map.containsKey(name);
    }
}
