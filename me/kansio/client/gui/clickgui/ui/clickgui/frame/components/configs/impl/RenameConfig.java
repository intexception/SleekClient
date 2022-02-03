package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.math.*;
import org.apache.commons.lang3.*;
import me.kansio.client.*;
import me.kansio.client.gui.notification.*;
import me.kansio.client.modules.impl.visuals.*;
import java.util.*;

public class RenameConfig extends ConfigComponent implements Priority
{
    private /* synthetic */ FrameConfig frame;
    private /* synthetic */ String tempName;
    private /* synthetic */ boolean typing;
    
    @Override
    public boolean mouseClicked(final int lIlIIIIllIlll, final int lIlIIIIllIIlI, final int lIlIIIIllIIIl) {
        if (RenderUtils.hover(this.x, this.y, lIlIIIIllIlll, lIlIIIIllIIlI, 125, this.getOffset()) && lIlIIIIllIIIl == 0) {
            this.typing = !this.typing;
        }
        return RenderUtils.hover(this.x, this.y, lIlIIIIllIlll, lIlIIIIllIIlI, 125, this.getOffset()) && lIlIIIIllIIIl == 0;
    }
    
    @Override
    public void drawScreen(final int lIlIIIlIIlIll, final int lIlIIIlIIlIlI) {
        final FontRenderer lIlIIIlIIlIIl = Minecraft.getMinecraft().fontRendererObj;
        lIlIIIlIIlIIl.drawString(String.valueOf(new StringBuilder().append("Change name: ").append(this.tempName)), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lIlIIIlIIlIIl.FONT_HEIGHT / 2.0f), -1, true);
        if (this.typing) {
            Gui.drawRect(this.x + 125, this.y + (this.getOffset() / 2.0f - lIlIIIlIIlIIl.FONT_HEIGHT / 2.0f) + 9.0f, 117.0, this.y + (this.getOffset() / 2.0f - lIlIIIlIIlIIl.FONT_HEIGHT / 2.0f) + 7.0f + 4.0f, -1);
        }
    }
    
    private double roundToPlace(final double lIlIIIIllllll, final int lIlIIIIlllllI) {
        if (lIlIIIIlllllI < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal lIlIIIlIIIIII = new BigDecimal(lIlIIIIllllll);
        lIlIIIlIIIIII = lIlIIIlIIIIII.setScale(lIlIIIIlllllI, RoundingMode.HALF_UP);
        return lIlIIIlIIIIII.doubleValue();
    }
    
    @Override
    public void keyTyped(final char lIlIIIIIllllI, final int lIlIIIIIlllIl) {
        if (lIlIIIIIlllIl == 54 || lIlIIIIIlllIl == 42 || lIlIIIIIlllIl == 58) {
            return;
        }
        if (lIlIIIIIlllIl == 28) {
            this.typing = false;
            final String lIlIIIIlIIllI = RandomStringUtils.randomAlphanumeric(12);
            Client.getInstance().getConfigManager().saveConfig(lIlIIIIlIIllI);
            Client.getInstance().getConfigManager().loadConfig(this.frame.config.getName(), false);
            Client.getInstance().getConfigManager().saveConfig(this.tempName);
            Client.getInstance().getConfigManager().loadConfig(lIlIIIIlIIllI, false);
            Client.getInstance().getConfigManager().removeConfig(this.frame.config.getName());
            Client.getInstance().getConfigManager().removeConfig(lIlIIIIlIIllI);
            Client.getInstance().getConfigManager().loadConfigs();
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Configs", "Successfully renamed config", 1));
            Minecraft.getMinecraft().thePlayer.closeScreen();
            final ClickGUI lIlIIIIlIIlIl = (ClickGUI)Client.getInstance().getModuleManager().getModuleByName("Click GUI");
            lIlIIIIlIIlIl.toggle();
            return;
        }
        if (lIlIIIIIlllIl == 14 && this.typing) {
            if (this.tempName.toCharArray().length == 0) {
                return;
            }
            this.tempName = this.removeLastChar(this.tempName);
        }
        else {
            final List<Character> lIlIIIIlIIIII = Arrays.asList('&', ' ', '[', ']', '(', ')', '.', ',', '<', '>', '-', '$', '!', '\"', '\'', '\\', '/', '=', '+', ',', '|', '^', '?', '`', ';', ':', '@', '£', '%', '{', '}', '_', '*', '»');
            for (final char lIlIIIIlIIlII : lIlIIIIlIIIII) {
                if (lIlIIIIIllllI == lIlIIIIlIIlII && this.typing) {
                    this.tempName = String.valueOf(new StringBuilder().append(this.tempName).append(lIlIIIIIllllI));
                    return;
                }
            }
            if (!Character.isLetterOrDigit(lIlIIIIIllllI)) {
                return;
            }
            if (this.typing) {
                this.tempName = String.valueOf(new StringBuilder().append(this.tempName).append(lIlIIIIIllllI));
            }
        }
    }
    
    @Override
    public void onGuiClosed(final int lIlIIIIlIllll, final int lIlIIIIlIlllI, final int lIlIIIIlIllIl) {
    }
    
    @Override
    public void initGui() {
        this.typing = false;
    }
    
    private String removeLastChar(final String lIlIIIIIlIllI) {
        return lIlIIIIIlIllI.substring(0, lIlIIIIIlIllI.length() - 1);
    }
    
    public RenameConfig(final int lIlIIIlIllIII, final int lIlIIIlIlIlll, final FrameConfig lIlIIIlIlIllI) {
        super(lIlIIIlIllIII, lIlIIIlIlIlll, lIlIIIlIlIllI);
        this.frame = lIlIIIlIlIllI;
        this.tempName = this.frame.config.getName();
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
}
