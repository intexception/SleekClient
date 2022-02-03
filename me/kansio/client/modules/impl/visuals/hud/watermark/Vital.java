package me.kansio.client.modules.impl.visuals.hud.watermark;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.event.impl.*;
import java.awt.*;
import oshi.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.utils.font.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import oshi.hardware.*;

public class Vital extends WaterMarkMode
{
    @Override
    public void onRenderOverlay(final RenderOverlayEvent lIlIIllIIIIIIl) {
        final int lIlIIllIIIIIII = new Color(87, 124, 255).getRGB();
        final Processor[] lIlIIlIlllllll = new SystemInfo().getHardware().getProcessors();
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.5, 2.5, 2.5);
        if (this.getHud().font.getValue()) {
            Fonts.YantramanavThin.drawStringWithShadow("S", 1.0f, 0.0f, lIlIIllIIIIIII);
        }
        else {
            Vital.mc.fontRendererObj.drawStringWithShadow("S", 1.0f, 0.0f, lIlIIllIIIIIII);
        }
        GlStateManager.popMatrix();
        if (this.getHud().font.getValue()) {
            Fonts.YantramanavThin.drawStringWithShadow("leek", 18.0f, 12.0f, lIlIIllIIIIIII);
            Fonts.YantramanavThin.drawStringWithShadow(String.valueOf(new StringBuilder().append("fps: ").append(Minecraft.getDebugFPS())), 1.0f, 22.0f, lIlIIllIIIIIII);
            Fonts.YantramanavThin.drawStringWithShadow(String.valueOf(new StringBuilder().append("gpu: ").append(GL11.glGetString(7937))), 1.0f, 32.0f, lIlIIllIIIIIII);
            Fonts.YantramanavThin.drawStringWithShadow(String.valueOf(new StringBuilder().append("cpu: ").append(lIlIIlIlllllll[0].toString())), 1.0f, 42.0f, lIlIIllIIIIIII);
        }
        else {
            Vital.mc.fontRendererObj.drawStringWithShadow("leek", 18.0f, 12.0f, lIlIIllIIIIIII);
            Vital.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("fps: ").append(Minecraft.getDebugFPS())), 1.0f, 22.0f, lIlIIllIIIIIII);
            Vital.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("gpu: ").append(GL11.glGetString(7937))), 1.0f, 32.0f, lIlIIllIIIIIII);
            Vital.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("cpu: ").append(lIlIIlIlllllll[0].toString())), 1.0f, 42.0f, lIlIIllIIIIIII);
        }
    }
    
    public Vital() {
        super("Vital");
    }
}
