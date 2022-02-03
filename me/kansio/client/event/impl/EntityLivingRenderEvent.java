package me.kansio.client.event.impl;

import me.kansio.client.event.*;
import net.minecraft.entity.*;

public class EntityLivingRenderEvent extends Event
{
    private /* synthetic */ boolean isPost;
    private /* synthetic */ boolean isPre;
    private /* synthetic */ EntityLivingBase entityLivingBase;
    
    public boolean isPost() {
        return this.isPost;
    }
    
    public EntityLivingBase getEntity() {
        return this.entityLivingBase;
    }
    
    public boolean isPre() {
        return this.isPre;
    }
    
    public EntityLivingRenderEvent(final boolean lIllIlIlllllll, final EntityLivingBase lIllIlIllllllI) {
        this.entityLivingBase = lIllIlIllllllI;
        this.isPre = lIllIlIlllllll;
        this.isPost = !lIllIlIlllllll;
    }
}
