package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import java.util.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;
import me.kansio.client.config.*;
import java.io.*;
import me.kansio.client.*;
import java.util.concurrent.atomic.*;
import org.lwjgl.input.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class ConfigCategory implements Priority
{
    private final /* synthetic */ ArrayList<FrameConfig> modules;
    private final /* synthetic */ Animate animation;
    private /* synthetic */ int yDrag;
    private /* synthetic */ int width;
    private /* synthetic */ int offset;
    private /* synthetic */ int xDrag;
    private /* synthetic */ int x;
    private /* synthetic */ int y;
    private /* synthetic */ boolean drag;
    private /* synthetic */ int height;
    
    public void keyTyped(final char lIIIlIlllIllI, final int lIIIlIlllIIlI) {
        this.modules.forEach(lIIIlIllIlIIl -> lIIIlIllIlIIl.keyTyped(lIIIlIlllIllI, lIIIlIlllIIlI));
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getX() {
        return this.x;
    }
    
    public ConfigCategory(final int lIIIlllIlIlIl, final int lIIIlllIlIlll) {
        this.modules = new ArrayList<FrameConfig>();
        this.animation = new Animate().setEase(Easing.CUBIC_OUT).setSpeed(250.0f).setMin(0.0f).setMax(62.5f);
        this.x = lIIIlllIlIlIl;
        this.y = lIIIlllIlIlll;
        this.xDrag = 0;
        this.yDrag = 0;
        this.offset = 0;
        this.drag = false;
        this.width = 125;
        this.height = 300;
        this.modules.add(new FrameConfig(null, this, 0, 0));
        this.modules.add(new FrameConfig(new Config("", null), this, 0, 0));
        Client.getInstance().getConfigManager().getConfigs().forEach(lIIIlIlIlllIl -> this.modules.add(new FrameConfig(lIIIlIlIlllIl, this, 0, 0)));
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setYDrag(final int lIIIllIIIlIIl) {
        this.yDrag = lIIIllIIIlIIl;
    }
    
    public void setX(final int lIIIllIIllllI) {
        this.x = lIIIllIIllllI;
    }
    
    public void setY(final int lIIIllIIllIII) {
        this.y = lIIIllIIllIII;
    }
    
    public void setXDrag(final int lIIIllIIIllIl) {
        this.xDrag = lIIIllIIIllIl;
    }
    
    public void setDrag(final boolean lIIIllIIIIIll) {
        this.drag = lIIIllIIIIIll;
    }
    
    public void drawScreen(final int lIIIlllIIIllI, final int lIIIlllIIIIII) {
        final AtomicInteger lIIIlllIIIlII = new AtomicInteger();
        this.modules.forEach(lIIIlIllIIlIl -> lIIIlllIIIlII.addAndGet(lIIIlIllIIlIl.getOffset()));
        this.height = Math.min(20 + lIIIlllIIIlII.get(), 300);
        if (Mouse.hasWheel() && RenderUtils.hover(this.x, this.y, lIIIlllIIIllI, lIIIlllIIIIII, 125, this.height)) {
            final int lIIIlllIIlIIl = Mouse.getDWheel();
            if (lIIIlllIIlIIl > 0 && this.offset - 14 > 0) {
                this.offset -= 15;
            }
            else if (lIIIlllIIlIIl < 0 && this.offset + 14 <= lIIIlllIIIlII.get() - this.height + 20) {
                this.offset += 15;
            }
        }
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.getHeight(), ConfigCategory.mainColor);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.width, this.getY() + 20, ConfigCategory.darkerMainColor);
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + this.getHeight(), ConfigCategory.darkerMainColor);
        Gui.drawRect(this.getX() + this.width, this.getY(), this.getX() + this.width + 1, this.getY() + this.getHeight(), ConfigCategory.darkerMainColor);
        Gui.drawRect(this.getX() - 1, this.y + this.getHeight(), this.getX() + this.width + 1, this.getY() + this.getHeight() + 1, ConfigCategory.darkerMainColor);
        if (this.drag) {
            this.setX(this.xDrag + lIIIlllIIIllI);
            this.setY(this.yDrag + lIIIlllIIIIII);
        }
        Minecraft.getMinecraft().fontRendererObj.drawString("Configs", this.x + 3, (int)(this.y + (10.0f - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f)), -1);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtils.prepareScissorBox(this.getX() + this.width / 2.0f - this.animation.update().getValue(), (float)(this.getY() + 20), this.x + this.width / 2.0f + this.animation.getValue(), (float)(this.y + this.getHeight()));
        int lIIIlllIIIIll = 0;
        for (final FrameConfig lIIIlllIIlIII : this.modules) {
            lIIIlllIIlIII.setX(this.x);
            lIIIlllIIlIII.setY(this.y + 20 + lIIIlllIIIIll - this.offset);
            lIIIlllIIlIII.drawScreen(lIIIlllIIIllI, lIIIlllIIIIII);
            lIIIlllIIIIll += lIIIlllIIlIII.getOffset();
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
    
    public void mouseReleased(final int lIIIllIlIlIII, final int lIIIllIlIIlll, final int lIIIllIlIIllI) {
        this.drag = false;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void initGui() {
        this.animation.setSpeed(100.0f).reset();
    }
    
    public void mouseClicked(final int lIIIllIllIIll, final int lIIIllIllIIlI, final int lIIIllIllIIIl) {
        for (final FrameConfig lIIIllIllIlIl : this.modules) {
            if (lIIIllIllIlIl.mouseClicked(lIIIllIllIIll, lIIIllIllIIlI, lIIIllIllIIIl)) {
                this.setDrag(false);
                return;
            }
        }
        if (RenderUtils.hover(this.x, this.y, lIIIllIllIIll, lIIIllIllIIlI, this.width, this.height) && lIIIllIllIIIl == 0) {
            this.setDrag(true);
            this.setXDrag(this.getX() - lIIIllIllIIll);
            this.setYDrag(this.getY() - lIIIllIllIIlI);
        }
        else {
            this.setDrag(false);
        }
    }
}
