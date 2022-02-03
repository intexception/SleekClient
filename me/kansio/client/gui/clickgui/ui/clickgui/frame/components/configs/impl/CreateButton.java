package me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.impl;

import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.components.configs.*;
import me.kansio.client.utils.render.*;
import java.math.*;
import me.kansio.client.*;
import me.kansio.client.gui.notification.*;
import me.kansio.client.modules.impl.visuals.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class CreateButton extends ConfigComponent implements Priority
{
    private /* synthetic */ String tempName;
    private /* synthetic */ FrameConfig frame;
    private /* synthetic */ boolean typing;
    
    public CreateButton(final int lllllllllllllllllllllIlIIllIlIll, final int lllllllllllllllllllllIlIIllIlIlI, final FrameConfig lllllllllllllllllllllIlIIllIlIIl) {
        super(lllllllllllllllllllllIlIIllIlIll, lllllllllllllllllllllIlIIllIlIlI, lllllllllllllllllllllIlIIllIlIIl);
        this.frame = lllllllllllllllllllllIlIIllIlIIl;
        this.tempName = "";
    }
    
    @Override
    public boolean mouseClicked(final int lllllllllllllllllllllIlIIIIllllI, final int lllllllllllllllllllllIlIIIIlIlll, final int lllllllllllllllllllllIlIIIIlIllI) {
        if (RenderUtils.hover(this.x, this.y, lllllllllllllllllllllIlIIIIllllI, lllllllllllllllllllllIlIIIIlIlll, 125, this.getOffset()) && lllllllllllllllllllllIlIIIIlIllI == 0) {
            this.typing = !this.typing;
        }
        return RenderUtils.hover(this.x, this.y, lllllllllllllllllllllIlIIIIllllI, lllllllllllllllllllllIlIIIIlIlll, 125, this.getOffset()) && lllllllllllllllllllllIlIIIIlIllI == 0;
    }
    
    private double roundToPlace(final double lllllllllllllllllllllIlIIIlllllI, final int lllllllllllllllllllllIlIIIlllIII) {
        if (lllllllllllllllllllllIlIIIlllIII < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal lllllllllllllllllllllIlIIIlllIll = new BigDecimal(lllllllllllllllllllllIlIIIlllllI);
        lllllllllllllllllllllIlIIIlllIll = lllllllllllllllllllllIlIIIlllIll.setScale(lllllllllllllllllllllIlIIIlllIII, RoundingMode.HALF_UP);
        return lllllllllllllllllllllIlIIIlllIll.doubleValue();
    }
    
    @Override
    public void onGuiClosed(final int lllllllllllllllllllllIlIIIIlIlII, final int lllllllllllllllllllllIlIIIIlIIll, final int lllllllllllllllllllllIlIIIIlIIlI) {
    }
    
    @Override
    public int getOffset() {
        return 15;
    }
    
    @Override
    public void keyTyped(final char lllllllllllllllllllllIlIIIIIIIII, final int lllllllllllllllllllllIIllllllIll) {
        if (lllllllllllllllllllllIIllllllIll == 54 || lllllllllllllllllllllIIllllllIll == 42 || lllllllllllllllllllllIIllllllIll == 58) {
            return;
        }
        if (lllllllllllllllllllllIIllllllIll == 28) {
            this.typing = false;
            if (this.tempName.equalsIgnoreCase("")) {
                return;
            }
            Client.getInstance().getConfigManager().saveConfig(this.tempName);
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Configs", "Successfully created config", 1));
            final ClickGUI lllllllllllllllllllllIlIIIIIIIll = (ClickGUI)Client.getInstance().getModuleManager().getModuleByName("Click GUI");
            if (Minecraft.getMinecraft().currentScreen != null) {
                Minecraft.getMinecraft().thePlayer.closeScreen();
                lllllllllllllllllllllIlIIIIIIIll.toggle();
            }
        }
        else if (lllllllllllllllllllllIIllllllIll == 14 && this.typing) {
            if (this.tempName.toCharArray().length == 0) {
                return;
            }
            this.tempName = this.removeLastChar(this.tempName);
        }
        else {
            final List<Character> lllllllllllllllllllllIIllllllllI = Arrays.asList('&', ' ', '[', ']', '(', ')', '.', ',', '<', '>', '-', '$', '!', '\"', '\'', '\\', '/', '=', '+', ',', '|', '^', '?', '`', ';', ':', '@', '£', '%', '{', '}', '_', '*', '»');
            for (final char lllllllllllllllllllllIlIIIIIIIlI : lllllllllllllllllllllIIllllllllI) {
                if (lllllllllllllllllllllIlIIIIIIIII == lllllllllllllllllllllIlIIIIIIIlI && this.typing) {
                    this.tempName = String.valueOf(new StringBuilder().append(this.tempName).append(lllllllllllllllllllllIlIIIIIIIII));
                    return;
                }
            }
            if (!Character.isLetterOrDigit(lllllllllllllllllllllIlIIIIIIIII)) {
                return;
            }
            if (this.typing) {
                this.tempName = String.valueOf(new StringBuilder().append(this.tempName).append(lllllllllllllllllllllIlIIIIIIIII));
            }
        }
    }
    
    @Override
    public void initGui() {
        this.typing = false;
    }
    
    private String removeLastChar(final String lllllllllllllllllllllIIlllllIlII) {
        return lllllllllllllllllllllIIlllllIlII.substring(0, lllllllllllllllllllllIIlllllIlII.length() - 1);
    }
    
    @Override
    public void drawScreen(final int lllllllllllllllllllllIlIIlIlIIIl, final int lllllllllllllllllllllIlIIlIIllll) {
        final FontRenderer lllllllllllllllllllllIlIIlIIllIl = Minecraft.getMinecraft().fontRendererObj;
        lllllllllllllllllllllIlIIlIIllIl.drawString(String.valueOf(new StringBuilder().append("» Create: ").append(this.tempName)), (float)(this.x + 5), this.y + (this.getOffset() / 2.0f - lllllllllllllllllllllIlIIlIIllIl.FONT_HEIGHT / 2.0f), -1, true);
        if (this.typing) {
            Gui.drawRect(this.x + 125, this.y + (this.getOffset() / 2.0f - lllllllllllllllllllllIlIIlIIllIl.FONT_HEIGHT / 2.0f) + 9.0f, 117.0, this.y + (this.getOffset() / 2.0f - lllllllllllllllllllllIlIIlIIllIl.FONT_HEIGHT / 2.0f) + 7.0f + 4.0f, -1);
        }
    }
}
