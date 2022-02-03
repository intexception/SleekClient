package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import me.kansio.client.config.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.*;
import me.kansio.client.gui.clickgui.utils.render.animation.easings.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.*;
import me.kansio.client.gui.notification.*;
import net.minecraft.client.*;
import me.kansio.client.modules.impl.visuals.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class DeleteButton extends ConfigComponent implements Priority
{
    private final /* synthetic */ Animate animation;
    private /* synthetic */ Config config;
    
    @Override
    public void initGui() {
    }
    
    @Override
    public void keyTyped(final char lIIIlIIllIll, final int lIIIlIIllIlI) {
    }
    
    public DeleteButton(final int lIIIlIlllIIl, final int lIIIlIllllII, final FrameConfig lIIIlIlllIll) {
        super(lIIIlIlllIIl, lIIIlIllllII, lIIIlIlllIll);
        this.config = lIIIlIlllIll.config;
        this.animation = new Animate().setMin(0.0f).setMax(5.0f).setSpeed(15.0f).setEase(Easing.LINEAR).setReversed(true);
    }
    
    @Override
    public void onGuiClosed(final int lIIIlIIlllll, final int lIIIlIIllllI, final int lIIIlIIlllIl) {
    }
    
    @Override
    public boolean mouseClicked(final int lIIIlIlIIIll, final int lIIIlIlIIllI, final int lIIIlIlIIlIl) {
        if (RenderUtils.hover(this.x, this.y, lIIIlIlIIIll, lIIIlIlIIllI, 125, this.getOffset())) {
            Client.getInstance().getConfigManager().removeConfig(this.config.getName());
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Deleted config", "Successfully deleted", 1));
            Minecraft.getMinecraft().thePlayer.closeScreen();
            final ClickGUI lIIIlIlIlIIl = (ClickGUI)Client.getInstance().getModuleManager().getModuleByName("Click GUI");
            lIIIlIlIlIIl.toggle();
            return true;
        }
        return false;
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
    
    @Override
    public void drawScreen(final int lIIIlIllIIlI, final int lIIIlIllIIIl) {
        this.animation.update();
        final FontRenderer lIIIlIllIIII = Minecraft.getMinecraft().fontRendererObj;
        lIIIlIllIIII.drawString("Delete Config", (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lIIIlIllIIII.FONT_HEIGHT / 2.0f), -1, true);
        RenderUtils.drawFilledCircle(this.x + 125 - 10, (int)(this.y + (this.getOffset() / 2.0f - lIIIlIllIIII.FONT_HEIGHT / 2.0f) + 6.75f), 5.0f, new Color(DeleteButton.darkerMainColor));
        if (this.animation.getValue() != 0.0f) {
            RenderUtils.drawFilledCircle(this.x + 125 - 10, (int)(this.y + (this.getOffset() / 2.0f - lIIIlIllIIII.FONT_HEIGHT / 2.0f) + 6.75f), this.animation.getValue(), new Color(DeleteButton.enabledColor));
            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
