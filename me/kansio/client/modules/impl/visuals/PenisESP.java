package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.google.common.eventbus.*;
import org.lwjgl.util.glu.*;

@ModuleData(name = "Penis ESP", category = ModuleCategory.VISUALS, description = "Shows a cock on players")
public class PenisESP extends Module
{
    private /* synthetic */ float pcumsize;
    private /* synthetic */ float pspin;
    private /* synthetic */ float pamount;
    
    private static boolean lineOfSight(final EntityPlayer lIlllIlIlIIll) {
        final Minecraft lIlllIlIlIlIl = Minecraft.getMinecraft();
        return lIlllIlIlIlIl.theWorld.rayTraceBlocks(new Vec3(lIlllIlIlIlIl.thePlayer.posX, lIlllIlIlIlIl.thePlayer.posY + lIlllIlIlIlIl.thePlayer.getEyeHeight(), lIlllIlIlIlIl.thePlayer.posZ), new Vec3(lIlllIlIlIIll.posX, lIlllIlIlIIll.posY + lIlllIlIlIIll.getEyeHeight(), lIlllIlIlIIll.posZ), false, true, false) == null;
    }
    
    @Subscribe
    public void onRender3D(final Render3DEvent lIllllIllIIII) {
        for (final Object lIllllIllIIlI : PenisESP.mc.theWorld.loadedEntityList) {
            if (lIllllIllIIlI instanceof EntityPlayer) {
                final EntityPlayer lIllllIllIlII = (EntityPlayer)lIllllIllIIlI;
                final double lIllllIllIIll = lIllllIllIlII.lastTickPosX + (lIllllIllIlII.posX - lIllllIllIlII.lastTickPosX) * PenisESP.mc.timer.renderPartialTicks;
                if (lineOfSight(lIllllIllIlII) && !lIllllIllIlII.noClip) {
                    PenisESP.mc.getRenderManager();
                    final double lIllllIlllIIl = lIllllIllIIll - PenisESP.mc.renderManager.renderPosX;
                    final double lIllllIlllIII = lIllllIllIlII.lastTickPosY + (lIllllIllIlII.posY - lIllllIllIlII.lastTickPosY) * PenisESP.mc.timer.renderPartialTicks;
                    PenisESP.mc.getRenderManager();
                    final double lIllllIllIlll = lIllllIlllIII - PenisESP.mc.renderManager.renderPosY;
                    final double lIllllIllIllI = lIllllIllIlII.lastTickPosZ + (lIllllIllIlII.posZ - lIllllIllIlII.lastTickPosZ) * PenisESP.mc.timer.renderPartialTicks;
                    PenisESP.mc.getRenderManager();
                    final double lIllllIllIlIl = lIllllIllIllI - PenisESP.mc.renderManager.renderPosZ;
                    GL11.glPushMatrix();
                    RenderHelper.disableStandardItemLighting();
                    this.esp(lIllllIllIlII, lIllllIlllIIl, lIllllIllIlll, lIllllIllIlIl);
                    RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
            }
            ++this.pamount;
            if (this.pamount > 25.0f) {
                ++this.pspin;
                if (this.pspin > 50.0f) {
                    this.pspin = -50.0f;
                }
                else if (this.pspin < -50.0f) {
                    this.pspin = 50.0f;
                }
                this.pamount = 0.0f;
            }
            ++this.pcumsize;
            if (this.pcumsize > 180.0f) {
                this.pcumsize = -180.0f;
            }
            else {
                if (this.pcumsize >= -180.0f) {
                    continue;
                }
                this.pcumsize = 180.0f;
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.pspin = 0.0f;
        this.pcumsize = 0.0f;
        this.pamount = 0.0f;
    }
    
    public void esp(final EntityPlayer lIlllIlllIlII, final double lIlllIlllIIll, final double lIlllIllIlIIl, final double lIlllIlllIIIl) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(lIlllIlllIIll, lIlllIllIlIIl, lIlllIlllIIIl);
        GL11.glRotatef(-lIlllIlllIlII.rotationYaw, 0.0f, lIlllIlllIlII.height, 0.0f);
        GL11.glTranslated(-lIlllIlllIIll, -lIlllIllIlIIl, -lIlllIlllIIIl);
        GL11.glTranslated(lIlllIlllIIll, lIlllIllIlIIl + lIlllIlllIlII.height / 2.0f - 0.22499999403953552, lIlllIlllIIIl);
        GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
        GL11.glRotated((double)((lIlllIlllIlII.isSneaking() ? 35 : 0) + this.pspin), (double)(1.0f + this.pspin), 0.0, (double)this.pcumsize);
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        final Cylinder lIlllIlllIIII = new Cylinder();
        lIlllIlllIIII.setDrawStyle(100013);
        lIlllIlllIIII.draw(0.1f, 0.11f, 0.4f, 25, 20);
        GL11.glColor4f(1.38f, 0.85f, 1.38f, 1.0f);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        final Sphere lIlllIllIllll = new Sphere();
        lIlllIllIllll.setDrawStyle(100013);
        lIlllIllIllll.draw(0.14f, 10, 20);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        final Sphere lIlllIllIlllI = new Sphere();
        lIlllIllIlllI.setDrawStyle(100013);
        lIlllIllIlllI.draw(0.14f, 10, 20);
        GL11.glColor4f(1.35f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
        final Sphere lIlllIllIllIl = new Sphere();
        lIlllIllIllIl.setDrawStyle(100013);
        lIlllIllIllIl.draw(0.13f, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}
