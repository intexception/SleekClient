package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    public GuiOtherSettingsOF(final GuiScreen p_i51_1_, final GameSettings p_i51_2_) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = p_i51_1_;
        this.settings = p_i51_2_;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.otherTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiOtherSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options gamesettings$options = GuiOtherSettingsOF.enumOptions[i];
            final int j = GuiOtherSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int k = GuiOtherSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
        }
        this.buttonList.add(new GuiButton(210, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id < 200 && button instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (button.id == 210) {
                this.mc.gameSettings.saveOptions();
                final GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        if (result) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiOtherSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
    }
    
    static {
        GuiOtherSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH };
    }
}
