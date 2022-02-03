package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import java.math.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.*;
import me.kansio.client.value.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import me.kansio.client.utils.render.*;
import java.util.*;

public class StringSetting extends Component implements Priority
{
    private /* synthetic */ boolean typing;
    
    @Override
    public void onGuiClosed(final int llllllllllllllllllllllIIllIIIIll, final int llllllllllllllllllllllIIllIIIIIl, final int llllllllllllllllllllllIIllIIIIII) {
    }
    
    private double roundToPlace(final double llllllllllllllllllllllIIlllIllIl, final int llllllllllllllllllllllIIlllIIllI) {
        if (llllllllllllllllllllllIIlllIIllI < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal llllllllllllllllllllllIIlllIlIlI = new BigDecimal(llllllllllllllllllllllIIlllIllIl);
        llllllllllllllllllllllIIlllIlIlI = llllllllllllllllllllllIIlllIlIlI.setScale(llllllllllllllllllllllIIlllIIllI, RoundingMode.HALF_UP);
        return llllllllllllllllllllllIIlllIlIlI.doubleValue();
    }
    
    public StringSetting(final int llllllllllllllllllllllIlIIIllIIl, final int llllllllllllllllllllllIlIIIllIII, final FrameModule llllllllllllllllllllllIlIIIlIlll, final Value llllllllllllllllllllllIlIIIIlllI) {
        super(llllllllllllllllllllllIlIIIllIIl, llllllllllllllllllllllIlIIIllIII, llllllllllllllllllllllIlIIIlIlll, llllllllllllllllllllllIlIIIIlllI);
    }
    
    @Override
    public void initGui() {
        this.typing = false;
    }
    
    @Override
    public void drawScreen(final int llllllllllllllllllllllIIlllllIII, final int llllllllllllllllllllllIIllllIlll) {
        final StringValue llllllllllllllllllllllIIllllIllI = (StringValue)this.getSetting();
        final FontRenderer llllllllllllllllllllllIIllllIlIl = Minecraft.getMinecraft().fontRendererObj;
        llllllllllllllllllllllIIllllIlIl.drawString(String.valueOf(new StringBuilder().append(llllllllllllllllllllllIIllllIllI.getName()).append(": ").append(llllllllllllllllllllllIIllllIllI.getValue())), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - llllllllllllllllllllllIIllllIlIl.FONT_HEIGHT / 2.0f), -1, true);
        if (this.typing) {
            Gui.drawRect(this.x + 125, this.y + (this.getOffset() / 2.0f - llllllllllllllllllllllIIllllIlIl.FONT_HEIGHT / 2.0f) + 9.0f, 117.0, this.y + (this.getOffset() / 2.0f - llllllllllllllllllllllIIllllIlIl.FONT_HEIGHT / 2.0f) + 7.0f + 4.0f, -1);
        }
    }
    
    @Override
    public boolean mouseClicked(final int llllllllllllllllllllllIIllIIlIll, final int llllllllllllllllllllllIIllIIlIlI, final int llllllllllllllllllllllIIllIIIlIl) {
        if (RenderUtils.hover(this.x, this.y, llllllllllllllllllllllIIllIIlIll, llllllllllllllllllllllIIllIIlIlI, 125, this.getOffset()) && llllllllllllllllllllllIIllIIIlIl == 0) {
            this.typing = !this.typing;
        }
        return RenderUtils.hover(this.x, this.y, llllllllllllllllllllllIIllIIlIll, llllllllllllllllllllllIIllIIlIlI, 125, this.getOffset()) && llllllllllllllllllllllIIllIIIlIl == 0;
    }
    
    @Override
    public void keyTyped(final char llllllllllllllllllllllIIlIIlllll, final int llllllllllllllllllllllIIlIIllllI) {
        final StringValue llllllllllllllllllllllIIlIIlllIl = (StringValue)this.getSetting();
        if (llllllllllllllllllllllIIlIIllllI == 54 || llllllllllllllllllllllIIlIIllllI == 42 || llllllllllllllllllllllIIlIIllllI == 58) {
            return;
        }
        if (llllllllllllllllllllllIIlIIllllI == 28) {
            this.typing = false;
            return;
        }
        if (llllllllllllllllllllllIIlIIllllI == 14 && this.typing) {
            if (llllllllllllllllllllllIIlIIlllIl.getValue().toCharArray().length == 0) {
                return;
            }
            llllllllllllllllllllllIIlIIlllIl.setValue(this.removeLastChar(llllllllllllllllllllllIIlIIlllIl.getValue()));
        }
        else {
            final List<Character> llllllllllllllllllllllIIlIIlllII = Arrays.asList('&', ' ', '#', '[', ']', '(', ')', '.', ',', '<', '>', '-', '$', '!', '\"', '\'', '\\', '/', '=', '+', ',', '|', '^', '?', '`', ';', ':', '@', '£', '%', '{', '}', '_', '*', '»');
            for (final char llllllllllllllllllllllIIlIlIIIIl : llllllllllllllllllllllIIlIIlllII) {
                if (llllllllllllllllllllllIIlIIlllll == llllllllllllllllllllllIIlIlIIIIl && this.typing) {
                    llllllllllllllllllllllIIlIIlllIl.setValue(String.valueOf(new StringBuilder().append(llllllllllllllllllllllIIlIIlllIl.getValue()).append(llllllllllllllllllllllIIlIIlllll)));
                    return;
                }
            }
            if (!Character.isLetterOrDigit(llllllllllllllllllllllIIlIIlllll)) {
                return;
            }
            if (this.typing) {
                llllllllllllllllllllllIIlIIlllIl.setValue(String.valueOf(new StringBuilder().append(llllllllllllllllllllllIIlIIlllIl.getValue()).append(llllllllllllllllllllllIIlIIlllll)));
            }
        }
    }
    
    private String removeLastChar(final String llllllllllllllllllllllIIlIIlIIIl) {
        return llllllllllllllllllllllIIlIIlIIIl.substring(0, llllllllllllllllllllllIIlIIlIIIl.length() - 1);
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
}
