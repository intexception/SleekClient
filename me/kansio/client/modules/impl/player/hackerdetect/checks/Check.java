package me.kansio.client.modules.impl.player.hackerdetect.checks;

import net.minecraft.client.*;
import me.kansio.client.modules.impl.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import me.kansio.client.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.value.*;

public abstract class Check
{
    protected static /* synthetic */ Minecraft mc;
    protected /* synthetic */ HackerDetect detect;
    
    public void onBlocksMCGameStartTick() {
    }
    
    public void onPacket(final PacketEvent lIIIlIllll) {
    }
    
    static {
        Check.mc = Minecraft.getMinecraft();
    }
    
    public abstract String name();
    
    public void flag(final EntityPlayer lIIIlIlIII) {
        final HackerDetect lIIIlIIlll = (HackerDetect)Client.getInstance().getModuleManager().getModuleByName("HackerDetect");
        final long lIIIlIIIll = ((Value<Long>)lIIIlIIlll.theme).getValue();
        int lIIIlIIIlI = -1;
        switch (((String)lIIIlIIIll).hashCode()) {
            case 79969970: {
                if (((String)lIIIlIIIll).equals("Sleek")) {
                    lIIIlIIIlI = 0;
                    break;
                }
                break;
            }
            case 82544993: {
                if (((String)lIIIlIIIll).equals("Verus")) {
                    lIIIlIIIlI = 1;
                    break;
                }
                break;
            }
        }
        switch (lIIIlIIIlI) {
            case 0: {
                ChatUtil.logSleekCheater(lIIIlIlIII.getName(), this.name());
                break;
            }
            case 1: {
                ChatUtil.logVerusCheater(lIIIlIlIII.getName(), this.name(), "2");
                break;
            }
        }
    }
    
    public void onUpdate() {
    }
    
    public Check() {
        this.detect = HackerDetect.getInstance();
    }
}
