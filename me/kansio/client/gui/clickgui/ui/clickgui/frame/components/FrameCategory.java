package me.kansio.client.gui.clickgui.ui.clickgui.frame.components;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import java.util.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.utils.render.*;
import java.util.concurrent.atomic.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class FrameCategory implements Priority
{
    private /* synthetic */ int width;
    private /* synthetic */ int height;
    private /* synthetic */ boolean drag;
    private final /* synthetic */ ArrayList<FrameModule> modules;
    private /* synthetic */ int y;
    private /* synthetic */ int xDrag;
    private /* synthetic */ int offset;
    private /* synthetic */ int x;
    private final /* synthetic */ ModuleCategory category;
    private final /* synthetic */ Animate animation;
    private /* synthetic */ int yDrag;
    
    public void setX(final int llllllllllllllllllllIIlIlllIlIII) {
        this.x = llllllllllllllllllllIIlIlllIlIII;
    }
    
    public void setDrag(final boolean llllllllllllllllllllIIlIllIIllIl) {
        this.drag = llllllllllllllllllllIIlIllIIllIl;
    }
    
    public FrameCategory(final ModuleCategory llllllllllllllllllllIIllIIlIIIlI, final int llllllllllllllllllllIIllIIlIIIIl, final int llllllllllllllllllllIIllIIlIIIII) {
        this.category = llllllllllllllllllllIIllIIlIIIlI;
        this.modules = new ArrayList<FrameModule>();
        this.animation = new Animate().setEase(Easing.CUBIC_OUT).setSpeed(250.0f).setMin(0.0f).setMax(62.5f);
        this.x = llllllllllllllllllllIIllIIlIIIIl;
        this.y = llllllllllllllllllllIIllIIlIIIII;
        this.xDrag = 0;
        this.yDrag = 0;
        this.offset = 0;
        this.drag = false;
        this.width = 125;
        this.height = 300;
        Client.getInstance().getModuleManager().getModulesFromCategory(llllllllllllllllllllIIllIIlIIIlI).forEach(llllllllllllllllllllIIlIlIlIlIll -> this.modules.add(new FrameModule(llllllllllllllllllllIIlIlIlIlIll, this, 0, 0)));
    }
    
    public void initGui() {
        this.animation.setSpeed(100.0f).reset();
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void keyTyped(final char llllllllllllllllllllIIlIllIIIIlI, final int llllllllllllllllllllIIlIllIIIIIl) {
        this.modules.forEach(llllllllllllllllllllIIlIlIllIlIl -> llllllllllllllllllllIIlIlIllIlIl.keyTyped(llllllllllllllllllllIIlIllIIIIlI, llllllllllllllllllllIIlIllIIIIIl));
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setY(final int llllllllllllllllllllIIlIlllIIIlI) {
        this.y = llllllllllllllllllllIIlIlllIIIlI;
    }
    
    public void setYDrag(final int llllllllllllllllllllIIlIllIlIIll) {
        this.yDrag = llllllllllllllllllllIIlIllIlIIll;
    }
    
    public void setXDrag(final int llllllllllllllllllllIIlIllIllIll) {
        this.xDrag = llllllllllllllllllllIIlIllIllIll;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void mouseReleased(final int llllllllllllllllllllIIlIllllIlII, final int llllllllllllllllllllIIlIllllIIll, final int llllllllllllllllllllIIlIllllIIlI) {
        this.drag = false;
    }
    
    public void mouseClicked(final int llllllllllllllllllllIIlIlllllIll, final int llllllllllllllllllllIIlIlllllllI, final int llllllllllllllllllllIIlIlllllIIl) {
        for (final FrameModule llllllllllllllllllllIIllIIIIIIIl : this.modules) {
            if (llllllllllllllllllllIIllIIIIIIIl.mouseClicked(llllllllllllllllllllIIlIlllllIll, llllllllllllllllllllIIlIlllllllI, llllllllllllllllllllIIlIlllllIIl)) {
                this.setDrag(false);
                return;
            }
        }
        if (RenderUtils.hover(this.x, this.y, llllllllllllllllllllIIlIlllllIll, llllllllllllllllllllIIlIlllllllI, this.width, this.height) && llllllllllllllllllllIIlIlllllIIl == 0) {
            this.setDrag(true);
            this.setXDrag(this.getX() - llllllllllllllllllllIIlIlllllIll);
            this.setYDrag(this.getY() - llllllllllllllllllllIIlIlllllllI);
        }
        else {
            this.setDrag(false);
        }
    }
    
    public void drawScreen(final int llllllllllllllllllllIIllIIIlIIlI, final int llllllllllllllllllllIIllIIIlIIIl) {
        final AtomicInteger llllllllllllllllllllIIllIIIlIIII = new AtomicInteger();
        this.modules.forEach(llllllllllllllllllllIIlIlIlIllll -> llllllllllllllllllllIIllIIIlIIII.addAndGet(llllllllllllllllllllIIlIlIlIllll.getOffset()));
        this.height = Math.min(20 + llllllllllllllllllllIIllIIIlIIII.get(), 300);
        if (Mouse.hasWheel() && RenderUtils.hover(this.x, this.y, llllllllllllllllllllIIllIIIlIIlI, llllllllllllllllllllIIllIIIlIIIl, 125, this.height)) {
            final int llllllllllllllllllllIIllIIIlIlIl = Mouse.getDWheel();
            if (llllllllllllllllllllIIllIIIlIlIl > 0 && this.offset - 14 > 0) {
                this.offset -= 15;
            }
            else if (llllllllllllllllllllIIllIIIlIlIl < 0 && this.offset + 14 <= llllllllllllllllllllIIllIIIlIIII.get() - this.height + 20) {
                this.offset += 15;
            }
        }
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.getHeight(), FrameCategory.mainColor);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.width, this.getY() + 20, FrameCategory.darkerMainColor);
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + this.getHeight(), FrameCategory.darkerMainColor);
        Gui.drawRect(this.getX() + this.width, this.getY(), this.getX() + this.width + 1, this.getY() + this.getHeight(), FrameCategory.darkerMainColor);
        Gui.drawRect(this.getX() - 1, this.y + this.getHeight(), this.getX() + this.width + 1, this.getY() + this.getHeight() + 1, FrameCategory.darkerMainColor);
        if (this.drag) {
            this.setX(this.xDrag + llllllllllllllllllllIIllIIIlIIlI);
            this.setY(this.yDrag + llllllllllllllllllllIIllIIIlIIIl);
        }
        Minecraft.getMinecraft().fontRendererObj.drawString(this.category.getName(), this.x + 3, (int)(this.y + (10.0f - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f)), -1);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtils.prepareScissorBox(this.getX() + this.width / 2.0f - this.animation.update().getValue(), (float)(this.getY() + 20), this.x + this.width / 2.0f + this.animation.getValue(), (float)(this.y + this.getHeight()));
        int llllllllllllllllllllIIllIIIIllll = 0;
        for (final FrameModule llllllllllllllllllllIIllIIIlIlII : this.modules) {
            llllllllllllllllllllIIllIIIlIlII.setX(this.x);
            llllllllllllllllllllIIllIIIlIlII.setY(this.y + 20 + llllllllllllllllllllIIllIIIIllll - this.offset);
            llllllllllllllllllllIIllIIIlIlII.drawScreen(llllllllllllllllllllIIllIIIlIIlI, llllllllllllllllllllIIllIIIlIIIl);
            llllllllllllllllllllIIllIIIIllll += llllllllllllllllllllIIllIIIlIlII.getOffset();
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
}
