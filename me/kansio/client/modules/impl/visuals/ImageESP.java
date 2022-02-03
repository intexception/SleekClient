package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import me.kansio.client.utils.render.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.google.common.eventbus.*;
import me.kansio.client.value.*;

@ModuleData(name = "Image ESP", description = "Displays images on players", category = ModuleCategory.VISUALS)
public class ImageESP extends Module
{
    private static final /* synthetic */ ResourceLocation FLOYED;
    private /* synthetic */ ModeValue modeValue;
    private static final /* synthetic */ ResourceLocation ZUIY2;
    private static final /* synthetic */ ResourceLocation ZUIY;
    
    static {
        ZUIY = new ResourceLocation("sleek/bg1.png");
        ZUIY2 = new ResourceLocation("sleek/bg1.png");
        FLOYED = new ResourceLocation("sleek/floyed.png");
    }
    
    @Subscribe
    public void onEntityRender(final Render3DEvent lIIlIIIlIllIIl) {
        for (final EntityPlayer lIIlIIIlIllIll : ImageESP.mc.thePlayer.getEntityWorld().playerEntities) {
            if (RenderUtils.isInViewFrustrum(lIIlIIIlIllIll) && !lIIlIIIlIllIll.isInvisible() && lIIlIIIlIllIll.isEntityAlive() && lIIlIIIlIllIll == ImageESP.mc.thePlayer) {
                continue;
            }
            final double lIIlIIIllIIIII = RenderUtils.interp(lIIlIIIlIllIll.posX, lIIlIIIlIllIll.lastTickPosX) - Minecraft.getMinecraft().getRenderManager().renderPosX;
            final double lIIlIIIlIlllll = RenderUtils.interp(lIIlIIIlIllIll.posY, lIIlIIIlIllIll.lastTickPosY) - Minecraft.getMinecraft().getRenderManager().renderPosY;
            final double lIIlIIIlIllllI = RenderUtils.interp(lIIlIIIlIllIll.posZ, lIIlIIIlIllIll.lastTickPosZ) - Minecraft.getMinecraft().getRenderManager().renderPosZ;
            GlStateManager.pushMatrix();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            GL11.glDisable(2929);
            final float lIIlIIIlIlllIl = MathHelper.clamp_float(ImageESP.mc.thePlayer.getDistanceToEntity(lIIlIIIlIllIll), 20.0f, Float.MAX_VALUE);
            final double lIIlIIIlIlllII = 0.005 * lIIlIIIlIlllIl;
            GlStateManager.translate(lIIlIIIllIIIII, lIIlIIIlIlllll, lIIlIIIlIllllI);
            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(-0.1, -0.1, 0.0);
            final int lIIlIIIlIlIIII = ((Value<Integer>)this.modeValue).getValue();
            short lIIlIIIlIIllll = -1;
            switch (((String)lIIlIIIlIlIIII).hashCode()) {
                case 2797003: {
                    if (((String)lIIlIIIlIlIIII).equals("Zuiy")) {
                        lIIlIIIlIIllll = 0;
                        break;
                    }
                    break;
                }
                case 86707143: {
                    if (((String)lIIlIIIlIlIIII).equals("Zuiy2")) {
                        lIIlIIIlIIllll = 1;
                        break;
                    }
                    break;
                }
                case 2107207151: {
                    if (((String)lIIlIIIlIlIIII).equals("Floyed")) {
                        lIIlIIIlIIllll = 2;
                        break;
                    }
                    break;
                }
            }
            switch (lIIlIIIlIIllll) {
                case 0: {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(ImageESP.ZUIY);
                    break;
                }
                case 1: {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(ImageESP.ZUIY2);
                    break;
                }
                case 2: {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(ImageESP.FLOYED);
                    break;
                }
            }
            Gui.drawScaledCustomSizeModalRect(lIIlIIIlIllIll.width / 2.0f - lIIlIIIlIlllIl / 3.0f, -lIIlIIIlIllIll.height - lIIlIIIlIlllIl, 0.0f, 0.0f, 1, 1, (int)(252.0 * (lIIlIIIlIlllII / 2.0)), (int)(476.0 * (lIIlIIIlIlllII / 2.0)), 1.0f, 1.0f);
            GL11.glEnable(2929);
            GlStateManager.popMatrix();
        }
    }
    
    public ImageESP() {
        this.modeValue = new ModeValue("Mode", this, new String[] { "Zuiy", "Zuiy2", "Floyed" });
    }
}
