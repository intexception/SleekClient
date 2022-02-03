package me.kansio.client.modules.impl.visuals.hud.arraylist;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.utils.render.*;
import java.util.*;
import me.kansio.client.*;
import me.kansio.client.utils.font.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import me.kansio.client.value.*;

public class Sleek extends ArrayListMode
{
    public Sleek() {
        super("Sleek");
    }
    
    @Override
    public void onRenderOverlay(final RenderOverlayEvent llllIIlllllll) {
        final HUD llllIIllllllI = this.getHud();
        HUD.notifications = (llllIIllllllI.noti.getValue() && llllIIllllllI.isToggled());
        int llllIIlllllIl = llllIIllllllI.arrayListY.getValue().intValue();
        int llllIIlllllII = 0;
        final Color llllIIllllIll = ColorUtils.getColorFromHud(llllIIlllllIl);
        if (llllIIllllllI.font.getValue()) {
            final ArrayList<Module> llllIlIIIlIIl = (ArrayList<Module>)(ArrayList)Client.getInstance().getModuleManager().getModulesSorted(Fonts.YantramanavThin);
            llllIlIIIlIIl.removeIf(llllIIlIlllll -> !llllIIlIlllll.isToggled());
            if (llllIIllllllI.hideRender.getValue()) {
                llllIlIIIlIIl.removeIf(llllIIllIIIIl -> llllIIllIIIIl.getCategory() == ModuleCategory.VISUALS);
            }
            for (final Module llllIlIIIlIlI : llllIlIIIlIIl) {
                if (!llllIlIIIlIlI.isToggled()) {
                    continue;
                }
                ++llllIIlllllII;
                final Module llllIlIIIllIl = llllIlIIIlIIl.get(llllIIlllllII - 1);
                final String llllIlIIIllII = String.valueOf(new StringBuilder().append(llllIlIIIlIlI.getName()).append("§7").append(llllIlIIIlIlI.getFormattedSuffix()));
                final float llllIlIIIlIll = (float)(llllIIlllllll.getSr().getScaledWidth() - Fonts.YantramanavThin.getStringWidth(llllIlIIIllII) - 6);
                Gui.drawRect(llllIlIIIlIll - 1.5, llllIIlllllIl - 1, llllIIlllllll.getSr().getScaledWidth(), Fonts.YantramanavThin.getHeight() + llllIIlllllIl, new Color(0, 0, 0, llllIIllllllI.bgalpha.getValue()).getRGB());
                final String s = this.getHud().line.getValue();
                switch (s) {
                    case "Wrapped": {
                        Gui.drawRect(llllIlIIIlIll - 2.5, llllIIlllllIl - 1, llllIlIIIlIll - 1.5, Fonts.YantramanavThin.getHeight() + llllIIlllllIl + 1, llllIIllllIll.getRGB());
                        break;
                    }
                }
                if (llllIlIIIlIIl.size() > llllIIlllllII) {
                    final Module llllIlIIlIIII = llllIlIIIlIIl.get(llllIIlllllII);
                    final String llllIlIIIllll = String.valueOf(new StringBuilder().append(llllIlIIlIIII.getName()).append("§7").append(llllIlIIlIIII.getFormattedSuffix()));
                    final float llllIlIIIlllI = (float)(llllIIlllllll.getSr().getScaledWidth() - Fonts.YantramanavThin.getStringWidth(llllIlIIIllll) - 7.5);
                    final char llllIIllIlIll = ((Value<Character>)this.getHud().line).getValue();
                    Exception llllIIllIlIlI = (Exception)(-1);
                    switch (((String)llllIIllIlIll).hashCode()) {
                        case 2433880: {
                            if (((String)llllIIllIlIll).equals("None")) {
                                llllIIllIlIlI = (Exception)0;
                                break;
                            }
                            break;
                        }
                        case -1034806171: {
                            if (((String)llllIIllIlIll).equals("Wrapped")) {
                                llllIIllIlIlI = (Exception)1;
                                break;
                            }
                            break;
                        }
                    }
                    switch (llllIIllIlIlI) {
                        case 1L: {
                            Gui.drawRect(llllIlIIIlIll - 2.5, Fonts.YantramanavThin.getHeight() + llllIIlllllIl + 1, llllIlIIIlllI, Fonts.YantramanavThin.getHeight() + llllIIlllllIl + 2, llllIIllllIll.getRGB());
                            break;
                        }
                    }
                }
                else {
                    final String s2 = this.getHud().line.getValue();
                    switch (s2) {
                        case "Wrapped": {
                            Gui.drawRect(llllIlIIIlIll - 2.5, Fonts.YantramanavThin.getHeight() + llllIIlllllIl + 1, llllIlIIIlIll + 100.0f, Fonts.YantramanavThin.getHeight() + llllIIlllllIl + 2, llllIIllllIll.getRGB());
                            break;
                        }
                    }
                }
                Fonts.YantramanavThin.drawStringWithShadow(llllIlIIIllII, (float)(llllIlIIIlIll + 1.5), (float)(0.5 + llllIIlllllIl), llllIIllllIll.getRGB());
                llllIIlllllIl += 9;
            }
        }
        else {
            final ArrayList<Module> llllIlIIIIIIl = (ArrayList<Module>)(ArrayList)Client.getInstance().getModuleManager().getModulesSorted(Sleek.mc.fontRendererObj);
            llllIlIIIIIIl.removeIf(llllIIllIIlIl -> !llllIIllIIlIl.isToggled());
            if (llllIIllllllI.hideRender.getValue()) {
                llllIlIIIIIIl.removeIf(llllIIllIIlll -> llllIIllIIlll.getCategory() == ModuleCategory.VISUALS);
            }
            for (final Module llllIlIIIIIlI : llllIlIIIIIIl) {
                if (!llllIlIIIIIlI.isToggled()) {
                    continue;
                }
                ++llllIIlllllII;
                final Module llllIlIIIIlIl = llllIlIIIIIIl.get(llllIIlllllII - 1);
                final String llllIlIIIIlII = String.valueOf(new StringBuilder().append(llllIlIIIIIlI.getName()).append("§7").append(llllIlIIIIIlI.getFormattedSuffix()));
                final float llllIlIIIIIll = (float)(llllIIlllllll.getSr().getScaledWidth() - Sleek.mc.fontRendererObj.getStringWidth(llllIlIIIIlII) - 6);
                Gui.drawRect(llllIlIIIIIll - 1.5, llllIIlllllIl - 1, llllIIlllllll.getSr().getScaledWidth(), Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 1, new Color(0, 0, 0, llllIIllllllI.bgalpha.getValue()).getRGB());
                final String s3 = this.getHud().line.getValue();
                switch (s3) {
                    case "Wrapped": {
                        Gui.drawRect(llllIlIIIIIll - 2.5, llllIIlllllIl - 1, llllIlIIIIIll - 1.5, Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 1, llllIIllllIll.getRGB());
                        break;
                    }
                }
                if (llllIlIIIIIIl.size() > llllIIlllllII) {
                    final Module llllIlIIIlIII = llllIlIIIIIIl.get(llllIIlllllII);
                    final String llllIlIIIIlll = String.valueOf(new StringBuilder().append(llllIlIIIlIII.getName()).append("§7").append(llllIlIIIlIII.getFormattedSuffix()));
                    final float llllIlIIIIllI = (float)(llllIIlllllll.getSr().getScaledWidth() - Sleek.mc.fontRendererObj.getStringWidth(llllIlIIIIlll) - 7.5);
                    final char llllIIllIlIll = ((Value<Character>)this.getHud().line).getValue();
                    Exception llllIIllIlIlI = (Exception)(-1);
                    switch (((String)llllIIllIlIll).hashCode()) {
                        case 2433880: {
                            if (((String)llllIIllIlIll).equals("None")) {
                                llllIIllIlIlI = (Exception)0;
                                break;
                            }
                            break;
                        }
                        case -1034806171: {
                            if (((String)llllIIllIlIll).equals("Wrapped")) {
                                llllIIllIlIlI = (Exception)1;
                                break;
                            }
                            break;
                        }
                    }
                    switch (llllIIllIlIlI) {
                        case 1L: {
                            Gui.drawRect(llllIlIIIIIll - 2.5, Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 1, llllIlIIIIllI, Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 2, llllIIllllIll.getRGB());
                            break;
                        }
                    }
                }
                else {
                    final String s4 = this.getHud().line.getValue();
                    switch (s4) {
                        case "Wrapped": {
                            Gui.drawRect(llllIlIIIIIll - 2.5, Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 1, llllIlIIIIIll + 100.0f, Sleek.mc.fontRendererObj.FONT_HEIGHT + llllIIlllllIl + 2, llllIIllllIll.getRGB());
                            break;
                        }
                    }
                }
                Sleek.mc.fontRendererObj.drawStringWithShadow(llllIlIIIIlII, (float)(llllIlIIIIIll + 1.5), (float)(0.5 + llllIIlllllIl), llllIIllllIll.getRGB());
                llllIIlllllIl += 11;
            }
        }
    }
}
