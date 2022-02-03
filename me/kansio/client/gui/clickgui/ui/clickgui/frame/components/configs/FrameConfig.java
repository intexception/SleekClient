package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import me.kansio.client.config.*;
import java.util.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.impl.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.*;
import me.kansio.client.*;

public class FrameConfig implements Priority
{
    private /* synthetic */ int y;
    private /* synthetic */ int offset;
    private final /* synthetic */ ConfigCategory owner;
    private /* synthetic */ boolean opened;
    public final /* synthetic */ Config config;
    private /* synthetic */ boolean listening;
    private /* synthetic */ int x;
    private final /* synthetic */ Animate moduleAnimation;
    private final /* synthetic */ ArrayList<ConfigComponent> components;
    
    public void setY(final int lIIIIIlIlIIllI) {
        this.y = lIIIIIlIlIIllI;
    }
    
    public void setX(final int lIIIIIlIlIlIlI) {
        this.x = lIIIIIlIlIlIlI;
    }
    
    public int getOffset() {
        this.offset = 0;
        if (this.opened) {
            for (final ConfigComponent lIIIIIllIIlIII : this.components) {
                if (lIIIIIllIIlIII.isHidden()) {
                    continue;
                }
                this.offset += lIIIIIllIIlIII.getOffset();
            }
        }
        this.setOffset(15 + this.offset);
        return this.offset;
    }
    
    public FrameConfig(final Config lIIIIIllllIIIl, final ConfigCategory lIIIIIllllIlIl, final int lIIIIIllllIlII, final int lIIIIIlllIlllI) {
        this.config = lIIIIIllllIIIl;
        this.components = new ArrayList<ConfigComponent>();
        this.owner = lIIIIIllllIlIl;
        this.moduleAnimation = new Animate();
        this.moduleAnimation.setMin(0.0f).setMax(255.0f).setReversed(false).setEase(Easing.LINEAR);
        this.opened = (this.config == null);
        this.listening = false;
        this.x = lIIIIIllllIlII;
        this.y = lIIIIIlllIlllI;
        if (this.config != null) {
            this.components.add(new DeleteButton(0, 0, this));
            this.components.add(new RenameConfig(0, 0, this));
        }
        else {
            this.components.add(new CreateButton(0, 0, this));
        }
    }
    
    public void keyTyped(final char lIIIIIlIllllII, final int lIIIIIlIlllIll) {
        for (final ConfigComponent lIIIIIlIlllllI : this.components) {
            if (lIIIIIlIlllllI.isHidden()) {
                continue;
            }
            if (this.config == null) {
                lIIIIIlIlllllI.keyTyped(lIIIIIlIllllII, lIIIIIlIlllIll);
                return;
            }
            if (!this.opened) {
                continue;
            }
            lIIIIIlIlllllI.keyTyped(lIIIIIlIllllII, lIIIIIlIlllIll);
        }
    }
    
    public void drawScreen(final int lIIIIIlllIIIIl, final int lIIIIIlllIIIII) {
        if (RenderUtils.hover(this.x, this.y, lIIIIIlllIIIIl, lIIIIIlllIIIII, 125, 15)) {}
        Minecraft.getMinecraft().fontRendererObj.drawString((this.config != null) ? this.config.getName() : "Options:", (float)(this.x + 3), this.y + (7.5f - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f), -1, true);
        int lIIIIIlllIIIll = 0;
        if (this.opened) {
            for (final ConfigComponent lIIIIIlllIIlll : this.components) {
                lIIIIIlllIIlll.setHidden(false);
                if (lIIIIIlllIIlll.isHidden()) {
                    continue;
                }
                lIIIIIlllIIlll.setX(this.x);
                lIIIIIlllIIlll.setY(this.y + 15 + lIIIIIlllIIIll);
                lIIIIIlllIIlll.drawScreen(lIIIIIlllIIIIl, lIIIIIlllIIIII);
                lIIIIIlllIIIll += lIIIIIlllIIlll.getOffset();
            }
        }
        this.setOffset(15 + lIIIIIlllIIIll);
    }
    
    public void setOffset(final int lIIIIIlIllIIlI) {
        this.offset = lIIIIIlIllIIlI;
    }
    
    public boolean mouseClicked(final int lIIIIIllIlIlII, final int lIIIIIllIlIIll, final int lIIIIIllIIlllI) {
        if (RenderUtils.hover(this.x, this.y, lIIIIIllIlIlII, lIIIIIllIlIIll, 125, 15) && RenderUtils.hover(this.owner.getX(), this.owner.getY(), lIIIIIllIlIlII, lIIIIIllIlIIll, 125, this.owner.getHeight())) {
            if (lIIIIIllIIlllI == 0 && this.config != null) {
                if (this.config.getName().equalsIgnoreCase("")) {
                    return true;
                }
                Client.getInstance().getConfigManager().loadConfig(this.config.getName(), false);
            }
            if (this.config != null) {
                if (this.config.getName().equalsIgnoreCase("")) {
                    return true;
                }
                if (lIIIIIllIIlllI == 1) {
                    this.opened = !this.opened;
                }
            }
            return true;
        }
        if (RenderUtils.hover(this.owner.getX(), this.owner.getY(), lIIIIIllIlIlII, lIIIIIllIlIIll, 125, this.owner.getHeight()) && this.opened) {
            for (final ConfigComponent lIIIIIllIlIllI : this.components) {
                if (lIIIIIllIlIllI.isHidden()) {
                    continue;
                }
                if (!this.opened) {
                    continue;
                }
                if (lIIIIIllIlIllI.mouseClicked(lIIIIIllIlIlII, lIIIIIllIlIIll, lIIIIIllIIlllI)) {
                    return true;
                }
            }
        }
        return false;
    }
}
