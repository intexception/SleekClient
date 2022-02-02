package me.kansio.client.event.impl;

import me.kansio.client.event.*;

public class Render3DEvent extends Event
{
    private final /* synthetic */ float partialTicks;
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public Render3DEvent(final float lllIIIlIIII) {
        this.partialTicks = lllIIIlIIII;
    }
}
