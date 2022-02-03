package net.minecraft.client.gui;

import me.kansio.client.*;
import me.kansio.client.utils.network.*;
import negroidslayer.*;
import me.kansio.client.gui.*;
import com.google.gson.*;
import org.lwjgl.input.*;
import java.io.*;

public class GuiMainMenu extends GuiScreen
{
    private GuiTextField username;
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        try {
            if (button.id == 0) {
                Client.getInstance().setUid(this.username.getText());
                final String serv = HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/getuser?uid=" + Client.getInstance().getUid());
                final JsonObject json = new JsonParser().parse(serv).getAsJsonObject();
                if (json.get("uid").getAsString().equals(Client.getInstance().getUid()) && json.get("hwid").getAsString().equals(NegroidFarm.guisdafghiusfgfsdhusdfghifsdhuidsfhuifdshuifsdhiudsfhiusfdhsdiuffsdhiudhsifusdfhiufsdhiufsdhiusdfhiufsdhiufsdhiu())) {
                    Client.getInstance().onStart();
                    Client.getInstance().setUsername(json.get("username").getAsString());
                    Client.getInstance().setDiscordTag(json.get("discordTag").getAsString());
                    Client.getInstance().setRank(json.get("rank").getAsString());
                    this.mc.displayGuiScreen(new MainMenu());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y2, final float z) {
        final FontRenderer font = this.mc.fontRendererObj;
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.drawCenteredString(font, "Login", (int)(GuiMainMenu.width / 2.0f), 80, -1);
        if (this.username.getText().isEmpty()) {
            font.drawStringWithShadow("UID", GuiMainMenu.width / 2.0f - 96.0f, 126.0f, -7829368);
        }
        super.drawScreen(x, y2, z);
    }
    
    @Override
    public void initGui() {
        final int var3 = GuiMainMenu.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiMainMenu.width / 2 - 100, 144, "Login"));
        (this.username = new GuiTextField(var3, this.mc.fontRendererObj, GuiMainMenu.width / 2 - 100, 120, 200, 20)).setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        this.username.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x, final int y2, final int button) {
        try {
            super.mouseClicked(x, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x, y2, button);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}
