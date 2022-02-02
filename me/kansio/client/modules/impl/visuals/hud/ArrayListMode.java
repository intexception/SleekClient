package me.kansio.client.modules.impl.visuals.hud;

import me.kansio.client.utils.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;
import me.kansio.client.event.impl.*;

public abstract class ArrayListMode extends Util
{
    private final /* synthetic */ String name;
    
    public void onDisable() {
    }
    
    public ArrayListMode(final String llIlIllllIlIlI) {
        this.name = llIlIllllIlIlI;
    }
    
    public String getName() {
        return this.name;
    }
    
    public HUD getHud() {
        return (HUD)Client.getInstance().getModuleManager().getModuleByName("Hud");
    }
    
    public void onRenderOverlay(final RenderOverlayEvent llIlIllllIIllI) {
    }
    
    public void onEnable() {
    }
}
