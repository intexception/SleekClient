package optifine;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.util.*;

public class GuiMessage extends GuiScreen
{
    private GuiScreen parentScreen;
    private String messageLine1;
    private String messageLine2;
    private final List listLines2;
    protected String confirmButtonText;
    private int ticksUntilEnable;
    
    public GuiMessage(final GuiScreen p_i48_1_, final String p_i48_2_, final String p_i48_3_) {
        this.listLines2 = Lists.newArrayList();
        this.parentScreen = p_i48_1_;
        this.messageLine1 = p_i48_2_;
        this.messageLine2 = p_i48_3_;
        this.confirmButtonText = I18n.format("gui.done", new Object[0]);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, GuiMessage.width / 2 - 74, GuiMessage.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, GuiMessage.width - 50));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        Config.getMinecraft().displayGuiScreen(this.parentScreen);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.messageLine1, GuiMessage.width / 2, 70, 16777215);
        int i = 90;
        for (final Object s : this.listLines2) {
            this.drawCenteredString(this.fontRendererObj, (String)s, GuiMessage.width / 2, i, 16777215);
            i += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void setButtonDelay(final int p_setButtonDelay_1_) {
        this.ticksUntilEnable = p_setButtonDelay_1_;
        for (final GuiButton guibutton : this.buttonList) {
            guibutton.enabled = false;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - 1;
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            for (final GuiButton guibutton : this.buttonList) {
                guibutton.enabled = true;
            }
        }
    }
}
