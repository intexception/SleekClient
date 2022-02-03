package me.kansio.client.modules.impl.combat;

import me.kansio.client.utils.*;
import java.awt.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import me.kansio.client.utils.render.*;
import java.util.*;
import me.kansio.client.utils.font.*;
import net.minecraft.entity.*;
import optifine.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import me.kansio.client.value.*;

public class TargetHUD extends Util
{
    public static /* synthetic */ double currentHealthWidth;
    
    private static int getHealthColor(final EntityLivingBase llIIllIIlIIIIl) {
        final float llIIllIIlIIIII = llIIllIIlIIIIl.getHealth();
        final float llIIllIIIlllll = llIIllIIlIIIIl.getMaxHealth();
        final float llIIllIIIllllI = Math.max(0.0f, Math.min(llIIllIIlIIIII, llIIllIIIlllll) / llIIllIIIlllll);
        return Color.HSBtoRGB(llIIllIIIllllI / 3.0f, 1.0f, 0.75f) | 0xFF000000;
    }
    
    static {
        TargetHUD.currentHealthWidth = 138.0;
    }
    
    public static void draw(final RenderOverlayEvent llIIllIIllIlII, final EntityLivingBase llIIllIIllIIII) {
        final KillAura llIIllIIllIIlI = (KillAura)Client.getInstance().getModuleManager().getModuleByName("KillAura");
        final boolean llIIllIIlIlllI = ((Value<Boolean>)llIIllIIllIIlI.targethudmode).getValue();
        double llIIllIIlIllIl = -1;
        switch (((String)llIIllIIlIlllI).hashCode()) {
            case 79969970: {
                if (((String)llIIllIIlIlllI).equals("Sleek")) {
                    llIIllIIlIllIl = 0;
                    break;
                }
                break;
            }
            case 2404129: {
                if (((String)llIIllIIlIlllI).equals("Moon")) {
                    llIIllIIlIllIl = 1;
                    break;
                }
                break;
            }
        }
        switch (llIIllIIlIllIl) {
            case 0.0: {
                float llIIllIIllllII = (float)(llIIllIIllIIII.getHealth() * 6.9);
                if (llIIllIIllllII > 138.0) {
                    llIIllIIllllII = 138.0f;
                    TargetHUD.currentHealthWidth = 138.0;
                }
                if (llIIllIIllllII > TargetHUD.currentHealthWidth) {
                    TargetHUD.currentHealthWidth += llIIllIIllllII / 10.0f;
                }
                else if (llIIllIIllllII < TargetHUD.currentHealthWidth) {
                    TargetHUD.currentHealthWidth -= llIIllIIllllII / 10.0f;
                }
                RenderUtils.drawBorderedRoundedRect(150.0f, 350.0f, 150.0f, 60.0f, 10.0f, 2.0f, 2, new Color(llIIllIIllIIII.hurtTime * 6, 0, 0, 100).getRGB());
                TargetHUD.mc.fontRendererObj.drawStringWithShadow(llIIllIIllIIII.getName(), 210.0f, 370.0f, -1);
                if (llIIllIIllIIII instanceof EntityPlayer) {
                    final ResourceLocation llIIllIIllllIl = ((AbstractClientPlayer)llIIllIIllIIII).getLocationSkin();
                    RenderUtils.drawFace(llIIllIIllllIl, 160, 360, 30, 30);
                }
                RenderUtils.drawBorderedRoundedRect(155.0f, 400.0f, 138.0f, 5.0f, 5.0f, 0.5f, new Color(40, 40, 40, 255).getRGB(), new Color(45, 45, 45, 255).getRGB());
                RenderUtils.drawBorderedRoundedRect(155.0f, 400.0f, (float)((llIIllIIllIIII.getHealth() > 0.0f) ? llIIllIIllllII : 6.9), 5.0f, 5.0f, 0.5f, ColorUtils.getColorFromHud(1).getRGB(), ColorUtils.getColorFromHud(1).getRGB());
                break;
            }
            case 1.0: {
                final float llIIllIIllIllI = (float)((llIIllIIllIlII.getSr().getScaledWidth() >> 1) - 5);
                final float llIIllIIllIlIl = (float)((llIIllIIllIlII.getSr().getScaledHeight() >> 1) + 120);
                if (llIIllIIllIIII != null && TargetHUD.mc.thePlayer != null && llIIllIIllIIII instanceof EntityPlayer) {
                    final NetworkPlayerInfo llIIllIIlllIll = TargetHUD.mc.getNetHandler().getPlayerInfo(llIIllIIllIIII.getUniqueID());
                    final String llIIllIIlllIlI = String.valueOf(new StringBuilder().append("Ping: ").append(Objects.isNull(llIIllIIlllIll) ? "0ms" : String.valueOf(new StringBuilder().append(llIIllIIlllIll.getResponseTime()).append("ms"))));
                    final String llIIllIIlllIIl = String.valueOf(new StringBuilder().append("Name: ").append(StringUtils.stripControlCodes(llIIllIIllIIII.getName())));
                    RenderUtils.drawBorderedRect(llIIllIIllIllI, llIIllIIllIlIl, 140.0, 45.0, 0.5, new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 90).getRGB());
                    RenderUtils.drawRect(llIIllIIllIllI, llIIllIIllIlIl, 45.0, 45.0, new Color(0, 0, 0).getRGB());
                    Fonts.Arial12.drawStringWithShadow(llIIllIIlllIIl, llIIllIIllIllI + 46.5, llIIllIIllIlIl + 4.0f, -1);
                    Fonts.Arial12.drawStringWithShadow(String.valueOf(new StringBuilder().append("Distance: ").append(MathUtils.round(TargetHUD.mc.thePlayer.getDistanceToEntity(llIIllIIllIIII), 2))), llIIllIIllIllI + 46.5, llIIllIIllIlIl + 12.0f, -1);
                    Fonts.Arial12.drawStringWithShadow(llIIllIIlllIlI, llIIllIIllIllI + 46.5, llIIllIIllIlIl + 28.0f, new Color(6118236).getRGB());
                    Fonts.Arial12.drawStringWithShadow(String.valueOf(new StringBuilder().append("Health: ").append(MathUtils.round((Float.isNaN(llIIllIIllIIII.getHealth()) ? 20.0f : llIIllIIllIIII.getHealth()) / 2.0f, 2))), llIIllIIllIllI + 46.5, llIIllIIllIlIl + 20.0f, getHealthColor(llIIllIIllIIII));
                    drawFace(llIIllIIllIllI + 0.5, llIIllIIllIlIl + 0.5, 8.0f, 8.0f, 8, 8, 44, 44, 64.0f, 64.0f, (AbstractClientPlayer)llIIllIIllIIII);
                    RenderUtils.drawBorderedRect(llIIllIIllIllI + 46.0f, llIIllIIllIlIl + 45.0f - 10.0f, 92.0, 8.0, 0.5, new Color(0).getRGB(), new Color(35, 35, 35).getRGB());
                    final double llIIllIIlllIII = 91.0f / llIIllIIllIIII.getMaxHealth();
                    final double llIIllIIllIlll = llIIllIIlllIII * Math.min(llIIllIIllIIII.getHealth(), llIIllIIllIIII.getMaxHealth());
                    RenderUtils.drawRect(llIIllIIllIllI + 46.5, llIIllIIllIlIl + 45.0f - 9.5, llIIllIIllIlll, 7.0, getHealthColor(llIIllIIllIIII));
                    break;
                }
                break;
            }
        }
    }
    
    private static void drawFace(final double llIIllIIIIIIIl, final double llIIllIIIIlIll, final float llIIlIllllllll, final float llIIlIlllllllI, final int llIIlIllllllIl, final int llIIllIIIIIlll, final int llIIlIlllllIll, final int llIIllIIIIIlIl, final float llIIlIlllllIIl, final float llIIllIIIIIIll, final AbstractClientPlayer llIIllIIIIIIlI) {
        try {
            final ResourceLocation llIIllIIIIllIl = llIIllIIIIIIlI.getLocationSkin();
            TargetHUD.mc.getTextureManager().bindTexture(llIIllIIIIllIl);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect(llIIllIIIIIIIl, llIIllIIIIlIll, llIIlIllllllll, llIIlIlllllllI, llIIlIllllllIl, llIIllIIIIIlll, llIIlIlllllIll, llIIllIIIIIlIl, llIIlIlllllIIl, llIIllIIIIIIll);
            GL11.glDisable(3042);
        }
        catch (Exception ex) {}
    }
}
