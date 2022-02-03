package me.kansio.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import me.kansio.client.gui.alt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.audio.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.utils.render.*;

public class MainMenu extends GuiScreen
{
    private /* synthetic */ boolean soviet;
    private static final /* synthetic */ ResourceLocation SBACKGROUND;
    private static final /* synthetic */ ResourceLocation BACKGROUND;
    /* synthetic */ PositionedSoundRecord soundRecord;
    
    @Override
    public void initGui() {
        final int lIIlIllIllIIl = MainMenu.height / 4 + 48;
        final int lIIlIllIllIII = 24;
        this.buttonList.add(new GuiButton(0, MainMenu.width / 2 - 100, lIIlIllIllIIl, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(1, MainMenu.width / 2 - 100, lIIlIllIllIIl + lIIlIllIllIII, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, MainMenu.width / 2 - 100, lIIlIllIllIIl + lIIlIllIllIII * 2, "Alt Manager"));
        this.buttonList.add(new GuiButton(3, MainMenu.width / 2 - 100, lIIlIllIllIIl + 84, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, MainMenu.width / 2 + 2, lIIlIllIllIIl + 84, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButton(5, 0, 0, 98, 20, "Soviet"));
    }
    
    public MainMenu() {
        this.soundRecord = PositionedSoundRecord.create(new ResourceLocation("bgm.soviet"), 1.0f, true);
        this.soviet = false;
    }
    
    @Override
    protected void actionPerformed(final GuiButton lIIlIllIlIIIl) throws IOException {
        switch (lIIlIllIlIIIl.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiAltManager(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                this.mc.shutdown();
                break;
            }
            case 5: {
                this.soviet = !this.soviet;
                if (this.soviet) {
                    this.mc.getSoundHandler().playSound(this.soundRecord);
                    break;
                }
                this.mc.getSoundHandler().stopSound(this.soundRecord);
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int lIIlIllIIIIIl, final int lIIlIllIIIllI, final float lIIlIllIIIlIl) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.drawImage(this.soviet ? MainMenu.SBACKGROUND : MainMenu.BACKGROUND, 0, 0, MainMenu.width, MainMenu.height);
        final String lIIlIllIIIlII = "§lS§fleek";
        Fonts.Arial30.drawCenteredString(lIIlIllIIIlII, (float)(MainMenu.width / 2), (float)(MainMenu.height / 4 + 24), ColorPalette.BLUE.getColor().getRGB());
        final String lIIlIllIIIIll = "Made with <3 by Reset, Kansio, PC and Divine";
        Fonts.Verdana.drawString(lIIlIllIIIIll, (float)(MainMenu.width - Fonts.Arial30.getStringWidth(lIIlIllIIIIll) + 115), (float)(MainMenu.height - 10), -1);
        super.drawScreen(lIIlIllIIIIIl, lIIlIllIIIllI, lIIlIllIIIlIl);
    }
    
    @Override
    protected void mouseClicked(final int lIIlIlIllIIll, final int lIIlIlIllIIlI, final int lIIlIlIllIIIl) throws IOException {
        super.mouseClicked(lIIlIlIllIIll, lIIlIlIllIIlI, lIIlIlIllIIIl);
    }
    
    static {
        BACKGROUND = new ResourceLocation("sleek/bg1.png");
        SBACKGROUND = new ResourceLocation("sleek/stalin-1.jpg");
    }
}
