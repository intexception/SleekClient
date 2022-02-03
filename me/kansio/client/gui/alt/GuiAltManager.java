package me.kansio.client.gui.alt;

import org.lwjgl.input.*;
import java.awt.datatransfer.*;
import org.apache.commons.lang3.*;
import java.awt.*;
import java.net.*;
import me.kansio.client.utils.chat.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public final class GuiAltManager extends GuiScreen
{
    private /* synthetic */ AltLoginThread thread;
    private /* synthetic */ String crackedStatus;
    private /* synthetic */ GuiTextField username;
    private final /* synthetic */ GuiScreen previousScreen;
    private /* synthetic */ GuiTextField password;
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public GuiAltManager(final GuiScreen lIlIIlllIllII) {
        this.previousScreen = lIlIIlllIllII;
    }
    
    @Override
    protected void actionPerformed(final GuiButton lIlIIlllIIIII) {
        try {
            switch (lIlIIlllIIIII.id) {
                case 1: {
                    this.mc.displayGuiScreen(this.previousScreen);
                    break;
                }
                case 0: {
                    this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                    this.thread.start();
                    break;
                }
                case 2: {
                    final String lIlIIlllIIlIl = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    if (!lIlIIlllIIlIl.contains(":")) {
                        break;
                    }
                    final String[] lIlIIlllIIlII = lIlIIlllIIlIl.split(":");
                    this.username.setText(lIlIIlllIIlII[0]);
                    this.password.setText(lIlIIlllIIlII[1]);
                    break;
                }
                case 3: {
                    this.thread = null;
                    this.thread = new AltLoginThread(RandomStringUtils.random(14, true, true), "");
                    this.thread.start();
                    break;
                }
                case 4: {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI("http://sellix.io/drilledalts"));
                        break;
                    }
                    break;
                }
                case 5: {
                    this.mc.displayGuiScreen(new GuiMicrosoftAltManager(this));
                    break;
                }
                case 6: {
                    this.thread = null;
                    this.thread = new AltLoginThread(NameUtil.generateName(), "");
                    this.thread.start();
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    @Override
    protected void keyTyped(final char lIlIIlIllllII, final int lIlIIlIlllllI) {
        try {
            super.keyTyped(lIlIIlIllllII, lIlIIlIlllllI);
        }
        catch (IOException lIlIIllIIIIIl) {
            lIlIIllIIIIIl.printStackTrace();
        }
        if (lIlIIlIllllII == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (lIlIIlIllllII == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(lIlIIlIllllII, lIlIIlIlllllI);
        this.password.textboxKeyTyped(lIlIIlIllllII, lIlIIlIlllllI);
    }
    
    public void setCrackedStatus(final String lIlIIlIlIIIII) {
        this.crackedStatus = lIlIIlIlIIIII;
    }
    
    @Override
    public void drawScreen(final int lIlIIllIlIllI, final int lIlIIllIlIlIl, final float lIlIIllIIlllI) {
        final FontRenderer lIlIIllIlIIll = this.mc.fontRendererObj;
        final ScaledResolution lIlIIllIlIIlI = new ScaledResolution(this.mc);
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(lIlIIllIlIIll, "Account Login", (int)(GuiAltManager.width / 2.0f), 20, -1);
        this.drawCenteredString(lIlIIllIlIIll, (this.thread == null) ? ((this.crackedStatus == null) ? String.valueOf(new StringBuilder().append(EnumChatFormatting.GRAY).append("Idle")) : String.valueOf(new StringBuilder().append(EnumChatFormatting.GREEN).append(this.crackedStatus))) : this.thread.getStatus(), (int)(GuiAltManager.width / 2.0f), 29, -1);
        if (this.username.getText().isEmpty()) {
            lIlIIllIlIIll.drawStringWithShadow("Username", GuiAltManager.width / 2.0f - 96.0f, 66.0f, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            lIlIIllIlIIll.drawStringWithShadow("Password", GuiAltManager.width / 2.0f - 96.0f, 106.0f, -7829368);
        }
        super.drawScreen(lIlIIllIlIllI, lIlIIllIlIlIl, lIlIIllIIlllI);
    }
    
    @Override
    protected void mouseClicked(final int lIlIIlIllIIlI, final int lIlIIlIllIIIl, final int lIlIIlIlIllII) {
        try {
            super.mouseClicked(lIlIIlIllIIlI, lIlIIlIllIIIl, lIlIIlIlIllII);
        }
        catch (IOException lIlIIlIllIlII) {
            lIlIIlIllIlII.printStackTrace();
        }
        this.username.mouseClicked(lIlIIlIllIIlI, lIlIIlIllIIIl, lIlIIlIlIllII);
        this.password.mouseClicked(lIlIIlIllIIlI, lIlIIlIllIIIl, lIlIIlIlIllII);
    }
    
    public String getCrackedStatus() {
        return this.crackedStatus;
    }
    
    @Override
    public void initGui() {
        final int lIlIIllIIlIII = GuiAltManager.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 24, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 48, "Clipboard"));
        this.buttonList.add(new GuiButton(3, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 48 + 24, "Generate Cracked Account"));
        this.buttonList.add(new GuiButton(5, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 48 + 24 + 24, "Microsoft Login"));
        this.buttonList.add(new GuiButton(4, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 48 + 72, "DrilledAlts (Good)"));
        this.buttonList.add(new GuiButton(6, GuiAltManager.width / 2 - 100, lIlIIllIIlIII + 72 + 12 + 48 + 24 + 72, "Generate Real Looking Name"));
        this.username = new GuiTextField(lIlIIllIIlIII, this.mc.fontRendererObj, GuiAltManager.width / 2 - 100, 60, 200, 20);
        this.password = new GuiTextField(lIlIIllIIlIII, this.mc.fontRendererObj, GuiAltManager.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
