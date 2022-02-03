package me.kansio.client.event.impl;

import me.kansio.client.event.*;

public class MouseEvent extends Event
{
    private /* synthetic */ int button;
    
    public MouseEvent(final int lllllllllllI) {
        this.button = lllllllllllI;
    }
    
    public void setButton(final int llllllllIlIl) {
        this.button = llllllllIlIl;
    }
    
    public int getButton() {
        return this.button;
    }
}
