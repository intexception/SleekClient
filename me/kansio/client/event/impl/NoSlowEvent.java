package me.kansio.client.event.impl;

import me.kansio.client.event.*;

public class NoSlowEvent extends Event
{
    private /* synthetic */ Type type;
    
    public Type getType() {
        return this.type;
    }
    
    public NoSlowEvent(final Type lIIIIIIlIlIIlI) {
        this.type = lIIIIIIlIlIIlI;
    }
    
    public enum Type
    {
        WATER, 
        SNEAK, 
        ITEM, 
        KEEPSPRINT, 
        SOULSAND;
    }
}
