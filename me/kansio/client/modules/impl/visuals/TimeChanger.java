package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.play.server.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.value.*;

@ModuleData(name = "Time Changer", category = ModuleCategory.VISUALS, description = "Changes the world time")
public class TimeChanger extends Module
{
    private /* synthetic */ ModeValue time;
    
    public TimeChanger() {
        this.time = new ModeValue("Mode", this, new String[] { "Day", "Noon", "Night", "Mid Night" });
    }
    
    @Subscribe
    public void onPacket(final PacketEvent llllIIIIIllIl) {
        if (llllIIIIIllIl.getPacket() instanceof S03PacketTimeUpdate) {
            llllIIIIIllIl.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llllIIIIlIlII) {
        final float llllIIIIlIIlI = ((Value<Float>)this.time).getValue();
        Exception llllIIIIlIIIl = (Exception)(-1);
        switch (((String)llllIIIIlIIlI).hashCode()) {
            case 68476: {
                if (((String)llllIIIIlIIlI).equals("Day")) {
                    llllIIIIlIIIl = (Exception)0;
                    break;
                }
                break;
            }
            case 2433920: {
                if (((String)llllIIIIlIIlI).equals("Noon")) {
                    llllIIIIlIIIl = (Exception)1;
                    break;
                }
                break;
            }
            case 75265016: {
                if (((String)llllIIIIlIIlI).equals("Night")) {
                    llllIIIIlIIIl = (Exception)2;
                    break;
                }
                break;
            }
            case 418871296: {
                if (((String)llllIIIIlIIlI).equals("Mid Night")) {
                    llllIIIIlIIIl = (Exception)3;
                    break;
                }
                break;
            }
        }
        switch (llllIIIIlIIIl) {
            case 0L: {
                TimeChanger.mc.theWorld.setWorldTime(1000L);
                break;
            }
            case 1L: {
                TimeChanger.mc.theWorld.setWorldTime(13200L);
                break;
            }
            case 2L: {
                TimeChanger.mc.theWorld.setWorldTime(13000L);
                break;
            }
            case 3L: {
                TimeChanger.mc.theWorld.setWorldTime(18000L);
                break;
            }
        }
    }
}
