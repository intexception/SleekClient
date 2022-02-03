package me.kansio.client.utils.render;

import me.kansio.client.utils.*;
import net.minecraft.client.renderer.culling.*;
import java.nio.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import javax.vecmath.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RenderUtils extends Util
{
    private static final /* synthetic */ IntBuffer viewport;
    private static final /* synthetic */ Frustum frustrum;
    private static final /* synthetic */ FloatBuffer projection;
    private static final /* synthetic */ FloatBuffer modelview;
    
    public static void drawFilledCircle(final int lllllllllllllllllllIlllIIllIlIIl, final int lllllllllllllllllllIlllIIllIlIII, final float lllllllllllllllllllIlllIIllIIIIl, final Color lllllllllllllllllllIlllIIllIIIII) {
        final int lllllllllllllllllllIlllIIllIIlIl = 50;
        final double lllllllllllllllllllIlllIIllIIlII = 6.283185307179586 / lllllllllllllllllllIlllIIllIIlIl;
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int lllllllllllllllllllIlllIIllIlIlI = 0; lllllllllllllllllllIlllIIllIlIlI < lllllllllllllllllllIlllIIllIIlIl; ++lllllllllllllllllllIlllIIllIlIlI) {
            final float lllllllllllllllllllIlllIIllIllII = (float)(lllllllllllllllllllIlllIIllIIIIl * Math.sin(lllllllllllllllllllIlllIIllIlIlI * lllllllllllllllllllIlllIIllIIlII));
            final float lllllllllllllllllllIlllIIllIlIll = (float)(lllllllllllllllllllIlllIIllIIIIl * Math.cos(lllllllllllllllllllIlllIIllIlIlI * lllllllllllllllllllIlllIIllIIlII));
            ColorUtils.glColor(lllllllllllllllllllIlllIIllIIIII);
            GL11.glVertex2f(lllllllllllllllllllIlllIIllIlIIl + lllllllllllllllllllIlllIIllIllII, lllllllllllllllllllIlllIIllIlIII + lllllllllllllllllllIlllIIllIlIll);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }
    
    public static void enable3D() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
    }
    
    public static ScaledResolution getResolution() {
        return new ScaledResolution(RenderUtils.mc);
    }
    
    public static void drawScaledCustomSizeModalRect(final int lllllllllllllllllllIllllIIIllIIl, final int lllllllllllllllllllIllllIIIIlIlI, final float lllllllllllllllllllIllllIIIIlIIl, final float lllllllllllllllllllIllllIIIIlIII, final int lllllllllllllllllllIllllIIIIIlll, final int lllllllllllllllllllIllllIIIIIllI, final int lllllllllllllllllllIllllIIIlIIll, final int lllllllllllllllllllIllllIIIlIIlI, final float lllllllllllllllllllIllllIIIIIIll, final float lllllllllllllllllllIllllIIIlIIII) {
        final float lllllllllllllllllllIllllIIIIllll = 1.0f / lllllllllllllllllllIllllIIIIIIll;
        final float lllllllllllllllllllIllllIIIIlllI = 1.0f / lllllllllllllllllllIllllIIIlIIII;
        final Tessellator lllllllllllllllllllIllllIIIIllIl = Tessellator.getInstance();
        final WorldRenderer lllllllllllllllllllIllllIIIIllII = lllllllllllllllllllIllllIIIIllIl.getWorldRenderer();
        lllllllllllllllllllIllllIIIIllII.begin(7, DefaultVertexFormats.POSITION_TEX);
        lllllllllllllllllllIllllIIIIllII.pos(lllllllllllllllllllIllllIIIllIIl, lllllllllllllllllllIllllIIIIlIlI + lllllllllllllllllllIllllIIIlIIlI, 0.0).tex(lllllllllllllllllllIllllIIIIlIIl * lllllllllllllllllllIllllIIIIllll, (lllllllllllllllllllIllllIIIIlIII + lllllllllllllllllllIllllIIIIIllI) * lllllllllllllllllllIllllIIIIlllI).endVertex();
        lllllllllllllllllllIllllIIIIllII.pos(lllllllllllllllllllIllllIIIllIIl + lllllllllllllllllllIllllIIIlIIll, lllllllllllllllllllIllllIIIIlIlI + lllllllllllllllllllIllllIIIlIIlI, 0.0).tex((lllllllllllllllllllIllllIIIIlIIl + lllllllllllllllllllIllllIIIIIlll) * lllllllllllllllllllIllllIIIIllll, (lllllllllllllllllllIllllIIIIlIII + lllllllllllllllllllIllllIIIIIllI) * lllllllllllllllllllIllllIIIIlllI).endVertex();
        lllllllllllllllllllIllllIIIIllII.pos(lllllllllllllllllllIllllIIIllIIl + lllllllllllllllllllIllllIIIlIIll, lllllllllllllllllllIllllIIIIlIlI, 0.0).tex((lllllllllllllllllllIllllIIIIlIIl + lllllllllllllllllllIllllIIIIIlll) * lllllllllllllllllllIllllIIIIllll, lllllllllllllllllllIllllIIIIlIII * lllllllllllllllllllIllllIIIIlllI).endVertex();
        lllllllllllllllllllIllllIIIIllII.pos(lllllllllllllllllllIllllIIIllIIl, lllllllllllllllllllIllllIIIIlIlI, 0.0).tex(lllllllllllllllllllIllllIIIIlIIl * lllllllllllllllllllIllllIIIIllll, lllllllllllllllllllIllllIIIIlIII * lllllllllllllllllllIllllIIIIlllI).endVertex();
        lllllllllllllllllllIllllIIIIllIl.draw();
    }
    
    public static float calculateCompensation(final float lllllllllllllllllllIlllIlIIIIlIl, float lllllllllllllllllllIlllIlIIIIlII, long lllllllllllllllllllIlllIIlllllIl, final double lllllllllllllllllllIlllIIlllllII) {
        final float lllllllllllllllllllIlllIlIIIIIIl = lllllllllllllllllllIlllIlIIIIlII - lllllllllllllllllllIlllIlIIIIlIl;
        if (lllllllllllllllllllIlllIIlllllIl < 1L) {
            lllllllllllllllllllIlllIIlllllIl = 1L;
        }
        if (lllllllllllllllllllIlllIIlllllIl > 1000L) {
            lllllllllllllllllllIlllIIlllllIl = 16L;
        }
        if (lllllllllllllllllllIlllIlIIIIIIl > lllllllllllllllllllIlllIIlllllII) {
            final double lllllllllllllllllllIlllIlIIIIlll = (lllllllllllllllllllIlllIIlllllII * lllllllllllllllllllIlllIIlllllIl / 16.0 < 0.5) ? 0.5 : (lllllllllllllllllllIlllIIlllllII * lllllllllllllllllllIlllIIlllllIl / 16.0);
            lllllllllllllllllllIlllIlIIIIlII -= (float)lllllllllllllllllllIlllIlIIIIlll;
            if (lllllllllllllllllllIlllIlIIIIlII < lllllllllllllllllllIlllIlIIIIlIl) {
                lllllllllllllllllllIlllIlIIIIlII = lllllllllllllllllllIlllIlIIIIlIl;
            }
        }
        else if (lllllllllllllllllllIlllIlIIIIIIl < -lllllllllllllllllllIlllIIlllllII) {
            final double lllllllllllllllllllIlllIlIIIIllI = (lllllllllllllllllllIlllIIlllllII * lllllllllllllllllllIlllIIlllllIl / 16.0 < 0.5) ? 0.5 : (lllllllllllllllllllIlllIIlllllII * lllllllllllllllllllIlllIIlllllIl / 16.0);
            lllllllllllllllllllIlllIlIIIIlII += (float)lllllllllllllllllllIlllIlIIIIllI;
            if (lllllllllllllllllllIlllIlIIIIlII > lllllllllllllllllllIlllIlIIIIlIl) {
                lllllllllllllllllllIlllIlIIIIlII = lllllllllllllllllllIlllIlIIIIlIl;
            }
        }
        else {
            lllllllllllllllllllIlllIlIIIIlII = lllllllllllllllllllIlllIlIIIIlIl;
        }
        return lllllllllllllllllllIlllIlIIIIlII;
    }
    
    public static void draw2DPolygon(final double lllllllllllllllllllIllIllIllIIll, final double lllllllllllllllllllIllIllIllIIlI, final double lllllllllllllllllllIllIllIlllIII, final int lllllllllllllllllllIllIllIllIlll, final int lllllllllllllllllllIllIllIllIllI) {
        if (lllllllllllllllllllIllIllIllIlll < 3) {
            return;
        }
        final Tessellator lllllllllllllllllllIllIllIllIlIl = Tessellator.getInstance();
        final WorldRenderer lllllllllllllllllllIllIllIllIlII = lllllllllllllllllllIllIllIllIlIl.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        ColorUtils.glColor(lllllllllllllllllllIllIllIllIllI);
        lllllllllllllllllllIllIllIllIlII.begin(6, DefaultVertexFormats.POSITION);
        for (int lllllllllllllllllllIllIllIlllIll = 0; lllllllllllllllllllIllIllIlllIll < lllllllllllllllllllIllIllIllIlll; ++lllllllllllllllllllIllIllIlllIll) {
            final double lllllllllllllllllllIllIllIllllII = 6.283185307179586 * lllllllllllllllllllIllIllIlllIll / lllllllllllllllllllIllIllIllIlll + Math.toRadians(180.0);
            lllllllllllllllllllIllIllIllIlII.pos(lllllllllllllllllllIllIllIllIIll + Math.sin(lllllllllllllllllllIllIllIllllII) * lllllllllllllllllllIllIllIlllIII, lllllllllllllllllllIllIllIllIIlI + Math.cos(lllllllllllllllllllIllIllIllllII) * lllllllllllllllllllIllIllIlllIII, 0.0).endVertex();
        }
        lllllllllllllllllllIllIllIllIlIl.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static boolean isInViewFrustrum(final Entity lllllllllllllllllllIllIllIlIIIlI) {
        return isInViewFrustrum(lllllllllllllllllllIllIllIlIIIlI.getEntityBoundingBox()) || lllllllllllllllllllIllIllIlIIIlI.ignoreFrustumCheck;
    }
    
    public static void drawArrow(float lllllllllllllllllllIlllIllIIllIl, float lllllllllllllllllllIlllIllIIllII, final int lllllllllllllllllllIlllIllIIlIll) {
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.3, 1.3);
        lllllllllllllllllllIlllIllIIllIl = (float)(lllllllllllllllllllIlllIllIIllIl / 1.3);
        lllllllllllllllllllIlllIllIIllII /= (float)1.3;
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        ColorUtils.glColor(lllllllllllllllllllIlllIllIIlIll);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(1);
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIllIl, (double)lllllllllllllllllllIlllIllIIllII);
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIllIl + 3.0f), (double)(lllllllllllllllllllIlllIllIIllII + 4.0f));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIllIl + 3.0f), (double)(lllllllllllllllllllIlllIllIIllII + 4.0f));
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIllIl + 6.0f), (double)lllllllllllllllllllIlllIllIIllII);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    private static boolean isInViewFrustrum(final AxisAlignedBB lllllllllllllllllllIllIllIlIIllI) {
        final Entity lllllllllllllllllllIllIllIlIIlll = RenderUtils.mc.getRenderViewEntity();
        RenderUtils.frustrum.setPosition(lllllllllllllllllllIllIllIlIIlll.posX, lllllllllllllllllllIllIllIlIIlll.posY, lllllllllllllllllllIllIllIlIIlll.posZ);
        return RenderUtils.frustrum.isBoundingBoxInFrustum(lllllllllllllllllllIllIllIlIIllI);
    }
    
    public static void drawRoundedRectWithShadow(final double lllllllllllllllllllIllIllllIIIII, final double lllllllllllllllllllIllIlllIllIII, final double lllllllllllllllllllIllIlllIlIllI, final double lllllllllllllllllllIllIlllIlIlIl, final double lllllllllllllllllllIllIlllIlIIlI, final int lllllllllllllllllllIllIlllIlIIIl) {
        drawRoundedRect(lllllllllllllllllllIllIllllIIIII + 2.0, lllllllllllllllllllIllIlllIllIII + 1.0, lllllllllllllllllllIllIlllIlIllI, lllllllllllllllllllIllIlllIlIlIl + 1.0, lllllllllllllllllllIllIlllIlIIlI, new Color(0).getRGB());
        drawRoundedRect(lllllllllllllllllllIllIllllIIIII, lllllllllllllllllllIllIlllIllIII, lllllllllllllllllllIllIlllIlIllI, lllllllllllllllllllIllIlllIlIlIl, lllllllllllllllllllIllIlllIlIIlI, lllllllllllllllllllIllIlllIlIIIl);
    }
    
    public static void drawFace(final ResourceLocation lllllllllllllllllllIlllIllllIIll, final int lllllllllllllllllllIlllIllllIIlI, final int lllllllllllllllllllIlllIllllIllI, final int lllllllllllllllllllIlllIllllIIII, final int lllllllllllllllllllIlllIlllIllll) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.mc.getTextureManager().bindTexture(lllllllllllllllllllIlllIllllIIll);
        drawScaledCustomSizeModalRect(lllllllllllllllllllIlllIllllIIlI, lllllllllllllllllllIlllIllllIllI, 8.0f, 8.0f, 8, 8, lllllllllllllllllllIlllIllllIIII, lllllllllllllllllllIlllIlllIllll, 64.0f, 64.0f);
        drawScaledCustomSizeModalRect(lllllllllllllllllllIlllIllllIIlI, lllllllllllllllllllIlllIllllIllI, 40.0f, 8.0f, 8, 8, lllllllllllllllllllIlllIllllIIII, lllllllllllllllllllIlllIlllIllll, 64.0f, 64.0f);
    }
    
    public static void prepareScissorBox(final float lllllllllllllllllllIllllIIllllII, final float lllllllllllllllllllIllllIIlllIll, final float lllllllllllllllllllIllllIIlllIlI, final float lllllllllllllllllllIllllIIllllll) {
        final ScaledResolution lllllllllllllllllllIllllIIlllllI = new ScaledResolution(RenderUtils.mc);
        final int lllllllllllllllllllIllllIIllllIl = lllllllllllllllllllIllllIIlllllI.getScaleFactor();
        GL11.glScissor((int)(lllllllllllllllllllIllllIIllllII * lllllllllllllllllllIllllIIllllIl), (int)((lllllllllllllllllllIllllIIlllllI.getScaledHeight() - lllllllllllllllllllIllllIIllllll) * lllllllllllllllllllIllllIIllllIl), (int)((lllllllllllllllllllIllllIIlllIlI - lllllllllllllllllllIllllIIllllII) * lllllllllllllllllllIllllIIllllIl), (int)((lllllllllllllllllllIllllIIllllll - lllllllllllllllllllIllllIIlllIll) * lllllllllllllllllllIllllIIllllIl));
    }
    
    public static void quickDrawFace(final ResourceLocation lllllllllllllllllllIlllIlllIlIIl, final int lllllllllllllllllllIlllIlllIlIII, final int lllllllllllllllllllIlllIlllIIlll, final int lllllllllllllllllllIlllIlllIIllI, final int lllllllllllllllllllIlllIlllIIlIl) {
        RenderUtils.mc.getTextureManager().bindTexture(lllllllllllllllllllIlllIlllIlIIl);
        drawScaledCustomSizeModalRect(lllllllllllllllllllIlllIlllIlIII, lllllllllllllllllllIlllIlllIIlll, 8.0f, 8.0f, 8, 8, lllllllllllllllllllIlllIlllIIllI, lllllllllllllllllllIlllIlllIIlIl, 64.0f, 64.0f);
        drawScaledCustomSizeModalRect(lllllllllllllllllllIlllIlllIlIII, lllllllllllllllllllIlllIlllIIlll, 40.0f, 8.0f, 8, 8, lllllllllllllllllllIlllIlllIIllI, lllllllllllllllllllIlllIlllIIlIl, 64.0f, 64.0f);
    }
    
    public static void drawCheckMark(final float lllllllllllllllllllIlllIlIlIlIlI, final float lllllllllllllllllllIlllIlIlIlIIl, final int lllllllllllllllllllIlllIlIlIlIII, final int lllllllllllllllllllIlllIlIlIIlll) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.2f);
        GL11.glBegin(3);
        ColorUtils.glColor(lllllllllllllllllllIlllIlIlIIlll);
        GL11.glVertex2d(lllllllllllllllllllIlllIlIlIlIlI + lllllllllllllllllllIlllIlIlIlIII - 6.5, (double)(lllllllllllllllllllIlllIlIlIlIIl + 3.0f));
        GL11.glVertex2d(lllllllllllllllllllIlllIlIlIlIlI + lllllllllllllllllllIlllIlIlIlIII - 11.5, (double)(lllllllllllllllllllIlllIlIlIlIIl + 10.0f));
        GL11.glVertex2d(lllllllllllllllllllIlllIlIlIlIlI + lllllllllllllllllllIlllIlIlIlIII - 13.5, (double)(lllllllllllllllllllIlllIlIlIlIIl + 8.0f));
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void disable3D() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
    }
    
    public static void drawBorderedRoundedRect(final float lllllllllllllllllllIlllIIIIIllII, final float lllllllllllllllllllIlllIIIIIlIll, final float lllllllllllllllllllIlllIIIIIlIlI, final float lllllllllllllllllllIlllIIIIlIIIl, final float lllllllllllllllllllIlllIIIIlIIII, final float lllllllllllllllllllIlllIIIIIllll, final int lllllllllllllllllllIlllIIIIIlllI, final int lllllllllllllllllllIlllIIIIIIlIl) {
        drawRoundedRect(lllllllllllllllllllIlllIIIIIllII, lllllllllllllllllllIlllIIIIIlIll, lllllllllllllllllllIlllIIIIIlIlI, lllllllllllllllllllIlllIIIIlIIIl, lllllllllllllllllllIlllIIIIlIIII, lllllllllllllllllllIlllIIIIIlllI);
        drawRoundedRect(lllllllllllllllllllIlllIIIIIllII + lllllllllllllllllllIlllIIIIIllll, lllllllllllllllllllIlllIIIIIlIll + lllllllllllllllllllIlllIIIIIllll, lllllllllllllllllllIlllIIIIIlIlI - lllllllllllllllllllIlllIIIIIllll * 2.0f, lllllllllllllllllllIlllIIIIlIIIl - lllllllllllllllllllIlllIIIIIllll * 2.0f, lllllllllllllllllllIlllIIIIlIIII, lllllllllllllllllllIlllIIIIIIlIl);
    }
    
    static {
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        frustrum = new Frustum();
    }
    
    public static boolean hover(final int lllllllllllllllllllIllllIlIlIlII, final int lllllllllllllllllllIllllIlIlIIll, final int lllllllllllllllllllIllllIlIIllII, final int lllllllllllllllllllIllllIlIlIIIl, final int lllllllllllllllllllIllllIlIIlIlI, final int lllllllllllllllllllIllllIlIIllll) {
        return lllllllllllllllllllIllllIlIIllII >= lllllllllllllllllllIllllIlIlIlII && lllllllllllllllllllIllllIlIIllII <= lllllllllllllllllllIllllIlIlIlII + lllllllllllllllllllIllllIlIIlIlI && lllllllllllllllllllIllllIlIlIIIl >= lllllllllllllllllllIllllIlIlIIll && lllllllllllllllllllIllllIlIlIIIl <= lllllllllllllllllllIllllIlIlIIll + lllllllllllllllllllIllllIlIIllll;
    }
    
    public static double interpolate(final double lllllllllllllllllllIlllIlIlIIIll, final double lllllllllllllllllllIlllIlIIlllll, final double lllllllllllllllllllIlllIlIlIIIIl) {
        return lllllllllllllllllllIlllIlIIlllll + (lllllllllllllllllllIlllIlIlIIIll - lllllllllllllllllllIlllIlIIlllll) * lllllllllllllllllllIlllIlIlIIIIl;
    }
    
    public static Vector3d project(final double lllllllllllllllllllIlllIllIllIll, final double lllllllllllllllllllIlllIllIlIllI, final double lllllllllllllllllllIlllIllIlIlIl) {
        final FloatBuffer lllllllllllllllllllIlllIllIllIII = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, RenderUtils.modelview);
        GL11.glGetFloat(2983, RenderUtils.projection);
        GL11.glGetInteger(2978, RenderUtils.viewport);
        if (GLU.gluProject((float)lllllllllllllllllllIlllIllIllIll, (float)lllllllllllllllllllIlllIllIlIllI, (float)lllllllllllllllllllIlllIllIlIlIl, RenderUtils.modelview, RenderUtils.projection, RenderUtils.viewport, lllllllllllllllllllIlllIllIllIII)) {
            return new Vector3d(lllllllllllllllllllIlllIllIllIII.get(0) / getResolution().getScaleFactor(), (Display.getHeight() - lllllllllllllllllllIlllIllIllIII.get(1)) / getResolution().getScaleFactor(), lllllllllllllllllllIlllIllIllIII.get(2));
        }
        return null;
    }
    
    public static void drawImage(final ResourceLocation lllllllllllllllllllIllllIIllIIIl, final int lllllllllllllllllllIllllIIlIlIll, final int lllllllllllllllllllIllllIIlIlIlI, final int lllllllllllllllllllIllllIIlIlllI, final int lllllllllllllllllllIllllIIlIllIl) {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(lllllllllllllllllllIllllIIllIIIl);
        Gui.drawModalRectWithCustomSizedTexture(lllllllllllllllllllIllllIIlIlIll, lllllllllllllllllllIllllIIlIlIlI, 0.0f, 0.0f, lllllllllllllllllllIllllIIlIlllI, lllllllllllllllllllIllllIIlIllIl, (float)lllllllllllllllllllIllllIIlIlllI, (float)lllllllllllllllllllIllllIIlIllIl);
    }
    
    public static void drawBorderedRect(final double lllllllllllllllllllIlllIIIllIIlI, final double lllllllllllllllllllIlllIIIllIIII, final double lllllllllllllllllllIlllIIIlIllll, final double lllllllllllllllllllIlllIIIllIllI, final double lllllllllllllllllllIlllIIIllIlIl, final int lllllllllllllllllllIlllIIIlIlIlI, final int lllllllllllllllllllIlllIIIlIlIIl) {
        drawRect(lllllllllllllllllllIlllIIIllIIlI, lllllllllllllllllllIlllIIIllIIII, lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIlIllll, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIllI, lllllllllllllllllllIlllIIIlIlIIl);
        drawRect(lllllllllllllllllllIlllIIIllIIlI, lllllllllllllllllllIlllIIIllIIII, lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIlIllll, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIlIl, lllllllllllllllllllIlllIIIlIlIlI);
        drawRect(lllllllllllllllllllIlllIIIllIIlI, lllllllllllllllllllIlllIIIllIIII, lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIllIlIl, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIllI, lllllllllllllllllllIlllIIIlIlIlI);
        drawRect(lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIlIllll, lllllllllllllllllllIlllIIIllIIII, lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIlIllll - lllllllllllllllllllIlllIIIllIlIl, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIllI, lllllllllllllllllllIlllIIIlIlIlI);
        drawRect(lllllllllllllllllllIlllIIIllIIlI, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIllI, lllllllllllllllllllIlllIIIllIIlI + lllllllllllllllllllIlllIIIlIllll, lllllllllllllllllllIlllIIIllIIII + lllllllllllllllllllIlllIIIllIllI - lllllllllllllllllllIlllIIIllIlIl, lllllllllllllllllllIlllIIIlIlIlI);
    }
    
    public static void drawTracerPointer(final float lllllllllllllllllllIlllIllIIIIll, final float lllllllllllllllllllIlllIllIIIIlI, final float lllllllllllllllllllIlllIlIlllIlI, final float lllllllllllllllllllIlllIllIIIIII, final float lllllllllllllllllllIlllIlIllllll, final int lllllllllllllllllllIlllIlIlllllI) {
        final boolean lllllllllllllllllllIlllIlIllllIl = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        ColorUtils.glColor(lllllllllllllllllllIlllIlIlllllI);
        GL11.glBegin(7);
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)lllllllllllllllllllIlllIllIIIIlI);
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIIIll - lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIllIIIIII), (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI));
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIlIllllll));
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIIIll + lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIllIIIIII), (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI));
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)lllllllllllllllllllIlllIllIIIIlI);
        GL11.glEnd();
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
        GL11.glBegin(2);
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)lllllllllllllllllllIlllIllIIIIlI);
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIIIll - lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIllIIIIII), (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI));
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIlIllllll));
        GL11.glVertex2d((double)(lllllllllllllllllllIlllIllIIIIll + lllllllllllllllllllIlllIlIlllIlI / lllllllllllllllllllIlllIllIIIIII), (double)(lllllllllllllllllllIlllIllIIIIlI + lllllllllllllllllllIlllIlIlllIlI));
        GL11.glVertex2d((double)lllllllllllllllllllIlllIllIIIIll, (double)lllllllllllllllllllIlllIllIIIIlI);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!lllllllllllllllllllIlllIlIllllIl) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
    }
    
    public static void drawRect(final double lllllllllllllllllllIlllIIlIIlllI, final double lllllllllllllllllllIlllIIlIIllIl, final double lllllllllllllllllllIlllIIlIIIlII, final double lllllllllllllllllllIlllIIlIIlIlI, final int lllllllllllllllllllIlllIIlIIlIIl) {
        Gui.drawRect(lllllllllllllllllllIlllIIlIIlllI, lllllllllllllllllllIlllIIlIIllIl, lllllllllllllllllllIlllIIlIIlllI + lllllllllllllllllllIlllIIlIIIlII, lllllllllllllllllllIlllIIlIIllIl + lllllllllllllllllllIlllIIlIIlIlI, lllllllllllllllllllIlllIIlIIlIIl);
    }
    
    public static double interp(final double lllllllllllllllllllIllIllIIllIll, final double lllllllllllllllllllIllIllIIlllII) {
        return lllllllllllllllllllIllIllIIlllII + (lllllllllllllllllllIllIllIIllIll - lllllllllllllllllllIllIllIIlllII) * Minecraft.getMinecraft().timer.renderPartialTicks;
    }
    
    public static void drawRoundedRect(double lllllllllllllllllllIllIlllllIlll, double lllllllllllllllllllIllIllllIlllI, final double lllllllllllllllllllIllIlllllIlIl, final double lllllllllllllllllllIllIlllllIlII, final double lllllllllllllllllllIllIlllllIIll, final int lllllllllllllllllllIllIllllIlIlI) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double lllllllllllllllllllIllIlllllIIIl = lllllllllllllllllllIllIlllllIlll + lllllllllllllllllllIllIlllllIlIl;
        double lllllllllllllllllllIllIlllllIIII = lllllllllllllllllllIllIllllIlllI + lllllllllllllllllllIllIlllllIlII;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        lllllllllllllllllllIllIlllllIlll *= 2.0;
        lllllllllllllllllllIllIllllIlllI *= 2.0;
        lllllllllllllllllllIllIlllllIIIl *= 2.0;
        lllllllllllllllllllIllIlllllIIII *= 2.0;
        GL11.glDisable(3553);
        ColorUtils.glColor(lllllllllllllllllllIllIllllIlIlI);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int lllllllllllllllllllIllIllllllIll = 0; lllllllllllllllllllIllIllllllIll <= 90; lllllllllllllllllllIllIllllllIll += 3) {
            GL11.glVertex2d(lllllllllllllllllllIllIlllllIlll + lllllllllllllllllllIllIlllllIIll + Math.sin(lllllllllllllllllllIllIllllllIll * 3.141592653589793 / 180.0) * (lllllllllllllllllllIllIlllllIIll * -1.0), lllllllllllllllllllIllIllllIlllI + lllllllllllllllllllIllIlllllIIll + Math.cos(lllllllllllllllllllIllIllllllIll * 3.141592653589793 / 180.0) * (lllllllllllllllllllIllIlllllIIll * -1.0));
        }
        for (int lllllllllllllllllllIllIllllllIlI = 90; lllllllllllllllllllIllIllllllIlI <= 180; lllllllllllllllllllIllIllllllIlI += 3) {
            GL11.glVertex2d(lllllllllllllllllllIllIlllllIlll + lllllllllllllllllllIllIlllllIIll + Math.sin(lllllllllllllllllllIllIllllllIlI * 3.141592653589793 / 180.0) * (lllllllllllllllllllIllIlllllIIll * -1.0), lllllllllllllllllllIllIlllllIIII - lllllllllllllllllllIllIlllllIIll + Math.cos(lllllllllllllllllllIllIllllllIlI * 3.141592653589793 / 180.0) * (lllllllllllllllllllIllIlllllIIll * -1.0));
        }
        for (int lllllllllllllllllllIllIllllllIIl = 0; lllllllllllllllllllIllIllllllIIl <= 90; lllllllllllllllllllIllIllllllIIl += 3) {
            GL11.glVertex2d(lllllllllllllllllllIllIlllllIIIl - lllllllllllllllllllIllIlllllIIll + Math.sin(lllllllllllllllllllIllIllllllIIl * 3.141592653589793 / 180.0) * lllllllllllllllllllIllIlllllIIll, lllllllllllllllllllIllIlllllIIII - lllllllllllllllllllIllIlllllIIll + Math.cos(lllllllllllllllllllIllIllllllIIl * 3.141592653589793 / 180.0) * lllllllllllllllllllIllIlllllIIll);
        }
        for (int lllllllllllllllllllIllIllllllIII = 90; lllllllllllllllllllIllIllllllIII <= 180; lllllllllllllllllllIllIllllllIII += 3) {
            GL11.glVertex2d(lllllllllllllllllllIllIlllllIIIl - lllllllllllllllllllIllIlllllIIll + Math.sin(lllllllllllllllllllIllIllllllIII * 3.141592653589793 / 180.0) * lllllllllllllllllllIllIlllllIIll, lllllllllllllllllllIllIllllIlllI + lllllllllllllllllllIllIlllllIIll + Math.cos(lllllllllllllllllllIllIllllllIII * 3.141592653589793 / 180.0) * lllllllllllllllllllIllIlllllIIll);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
