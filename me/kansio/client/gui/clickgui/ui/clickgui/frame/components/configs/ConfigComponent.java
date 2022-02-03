package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs;

public abstract class ConfigComponent
{
    protected /* synthetic */ int x;
    private /* synthetic */ boolean hidden;
    private final /* synthetic */ FrameConfig owner;
    protected /* synthetic */ int y;
    
    public abstract int getOffset();
    
    public void setHidden(final boolean llllllllllllllllllllllllIllllIlI) {
        this.hidden = llllllllllllllllllllllllIllllIlI;
    }
    
    public ConfigComponent(final int lllllllllllllllllllllllllIIIllll, final int lllllllllllllllllllllllllIIIlllI, final FrameConfig lllllllllllllllllllllllllIIIllIl) {
        this.owner = lllllllllllllllllllllllllIIIllIl;
        this.x = lllllllllllllllllllllllllIIIllll;
        this.y = lllllllllllllllllllllllllIIIlllI;
    }
    
    public abstract void onGuiClosed(final int p0, final int p1, final int p2);
    
    public abstract boolean mouseClicked(final int p0, final int p1, final int p2);
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setX(final int lllllllllllllllllllllllllIIIlIIl) {
        this.x = lllllllllllllllllllllllllIIIlIIl;
    }
    
    public void setY(final int lllllllllllllllllllllllllIIIIIll) {
        this.y = lllllllllllllllllllllllllIIIIIll;
    }
    
    public abstract void drawScreen(final int p0, final int p1);
    
    public abstract void initGui();
    
    public abstract void keyTyped(final char p0, final int p1);
}
