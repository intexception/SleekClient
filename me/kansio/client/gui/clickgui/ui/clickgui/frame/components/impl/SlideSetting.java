package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import java.math.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.*;
import me.kansio.client.value.*;
import me.kansio.client.value.value.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import me.kansio.client.utils.render.*;

public class SlideSetting extends Component implements Priority
{
    private /* synthetic */ boolean drag;
    
    @Override
    public void onGuiClosed(final int llllllllllllllllllllllllllIIllll, final int llllllllllllllllllllllllllIIllIl, final int llllllllllllllllllllllllllIIlIll) {
    }
    
    private double roundToPlace(final double lIll, final int lIlI) {
        if (lIlI < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal llII = new BigDecimal(lIll);
        llII = llII.setScale(lIlI, RoundingMode.HALF_UP);
        return llII.doubleValue();
    }
    
    @Override
    public void keyTyped(final char llllllllllllllllllllllllllIIIllI, final int llllllllllllllllllllllllllIIIlII) {
    }
    
    private double snapToStep(double I, final double llllllllllllllllllllllllllllllll) {
        if (llllllllllllllllllllllllllllllll > 0.0) {
            I = llllllllllllllllllllllllllllllll * Math.round((double)(I / llllllllllllllllllllllllllllllll));
        }
        return (double)I;
    }
    
    public SlideSetting(final int lllIlI, final int llIlII, final FrameModule llIIll, final Value llIIlI) {
        super(lllIlI, llIlII, llIIll, llIIlI);
    }
    
    private void setValue(final Number lllllllllllllllllllllllllllllIII) {
        final NumberValue llllllllllllllllllllllllllllIllI = (NumberValue)this.getSetting();
        if (llllllllllllllllllllllllllllIllI.getIncrement() instanceof Double) {
            llllllllllllllllllllllllllllIllI.setValue(MathHelper.clamp_double(this.snapToStep(lllllllllllllllllllllllllllllIII.doubleValue(), llllllllllllllllllllllllllllIllI.getIncrement().doubleValue()), llllllllllllllllllllllllllllIllI.getMin().doubleValue(), llllllllllllllllllllllllllllIllI.getMax().doubleValue()));
        }
        if (llllllllllllllllllllllllllllIllI.getIncrement() instanceof Integer) {
            llllllllllllllllllllllllllllIllI.setValue(MathHelper.clamp_int((int)this.snapToStep(lllllllllllllllllllllllllllllIII.doubleValue(), llllllllllllllllllllllllllllIllI.getIncrement().doubleValue()), llllllllllllllllllllllllllllIllI.getMin().intValue(), llllllllllllllllllllllllllllIllI.getMax().intValue()));
        }
        if (llllllllllllllllllllllllllllIllI.getIncrement() instanceof Float) {
            llllllllllllllllllllllllllllIllI.setValue(MathHelper.clamp_float((float)this.snapToStep(lllllllllllllllllllllllllllllIII.doubleValue(), llllllllllllllllllllllllllllIllI.getIncrement().doubleValue()), llllllllllllllllllllllllllllIllI.getMin().floatValue(), llllllllllllllllllllllllllllIllI.getMax().floatValue()));
        }
        if (llllllllllllllllllllllllllllIllI.getIncrement() instanceof Long) {
            llllllllllllllllllllllllllllIllI.setValue(MathHelper.clamp_int((int)this.snapToStep(lllllllllllllllllllllllllllllIII.doubleValue(), llllllllllllllllllllllllllllIllI.getIncrement().doubleValue()), llllllllllllllllllllllllllllIllI.getMin().intValue(), llllllllllllllllllllllllllllIllI.getMax().intValue()));
        }
    }
    
    @Override
    public void drawScreen(final int lIIIll, final int lIIIlI) {
        if (!Mouse.isButtonDown(0)) {
            this.drag = false;
        }
        final NumberValue lIIIIl = (NumberValue)this.getSetting();
        final double lIIIII = lIIIIl.getMin().doubleValue();
        final double lllll = lIIIIl.getMax().doubleValue();
        final double llllI = Math.min(130, Math.max(0, lIIIll - this.x));
        final double lllIl = 125.0 * (lIIIIl.getValue().doubleValue() - lIIIII) / (lllll - lIIIII);
        Gui.drawRect(this.x, this.y, this.x + (int)lllIl, this.y + this.getOffset(), SlideSetting.darkerMainColor);
        final FontRenderer lllII = Minecraft.getMinecraft().fontRendererObj;
        if (this.drag) {
            if (llllI == 0.0) {
                lIIIIl.setValue(lIIIII);
            }
            else {
                final double lIIlIl = this.roundToPlace(llllI / 125.0 * (lllll - lIIIII) + lIIIII, 2);
                if (lIIlIl <= lllll) {
                    this.setValue(lIIlIl);
                }
            }
        }
        lllII.drawString(String.valueOf(new StringBuilder().append(this.getSetting().getName()).append(": ").append(this.roundToPlace(((NumberValue)this.getSetting()).getValue().doubleValue(), 2))), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lllII.FONT_HEIGHT / 2.0f), -1, true);
    }
    
    @Override
    public void initGui() {
        this.drag = false;
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
    
    @Override
    public boolean mouseClicked(final int llllllllllllllllllllllllllIllIIl, final int llllllllllllllllllllllllllIlllll, final int llllllllllllllllllllllllllIlllIl) {
        final boolean drag = RenderUtils.hover(this.x, this.y, llllllllllllllllllllllllllIllIIl, llllllllllllllllllllllllllIlllll, 125, this.getOffset()) && llllllllllllllllllllllllllIlllIl == 0;
        this.drag = drag;
        return drag;
    }
}
