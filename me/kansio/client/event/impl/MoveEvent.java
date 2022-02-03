package me.kansio.client.event.impl;

import me.kansio.client.event.*;

public class MoveEvent extends Event
{
    private /* synthetic */ double motionY;
    private /* synthetic */ double motionZ;
    private /* synthetic */ double motionX;
    private /* synthetic */ double strafeSpeed;
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public MoveEvent(final double llIIIlIlIIIlll, final double llIIIlIlIIIIlI, final double llIIIlIlIIIIIl) {
        this.motionX = llIIIlIlIIIlll;
        this.motionY = llIIIlIlIIIIlI;
        this.motionZ = llIIIlIlIIIIIl;
    }
    
    public void setMotionZ(final double llIIIlIIlIIllI) {
        this.motionZ = llIIIlIIlIIllI;
    }
    
    public double getStrafeSpeed() {
        return this.strafeSpeed;
    }
    
    public void setStrafeSpeed(final double llIIIlIIlIIIlI) {
        this.strafeSpeed = llIIIlIIlIIIlI;
    }
    
    public void setMotionX(final double llIIIlIIllIlII) {
        this.motionX = llIIIlIIllIlII;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public void setMotionY(final double llIIIlIIlIllII) {
        this.motionY = llIIIlIIlIllII;
    }
}
