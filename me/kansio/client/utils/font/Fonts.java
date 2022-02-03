package me.kansio.client.utils.font;

import me.kansio.client.utils.*;
import net.minecraft.util.*;
import java.awt.*;

public class Fonts extends Util
{
    private static Font fontFromTTF(final ResourceLocation llIIlIIIllllll, final float llIIlIIIlllllI, final int llIIlIIIllllIl) {
        Font llIIlIIIllllII = null;
        try {
            llIIlIIIllllII = Font.createFont(llIIlIIIllllIl, Fonts.mc.getResourceManager().getResource(llIIlIIIllllll).getInputStream());
            llIIlIIIllllII = llIIlIIIllllII.deriveFont(llIIlIIIlllllI);
        }
        catch (Exception llIIlIIlIIIIII) {
            llIIlIIlIIIIII.printStackTrace();
        }
        return llIIlIIIllllII;
    }
    
    static {
        Arial30 = new MCFontRenderer(new Font("Arial", 1, 30), true, true);
        Verdana = new MCFontRenderer(new Font("Verdana", 0, 18), true, true);
        Arial12 = new MCFontRenderer(new Font("Arial", 0, 12), true, true);
        HUD = new MCFontRenderer(new Font("Arial", 0, 18), true, true);
        SEGOE18 = new MCFontRenderer(new Font("Tahoma", 0, 18), true, true);
        SEGOE12 = new MCFontRenderer(new Font("Tahoma", 0, 12), true, true);
        NotifIcon = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/notif-icon.ttf"), 20.0f, 0), true, true);
        UbuntuLight = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Ubuntu-Light.ttf"), 12.0f, 0), true, true);
        UbuntuMedium = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Ubuntu-Medium.ttf"), 18.0f, 0), true, true);
        YantramanavThin = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Yantramanav-Regular.ttf"), 18.0f, 0), true, true);
    }
}
