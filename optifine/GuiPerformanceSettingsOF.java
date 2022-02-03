package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    public GuiPerformanceSettingsOF(final GuiScreen p_i52_1_, final GameSettings p_i52_2_) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = p_i52_1_;
        this.settings = p_i52_2_;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.performanceTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiPerformanceSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options gamesettings$options = GuiPerformanceSettingsOF.enumOptions[i];
            final int j = GuiPerformanceSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int k = GuiPerformanceSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
        }
        this.buttonList.add(new GuiButton(200, GuiPerformanceSettingsOF.width / 2 - 100, GuiPerformanceSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
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
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiPerformanceSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
    }
    
    static {
        GuiPerformanceSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
    }
}
