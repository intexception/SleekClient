package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.gui.*;

@ModuleData(name = "Click GUI", category = ModuleCategory.VISUALS, description = "The click gui... nothing special (Credit: Wykt)", bind = 54)
public class ClickGUI extends Module
{
    private /* synthetic */ BooleanValue font;
    public /* synthetic */ BooleanValue hudcolor;
    
    public ClickGUI() {
        this.hudcolor = new BooleanValue("Hud Colour", this, false);
        this.font = new BooleanValue("Font", this, false);
    }
    
    @Override
    public void onEnable() {
        ClickGUI.mc.displayGuiScreen(new me.kansio.client.gui.clickgui.ui.clickgui.frame.ClickGUI());
        this.toggle();
    }
}
