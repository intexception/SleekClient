package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import java.io.*;

public class GuiCustomizeSkin extends GuiScreen
{
    private final GuiScreen parentScreen;
    private String title;
    
    public GuiCustomizeSkin(final GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }
    
    @Override
    public void initGui() {
        int i = 0;
        this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
        for (final EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
            this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), GuiCustomizeSkin.width / 2 - 155 + i % 2 * 160, GuiCustomizeSkin.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
            ++i;
        }
        if (i % 2 == 1) {
            ++i;
        }
        this.buttonList.add(new GuiButton(200, GuiCustomizeSkin.width / 2 - 100, GuiCustomizeSkin.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button instanceof ButtonPart) {
                final EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
                this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
                button.displayString = this.func_175358_a(enumplayermodelparts);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiCustomizeSkin.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private String func_175358_a(final EnumPlayerModelParts playerModelParts) {
        String s;
        if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
            s = I18n.format("options.on", new Object[0]);
        }
        else {
            s = I18n.format("options.off", new Object[0]);
        }
        return playerModelParts.func_179326_d().getFormattedText() + ": " + s;
    }
    
    class ButtonPart extends GuiButton
    {
        private final EnumPlayerModelParts playerModelParts;
        
        private ButtonPart(final int p_i45514_2_, final int p_i45514_3_, final int p_i45514_4_, final int p_i45514_5_, final int p_i45514_6_, final EnumPlayerModelParts playerModelParts) {
            super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
            this.playerModelParts = playerModelParts;
        }
    }
}
