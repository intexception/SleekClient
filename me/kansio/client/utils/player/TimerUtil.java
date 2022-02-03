package me.kansio.client.utils.player;

import me.kansio.client.utils.*;

public class TimerUtil extends Util
{
    public static /* synthetic */ float DEFAULT_TIMER;
    public static /* synthetic */ float MAX_WATCHDOG_TIMER;
    public static /* synthetic */ float MAX_VERUS_LEGIT_TIMER;
    public static /* synthetic */ float DEFAULT_TPS;
    
    public static void Reset() {
        TimerUtil.mc.timer.timerSpeed = TimerUtil.DEFAULT_TIMER;
    }
    
    public static void setTimer(final float lIIIlllllllIII, final int lIIIllllllIlll, final boolean lIIIlllllllIIl) {
        if (canTimer()) {
            setDoTimer((lIIIlllllllIIl && TimerUtil.mc.thePlayer.onGround) ? lIIIlllllllIII : TimerUtil.DEFAULT_TIMER, lIIIllllllIlll);
        }
    }
    
    public static void setTimer(final float lIIlIIIIIIIIlI, final boolean lIIlIIIIIIIIIl) {
        if (canTimer()) {
            setDoTimer((lIIlIIIIIIIIIl && TimerUtil.mc.thePlayer.onGround) ? lIIlIIIIIIIIlI : TimerUtil.DEFAULT_TIMER, 0);
        }
    }
    
    public static void setTimer(final float lIIlIIIIIIlIII, final int lIIlIIIIIIIlll) {
        if (canTimer()) {
            setDoTimer(lIIlIIIIIIlIII, lIIlIIIIIIIlll);
        }
    }
    
    private static boolean canTimer() {
        return TimerUtil.mc.thePlayer.isServerWorld() && TimerUtil.mc.thePlayer.isEntityAlive();
    }
    
    private static void setDoTimer(final float lIIIllllllIIll, final int lIIIllllllIIlI) {
        if (lIIIllllllIIlI == 0) {
            TimerUtil.mc.timer.timerSpeed = lIIIllllllIIll;
        }
        else {
            TimerUtil.mc.timer.timerSpeed = ((TimerUtil.mc.thePlayer.ticksExisted % lIIIllllllIIlI == 0) ? lIIIllllllIIll : TimerUtil.DEFAULT_TIMER);
        }
    }
    
    static {
        TimerUtil.DEFAULT_TIMER = 1.0f;
        TimerUtil.DEFAULT_TPS = 20.0f;
        TimerUtil.MAX_WATCHDOG_TIMER = 1.18f;
        TimerUtil.MAX_VERUS_LEGIT_TIMER = 1.00042f;
    }
    
    public static void setTimer(final float lIIlIIIIIIllII) {
        setDoTimer(lIIlIIIIIIllII, 0);
    }
}
