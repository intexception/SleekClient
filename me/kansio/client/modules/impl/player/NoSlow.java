package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import com.google.common.eventbus.*;

@ModuleData(name = "No Slow", category = ModuleCategory.PLAYER, description = "Stops you from getting slowed down")
public class NoSlow extends Module
{
    public /* synthetic */ BooleanValue soulsand;
    public /* synthetic */ BooleanValue item;
    public /* synthetic */ ModeValue mode;
    public /* synthetic */ BooleanValue water;
    
    @Subscribe
    public void onNoSlow(final NoSlowEvent lIlIllIIlIlIll) {
        switch (lIlIllIIlIlIll.getType()) {
            case ITEM: {
                lIlIllIIlIlIll.setCancelled(this.item.getValue());
                break;
            }
            case WATER: {
                lIlIllIIlIlIll.setCancelled(this.water.getValue());
                break;
            }
            case SOULSAND: {
                lIlIllIIlIlIll.setCancelled(this.soulsand.getValue());
                break;
            }
        }
    }
    
    public NoSlow() {
        this.mode = new ModeValue("Mode", this, new String[] { "Vanilla" });
        this.item = new BooleanValue("Item", this, true);
        this.water = new BooleanValue("Water", this, false);
        this.soulsand = new BooleanValue("SoulSand", this, false);
    }
}
