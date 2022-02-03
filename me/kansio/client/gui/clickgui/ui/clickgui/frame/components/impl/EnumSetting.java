package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import net.minecraft.client.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.gui.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.*;
import me.kansio.client.value.*;

public class EnumSetting extends Component implements Priority
{
    @Override
    public void initGui() {
    }
    
    @Override
    public void keyTyped(final char lIlIllIll, final int lIlIllIlI) {
    }
    
    @Override
    public void drawScreen(final int lIlllllII, final int lIllllIll) {
        final FontRenderer lIllllIlI = Minecraft.getMinecraft().fontRendererObj;
        lIllllIlI.drawString(this.getSetting().getName(), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lIllllIlI.FONT_HEIGHT / 2.0f), -1, true);
        lIllllIlI.drawString(((ModeValue)this.getSetting()).getValue().toUpperCase(), (float)(this.x + 125 - lIllllIlI.getStringWidth(((ModeValue)this.getSetting()).getValue().toUpperCase()) - 5), this.y + (this.getOffset() / 2.0f - lIllllIlI.FONT_HEIGHT / 2.0f), -1, true);
    }
    
    @Override
    public boolean mouseClicked(final int lIllIIlll, final int lIllIlIlI, final int lIllIlIIl) {
        if (RenderUtils.hover(this.x, this.y, lIllIIlll, lIllIlIlI, 125, this.getOffset())) {
            final ModeValue lIllIlllI = (ModeValue)this.getSetting();
            int lIllIllIl = 0;
            for (final String lIllIllll : lIllIlllI.getChoices()) {
                if (lIllIllll.equals(((Value<Object>)lIllIlllI).getValue())) {
                    break;
                }
                ++lIllIllIl;
            }
            if (lIllIlIIl == 1) {
                if (lIllIllIl - 1 >= 0) {
                    lIllIlllI.setValue(lIllIlllI.getChoices().get(lIllIllIl - 1));
                }
                else {
                    lIllIlllI.setValue(lIllIlllI.getChoices().get(lIllIlllI.getChoices().size() - 1));
                }
                if (lIllIlllI.getOwner().isToggled()) {
                    lIllIlllI.getOwner().toggle();
                    lIllIlllI.getOwner().toggle();
                }
            }
            if (lIllIlIIl == 0) {
                if (lIllIllIl + 1 < lIllIlllI.getChoices().size()) {
                    lIllIlllI.setValue(lIllIlllI.getChoices().get(lIllIllIl + 1));
                }
                else {
                    lIllIlllI.setValue(lIllIlllI.getChoices().get(0));
                }
                if (lIllIlllI.getOwner().isToggled()) {
                    lIllIlllI.getOwner().toggle();
                    lIllIlllI.getOwner().toggle();
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onGuiClosed(final int lIlIlllll, final int lIlIllllI, final int lIlIlllIl) {
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
    
    public EnumSetting(final int llIIIlIIl, final int llIIIIIll, final FrameModule llIIIIIlI, final Value llIIIIllI) {
        super(llIIIlIIl, llIIIIIll, llIIIIIlI, llIIIIllI);
    }
}
