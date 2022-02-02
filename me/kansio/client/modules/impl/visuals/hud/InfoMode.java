package me.kansio.client.modules.impl.visuals.hud;

import me.kansio.client.utils.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;
import me.kansio.client.event.impl.*;

public abstract class InfoMode extends Util
{
    private final /* synthetic */ String name;
    
    public HUD getHud() {
        return (HUD)Client.getInstance().getModuleManager().getModuleByName("Hud");
    }
    
    public InfoMode(final String lIIlIIIllIllI) {
        this.name = lIIlIIIllIllI;
    }
    
    public void onDisable() {
    }
    
    public void onRenderOverlay(final RenderOverlayEvent lIIlIIIllIlII) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public void onEnable() {
    }
}
