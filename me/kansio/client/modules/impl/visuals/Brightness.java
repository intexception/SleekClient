package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.potion.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Brightness", category = ModuleCategory.VISUALS, description = "Changes the game brightness")
public class Brightness extends Module
{
    private /* synthetic */ ModeValue mode;
    private /* synthetic */ float oldGamma;
    
    @Override
    public void onDisable() {
        Brightness.mc.gameSettings.gammaSetting = this.oldGamma;
        Brightness.mc.thePlayer.removePotionEffect(16);
    }
    
    @Override
    public void onEnable() {
        this.oldGamma = Brightness.mc.gameSettings.gammaSetting;
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llllllllllllllllllllIIIlIlIIllIl) {
        final byte llllllllllllllllllllIIIlIlIIlIll = (byte)this.mode.getValueAsString();
        short llllllllllllllllllllIIIlIlIIlIlI = -1;
        switch (((String)llllllllllllllllllllIIIlIlIIlIll).hashCode()) {
            case 68567943: {
                if (((String)llllllllllllllllllllIIIlIlIIlIll).equals("Gamma")) {
                    llllllllllllllllllllIIIlIlIIlIlI = 0;
                    break;
                }
                break;
            }
            case -1898564173: {
                if (((String)llllllllllllllllllllIIIlIlIIlIll).equals("Potion")) {
                    llllllllllllllllllllIIIlIlIIlIlI = 1;
                    break;
                }
                break;
            }
        }
        switch (llllllllllllllllllllIIIlIlIIlIlI) {
            case 0: {
                Brightness.mc.gameSettings.gammaSetting = 2000.0f;
                break;
            }
            case 1: {
                Brightness.mc.thePlayer.addPotionEffect(new PotionEffect(16, 16340, 30));
                break;
            }
        }
    }
    
    public Brightness() {
        this.mode = new ModeValue("Mode", this, new String[] { "Gamma", "Potion" });
    }
}
