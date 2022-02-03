package me.kansio.client.event;

public abstract class Event
{
    private /* synthetic */ boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean llIIllIlIIl) {
        this.cancelled = llIIllIlIIl;
    }
}
