package me.kansio.client.gui.clickgui.ui.clickgui.frame.components;

import me.kansio.client.value.*;

public abstract class Component
{
    private final /* synthetic */ Value setting;
    protected /* synthetic */ int x;
    protected /* synthetic */ int y;
    private /* synthetic */ boolean hidden;
    private final /* synthetic */ FrameModule owner;
    
    public void setX(final int llIllIIIIIIIlI) {
        this.x = llIllIIIIIIIlI;
    }
    
    public void setY(final int llIlIllllllIlI) {
        this.y = llIlIllllllIlI;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public abstract void initGui();
    
    public abstract boolean mouseClicked(final int p0, final int p1, final int p2);
    
    public Component(final int llIllIIIIIlllI, final int llIllIIIIIlIII, final FrameModule llIllIIIIIllII, final Value llIllIIIIIIllI) {
        this.owner = llIllIIIIIllII;
        this.setting = llIllIIIIIIllI;
        this.x = llIllIIIIIlllI;
        this.y = llIllIIIIIlIII;
    }
    
    public abstract void drawScreen(final int p0, final int p1);
    
    public abstract void onGuiClosed(final int p0, final int p1, final int p2);
    
    public abstract void keyTyped(final char p0, final int p1);
    
    public void setHidden(final boolean llIlIllllIlllI) {
        this.hidden = llIlIllllIlllI;
    }
    
    public abstract int getOffset();
    
    public Value getSetting() {
        return this.setting;
    }
}
