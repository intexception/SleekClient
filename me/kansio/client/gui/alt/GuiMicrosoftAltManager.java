package me.kansio.client.gui.alt;

import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.text.*;
import fr.litarvan.openauth.microsoft.*;
import java.io.*;

public final class GuiMicrosoftAltManager extends GuiScreen
{
    private /* synthetic */ GuiTextField password;
    private /* synthetic */ AltLoginThread thread;
    private /* synthetic */ GuiTextField username;
    private /* synthetic */ String crackedStatus;
    private final /* synthetic */ GuiScreen previousScreen;
    
    @Override
    public void initGui() {
        final int llIIlIIIlllI = GuiMicrosoftAltManager.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiMicrosoftAltManager.width / 2 - 100, llIIlIIIlllI + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, GuiMicrosoftAltManager.width / 2 - 100, llIIlIIIlllI + 72 + 12 + 24, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiMicrosoftAltManager.width / 2 - 100, llIIlIIIlllI + 72 + 12 + 48, "Clipboard"));
        this.username = new GuiTextField(llIIlIIIlllI, this.mc.fontRendererObj, GuiMicrosoftAltManager.width / 2 - 100, 60, 200, 20);
        this.password = new GuiTextField(llIIlIIIlllI, this.mc.fontRendererObj, GuiMicrosoftAltManager.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
    
    @Override
    public void drawScreen(final int llIIlIIlIllI, final int llIIlIIlIlIl, final float llIIlIIlIlII) {
        final FontRenderer llIIlIIllIIl = this.mc.fontRendererObj;
        final ScaledResolution llIIlIIllIII = new ScaledResolution(this.mc);
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(llIIlIIllIIl, "Account Login", (int)(GuiMicrosoftAltManager.width / 2.0f), 20, -1);
        this.drawCenteredString(llIIlIIllIIl, (this.thread == null) ? ((this.crackedStatus == null) ? String.valueOf(new StringBuilder().append(EnumChatFormatting.GRAY).append("Idle")) : String.valueOf(new StringBuilder().append(EnumChatFormatting.GREEN).append(this.crackedStatus))) : this.thread.getStatus(), (int)(GuiMicrosoftAltManager.width / 2.0f), 29, -1);
        if (this.username.getText().isEmpty()) {
            llIIlIIllIIl.drawStringWithShadow("Username", GuiMicrosoftAltManager.width / 2.0f - 96.0f, 66.0f, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            llIIlIIllIIl.drawStringWithShadow("Password", GuiMicrosoftAltManager.width / 2.0f - 96.0f, 106.0f, -7829368);
        }
        super.drawScreen(llIIlIIlIllI, llIIlIIlIlIl, llIIlIIlIlII);
    }
    
    public void setCrackedStatus(final String llIIIllIIllI) {
        this.crackedStatus = llIIIllIIllI;
    }
    
    @Override
    protected void actionPerformed(final GuiButton llIIlIlIlIII) {
        try {
            switch (llIIlIlIlIII.id) {
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
                    final MicrosoftAuthenticator llIIlIlIlIll = new MicrosoftAuthenticator();
                    final MicrosoftAuthResult llIIlIlIlIlI = llIIlIlIlIll.loginWithCredentials(this.username.getText(), this.password.getText());
                    this.crackedStatus = MessageFormat.format("Logged as {0}", llIIlIlIlIlI.getProfile().getName());
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    public String getCrackedStatus() {
        return this.crackedStatus;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char llIIlIIIIlIl, final int llIIlIIIIIIl) {
        try {
            super.keyTyped(llIIlIIIIlIl, llIIlIIIIIIl);
        }
        catch (IOException llIIlIIIIlll) {
            llIIlIIIIlll.printStackTrace();
        }
        if (llIIlIIIIlIl == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (llIIlIIIIlIl == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(llIIlIIIIlIl, llIIlIIIIIIl);
        this.password.textboxKeyTyped(llIIlIIIIlIl, llIIlIIIIIIl);
    }
    
    @Override
    protected void mouseClicked(final int llIIIlllIlII, final int llIIIlllIlll, final int llIIIlllIllI) {
        try {
            super.mouseClicked(llIIIlllIlII, llIIIlllIlll, llIIIlllIllI);
        }
        catch (IOException llIIIllllIlI) {
            llIIIllllIlI.printStackTrace();
        }
        this.username.mouseClicked(llIIIlllIlII, llIIIlllIlll, llIIIlllIllI);
        this.password.mouseClicked(llIIIlllIlII, llIIIlllIlll, llIIIlllIllI);
    }
    
    public GuiMicrosoftAltManager(final GuiScreen llIIlIllIIlI) {
        this.previousScreen = llIIlIllIIlI;
    }
}
