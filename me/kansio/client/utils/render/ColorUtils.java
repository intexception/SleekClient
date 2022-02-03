package me.kansio.client.utils.render;

import me.kansio.client.utils.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;

public class ColorUtils extends Util
{
    public static Color setAlpha(final Color lIllIIIlII, final int lIllIIIIll) {
        return new Color(lIllIIIlII.getRed(), lIllIIIlII.getGreen(), lIllIIIlII.getBlue(), lIllIIIIll);
    }
    
    public static void glColor(final int llIIIIllIl) {
        final float llIIIlIIIl = (llIIIIllIl >> 16 & 0xFF) / 255.0f;
        final float llIIIlIIII = (llIIIIllIl >> 8 & 0xFF) / 255.0f;
        final float llIIIIllll = (llIIIIllIl & 0xFF) / 255.0f;
        final float llIIIIlllI = (llIIIIllIl >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(llIIIlIIIl, llIIIlIIII, llIIIIllll, llIIIIlllI);
    }
    
    public static void glColor(final Color lIllllIIlI) {
        GlStateManager.color(lIllllIIlI.getRed() / 255.0f, lIllllIIlI.getGreen() / 255.0f, lIllllIIlI.getBlue() / 255.0f, lIllllIIlI.getAlpha() / 255.0f);
    }
    
    public static Color getColorFromHud(final int lIllIIlIlI) {
        Color lIllIIlIll = null;
        final Exception lIllIIlIII = (Exception)((HUD)Client.getInstance().getModuleManager().getModuleByName("HUD")).getColorMode().getValue();
        boolean lIllIIIlll = -1 != 0;
        switch (((String)lIllIIlIII).hashCode()) {
            case 79969970: {
                if (((String)lIllIIlIII).equals("Sleek")) {
                    lIllIIIlll = false;
                    break;
                }
                break;
            }
            case 628530586: {
                if (((String)lIllIIlIII).equals("Nitrogen")) {
                    lIllIIIlll = true;
                    break;
                }
                break;
            }
            case -1656737386: {
                if (((String)lIllIIlIII).equals("Rainbow")) {
                    lIllIIIlll = (2 != 0);
                    break;
                }
                break;
            }
            case 961091784: {
                if (((String)lIllIIlIII).equals("Astolfo")) {
                    lIllIIIlll = (3 != 0);
                    break;
                }
                break;
            }
        }
        switch (lIllIIIlll) {
            case 0: {
                lIllIIlIll = getGradientOffset(new Color(0, 255, 128), new Color(212, 1, 1), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + lIllIIlIlI / ColorUtils.mc.fontRendererObj.FONT_HEIGHT * 9.95);
                break;
            }
            case 1: {
                lIllIIlIll = getGradientOffset(new Color(128, 171, 255), new Color(160, 72, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + lIllIIlIlI / ColorUtils.mc.fontRendererObj.FONT_HEIGHT * 9.95);
                break;
            }
            case 2: {
                lIllIIlIll = new Color(getRainbow(6000, lIllIIlIlI * 15));
                break;
            }
            case 3: {
                lIllIIlIll = getGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + lIllIIlIlI / ColorUtils.mc.fontRendererObj.FONT_HEIGHT * 9.95);
                break;
            }
        }
        return lIllIIlIll;
    }
    
    public static void glColor(final int lIlllllIlI, final int lIlllllIIl, final int lIlllllIII, final float lIlllllllI) {
        final float lIllllllIl = 0.003921569f * lIlllllIlI;
        final float lIllllllII = 0.003921569f * lIlllllIIl;
        final float lIlllllIll = 0.003921569f * lIlllllIII;
        GL11.glColor4f(lIllllllIl, lIllllllII, lIlllllIll, lIlllllllI);
    }
    
    public static int getRainbow(final int lIllIlIllI, final int lIllIlIlIl) {
        float lIllIlIlII = (float)((System.currentTimeMillis() + lIllIlIlIl) % lIllIlIllI);
        lIllIlIlII /= lIllIlIllI;
        return Color.getHSBColor(lIllIlIlII, 0.75f, 1.0f).getRGB();
    }
    
    public static Color getGradientOffset(final Color lIlllIIIII, final Color lIllIlllll, double lIllIllllI) {
        if (lIllIllllI > 1.0) {
            final double lIlllIlIIl = lIllIllllI % 1.0;
            final int lIlllIlIII = (int)lIllIllllI;
            lIllIllllI = ((lIlllIlIII % 2 == 0) ? lIlllIlIIl : (1.0 - lIlllIlIIl));
        }
        final double lIlllIIlII = 1.0 - lIllIllllI;
        final int lIlllIIIll = (int)(lIlllIIIII.getRed() * lIlllIIlII + lIllIlllll.getRed() * lIllIllllI);
        final int lIlllIIIlI = (int)(lIlllIIIII.getGreen() * lIlllIIlII + lIllIlllll.getGreen() * lIllIllllI);
        final int lIlllIIIIl = (int)(lIlllIIIII.getBlue() * lIlllIIlII + lIllIlllll.getBlue() * lIllIllllI);
        return new Color(lIlllIIIll, lIlllIIIlI, lIlllIIIIl);
    }
}
