package me.kansio.client.event.impl;

import me.kansio.client.event.*;

public class ServerJoinEvent extends Event
{
    private /* synthetic */ String serverIP;
    private /* synthetic */ String ign;
    
    public String getIgn() {
        return this.ign;
    }
    
    public String getServerIP() {
        return this.serverIP;
    }
    
    public ServerJoinEvent(final String lIlIIIllllIllI, final String lIlIIIlllllIII) {
        this.serverIP = lIlIIIllllIllI;
        this.ign = lIlIIIlllllIII;
    }
}
