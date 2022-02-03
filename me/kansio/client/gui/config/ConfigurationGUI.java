package me.kansio.client.gui.config;

import me.kansio.client.config.*;
import me.kansio.client.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.gui.notification.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import me.kansio.client.utils.font.*;

public class ConfigurationGUI extends GuiScreen
{
    private /* synthetic */ Config selectedConfig;
    private /* synthetic */ boolean typing;
    private final /* synthetic */ ArrayList<Config> listedConfigs;
    private /* synthetic */ String name;
    private /* synthetic */ int page;
    
    private String removeLastChar(final String llllllllllllllllllllIIIllllIIIll) {
        return llllllllllllllllllllIIIllllIIIll.substring(0, llllllllllllllllllllIIIllllIIIll.length() - 1);
    }
    
    @Override
    public void initGui() {
        this.listedConfigs.clear();
        int llllllllllllllllllllIIIlllIllIll = 0;
        for (final Config llllllllllllllllllllIIIlllIlllIl : Client.getInstance().getConfigManager().getConfigs()) {
            if (++llllllllllllllllllllIIIlllIllIll > 7) {
                continue;
            }
            this.listedConfigs.add(llllllllllllllllllllIIIlllIlllIl);
        }
        super.initGui();
    }
    
    @Override
    protected void mouseClicked(final int llllllllllllllllllllIIIllIllllII, final int llllllllllllllllllllIIIllIllIlIl, final int llllllllllllllllllllIIIllIllIlII) throws IOException {
        final ScaledResolution llllllllllllllllllllIIIllIlllIIl = RenderUtils.getResolution();
        if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 10, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 - 80, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 125, 16)) {
            this.typing = !this.typing;
            return;
        }
        if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 10, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 + 38, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 14, 13)) {
            if (this.page != 0) {
                --this.page;
            }
            this.loadConfigs(this.page);
            return;
        }
        if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 30, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 + 38, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 14, 13)) {
            ++this.page;
            this.loadConfigs(this.page);
            return;
        }
        if (this.selectedConfig != null) {
            if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 10, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 + 88, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 50, 13)) {
                Client.getInstance().getConfigManager().loadConfig(this.selectedConfig.getName(), false);
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Success!", "Loaded the config", 5));
                this.loadConfigs(this.page);
                return;
            }
            if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 80, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 + 88, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 50, 13)) {
                Client.getInstance().getConfigManager().removeConfig(this.selectedConfig.getName());
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Success!", "Removed the config", 5));
                this.loadConfigs(this.page);
                return;
            }
        }
        if (!RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 + 10, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 - 38, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 50, 13)) {
            int llllllllllllllllllllIIIllIlllIII = 4;
            for (int llllllllllllllllllllIIIllIlllllI = 0; llllllllllllllllllllIIIllIlllllI < 7; ++llllllllllllllllllllIIIllIlllllI) {
                if (RenderUtils.hover(llllllllllllllllllllIIIllIlllIIl.getScaledWidth() / 2 - 140, llllllllllllllllllllIIIllIlllIIl.getScaledHeight() / 2 - 92 + llllllllllllllllllllIIIllIlllIII, llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, 135, 25)) {
                    try {
                        this.selectedConfig = this.listedConfigs.get(llllllllllllllllllllIIIllIlllllI);
                    }
                    catch (Exception ex) {}
                    return;
                }
                llllllllllllllllllllIIIllIlllIII += 28;
            }
            super.mouseClicked(llllllllllllllllllllIIIllIllllII, llllllllllllllllllllIIIllIllIlIl, llllllllllllllllllllIIIllIllIlII);
            return;
        }
        if (this.name.equalsIgnoreCase("")) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error!", "Name the config", 5));
            return;
        }
        if (this.name.startsWith(" ")) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error!", "Invalid first char", 5));
            return;
        }
        this.typing = false;
        Client.getInstance().getConfigManager().saveConfig(this.name);
        NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Success!", "Created the config", 5));
        this.name = "";
    }
    
    @Override
    protected void mouseReleased(final int llllllllllllllllllllIIIllIlIIllI, final int llllllllllllllllllllIIIllIlIlIIl, final int llllllllllllllllllllIIIllIlIIlII) {
        super.mouseReleased(llllllllllllllllllllIIIllIlIIllI, llllllllllllllllllllIIIllIlIlIIl, llllllllllllllllllllIIIllIlIIlII);
    }
    
    @Override
    protected void keyTyped(final char llllllllllllllllllllIIIllllIllIl, final int llllllllllllllllllllIIIllllIllII) throws IOException {
        if (llllllllllllllllllllIIIllllIllII != 1) {
            if (llllllllllllllllllllIIIllllIllII == 28) {
                this.typing = false;
                return;
            }
            if (this.typing) {
                if (llllllllllllllllllllIIIllllIllII == 14) {
                    if (this.name.toCharArray().length == 0) {
                        return;
                    }
                    this.name = this.removeLastChar(this.name);
                    return;
                }
                else {
                    final List<Character> llllllllllllllllllllIIIllllIllll = Arrays.asList('&', ' ', '[', ']', '(', ')', '.', ',', '<', '>', '-', '$', '!', '\"', '\'', '\\', '/', '=', '+', ',', '|', '^', '?', '`', ';', ':', '@', '£', '%', '{', '}', '_', '*', '»');
                    for (final char llllllllllllllllllllIIIlllllIIII : llllllllllllllllllllIIIllllIllll) {
                        if (llllllllllllllllllllIIIllllIllIl == llllllllllllllllllllIIIlllllIIII) {
                            this.name = String.valueOf(new StringBuilder().append(this.name).append(llllllllllllllllllllIIIllllIllIl));
                            return;
                        }
                    }
                    if (!Character.isLetterOrDigit(llllllllllllllllllllIIIllllIllIl)) {
                        return;
                    }
                    if (this.typing) {
                        this.name = String.valueOf(new StringBuilder().append(this.name).append(llllllllllllllllllllIIIllllIllIl));
                    }
                }
            }
        }
        super.keyTyped(llllllllllllllllllllIIIllllIllIl, llllllllllllllllllllIIIllllIllII);
    }
    
    @Override
    public void drawScreen(final int llllllllllllllllllllIIIlllllllIl, final int llllllllllllllllllllIIIlllllllII, final float llllllllllllllllllllIIlIIIIIIIIl) {
        final ScaledResolution llllllllllllllllllllIIlIIIIIIIII = RenderUtils.getResolution();
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 150, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 100, 300.0, 220.0, 3.0, new Color(33, 33, 33, 255).getRGB());
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 148, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 98, 296.0, 216.0, 3.0, new Color(38, 38, 38, 255).getRGB());
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 140, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 90, 135.0, 200.0, 3.0, new Color(45, 45, 45, 255).getRGB());
        int llllllllllllllllllllIIIlllllllll = 5;
        for (final Config llllllllllllllllllllIIlIIIIIIlIl : this.listedConfigs) {
            RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 140, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 92 + llllllllllllllllllllIIIlllllllll, 135.0, 25.0, 3.0, (this.selectedConfig != null && this.selectedConfig == llllllllllllllllllllIIlIIIIIIlIl) ? new Color(198, 96, 234).getRGB() : new Color(57, 57, 57, 255).getRGB());
            this.mc.fontRendererObj.drawString(llllllllllllllllllllIIlIIIIIIlIl.getName().replace("(Verified)", "§a§l\u2714 §f"), llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 138, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 90 + llllllllllllllllllllIIIlllllllll, -1);
            Fonts.UbuntuLight.drawString(String.valueOf(new StringBuilder().append("Created by: ").append(llllllllllllllllllllIIlIIIIIIlIl.getAuthor())), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 138), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 78 + llllllllllllllllllllIIIlllllllll), -1);
            Fonts.UbuntuLight.drawString(String.valueOf(new StringBuilder().append("Updated: ").append(llllllllllllllllllllIIlIIIIIIlIl.getLastUpdated())), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 - 138), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 72 + llllllllllllllllllllIIIlllllllll), -1);
            llllllllllllllllllllIIIlllllllll += 28;
        }
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 5, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 90, 135.0, 75.0, 3.0, new Color(45, 45, 45, 255).getRGB());
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 10, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 80, 125.0, 16.0, 3.0, this.typing ? new Color(66, 66, 66, 255).getRGB() : new Color(56, 56, 56, 255).getRGB());
        if (this.name.equalsIgnoreCase("") && !this.typing) {
            Fonts.Arial12.drawString("Name...", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 13), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 73), new Color(90, 90, 90).getRGB());
        }
        else {
            Fonts.Arial12.drawString(this.name, (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 13), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 73), new Color(255, 255, 255).getRGB());
        }
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 10, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 38, 50.0, 13.0, 3.0, new Color(215, 103, 194, 255).getRGB());
        Fonts.Arial12.drawString("Create config", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 14), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 - 33), -1);
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 5, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 35, 135.0, 75.0, 3.0, new Color(45, 45, 45, 255).getRGB());
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 10, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 38, 14.0, 13.0, 3.0, new Color(215, 103, 194, 255).getRGB());
        Fonts.Arial12.drawString("<-", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 14), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 44), -1);
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 30, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 38, 14.0, 13.0, 3.0, new Color(215, 103, 194, 255).getRGB());
        Fonts.Arial12.drawString("->", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 34), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 44), -1);
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 10, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 88, 50.0, 13.0, 3.0, new Color(215, 103, 194, 255).getRGB());
        Fonts.Arial12.drawString("Load config", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 14), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 94), -1);
        RenderUtils.drawRoundedRect(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 80, llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 88, 50.0, 13.0, 3.0, new Color(215, 103, 194, 255).getRGB());
        Fonts.Arial12.drawString("Delete config", (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledWidth() / 2 + 84), (float)(llllllllllllllllllllIIlIIIIIIIII.getScaledHeight() / 2 + 94), -1);
        super.drawScreen(llllllllllllllllllllIIIlllllllIl, llllllllllllllllllllIIIlllllllII, llllllllllllllllllllIIlIIIIIIIIl);
    }
    
    public ConfigurationGUI() {
        this.listedConfigs = new ArrayList<Config>();
        this.selectedConfig = null;
        this.name = "";
        this.typing = false;
        this.page = 0;
    }
    
    public void loadConfigs(final int llllllllllllllllllllIIIlllIIllIl) {
        this.listedConfigs.clear();
        int llllllllllllllllllllIIIlllIIllII = 0;
        for (int llllllllllllllllllllIIIlllIlIIIl = 0; llllllllllllllllllllIIIlllIlIIIl < llllllllllllllllllllIIIlllIIllIl; ++llllllllllllllllllllIIIlllIlIIIl) {
            llllllllllllllllllllIIIlllIIllII += 7;
        }
        for (int llllllllllllllllllllIIIlllIIllll = llllllllllllllllllllIIIlllIIllII; llllllllllllllllllllIIIlllIIllll < llllllllllllllllllllIIIlllIIllII + 7; ++llllllllllllllllllllIIIlllIIllll) {
            try {
                this.listedConfigs.add(Client.getInstance().getConfigManager().getConfigs().get(llllllllllllllllllllIIIlllIIllll));
            }
            catch (ArrayIndexOutOfBoundsException llllllllllllllllllllIIIlllIlIIII) {
                return;
            }
        }
    }
}
