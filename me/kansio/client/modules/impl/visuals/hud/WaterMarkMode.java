package me.kansio.client.modules.impl.visuals.hud;

import me.kansio.client.utils.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;

public abstract class WaterMarkMode extends Util
{
    private final /* synthetic */ String name;
    
    public void onRenderOverlay(final RenderOverlayEvent lllllllllllllllllllIlllllIIllIII) {
    }
    
    public HUD getHud() {
        return (HUD)Client.getInstance().getModuleManager().getModuleByName("Hud");
    }
    
    public WaterMarkMode(final String lllllllllllllllllllIlllllIIlllII) {
        this.name = lllllllllllllllllllIlllllIIlllII;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void onDisable() {
    }
    
    public void onEnable() {
    }
}
