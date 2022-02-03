package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import me.kansio.client.gui.*;
import java.io.*;

public class GuiMemoryErrorScreen extends GuiScreen
{
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(0, GuiMemoryErrorScreen.width / 2 - 155, GuiMemoryErrorScreen.height / 4 + 120 + 12, I18n.format("gui.toTitle", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, GuiMemoryErrorScreen.width / 2 - 155 + 160, GuiMemoryErrorScreen.height / 4 + 120 + 12, I18n.format("menu.quit", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new MainMenu());
        }
        else if (button.id == 1) {
            this.mc.shutdown();
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Out of memory!", GuiMemoryErrorScreen.width / 2, GuiMemoryErrorScreen.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRendererObj, "Minecraft has run out of memory.", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 0, 10526880);
        this.drawString(this.fontRendererObj, "This could be caused by a bug in the game or by the", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 18, 10526880);
        this.drawString(this.fontRendererObj, "Java Virtual Machine not being allocated enough", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 27, 10526880);
        this.drawString(this.fontRendererObj, "memory.", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 36, 10526880);
        this.drawString(this.fontRendererObj, "To prevent level corruption, the current game has quit.", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 54, 10526880);
        this.drawString(this.fontRendererObj, "We've tried to free up enough memory to let you go back to", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 63, 10526880);
        this.drawString(this.fontRendererObj, "the main menu and back to playing, but this may not have worked.", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 72, 10526880);
        this.drawString(this.fontRendererObj, "Please restart the game if you see this message again.", GuiMemoryErrorScreen.width / 2 - 140, GuiMemoryErrorScreen.height / 4 - 60 + 60 + 81, 10526880);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
