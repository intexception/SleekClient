package me.kansio.client.gui.notification;

import java.awt.*;
import net.minecraft.client.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;
import me.kansio.client.utils.font.*;
import net.minecraft.client.gui.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Notification
{
    private /* synthetic */ long fadeOut;
    private /* synthetic */ String messsage;
    private /* synthetic */ long fadedIn;
    private /* synthetic */ long start;
    private /* synthetic */ long end;
    private /* synthetic */ String title;
    private /* synthetic */ NotificationType type;
    
    public void render() {
        final int lIIlIlIlllllIl = 120;
        final int lIIlIlIlllllII = 30;
        final long lIIlIlIllllIll = this.getTime();
        double lIIlIlIllllllI = 0.0;
        if (lIIlIlIllllIll < this.fadedIn) {
            final double lIIlIllIIIllIl = Math.tanh(lIIlIlIllllIll / (double)this.fadedIn * 3.0) * lIIlIlIlllllIl;
        }
        else if (lIIlIlIllllIll > this.fadeOut) {
            final double lIIlIllIIIllII = Math.tanh(3.0 - (lIIlIlIllllIll - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * lIIlIlIlllllIl;
        }
        else {
            lIIlIlIllllllI = lIIlIlIlllllIl;
        }
        final Color lIIlIlIllllIlI = new Color(0, 0, 0, 100);
        switch (this.type) {
            case TOGGLEON: {
                final Color lIIlIllIIIlIll = new Color(ColorPalette.GREEN.getColor().getRGB());
                final String lIIlIllIIIlIlI = "E";
                break;
            }
            case TOGGLEOFF: {
                final Color lIIlIllIIIlIIl = new Color(ColorPalette.RED.getColor().getRGB());
                final String lIIlIllIIIlIII = "D";
                break;
            }
            case TIME: {
                final Color lIIlIllIIIIlll = new Color(255, 255, 255);
                final String lIIlIllIIIIllI = "F";
                break;
            }
            case INFO: {
                final Color lIIlIllIIIIlIl = new Color(255, 255, 255);
                final String lIIlIllIIIIlII = "C";
                break;
            }
            case WARNING: {
                final Color lIIlIllIIIIIll = new Color(ColorPalette.YELLOW.getColor().getRGB());
                final String lIIlIllIIIIIlI = "A";
                break;
            }
            case ERROR: {
                final Color lIIlIllIIIIIIl = new Color(ColorPalette.RED.getColor().getRGB());
                final String lIIlIllIIIIIII = "B";
                break;
            }
            default: {
                throw new IllegalStateException(String.valueOf(new StringBuilder().append("Unexpected value: ").append(this.type)));
            }
        }
        final FontRenderer lIIlIlIlllIlll = Minecraft.getMinecraft().fontRendererObj;
        drawRect(GuiScreen.width - lIIlIlIllllllI, GuiScreen.height - 5 - lIIlIlIlllllII - 20, GuiScreen.width, GuiScreen.height - 10 - 20, lIIlIlIllllIlI.getRGB());
        final Color lIIlIlIllllIIl;
        drawRect(GuiScreen.width - lIIlIlIllllllI, GuiScreen.height - 5 - lIIlIlIlllllII - 20, GuiScreen.width - lIIlIlIllllllI + 2.0, GuiScreen.height - 10 - 20, lIIlIlIllllIIl.getRGB());
        final HUD lIIlIlIlllIllI = (HUD)Client.getInstance().getModuleManager().getModuleByName("HUD");
        if (lIIlIlIlllIllI.font.getValue()) {
            Fonts.Verdana.drawString(this.title, (float)(int)(GuiScreen.width - lIIlIlIllllllI + 8.0), (float)(GuiScreen.height - 2 - lIIlIlIlllllII - 20), lIIlIlIllllIIl.getRGB());
            final String lIIlIlIllllIII;
            Fonts.NotifIcon.drawString(lIIlIlIllllIII, (float)(int)(GuiScreen.width - lIIlIlIllllllI + 100.0), (float)(GuiScreen.height + 2 - lIIlIlIlllllII - 20), lIIlIlIllllIIl.getRGB());
            Fonts.Verdana.drawString(this.messsage, (float)(int)(GuiScreen.width - lIIlIlIllllllI + 8.0), (float)(GuiScreen.height - 40), -1);
        }
        else {
            lIIlIlIlllIlll.drawString(this.title, (int)(GuiScreen.width - lIIlIlIllllllI + 8.0), GuiScreen.height - 2 - lIIlIlIlllllII - 20, lIIlIlIllllIIl.getRGB());
            final String lIIlIlIllllIII;
            Fonts.NotifIcon.drawString(lIIlIlIllllIII, (float)(int)(GuiScreen.width - lIIlIlIllllllI + 100.0), (float)(GuiScreen.height + 2 - lIIlIlIlllllII - 20), lIIlIlIllllIIl.getRGB());
            lIIlIlIlllIlll.drawString(this.messsage, (int)(GuiScreen.width - lIIlIlIllllllI + 8.0), GuiScreen.height - 20 - 20, -1);
        }
    }
    
    public static void drawRect(final int lIIlIlIlIIIIlI, double lIIlIlIlIIIIIl, double lIIlIlIlIIIIII, double lIIlIlIIllllll, double lIIlIlIIlllllI, final int lIIlIlIlIIIlIl) {
        if (lIIlIlIlIIIIIl < lIIlIlIIllllll) {
            final double lIIlIlIlIIllII = lIIlIlIlIIIIIl;
            lIIlIlIlIIIIIl = lIIlIlIIllllll;
            lIIlIlIIllllll = lIIlIlIlIIllII;
        }
        if (lIIlIlIlIIIIII < lIIlIlIIlllllI) {
            final double lIIlIlIlIIlIll = lIIlIlIlIIIIII;
            lIIlIlIlIIIIII = lIIlIlIIlllllI;
            lIIlIlIIlllllI = lIIlIlIlIIlIll;
        }
        final Tessellator lIIlIlIlIIIlII = Tessellator.getInstance();
        final WorldRenderer lIIlIlIlIIIIll = lIIlIlIlIIIlII.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        ColorUtils.glColor(lIIlIlIlIIIlIl);
        lIIlIlIlIIIIll.begin(lIIlIlIlIIIIlI, DefaultVertexFormats.POSITION);
        lIIlIlIlIIIIll.pos(lIIlIlIlIIIIIl, lIIlIlIIlllllI, 0.0).endVertex();
        lIIlIlIlIIIIll.pos(lIIlIlIIllllll, lIIlIlIIlllllI, 0.0).endVertex();
        lIIlIlIlIIIIll.pos(lIIlIlIIllllll, lIIlIlIlIIIIII, 0.0).endVertex();
        lIIlIlIlIIIIll.pos(lIIlIlIlIIIIIl, lIIlIlIlIIIIII, 0.0).endVertex();
        lIIlIlIlIIIlII.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public void show() {
        this.start = System.currentTimeMillis();
    }
    
    public static void drawRect(double lIIlIlIlIllIll, double lIIlIlIlIllIlI, double lIIlIlIlIllIIl, double lIIlIlIlIllIII, final int lIIlIlIlIllllI) {
        if (lIIlIlIlIllIll < lIIlIlIlIllIIl) {
            final double lIIlIlIllIIlII = lIIlIlIlIllIll;
            lIIlIlIlIllIll = (double)lIIlIlIlIllIIl;
            lIIlIlIlIllIIl = lIIlIlIllIIlII;
        }
        if (lIIlIlIlIllIlI < lIIlIlIlIllIII) {
            final double lIIlIlIllIIIll = (double)lIIlIlIlIllIlI;
            lIIlIlIlIllIlI = lIIlIlIlIllIII;
            lIIlIlIlIllIII = lIIlIlIllIIIll;
        }
        final Tessellator lIIlIlIlIlllIl = Tessellator.getInstance();
        final WorldRenderer lIIlIlIlIlllII = lIIlIlIlIlllIl.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        ColorUtils.glColor(lIIlIlIlIllllI);
        lIIlIlIlIlllII.begin(7, DefaultVertexFormats.POSITION);
        lIIlIlIlIlllII.pos(lIIlIlIlIllIll, lIIlIlIlIllIII, 0.0).endVertex();
        lIIlIlIlIlllII.pos((double)lIIlIlIlIllIIl, lIIlIlIlIllIII, 0.0).endVertex();
        lIIlIlIlIlllII.pos((double)lIIlIlIlIllIIl, (double)lIIlIlIlIllIlI, 0.0).endVertex();
        lIIlIlIlIlllII.pos(lIIlIlIlIllIll, (double)lIIlIlIlIllIlI, 0.0).endVertex();
        lIIlIlIlIlllIl.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public Notification(final NotificationType lIIlIllIlIlIIl, final String lIIlIllIlIlIII, final String lIIlIllIlIIIlI, final int lIIlIllIlIIIIl) {
        this.type = lIIlIllIlIlIIl;
        this.title = lIIlIllIlIlIII;
        this.messsage = lIIlIllIlIIIlI;
        this.fadedIn = 200 * lIIlIllIlIIIIl;
        this.fadeOut = this.fadedIn + 500 * lIIlIllIlIIIIl;
        this.end = this.fadeOut + this.fadedIn;
    }
    
    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public boolean isShown() {
        return this.getTime() <= this.end;
    }
    
    public enum NotificationType
    {
        TOGGLEON, 
        INFO, 
        TOGGLEOFF, 
        ERROR, 
        WARNING, 
        TIME;
    }
}
