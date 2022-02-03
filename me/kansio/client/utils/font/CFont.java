package me.kansio.client.utils.font;

import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.geom.*;
import org.lwjgl.opengl.*;

public class CFont
{
    protected /* synthetic */ CharData[] charData;
    protected /* synthetic */ Font font;
    protected /* synthetic */ boolean antiAlias;
    protected /* synthetic */ int fontHeight;
    private /* synthetic */ float imgSize;
    protected /* synthetic */ boolean fractionalMetrics;
    protected /* synthetic */ int charOffset;
    protected /* synthetic */ DynamicTexture tex;
    
    public boolean isAntiAlias() {
        return this.antiAlias;
    }
    
    protected DynamicTexture setupTexture(final Font llllllIllIIl, final boolean llllllIllllI, final boolean llllllIlIlll, final CharData[] llllllIlllII) {
        final BufferedImage llllllIllIll = this.generateFontImage(llllllIllIIl, llllllIllllI, llllllIlIlll, llllllIlllII);
        try {
            return new DynamicTexture(llllllIllIll);
        }
        catch (Exception lllllllIIIIl) {
            lllllllIIIIl.printStackTrace();
            return null;
        }
    }
    
    protected BufferedImage generateFontImage(final Font lllllIllIIlI, final boolean lllllIllllIl, final boolean lllllIllllII, final CharData[] lllllIlIllll) {
        final int lllllIlllIlI = (int)this.imgSize;
        final BufferedImage lllllIlllIIl = new BufferedImage(lllllIlllIlI, lllllIlllIlI, 2);
        final Graphics2D lllllIlllIII = (Graphics2D)lllllIlllIIl.getGraphics();
        lllllIlllIII.setFont(lllllIllIIlI);
        lllllIlllIII.setColor(new Color(255, 255, 255, 0));
        lllllIlllIII.fillRect(0, 0, lllllIlllIlI, lllllIlllIlI);
        lllllIlllIII.setColor(Color.WHITE);
        lllllIlllIII.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, lllllIllllII ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        lllllIlllIII.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, lllllIllllIl ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        lllllIlllIII.setRenderingHint(RenderingHints.KEY_ANTIALIASING, lllllIllllIl ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        final FontMetrics lllllIllIlll = lllllIlllIII.getFontMetrics();
        int lllllIllIllI = 0;
        int lllllIllIlIl = 0;
        int lllllIllIlII = 1;
        for (int llllllIIIIII = 0; llllllIIIIII < lllllIlIllll.length; ++llllllIIIIII) {
            final char llllllIIIIll = (char)llllllIIIIII;
            final CharData llllllIIIIlI = new CharData();
            final Rectangle2D llllllIIIIIl = lllllIllIlll.getStringBounds(String.valueOf(llllllIIIIll), lllllIlllIII);
            llllllIIIIlI.width = llllllIIIIIl.getBounds().width + 8;
            llllllIIIIlI.height = llllllIIIIIl.getBounds().height;
            if (lllllIllIlIl + llllllIIIIlI.width >= lllllIlllIlI) {
                lllllIllIlIl = 0;
                lllllIllIlII += lllllIllIllI;
                lllllIllIllI = 0;
            }
            if (llllllIIIIlI.height > lllllIllIllI) {
                lllllIllIllI = llllllIIIIlI.height;
            }
            llllllIIIIlI.storedX = lllllIllIlIl;
            llllllIIIIlI.storedY = lllllIllIlII;
            if (llllllIIIIlI.height > this.fontHeight) {
                this.fontHeight = llllllIIIIlI.height;
            }
            lllllIlIllll[llllllIIIIII] = llllllIIIIlI;
            lllllIlllIII.drawString(String.valueOf(llllllIIIIll), lllllIllIlIl + 2, lllllIllIlII + lllllIllIlll.getAscent());
            lllllIllIlIl += llllllIIIIlI.width;
        }
        return lllllIlllIIl;
    }
    
    public int getStringWidth(final String llllIlIllIlI) {
        int llllIlIllIIl = 0;
        final double llllIlIlIlIl = (Object)llllIlIllIlI.toCharArray();
        final int llllIlIlIlII = llllIlIlIlIl.length;
        for (boolean llllIlIlIIll = false; (llllIlIlIIll ? 1 : 0) < llllIlIlIlII; ++llllIlIlIIll) {
            final char llllIlIlllII = llllIlIlIlIl[llllIlIlIIll];
            if (llllIlIlllII < this.charData.length) {
                llllIlIllIIl += this.charData[llllIlIlllII].width - 8 + this.charOffset;
            }
        }
        return llllIlIllIIl / 2;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public void setFont(final Font llllIIlIIIIl) {
        this.font = llllIIlIIIIl;
        this.tex = this.setupTexture(llllIIlIIIIl, this.antiAlias, this.fractionalMetrics, this.charData);
    }
    
    public boolean isFractionalMetrics() {
        return this.fractionalMetrics;
    }
    
    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }
    
    public void drawChar(final CharData[] lllllIIllIll, final char lllllIIllIlI, final float lllllIIlIlII, final float lllllIIllIII) throws ArrayIndexOutOfBoundsException {
        try {
            this.drawQuad(lllllIIlIlII, lllllIIllIII, (float)lllllIIllIll[lllllIIllIlI].width, (float)lllllIIllIll[lllllIIllIlI].height, (float)lllllIIllIll[lllllIIllIlI].storedX, (float)lllllIIllIll[lllllIIllIlI].storedY, (float)lllllIIllIll[lllllIIllIlI].width, (float)lllllIIllIll[lllllIIllIlI].height);
        }
        catch (Exception lllllIIlllIl) {
            lllllIIlllIl.printStackTrace();
        }
    }
    
    protected void drawQuad(final float lllllIIIIIll, final float llllIlllIlIl, final float lllllIIIIIIl, final float llllIlllIIll, final float llllIlllllll, final float llllIlllIIIl, final float llllIlllIIII, final float llllIlllllII) {
        final float llllIllllIll = llllIlllllll / this.imgSize;
        final float llllIllllIlI = llllIlllIIIl / this.imgSize;
        final float llllIllllIIl = llllIlllIIII / this.imgSize;
        final float llllIllllIII = llllIlllllII / this.imgSize;
        GL11.glTexCoord2f(llllIllllIll + llllIllllIIl, llllIllllIlI);
        GL11.glVertex2d((double)(lllllIIIIIll + lllllIIIIIIl), (double)llllIlllIlIl);
        GL11.glTexCoord2f(llllIllllIll, llllIllllIlI);
        GL11.glVertex2d((double)lllllIIIIIll, (double)llllIlllIlIl);
        GL11.glTexCoord2f(llllIllllIll, llllIllllIlI + llllIllllIII);
        GL11.glVertex2d((double)lllllIIIIIll, (double)(llllIlllIlIl + llllIlllIIll));
        GL11.glTexCoord2f(llllIllllIll, llllIllllIlI + llllIllllIII);
        GL11.glVertex2d((double)lllllIIIIIll, (double)(llllIlllIlIl + llllIlllIIll));
        GL11.glTexCoord2f(llllIllllIll + llllIllllIIl, llllIllllIlI + llllIllllIII);
        GL11.glVertex2d((double)(lllllIIIIIll + lllllIIIIIIl), (double)(llllIlllIlIl + llllIlllIIll));
        GL11.glTexCoord2f(llllIllllIll + llllIllllIIl, llllIllllIlI);
        GL11.glVertex2d((double)(lllllIIIIIll + lllllIIIIIIl), (double)llllIlllIlIl);
    }
    
    public int getStringHeight(final String llllIllIlIII) {
        return this.getHeight();
    }
    
    public void setFractionalMetrics(final boolean llllIIllIlIl) {
        if (this.fractionalMetrics != llllIIllIlIl) {
            this.fractionalMetrics = llllIIllIlIl;
            this.tex = this.setupTexture(this.font, this.antiAlias, llllIIllIlIl, this.charData);
        }
    }
    
    public CFont(final Font lllllllIlIll, final boolean lllllllIlIlI, final boolean lllllllIllIl) {
        this.imgSize = 512.0f;
        this.charData = new CharData[256];
        this.fontHeight = -1;
        this.charOffset = 0;
        this.font = lllllllIlIll;
        this.antiAlias = lllllllIlIlI;
        this.fractionalMetrics = lllllllIllIl;
        this.tex = this.setupTexture(lllllllIlIll, lllllllIlIlI, lllllllIllIl, this.charData);
    }
    
    public void setAntiAlias(final boolean llllIlIIlIlI) {
        if (this.antiAlias != llllIlIIlIlI) {
            this.antiAlias = llllIlIIlIlI;
            this.tex = this.setupTexture(this.font, llllIlIIlIlI, this.fractionalMetrics, this.charData);
        }
    }
    
    protected static class CharData
    {
        public /* synthetic */ int storedX;
        public /* synthetic */ int height;
        public /* synthetic */ int storedY;
        public /* synthetic */ int width;
    }
}
