package me.kansio.client.gui.clickgui.ui.clickgui.frame.components;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import java.util.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;
import me.kansio.client.*;
import me.kansio.client.value.value.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.impl.*;
import me.kansio.client.value.*;
import org.lwjgl.input.*;
import me.kansio.client.utils.chat.*;
import java.awt.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class FrameModule implements Priority
{
    private final /* synthetic */ ArrayList<Component> components;
    private final /* synthetic */ Animate moduleAnimation;
    private final /* synthetic */ FrameCategory owner;
    private /* synthetic */ int x;
    private /* synthetic */ boolean listening;
    private /* synthetic */ int y;
    private /* synthetic */ int offset;
    private /* synthetic */ boolean opened;
    private final /* synthetic */ Module module;
    
    public int getOffset() {
        this.offset = 0;
        if (this.opened) {
            for (final Component llIIIIllIllIII : this.components) {
                if (llIIIIllIllIII.isHidden()) {
                    continue;
                }
                this.offset += llIIIIllIllIII.getOffset();
            }
        }
        this.setOffset(15 + this.offset);
        return this.offset;
    }
    
    public FrameModule(final Module llIIIlIIIlIIIl, final FrameCategory llIIIlIIIlIlIl, final int llIIIlIIIIllll, final int llIIIlIIIlIIll) {
        this.module = llIIIlIIIlIIIl;
        this.components = new ArrayList<Component>();
        this.owner = llIIIlIIIlIlIl;
        this.moduleAnimation = new Animate();
        this.moduleAnimation.setMin(0.0f).setMax(255.0f).setReversed(!llIIIlIIIlIIIl.isToggled()).setEase(Easing.LINEAR);
        this.opened = false;
        this.listening = false;
        this.x = llIIIlIIIIllll;
        this.y = llIIIlIIIlIIll;
        Client.getInstance().getValueManager().getValuesFromOwner(llIIIlIIIlIIIl).forEach(llIIIIlIlIlllI -> {
            if (llIIIIlIlIlllI instanceof BooleanValue) {
                this.components.add(new BoolSetting(0, 0, this, llIIIIlIlIlllI));
            }
            if (llIIIIlIlIlllI instanceof ModeValue) {
                this.components.add(new EnumSetting(0, 0, this, llIIIIlIlIlllI));
            }
            if (llIIIIlIlIlllI instanceof NumberValue) {
                this.components.add(new SlideSetting(0, 0, this, llIIIIlIlIlllI));
            }
            if (llIIIIlIlIlllI instanceof StringValue) {
                this.components.add(new StringSetting(0, 0, this, llIIIIlIlIlllI));
            }
        });
    }
    
    public void keyTyped(final char llIIIIllIIlIIl, final int llIIIIllIIlIll) {
        for (final Component llIIIIllIIlllI : this.components) {
            if (llIIIIllIIlllI.isHidden()) {
                continue;
            }
            llIIIIllIIlllI.keyTyped(llIIIIllIIlIIl, llIIIIllIIlIll);
        }
        if (this.listening) {
            if (llIIIIllIIlIll == 1) {
                this.module.setKeyBind(0);
            }
            else {
                this.module.setKeyBind(llIIIIllIIlIll, true);
                ChatUtil.log(String.valueOf(new StringBuilder().append(this.module.getName()).append(" is now binded to ").append(Keyboard.getKeyName(llIIIIllIIlIll))));
                this.listening = false;
            }
        }
    }
    
    public void drawScreen(final int llIIIIlllllIIl, final int llIIIIlllllIII) {
        this.moduleAnimation.setReversed(!this.module.isToggled());
        this.moduleAnimation.setSpeed(1000.0f).update();
        int llIIIIllllIlll;
        if (((ClickGUI)Client.getInstance().getModuleManager().getModuleByName("Click GUI")).hudcolor.getValue()) {
            Color llIIIlIIIIIIIl = ColorUtils.getGradientOffset(new Color(0, 255, 128), new Color(212, 1, 1), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + this.y / Fonts.Verdana.getHeight() * 9.95);
            final float llIIIIlllIllll = ((Value<Float>)((HUD)Client.getInstance().getModuleManager().getModuleByName("HUD")).getColorMode()).getValue();
            double llIIIIlllIlllI = -1;
            switch (((String)llIIIIlllIllll).hashCode()) {
                case 79969970: {
                    if (((String)llIIIIlllIllll).equals("Sleek")) {
                        llIIIIlllIlllI = 0;
                        break;
                    }
                    break;
                }
                case 628530586: {
                    if (((String)llIIIIlllIllll).equals("Nitrogen")) {
                        llIIIIlllIlllI = 1;
                        break;
                    }
                    break;
                }
                case -1656737386: {
                    if (((String)llIIIIlllIllll).equals("Rainbow")) {
                        llIIIIlllIlllI = 2;
                        break;
                    }
                    break;
                }
                case 961091784: {
                    if (((String)llIIIIlllIllll).equals("Astolfo")) {
                        llIIIIlllIlllI = 3;
                        break;
                    }
                    break;
                }
            }
            switch (llIIIIlllIlllI) {
                case 0.0: {
                    llIIIlIIIIIIIl = ColorUtils.getGradientOffset(new Color(0, 255, 128), new Color(212, 1, 1), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + this.y / Fonts.Verdana.getHeight() * 9.95);
                    break;
                }
                case 1.0: {
                    llIIIlIIIIIIIl = ColorUtils.getGradientOffset(new Color(128, 171, 255), new Color(160, 72, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0);
                    break;
                }
                case 2.0: {
                    llIIIlIIIIIIIl = new Color(ColorUtils.getRainbow(6000, 0));
                    break;
                }
                case 3.0: {
                    llIIIlIIIIIIIl = ColorUtils.getGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0);
                    break;
                }
            }
            final int llIIIlIIIIIIII = llIIIlIIIIIIIl.getRGB();
            final int llIIIIllllllll = new Color(llIIIlIIIIIIII).darker().getRGB();
        }
        else {
            llIIIIllllIlll = FrameModule.enabledColor;
        }
        if (RenderUtils.hover(this.x, this.y, llIIIIlllllIIl, llIIIIlllllIII, 125, 15)) {}
        if (this.module.isToggled() || (this.moduleAnimation.isReversed() && this.moduleAnimation.getValue() != 0.0f)) {
            Gui.drawRect(this.x, this.y, this.x + 125, this.y + 15, ColorUtils.setAlpha(new Color(llIIIIllllIlll), (int)this.moduleAnimation.getValue()).getRGB());
        }
        Minecraft.getMinecraft().fontRendererObj.drawString(this.listening ? "Press new keybind" : this.module.getName(), (float)(this.x + 3), this.y + (7.5f - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f), -1, true);
        if (!this.module.getValues().isEmpty()) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.opened ? "-" : "+", (float)(this.x + this.owner.getWidth() - 9), this.y + (7.5f - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f), -1, true);
        }
        int llIIIIllllIllI = 0;
        if (this.opened) {
            for (final Component llIIIIlllllIll : this.components) {
                boolean llIIIIllllllII = false;
                if (llIIIIlllllIll.getSetting().hasParent() && llIIIIlllllIll.getSetting().getParent() != null) {
                    if ((llIIIIlllllIll.getSetting().getParent() instanceof ModeValue && llIIIIlllllIll.getSetting().getModes().contains(llIIIIlllllIll.getSetting().getParent().getValue())) || (llIIIIlllllIll.getSetting().getParent() instanceof BooleanValue && ((BooleanValue)llIIIIlllllIll.getSetting().getParent()).getValue())) {
                        final boolean llIIIIlllllllI = false;
                    }
                    else {
                        final boolean llIIIIllllllIl = true;
                    }
                }
                else {
                    llIIIIllllllII = false;
                }
                llIIIIlllllIll.setHidden(llIIIIllllllII);
                if (llIIIIlllllIll.isHidden()) {
                    continue;
                }
                llIIIIlllllIll.setX(this.x);
                llIIIIlllllIll.setY(this.y + 15 + llIIIIllllIllI);
                llIIIIlllllIll.drawScreen(llIIIIlllllIIl, llIIIIlllllIII);
                llIIIIllllIllI += llIIIIlllllIll.getOffset();
            }
        }
        this.setOffset(15 + llIIIIllllIllI);
    }
    
    public void setOffset(final int llIIIIllIIIIII) {
        this.offset = llIIIIllIIIIII;
    }
    
    public void setX(final int llIIIIlIlllIlI) {
        this.x = llIIIIlIlllIlI;
    }
    
    public boolean mouseClicked(final int llIIIIlllIIlII, final int llIIIIlllIIIll, final int llIIIIlllIIIlI) {
        if (RenderUtils.hover(this.x, this.y, llIIIIlllIIlII, llIIIIlllIIIll, 125, 15) && RenderUtils.hover(this.owner.getX(), this.owner.getY(), llIIIIlllIIlII, llIIIIlllIIIll, 125, this.owner.getHeight())) {
            switch (llIIIIlllIIIlI) {
                case 0: {
                    this.module.toggle();
                    break;
                }
                case 1: {
                    if (this.opened) {
                        this.opened = false;
                        this.offset = 200;
                        break;
                    }
                    this.opened = true;
                    break;
                }
                case 2: {
                    this.listening = true;
                    break;
                }
            }
            return true;
        }
        if (RenderUtils.hover(this.owner.getX(), this.owner.getY(), llIIIIlllIIlII, llIIIIlllIIIll, 125, this.owner.getHeight()) && this.opened) {
            for (final Component llIIIIlllIIllI : this.components) {
                if (llIIIIlllIIllI.isHidden()) {
                    continue;
                }
                if (llIIIIlllIIllI.mouseClicked(llIIIIlllIIlII, llIIIIlllIIIll, llIIIIlllIIIlI)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setY(final int llIIIIlIllIllI) {
        this.y = llIIIIlIllIllI;
    }
}
