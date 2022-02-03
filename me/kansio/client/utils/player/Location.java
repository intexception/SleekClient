package me.kansio.client.utils.player;

import javax.vecmath.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;

public class Location
{
    public /* synthetic */ float pitch;
    public /* synthetic */ float yaw;
    public /* synthetic */ boolean ignoreF3Rotations;
    public /* synthetic */ double z;
    public /* synthetic */ double x;
    public /* synthetic */ double y;
    public /* synthetic */ boolean can3DRotate;
    
    public Location multiply(final double lllIlIIlIllII, final double lllIlIIlIllll, final double lllIlIIlIlIlI) {
        this.x *= lllIlIIlIllII;
        this.y *= lllIlIIlIllll;
        this.z *= lllIlIIlIlIlI;
        return this;
    }
    
    public Location(final float lllIllIIIlIll, final float lllIllIIIlIlI) {
        this.yaw = lllIllIIIlIll;
        this.pitch = lllIllIIIlIlI;
    }
    
    public Vector2f getRotations() {
        return new Vector2f(this.yaw, this.pitch);
    }
    
    public Location normalize() {
        this.normalizeX();
        this.normalizeY();
        this.normalizeZ();
        return this;
    }
    
    public boolean isCan3DRotate() {
        return this.can3DRotate;
    }
    
    public double getY() {
        return this.y;
    }
    
    public Location setRotation(final Vector2f lllIIllllIlll) {
        if (lllIIllllIlll == null) {
            return this;
        }
        this.yaw = lllIIllllIlll.x;
        this.pitch = Math.min(Math.max(lllIIllllIlll.y, -90.0f), 90.0f);
        return this;
    }
    
    public void setIgnoreF3Rotations(final boolean lllIlIllIllll) {
        this.ignoreF3Rotations = lllIlIllIllll;
    }
    
    public Location(final Entity lllIllIlIlllI) {
        this.yaw = lllIllIlIlllI.rotationYaw;
        this.pitch = lllIllIlIlllI.rotationPitch;
        this.x = lllIllIlIlllI.posX;
        this.y = lllIllIlIlllI.posY;
        this.z = lllIllIlIlllI.posZ;
    }
    
    public Location divide(final Location lllIlIlIlIIII) {
        this.x /= lllIlIlIlIIII.x;
        this.y /= lllIlIlIlIIII.y;
        this.z /= lllIlIlIlIIII.z;
        return this;
    }
    
    public Location subtract(final double lllIlIIllllII, final double lllIlIIllIlll, final double lllIlIIlllIlI) {
        this.x -= lllIlIIllllII;
        this.y -= lllIlIIllIlll;
        this.z -= lllIlIIlllIlI;
        return this;
    }
    
    public Location setY(final double lllIlIIIIlIII) {
        this.y = lllIlIIIIlIII;
        return this;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public Location setX(final double lllIlIIIIlllI) {
        this.x = lllIlIIIIlllI;
        return this;
    }
    
    public Location normalizeY() {
        Minecraft.getMinecraft().thePlayer.motionY = this.y;
        return this;
    }
    
    public Location(final float lllIllIlIIllI, final float lllIllIIlllll, final double lllIllIIllllI, final double lllIllIlIIIll, final double lllIllIIlllII) {
        this.yaw = lllIllIlIIllI;
        this.pitch = lllIllIIlllll;
        this.x = lllIllIIllllI;
        this.y = lllIllIlIIIll;
        this.z = lllIllIIlllII;
    }
    
    public Location(final double lllIllIIlIllI, final double lllIllIIlIIIl, final double lllIllIIlIlII) {
        this.x = lllIllIIlIllI;
        this.y = lllIllIIlIIIl;
        this.z = lllIllIIlIlII;
    }
    
    public Location normalizeX() {
        Minecraft.getMinecraft().thePlayer.motionX = this.x;
        return this;
    }
    
    public double getX() {
        return this.x;
    }
    
    public Location multiply(final Location lllIlIlIlIlII) {
        this.x *= lllIlIlIlIlII.x;
        this.y *= lllIlIlIlIlII.y;
        this.z *= lllIlIlIlIlII.z;
        return this;
    }
    
    public Location setZ(final double lllIlIIIIIIII) {
        this.z = lllIlIIIIIIII;
        return this;
    }
    
    public void setCan3DRotate(final boolean lllIlIllllIlI) {
        this.can3DRotate = lllIlIllllIlI;
    }
    
    public Location setPitch(final float lllIlIIIlIIlI) {
        this.pitch = lllIlIIIlIIlI;
        return this;
    }
    
    public Location clone() {
        return new Location(this.yaw, this.pitch, this.x, this.y, this.z);
    }
    
    public double getZ() {
        return this.z;
    }
    
    public boolean isIgnoreF3Rotations() {
        return this.ignoreF3Rotations;
    }
    
    public Location divide(final double lllIlIIlIIIII, final double lllIlIIlIIIll, final double lllIlIIlIIIlI) {
        this.x /= lllIlIIlIIIII;
        this.y /= lllIlIIlIIIll;
        this.z /= lllIlIIlIIIlI;
        return this;
    }
    
    public Location add(final Location lllIlIllIIIII) {
        this.x += lllIlIllIIIII.x;
        this.y += lllIlIllIIIII.y;
        this.z += lllIlIllIIIII.z;
        return this;
    }
    
    public Location add(final double lllIlIlIIlIII, final double lllIlIlIIIlll, final double lllIlIlIIIIlI) {
        this.x += lllIlIlIIlIII;
        this.y += lllIlIlIIIlll;
        this.z += lllIlIlIIIIlI;
        return this;
    }
    
    public Location subtract(final Location lllIlIlIlllII) {
        this.x -= lllIlIlIlllII.x;
        this.y -= lllIlIlIlllII.y;
        this.z -= lllIlIlIlllII.z;
        return this;
    }
    
    public Location setYaw(final float lllIlIIIllIlI) {
        this.yaw = lllIlIIIllIlI;
        return this;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public Location normalizeZ() {
        Minecraft.getMinecraft().thePlayer.motionZ = this.z;
        return this;
    }
}
