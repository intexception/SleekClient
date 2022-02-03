package me.kansio.client.modules.impl.movement.flight.verus;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class VerusDamage extends FlightMode
{
    private /* synthetic */ double veroos;
    
    @Override
    public void onEnable() {
        if (!VerusDamage.mc.thePlayer.onGround) {
            this.getFlight().toggle();
            return;
        }
        this.veroos = 0.22;
        TimerUtil.setTimer(0.8f);
        PlayerUtil.damageVerus();
    }
    
    public VerusDamage() {
        super("Verus Damage");
        this.veroos = 2.5;
    }
    
    @Override
    public void onMove(final MoveEvent lIIllIIlIlIIIl) {
        PlayerUtil.setMotion(lIIllIIlIlIIIl, this.veroos);
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIIllIIlIlIllI) {
        if (VerusDamage.mc.thePlayer.hurtTime > 8) {
            this.veroos = this.getFlight().getSpeed().getValue();
        }
        if (VerusDamage.mc.thePlayer.hurtResistantTime < 2) {
            this.veroos = 0.2199999988079071;
        }
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lIIllIIlIIIlIl) {
        if (lIIllIIlIIIlIl.getBlock() instanceof BlockAir) {
            if (VerusDamage.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lIIllIIlIIlIlI = lIIllIIlIIIlIl.getX();
            final double lIIllIIlIIlIIl = lIIllIIlIIIlIl.getY();
            final double lIIllIIlIIlIII = lIIllIIlIIIlIl.getZ();
            if (lIIllIIlIIlIIl < VerusDamage.mc.thePlayer.posY) {
                lIIllIIlIIIlIl.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIIllIIlIIlIlI, lIIllIIlIIlIIl, lIIllIIlIIlIII));
            }
        }
    }
}
