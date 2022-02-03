package me.kansio.client.modules.impl.visuals.hud.watermark;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.event.impl.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.utils.render.*;

public class Intent extends WaterMarkMode
{
    public Intent() {
        super("Intent");
    }
    
    @Override
    public void onRenderOverlay(final RenderOverlayEvent lllllllllllllllllllllIlIlIIllIlI) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(4.0f, 4.0f, 0.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.translate(-4.0f, -4.0f, 0.0f);
        if (this.getHud().font.getValue()) {
            Fonts.YantramanavThin.drawString(ChatUtil.translateColorCodes(this.getHud().clientName.getValue()), 4.0f, 4.0f, ColorUtils.getColorFromHud(1).getRGB());
        }
        else {
            Intent.mc.fontRendererObj.drawString(ChatUtil.translateColorCodes(this.getHud().clientName.getValue()), 4, 4, ColorUtils.getColorFromHud(1).getRGB());
        }
        GlStateManager.popMatrix();
    }
}
