package me.kansio.client.event.impl;

import me.kansio.client.event.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class BlockCollisionEvent extends Event
{
    private /* synthetic */ int x;
    private /* synthetic */ AxisAlignedBB axisAlignedBB;
    private final /* synthetic */ Block block;
    private /* synthetic */ int y;
    private /* synthetic */ int z;
    
    public BlockCollisionEvent(final AxisAlignedBB lIIllIlIIIllIl, final Block lIIllIlIIIIllI, final int lIIllIlIIIIlIl, final int lIIllIlIIIIlII, final int lIIllIlIIIlIIl) {
        this.axisAlignedBB = lIIllIlIIIllIl;
        this.block = lIIllIlIIIIllI;
        this.x = lIIllIlIIIIlIl;
        this.y = lIIllIlIIIIlII;
        this.z = lIIllIlIIIlIIl;
    }
    
    public AxisAlignedBB getAxisAlignedBB() {
        return this.axisAlignedBB;
    }
    
    public void setY(final int lIIllIIllIlIII) {
        this.y = lIIllIIllIlIII;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setAxisAlignedBB(final AxisAlignedBB lIIllIIllllIlI) {
        this.axisAlignedBB = lIIllIIllllIlI;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public void setZ(final int lIIllIIlIlllll) {
        this.z = lIIllIIlIlllll;
    }
    
    public void setX(final int lIIllIIlllIIll) {
        this.x = lIIllIIlllIIll;
    }
}
