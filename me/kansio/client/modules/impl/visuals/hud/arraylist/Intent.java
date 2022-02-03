package me.kansio.client.modules.impl.visuals.hud.arraylist;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.*;
import java.util.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.gui.*;

public class Intent extends ArrayListMode
{
    @Override
    public void onRenderOverlay(final RenderOverlayEvent lllllllllllllllllllIlllllIlllllI) {
        final ScaledResolution lllllllllllllllllllIlllllIllllIl = new ScaledResolution(Intent.mc);
        Client.getInstance().getModuleManager().getModules().sort(Comparator.comparingInt(lllllllllllllllllllIlllllIllIIlI -> Intent.mc.fontRendererObj.getStringWidth(lllllllllllllllllllIlllllIllIIlI.getName())).reversed());
        int lllllllllllllllllllIlllllIlllIll = 0;
        for (final Module lllllllllllllllllllIllllllIIIIII : Client.getInstance().getModuleManager().getModules()) {
            if (!lllllllllllllllllllIllllllIIIIII.isToggled()) {
                continue;
            }
            final double lllllllllllllllllllIllllllIIIIIl = lllllllllllllllllllIlllllIlllIll * (Intent.mc.fontRendererObj.FONT_HEIGHT + 6);
            if (this.getHud().font.getValue()) {
                Gui.drawRect(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Fonts.YantramanavThin.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 10, lllllllllllllllllllIllllllIIIIIl, lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Fonts.YantramanavThin.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 8, 6 + Intent.mc.fontRendererObj.FONT_HEIGHT + lllllllllllllllllllIllllllIIIIIl, ColorUtils.getColorFromHud(lllllllllllllllllllIlllllIlllIll).getRGB());
                Gui.drawRect(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Fonts.YantramanavThin.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 8, lllllllllllllllllllIllllllIIIIIl, lllllllllllllllllllIlllllIllllIl.getScaledWidth(), 6 + Intent.mc.fontRendererObj.FONT_HEIGHT + lllllllllllllllllllIllllllIIIIIl, -1879048192);
                Fonts.YantramanavThin.drawStringWithShadow(lllllllllllllllllllIllllllIIIIII.getName(), (float)(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Fonts.YantramanavThin.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 4), (float)(4.0 + lllllllllllllllllllIllllllIIIIIl), ColorUtils.getColorFromHud(lllllllllllllllllllIlllllIlllIll).getRGB());
            }
            else {
                Gui.drawRect(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Intent.mc.fontRendererObj.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 10, lllllllllllllllllllIllllllIIIIIl, lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Intent.mc.fontRendererObj.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 8, 6 + Intent.mc.fontRendererObj.FONT_HEIGHT + lllllllllllllllllllIllllllIIIIIl, ColorUtils.getColorFromHud(lllllllllllllllllllIlllllIlllIll).getRGB());
                Gui.drawRect(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Intent.mc.fontRendererObj.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 8, lllllllllllllllllllIllllllIIIIIl, lllllllllllllllllllIlllllIllllIl.getScaledWidth(), 6 + Intent.mc.fontRendererObj.FONT_HEIGHT + lllllllllllllllllllIllllllIIIIIl, -1879048192);
                Intent.mc.fontRendererObj.drawStringWithShadow(lllllllllllllllllllIllllllIIIIII.getName(), (float)(lllllllllllllllllllIlllllIllllIl.getScaledWidth() - Intent.mc.fontRendererObj.getStringWidth(lllllllllllllllllllIllllllIIIIII.getName()) - 4), (float)(4.0 + lllllllllllllllllllIllllllIIIIIl), ColorUtils.getColorFromHud(lllllllllllllllllllIlllllIlllIll).getRGB());
            }
            ++lllllllllllllllllllIlllllIlllIll;
        }
    }
    
    public Intent() {
        super("Intent");
    }
}
