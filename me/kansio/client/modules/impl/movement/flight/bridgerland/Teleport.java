package me.kansio.client.modules.impl.movement.flight.bridgerland;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;

public class Teleport extends FlightMode
{
    public Teleport() {
        super("BridgerLand (TP)");
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIlllIIllIlllI) {
        if (lIlllIIllIlllI.isPre()) {
            double lIlllIIlllIlII = 0.0;
            final double lIlllIIlllIIlI = this.getFlight().getSpeed().getValue();
            if (Teleport.mc.gameSettings.keyBindJump.isKeyDown()) {
                lIlllIIlllIlII = lIlllIIlllIIlI / 5.0;
            }
            if (Teleport.mc.gameSettings.keyBindSneak.isKeyDown()) {
                lIlllIIlllIlII = -lIlllIIlllIIlI / 5.0;
            }
            if (!this.getFlight().getAntikick().getValue() && !Teleport.mc.thePlayer.onGround) {
                Teleport.mc.thePlayer.motionY = 0.0;
            }
            if (Teleport.mc.thePlayer.ticksExisted % 3 == 0) {
                PlayerUtil.setMotion(lIlllIIlllIIlI);
                Teleport.mc.thePlayer.motionY = lIlllIIlllIlII;
            }
            else {
                PlayerUtil.setMotion(0.0);
            }
        }
    }
    
    @Override
    public void onPacket(final PacketEvent lIlllIIlIllIIl) {
        if (lIlllIIlIllIIl.getPacket() instanceof S08PacketPlayerPosLook) {
            lIlllIIlIllIIl.setCancelled(true);
        }
    }
}
