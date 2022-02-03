package me.kansio.client.utils.math;

import me.kansio.client.utils.*;

public final class Stopwatch extends Util
{
    private /* synthetic */ long currentTime;
    
    private long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public void resetTime() {
        this.setCurrentTime(this.getCurrentTime());
    }
    
    public long getTimeRemaining(final long lllIIlIIIlIlI) {
        final long lllIIlIIIlIIl = this.getCurrentTime();
        final long lllIIlIIIlIII = this.getStartTime();
        return (lllIIlIIIlIIl - lllIIlIIIlIII >= lllIIlIIIlIlI) ? 0L : (lllIIlIIIlIIl - lllIIlIIIlIII);
    }
    
    public long getStartTime() {
        return this.currentTime;
    }
    
    public Stopwatch() {
        this.setCurrentTime(this.getCurrentTime());
    }
    
    public boolean timeElapsed(final long lllIIlIIlIIlI) {
        return this.getCurrentTime() - this.getStartTime() >= lllIIlIIlIIlI;
    }
    
    public void setCurrentTime(final long lllIIlIIllIII) {
        this.currentTime = lllIIlIIllIII;
    }
}
