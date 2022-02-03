package me.kansio.client.modules.impl.player.hackerdetect.checks.movement;

import me.kansio.client.modules.impl.player.hackerdetect.checks.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import me.kansio.client.*;

public class FlightA extends Check
{
    private /* synthetic */ HashMap<EntityPlayer, Integer> airTicks;
    private /* synthetic */ Minecraft mc;
    
    public FlightA() {
        this.mc = Minecraft.getMinecraft();
        this.airTicks = new HashMap<EntityPlayer, Integer>();
    }
    
    @Override
    public String name() {
        return "Flight (Check A)";
    }
    
    @Override
    public void onUpdate() {
        for (final EntityPlayer llIlIIIIIIlllI : this.mc.theWorld.playerEntities) {
            if (llIlIIIIIIlllI == this.mc.thePlayer) {
                return;
            }
            final double llIlIIIIIlIIII = llIlIIIIIIlllI.posY - llIlIIIIIIlllI.prevPosY;
            if (llIlIIIIIIlllI.onGround) {
                this.airTicks.put(llIlIIIIIIlllI, 0);
                return;
            }
            if (llIlIIIIIlIIII < -0.45) {
                this.airTicks.put(llIlIIIIIIlllI, 0);
                return;
            }
            final int llIlIIIIIIllll = this.airTicks.getOrDefault(llIlIIIIIIlllI, 0);
            this.airTicks.put(llIlIIIIIIlllI, llIlIIIIIIllll + 1);
            if (llIlIIIIIIllll <= 35 || Client.getInstance().getTargetManager().isTarget(llIlIIIIIIlllI)) {
                continue;
            }
            this.flag(llIlIIIIIIlllI);
            Client.getInstance().getTargetManager().getTarget().add(llIlIIIIIIlllI.getName());
        }
    }
}
