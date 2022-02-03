package me.kansio.client.event.impl;

import me.kansio.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class RenderOverlayEvent extends Event
{
    private /* synthetic */ ScaledResolution sr;
    
    public ScaledResolution getSr() {
        return this.sr;
    }
    
    public RenderOverlayEvent() {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
    }
}
