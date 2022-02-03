package me.kansio.client.event.impl;

import me.kansio.client.event.*;
import net.minecraft.client.*;

public class UpdateEvent extends Event
{
    private final /* synthetic */ boolean isPre;
    private /* synthetic */ double posY;
    private /* synthetic */ double posZ;
    private /* synthetic */ float rotationYaw;
    private /* synthetic */ boolean onGround;
    private /* synthetic */ double posX;
    private /* synthetic */ float rotationPitch;
    
    public void setPosY(final double lIIllIIIlIl) {
        this.posY = lIIllIIIlIl;
    }
    
    public void setPosX(final double lIIllIlIlll) {
        this.posX = lIIllIlIlll;
    }
    
    public void setRotationYaw(final float lIIlIlIIIlI) {
        Minecraft.getMinecraft().thePlayer.renderYawOffset = lIIlIlIIIlI;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = lIIlIlIIIlI;
        this.rotationYaw = lIIlIlIIIlI;
    }
    
    public void setPosZ(final double lIIlIllIlIl) {
        this.posZ = lIIlIllIlIl;
    }
    
    public float getRotationPitch() {
        return this.rotationPitch;
    }
    
    public void setOnGround(final boolean lIIIllllIlI) {
        this.onGround = lIIIllllIlI;
    }
    
    public UpdateEvent(final boolean lIIlllIIlll) {
        this.isPre = lIIlllIIlll;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public boolean isPre() {
        return this.isPre;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public UpdateEvent(final double lIIllllIllI, final double lIIllllIlIl, final double lIIllllIlII, final float lIIllllIIll, final float lIIllllIIlI, final boolean lIIlllllIII) {
        this(true);
        this.posX = lIIllllIllI;
        this.posY = lIIllllIlIl;
        this.posZ = lIIllllIlII;
        this.rotationYaw = lIIllllIIll;
        this.rotationPitch = lIIllllIIlI;
        this.onGround = lIIlllllIII;
    }
    
    public float getRotationYaw() {
        return this.rotationYaw;
    }
    
    public void setRotationPitch(final float lIIlIIIllIl) {
        Minecraft.getMinecraft().thePlayer.renderPitchHead = lIIlIIIllIl;
        this.rotationPitch = lIIlIIIllIl;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
}
