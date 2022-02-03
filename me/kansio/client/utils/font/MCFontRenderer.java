package me.kansio.client.utils.font;

import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class MCFontRenderer extends CFont
{
    protected /* synthetic */ CharData[] italicChars;
    protected /* synthetic */ CharData[] boldChars;
    protected /* synthetic */ DynamicTexture texItalic;
    protected /* synthetic */ CharData[] boldItalicChars;
    protected /* synthetic */ DynamicTexture texBold;
    protected /* synthetic */ DynamicTexture texItalicBold;
    private final /* synthetic */ int[] colorCode;
    
    public float drawStringWithShadow(final String lllIIIIIlIllI, final float lllIIIIIllIll, final float lllIIIIIllIlI, final int lllIIIIIllIIl) {
        final float lllIIIIIllIII = this.drawString(lllIIIIIlIllI, lllIIIIIllIll + 1.0, lllIIIIIllIlI + 0.5, lllIIIIIllIIl, true);
        return Math.max(lllIIIIIllIII, this.drawString(lllIIIIIlIllI, lllIIIIIllIll, lllIIIIIllIlI, lllIIIIIllIIl, false));
    }
    
    public int getStringWidthCust(final String llIllIIlIllll) {
        if (llIllIIlIllll == null) {
            return 0;
        }
        int llIllIIllIlIl = 0;
        CharData[] llIllIIllIlII = this.charData;
        boolean llIllIIllIIll = false;
        boolean llIllIIllIIlI = false;
        for (int llIllIIllIIIl = llIllIIlIllll.length(), llIllIIlllIII = 0; llIllIIlllIII < llIllIIllIIIl; ++llIllIIlllIII) {
            final char llIllIIlllIIl = llIllIIlIllll.charAt(llIllIIlllIII);
            if (String.valueOf(llIllIIlllIIl).equals("§") && llIllIIlllIII < llIllIIllIIIl) {
                final int llIllIIlllIlI = "0123456789abcdefklmnor".indexOf(llIllIIlllIIl);
                if (llIllIIlllIlI < 16) {
                    llIllIIllIIll = false;
                    llIllIIllIIlI = false;
                }
                else if (llIllIIlllIlI == 17) {
                    llIllIIllIIll = true;
                    if (llIllIIllIIlI) {
                        llIllIIllIlII = this.boldItalicChars;
                    }
                    else {
                        llIllIIllIlII = this.boldChars;
                    }
                }
                else if (llIllIIlllIlI == 20) {
                    llIllIIllIIlI = true;
                    if (llIllIIllIIll) {
                        llIllIIllIlII = this.boldItalicChars;
                    }
                    else {
                        llIllIIllIlII = this.italicChars;
                    }
                }
                else if (llIllIIlllIlI == 21) {
                    llIllIIllIIll = false;
                    llIllIIllIIlI = false;
                    llIllIIllIlII = this.charData;
                }
                ++llIllIIlllIII;
            }
            else if (llIllIIlllIIl < llIllIIllIlII.length && llIllIIlllIIl >= '\0') {
                llIllIIllIlIl += llIllIIllIlII[llIllIIlllIIl].width - 8 + this.charOffset;
            }
        }
        return (llIllIIllIlIl - this.charOffset) / 2;
    }
    
    public float drawString(final String lllIIIIlIllII, final double lllIIIIlIIllI, final double lllIIIIlIlIlI, final int lllIIIIlIIlII) {
        return this.drawString(lllIIIIlIllII, lllIIIIlIIllI, lllIIIIlIlIlI, lllIIIIlIIlII, false);
    }
    
    public float drawCenteredStringWithShadow(final String llIlllIlIIlII, final double llIlllIlIIIll, final double llIlllIlIlIII, final int llIlllIlIIIIl) {
        final float llIlllIlIIllI = this.drawString(llIlllIlIIlII, llIlllIlIIIll - this.getStringWidth(llIlllIlIIlII) / 2 + 0.45, llIlllIlIlIII + 0.5, llIlllIlIIIIl, true);
        return this.drawString(llIlllIlIIlII, llIlllIlIIIll - this.getStringWidth(llIlllIlIIlII) / 2, llIlllIlIlIII, llIlllIlIIIIl);
    }
    
    public float drawCenteredString(final String llIlllllIlIlI, final double llIlllllIlIIl, final double llIlllllIIIll, final int llIlllllIIlll) {
        return this.drawString(llIlllllIlIlI, llIlllllIlIIl - this.getStringWidth(llIlllllIlIlI) / 2, llIlllllIIIll, llIlllllIIlll);
    }
    
    public float drawCenteredStringWithShadow(final String llIllllIllIlI, final float llIllllIllIIl, final float llIllllIlIIlI, final int llIllllIlIlll) {
        final float llIllllIlIllI = this.drawString(llIllllIllIlI, llIllllIllIIl - this.getStringWidth(llIllllIllIlI) / 2 + 0.45, llIllllIlIIlI + 0.5, llIllllIlIlll, true);
        return this.drawString(llIllllIllIlI, llIllllIllIIl - this.getStringWidth(llIllllIllIlI) / 2, llIllllIlIIlI, llIllllIlIlll);
    }
    
    public float drawCenteredString(final String llIllllllIlII, final float llIlllllllIII, final float llIllllllIIlI, final int llIllllllIllI) {
        return this.drawString(llIllllllIlII, llIlllllllIII - this.getStringWidth(llIllllllIlII) / 2, llIllllllIIlI, llIllllllIllI);
    }
    
    @Override
    public void setAntiAlias(final boolean llIllIIIlllIl) {
        super.setAntiAlias(llIllIIIlllIl);
        this.setupBoldItalicIDs();
    }
    
    public List<String> wrapWords(final String llIlIlllIllII, final double llIlIlllIlIll) {
        final List llIlIlllIlIlI = new ArrayList();
        if (this.getStringWidth(llIlIlllIllII) > llIlIlllIlIll) {
            final String[] llIlIllllIIII = llIlIlllIllII.split(" ");
            String llIlIlllIllll = "";
            char llIlIlllIlllI = '\uffff';
            int llIlIlllIIIlI = (Object)llIlIllllIIII;
            final Exception llIlIlllIIIIl = (Exception)llIlIlllIIIlI.length;
            for (final String llIlIllllIIlI : llIlIlllIIIlI) {
                for (int llIlIllllIIll = 0; llIlIllllIIll < llIlIllllIIlI.toCharArray().length; ++llIlIllllIIll) {
                    final char llIlIllllIlII = llIlIllllIIlI.toCharArray()[llIlIllllIIll];
                    if (String.valueOf(llIlIllllIlII).equals("§") && llIlIllllIIll < llIlIllllIIlI.toCharArray().length - 1) {
                        llIlIlllIlllI = llIlIllllIIlI.toCharArray()[llIlIllllIIll + 1];
                    }
                }
                if (this.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlllIllll).append(llIlIllllIIlI).append(" "))) < llIlIlllIlIll) {
                    llIlIlllIllll = String.valueOf(new StringBuilder().append(llIlIlllIllll).append(llIlIllllIIlI).append(" "));
                }
                else {
                    llIlIlllIlIlI.add(llIlIlllIllll);
                    llIlIlllIllll = String.valueOf(new StringBuilder().append("").append(llIlIlllIlllI).append(llIlIllllIIlI).append(" "));
                }
            }
            if (llIlIlllIllll.length() > 0) {
                if (this.getStringWidth(llIlIlllIllll) < llIlIlllIlIll) {
                    llIlIlllIlIlI.add(String.valueOf(new StringBuilder().append("").append(llIlIlllIlllI).append(llIlIlllIllll).append(" ")));
                    llIlIlllIllll = "";
                }
                else {
                    llIlIlllIIIlI = (int)this.formatString(llIlIlllIllll, llIlIlllIlIll).iterator();
                    while (((Iterator)llIlIlllIIIlI).hasNext()) {
                        final String llIlIllllIIIl = ((Iterator<String>)llIlIlllIIIlI).next();
                        llIlIlllIlIlI.add(llIlIllllIIIl);
                    }
                }
            }
        }
        else {
            llIlIlllIlIlI.add(llIlIlllIllII);
        }
        return (List<String>)llIlIlllIlIlI;
    }
    
    @Override
    public int getStringWidth(final String llIllIlIlIlII) {
        if (llIllIlIlIlII == null) {
            return 0;
        }
        int llIllIlIlIIll = 0;
        CharData[] llIllIlIlIIlI = this.charData;
        boolean llIllIlIlIIIl = false;
        boolean llIllIlIlIIII = false;
        for (int llIllIlIIllll = llIllIlIlIlII.length(), llIllIlIlIllI = 0; llIllIlIlIllI < llIllIlIIllll; ++llIllIlIlIllI) {
            final char llIllIlIlIlll = llIllIlIlIlII.charAt(llIllIlIlIllI);
            if (String.valueOf(llIllIlIlIlll).equals("§") && llIllIlIlIllI < llIllIlIIllll) {
                final int llIllIlIllIII = "0123456789abcdefklmnor".indexOf(llIllIlIlIlll);
                if (llIllIlIllIII < 16) {
                    llIllIlIlIIIl = false;
                    llIllIlIlIIII = false;
                }
                else if (llIllIlIllIII == 17) {
                    llIllIlIlIIIl = true;
                    if (llIllIlIlIIII) {
                        llIllIlIlIIlI = this.boldItalicChars;
                    }
                    else {
                        llIllIlIlIIlI = this.boldChars;
                    }
                }
                else if (llIllIlIllIII == 20) {
                    llIllIlIlIIII = true;
                    if (llIllIlIlIIIl) {
                        llIllIlIlIIlI = this.boldItalicChars;
                    }
                    else {
                        llIllIlIlIIlI = this.italicChars;
                    }
                }
                else if (llIllIlIllIII == 21) {
                    llIllIlIlIIIl = false;
                    llIllIlIlIIII = false;
                    llIllIlIlIIlI = this.charData;
                }
                ++llIllIlIlIllI;
            }
            else if (llIllIlIlIlll < llIllIlIlIIlI.length && llIllIlIlIlll >= '\0') {
                llIllIlIlIIll += llIllIlIlIIlI[llIllIlIlIlll].width - 8 + this.charOffset;
            }
        }
        return llIllIlIlIIll / 2;
    }
    
    @Override
    public void setFont(final Font llIllIIlIIIIl) {
        super.setFont(llIllIIlIIIIl);
        this.setupBoldItalicIDs();
    }
    
    public void drawCenteredStringWithOutline(final String llIlllIllIlIl, final double llIlllIlllIIl, final double llIlllIlllIII, final int llIlllIllIlll) {
        this.drawCenteredString(llIlllIllIlIl, llIlllIlllIIl - 0.5, llIlllIlllIII, 0);
        this.drawCenteredString(llIlllIllIlIl, llIlllIlllIIl + 0.5, llIlllIlllIII, 0);
        this.drawCenteredString(llIlllIllIlIl, llIlllIlllIIl, llIlllIlllIII - 0.5, 0);
        this.drawCenteredString(llIlllIllIlIl, llIlllIlllIIl, llIlllIlllIII + 0.5, 0);
        this.drawCenteredString(llIlllIllIlIl, llIlllIlllIIl, llIlllIlllIII, llIlllIllIlll);
    }
    
    public float drawString(final String lllIIIIlllIll, final float lllIIIIlllIlI, final float lllIIIIllIlII, final int lllIIIIllIIll) {
        return this.drawString(lllIIIIlllIll, lllIIIIlllIlI, lllIIIIllIlII, lllIIIIllIIll, false);
    }
    
    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }
    
    public List<String> formatString(final String llIlIllIIlIIl, final double llIlIllIIllll) {
        final List llIlIllIIlllI = new ArrayList();
        String llIlIllIIllIl = "";
        char llIlIllIIllII = '\uffff';
        final char[] llIlIllIIlIll = llIlIllIIlIIl.toCharArray();
        for (int llIlIllIlIIlI = 0; llIlIllIlIIlI < llIlIllIIlIll.length; ++llIlIllIlIIlI) {
            final char llIlIllIlIIll = llIlIllIIlIll[llIlIllIlIIlI];
            if (String.valueOf(llIlIllIlIIll).equals("§") && llIlIllIlIIlI < llIlIllIIlIll.length - 1) {
                llIlIllIIllII = llIlIllIIlIll[llIlIllIlIIlI + 1];
            }
            if (this.getStringWidth(String.valueOf(new StringBuilder().append(llIlIllIIllIl).append(llIlIllIlIIll))) < llIlIllIIllll) {
                llIlIllIIllIl = String.valueOf(new StringBuilder().append(llIlIllIIllIl).append(llIlIllIlIIll));
            }
            else {
                llIlIllIIlllI.add(llIlIllIIllIl);
                llIlIllIIllIl = String.valueOf(new StringBuilder().append("").append(llIlIllIIllII).append(String.valueOf(llIlIllIlIIll)));
            }
        }
        if (llIlIllIIllIl.length() > 0) {
            llIlIllIIlllI.add(llIlIllIIllIl);
        }
        return (List<String>)llIlIllIIlllI;
    }
    
    private void drawLine(final double llIllIIIIIllI, final double llIllIIIIlIlI, final double llIllIIIIlIIl, final double llIllIIIIlIII, final float llIllIIIIIIlI) {
        GL11.glDisable(3553);
        GL11.glLineWidth(llIllIIIIIIlI);
        GL11.glBegin(1);
        GL11.glVertex2d(llIllIIIIIllI, llIllIIIIlIlI);
        GL11.glVertex2d(llIllIIIIlIIl, llIllIIIIlIII);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public float drawStringWithShadow(final String lllIIIIIIIlII, final double lllIIIIIIIIll, final double lllIIIIIIIIlI, final int lllIIIIIIIlll) {
        final float lllIIIIIIIllI = this.drawString(lllIIIIIIIlII, lllIIIIIIIIll + 1.0, lllIIIIIIIIlI + 0.5, lllIIIIIIIlll, true);
        return Math.max(lllIIIIIIIllI, this.drawString(lllIIIIIIIlII, lllIIIIIIIIll, lllIIIIIIIIlI, lllIIIIIIIlll, false));
    }
    
    private void setupMinecraftColorcodes() {
        for (int llIlIlIllIlll = 0; llIlIlIllIlll < 32; ++llIlIlIllIlll) {
            final int llIlIlIlllIll = (llIlIlIllIlll >> 3 & 0x1) * 85;
            int llIlIlIlllIlI = (llIlIlIllIlll >> 2 & 0x1) * 170 + llIlIlIlllIll;
            int llIlIlIlllIIl = (llIlIlIllIlll >> 1 & 0x1) * 170 + llIlIlIlllIll;
            int llIlIlIlllIII = (llIlIlIllIlll >> 0 & 0x1) * 170 + llIlIlIlllIll;
            if (llIlIlIllIlll == 6) {
                llIlIlIlllIlI += 85;
            }
            if (llIlIlIllIlll >= 16) {
                llIlIlIlllIlI /= 4;
                llIlIlIlllIIl /= 4;
                llIlIlIlllIII /= 4;
            }
            this.colorCode[llIlIlIllIlll] = ((llIlIlIlllIlI & 0xFF) << 16 | (llIlIlIlllIIl & 0xFF) << 8 | (llIlIlIlllIII & 0xFF));
        }
    }
    
    public void drawStringWithOutline(final String llIllllIIlIIl, final double llIllllIIIIll, final double llIllllIIIlll, final int llIllllIIIllI) {
        this.drawString(llIllllIIlIIl, llIllllIIIIll - 0.5, llIllllIIIlll, 0);
        this.drawString(llIllllIIlIIl, llIllllIIIIll + 0.5, llIllllIIIlll, 0);
        this.drawString(llIllllIIlIIl, llIllllIIIIll, llIllllIIIlll - 0.5, 0);
        this.drawString(llIllllIIlIIl, llIllllIIIIll, llIllllIIIlll + 0.5, 0);
        this.drawString(llIllllIIlIIl, llIllllIIIIll, llIllllIIIlll, llIllllIIIllI);
    }
    
    public float drawString(final String llIllIlllIlIl, double llIllIlllIlII, double llIllIlllIIll, int llIllIlllIIlI, final boolean llIllIlllIIIl) {
        final Minecraft llIllIlllllll = Minecraft.getMinecraft();
        --llIllIlllIlII;
        if (llIllIlllIlIl == null) {
            return 0.0f;
        }
        if (llIllIlllIIlI == 553648127) {
            llIllIlllIIlI = 16777215;
        }
        if ((llIllIlllIIlI & 0xFC000000) == 0x0) {
            llIllIlllIIlI |= 0xFF000000;
        }
        if (llIllIlllIIIl) {
            llIllIlllIIlI = ((llIllIlllIIlI & 0xFCFCFC) >> 2 | (llIllIlllIIlI & new Color(20, 20, 20, 200).getRGB()));
        }
        CharData[] llIllIllllllI = this.charData;
        final float llIllIlllllIl = (llIllIlllIIlI >> 24 & 0xFF) / 255.0f;
        boolean llIllIlllllII = false;
        boolean llIllIllllIll = false;
        boolean llIllIllllIlI = false;
        boolean llIllIllllIIl = false;
        boolean llIllIllllIII = false;
        final boolean llIllIlllIlll = true;
        llIllIlllIlII *= (long)2.0;
        llIllIlllIIll = (llIllIlllIIll - 3.0) * 2.0;
        if (llIllIlllIlll) {
            GL11.glPushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((llIllIlllIIlI >> 16 & 0xFF) / 255.0f, (llIllIlllIIlI >> 8 & 0xFF) / 255.0f, (llIllIlllIIlI & 0xFF) / 255.0f, llIllIlllllIl);
            final int llIlllIIIIllI = llIllIlllIlIl.length();
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            for (int llIlllIIIIlll = 0; llIlllIIIIlll < llIlllIIIIllI; ++llIlllIIIIlll) {
                final char llIlllIIIlIII = llIllIlllIlIl.charAt(llIlllIIIIlll);
                if (String.valueOf(llIlllIIIlIII).equals("§") && llIlllIIIIlll < llIlllIIIIllI) {
                    int llIlllIIIlIIl = 21;
                    try {
                        llIlllIIIlIIl = "0123456789abcdefklmnor".indexOf(llIllIlllIlIl.charAt(llIlllIIIIlll + 1));
                    }
                    catch (Exception llIlllIIIlIll) {
                        llIlllIIIlIll.printStackTrace();
                    }
                    if (llIlllIIIlIIl < 16) {
                        llIllIllllIll = false;
                        llIllIllllIlI = false;
                        llIllIlllllII = false;
                        llIllIllllIII = false;
                        llIllIllllIIl = false;
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        llIllIllllllI = this.charData;
                        if (llIlllIIIlIIl < 0 || llIlllIIIlIIl > 15) {
                            llIlllIIIlIIl = 15;
                        }
                        if (llIllIlllIIIl) {
                            llIlllIIIlIIl += 16;
                        }
                        final int llIlllIIIlIlI = this.colorCode[llIlllIIIlIIl];
                        GlStateManager.color((llIlllIIIlIlI >> 16 & 0xFF) / 255.0f, (llIlllIIIlIlI >> 8 & 0xFF) / 255.0f, (llIlllIIIlIlI & 0xFF) / 255.0f, llIllIlllllIl);
                    }
                    else if (llIlllIIIlIIl == 16) {
                        llIllIlllllII = true;
                    }
                    else if (llIlllIIIlIIl == 17) {
                        llIllIllllIll = true;
                        if (llIllIllllIlI) {
                            GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                            llIllIllllllI = this.boldItalicChars;
                        }
                        else {
                            GlStateManager.bindTexture(this.texBold.getGlTextureId());
                            llIllIllllllI = this.boldChars;
                        }
                    }
                    else if (llIlllIIIlIIl == 18) {
                        llIllIllllIIl = true;
                    }
                    else if (llIlllIIIlIIl == 19) {
                        llIllIllllIII = true;
                    }
                    else if (llIlllIIIlIIl == 20) {
                        llIllIllllIlI = true;
                        if (llIllIllllIll) {
                            GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                            llIllIllllllI = this.boldItalicChars;
                        }
                        else {
                            GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                            llIllIllllllI = this.italicChars;
                        }
                    }
                    else if (llIlllIIIlIIl == 21) {
                        llIllIllllIll = false;
                        llIllIllllIlI = false;
                        llIllIlllllII = false;
                        llIllIllllIII = false;
                        llIllIllllIIl = false;
                        GlStateManager.color((llIllIlllIIlI >> 16 & 0xFF) / 255.0f, (llIllIlllIIlI >> 8 & 0xFF) / 255.0f, (llIllIlllIIlI & 0xFF) / 255.0f, llIllIlllllIl);
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        llIllIllllllI = this.charData;
                    }
                    ++llIlllIIIIlll;
                }
                else if (llIlllIIIlIII < llIllIllllllI.length && llIlllIIIlIII >= '\0') {
                    GL11.glBegin(4);
                    this.drawChar(llIllIllllllI, llIlllIIIlIII, (float)llIllIlllIlII, (float)llIllIlllIIll);
                    GL11.glEnd();
                    if (llIllIllllIIl) {
                        this.drawLine((double)llIllIlllIlII, llIllIlllIIll + llIllIllllllI[llIlllIIIlIII].height / 2, llIllIlllIlII + (double)llIllIllllllI[llIlllIIIlIII].width - 8.0, llIllIlllIIll + llIllIllllllI[llIlllIIIlIII].height / 2, 1.0f);
                    }
                    if (llIllIllllIII) {
                        this.drawLine((double)llIllIlllIlII, llIllIlllIIll + llIllIllllllI[llIlllIIIlIII].height - 2.0, llIllIlllIlII + (double)llIllIllllllI[llIlllIIIlIII].width - 8.0, llIllIlllIIll + llIllIllllllI[llIlllIIIlIII].height - 2.0, 1.0f);
                    }
                    llIllIlllIlII += llIllIllllllI[llIlllIIIlIII].width - 8 + this.charOffset;
                }
            }
            GL11.glHint(3155, 4352);
            GL11.glPopMatrix();
        }
        return llIllIlllIlII / 2.0f;
    }
    
    @Override
    public void setFractionalMetrics(final boolean llIllIIIlIlll) {
        super.setFractionalMetrics(llIllIIIlIlll);
        this.setupBoldItalicIDs();
    }
    
    public MCFontRenderer(final Font lllIIIlIIIlII, final boolean lllIIIlIIIIll, final boolean lllIIIlIIIIlI) {
        super(lllIIIlIIIlII, lllIIIlIIIIll, lllIIIlIIIIlI);
        this.boldChars = new CharData[256];
        this.italicChars = new CharData[256];
        this.boldItalicChars = new CharData[256];
        this.colorCode = new int[32];
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }
}
