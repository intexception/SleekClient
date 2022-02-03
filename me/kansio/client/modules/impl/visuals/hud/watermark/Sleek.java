package me.kansio.client.modules.impl.visuals.hud.watermark;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.modules.impl.visuals.*;
import java.awt.*;

public class Sleek extends WaterMarkMode
{
    public Sleek() {
        super("Sleek");
    }
    
    @Override
    public void onRenderOverlay(final RenderOverlayEvent llllllllllllllllllllIIllIllIIllI) {
        final HUD llllllllllllllllllllIIllIllIIlIl = this.getHud();
        final int llllllllllllllllllllIIllIllIIlII = llllllllllllllllllllIIllIllIIlIl.arrayListY.getValue().intValue();
        final Color llllllllllllllllllllIIllIllIIIll = ColorUtils.getColorFromHud(llllllllllllllllllllIIllIllIIlII);
        if (llllllllllllllllllllIIllIllIIlIl.font.getValue()) {
            Fonts.YantramanavThin.drawStringWithShadow(ChatUtil.translateColorCodes(this.getHud().clientName.getValue()), 4.0f, 4.0f, llllllllllllllllllllIIllIllIIIll.getRGB());
        }
        else {
            Sleek.mc.fontRendererObj.drawStringWithShadow(ChatUtil.translateColorCodes(this.getHud().clientName.getValue()), 4.0f, 4.0f, llllllllllllllllllllIIllIllIIIll.getRGB());
        }
    }
}
