package me.kansio.client.gui.clickgui.ui.clickgui.frame;

import net.minecraft.client.gui.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.*;
import java.io.*;
import java.util.*;
import me.kansio.client.modules.api.*;

public class ClickGUI extends GuiScreen
{
    private final /* synthetic */ List<FrameCategory> categories;
    
    @Override
    protected void keyTyped(final char lIIIIlIlIIllII, final int lIIIIlIlIIlIII) throws IOException {
        this.categories.forEach(lIIIIlIIIIlllI -> lIIIIlIIIIlllI.keyTyped(lIIIIlIlIIllII, lIIIIlIlIIlIII));
        super.keyTyped(lIIIIlIlIIllII, lIIIIlIlIIlIII);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public ClickGUI() {
        this.categories = new ArrayList<FrameCategory>();
        int lIIIIlIllIIllI = -1;
        final long lIIIIlIllIIIll = (Object)ModuleCategory.values();
        final float lIIIIlIllIIIlI = lIIIIlIllIIIll.length;
        for (byte lIIIIlIllIIIIl = 0; lIIIIlIllIIIIl < lIIIIlIllIIIlI; ++lIIIIlIllIIIIl) {
            final ModuleCategory lIIIIlIllIlIII = lIIIIlIllIIIll[lIIIIlIllIIIIl];
            if (lIIIIlIllIlIII != ModuleCategory.HIDDEN) {
                this.categories.add(new FrameCategory(lIIIIlIllIlIII, 10 + ++lIIIIlIllIIllI * 135, 10));
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int lIIIIlIIlllllI, final int lIIIIlIIllllIl, final int lIIIIlIIllllII) throws IOException {
        this.categories.forEach(lIIIIlIIIlIlll -> lIIIIlIIIlIlll.mouseClicked(lIIIIlIIlllllI, lIIIIlIIllllIl, lIIIIlIIllllII));
        super.mouseClicked(lIIIIlIIlllllI, lIIIIlIIllllIl, lIIIIlIIllllII);
    }
    
    @Override
    public void drawScreen(final int lIIIIlIlIlIlll, final int lIIIIlIlIlIIlI, final float lIIIIlIlIlIIIl) {
        this.categories.forEach(lIIIIlIIIIIlIl -> lIIIIlIIIIIlIl.drawScreen(lIIIIlIlIlIlll, lIIIIlIlIlIIlI));
        super.drawScreen(lIIIIlIlIlIlll, lIIIIlIlIlIIlI, lIIIIlIlIlIIIl);
    }
    
    @Override
    protected void mouseReleased(final int lIIIIlIIllIllI, final int lIIIIlIIllIIIl, final int lIIIIlIIllIlII) {
        this.categories.forEach(lIIIIlIIlIIlll -> lIIIIlIIlIIlll.mouseReleased(lIIIIlIIllIllI, lIIIIlIIllIIIl, lIIIIlIIllIlII));
        super.mouseReleased(lIIIIlIIllIllI, lIIIIlIIllIIIl, lIIIIlIIllIlII);
    }
    
    @Override
    public void initGui() {
        this.categories.forEach(FrameCategory::initGui);
        super.initGui();
    }
}
