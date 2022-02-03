package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.entity.*;
import me.kansio.client.*;
import net.minecraft.util.*;
import me.kansio.client.value.*;

@ModuleData(name = "Cape", category = ModuleCategory.VISUALS, description = "Custom client capes")
public class Cape extends Module
{
    private /* synthetic */ ModeValue capemode;
    
    public boolean canRender(final AbstractClientPlayer lllIIIIIlI) {
        return lllIIIIIlI == Cape.mc.thePlayer || Client.getInstance().getFriendManager().isFriend(lllIIIIIlI.getName()) || Client.getInstance().getUsers().containsKey(lllIIIIIlI.getName());
    }
    
    public ResourceLocation getCape() {
        final byte lllIIIIllI = ((Value<Byte>)this.capemode).getValue();
        byte lllIIIIlIl = -1;
        switch (((String)lllIIIIllI).hashCode()) {
            case 79969970: {
                if (((String)lllIIIIllI).equals("Sleek")) {
                    lllIIIIlIl = 0;
                    break;
                }
                break;
            }
        }
        switch (lllIIIIlIl) {
            case 0: {
                return new ResourceLocation("sleek/capes/sleekcape.png");
            }
            default: {
                throw new IllegalStateException(String.valueOf(new StringBuilder().append("Unexpected value: ").append(this.capemode.getValue())));
            }
        }
    }
    
    public Cape() {
        this.capemode = new ModeValue("Cape", this, new String[] { "Sleek", "None" });
    }
}
