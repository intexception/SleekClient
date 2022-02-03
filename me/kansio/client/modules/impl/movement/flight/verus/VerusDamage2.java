package me.kansio.client.modules.impl.movement.flight.verus;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;

public class VerusDamage2 extends FlightMode
{
    private /* synthetic */ double startY;
    private /* synthetic */ double veroos;
    
    public VerusDamage2() {
        super("Verus Longjump");
        this.veroos = 2.5;
    }
    
    @Override
    public void onEnable() {
        PlayerUtil.damageVerus();
        this.veroos = 0.22;
        this.startY = VerusDamage2.mc.thePlayer.posY - 1.0;
    }
    
    @Override
    public void onMove(final MoveEvent lIlIIlIIllIIll) {
        if (VerusDamage2.mc.thePlayer.onGround && VerusDamage2.mc.thePlayer.isMoving()) {
            lIlIIlIIllIIll.setMotionY(VerusDamage2.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
        }
        PlayerUtil.setMotion(lIlIIlIIllIIll, this.veroos);
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lIlIIlIIlIIlll) {
        if (lIlIIlIIlIIlll.getBlock() instanceof BlockAir && lIlIIlIIlIIlll.getY() <= this.startY) {
            final double lIlIIlIIlIlIll = lIlIIlIIlIIlll.getX();
            final double lIlIIlIIlIlIlI = lIlIIlIIlIIlll.getY();
            final double lIlIIlIIlIlIIl = lIlIIlIIlIIlll.getZ();
            lIlIIlIIlIIlll.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIlIIlIIlIlIll, lIlIIlIIlIlIlI, lIlIIlIIlIlIIl));
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIlIIlIIlllIII) {
        if (VerusDamage2.mc.thePlayer.hurtTime > 8) {
            this.veroos = this.getFlight().getSpeed().getValue();
        }
        if (VerusDamage2.mc.thePlayer.hurtResistantTime < 3) {
            this.veroos = 0.2199999988079071;
        }
    }
}
