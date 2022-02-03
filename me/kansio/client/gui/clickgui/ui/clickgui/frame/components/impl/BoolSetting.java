package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import net.minecraft.client.*;
import java.awt.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.*;
import me.kansio.client.value.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;

public class BoolSetting extends Component implements Priority
{
    private final /* synthetic */ Animate animation;
    
    @Override
    public void drawScreen(final int lIlIlllIlIl, final int lIlIlllIlII) {
        this.animation.update();
        final FontRenderer lIlIlllIIll = Minecraft.getMinecraft().fontRendererObj;
        lIlIlllIIll.drawString(this.getSetting().getName(), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lIlIlllIIll.FONT_HEIGHT / 2.0f), -1, true);
        RenderUtils.drawFilledCircle(this.x + 125 - 10, (int)(this.y + (this.getOffset() / 2.0f - lIlIlllIIll.FONT_HEIGHT / 2.0f) + 6.75f), 5.0f, new Color(BoolSetting.darkerMainColor));
        if (((BooleanValue)this.getSetting()).getValue() || this.animation.getValue() != 0.0f) {
            RenderUtils.drawFilledCircle(this.x + 125 - 10, (int)(this.y + (this.getOffset() / 2.0f - lIlIlllIIll.FONT_HEIGHT / 2.0f) + 6.75f), this.animation.getValue(), new Color(BoolSetting.enabledColor));
            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void initGui() {
    }
    
    public BoolSetting(final int lIllIIIIIlI, final int lIllIIIIIIl, final FrameModule lIlIllllIll, final Value lIlIlllllll) {
        super(lIllIIIIIlI, lIllIIIIIIl, lIlIllllIll, lIlIlllllll);
        this.animation = new Animate().setMin(0.0f).setMax(5.0f).setSpeed(15.0f).setEase(Easing.LINEAR).setReversed(!((BooleanValue)lIlIlllllll).getValue());
    }
    
    @Override
    public void keyTyped(final char lIlIlIllllI, final int lIlIlIlllIl) {
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
    
    @Override
    public boolean mouseClicked(final int lIlIllIIllI, final int lIlIllIlIIl, final int lIlIllIlIII) {
        if (RenderUtils.hover(this.x, this.y, lIlIllIIllI, lIlIllIlIIl, 125, this.getOffset())) {
            final BooleanValue lIlIllIllII = (BooleanValue)this.getSetting();
            lIlIllIllII.setValue(!lIlIllIllII.getValue());
            this.animation.setReversed(!lIlIllIllII.getValue());
            return true;
        }
        return false;
    }
    
    @Override
    public void onGuiClosed(final int lIlIllIIIlI, final int lIlIllIIIIl, final int lIlIllIIIII) {
    }
}
