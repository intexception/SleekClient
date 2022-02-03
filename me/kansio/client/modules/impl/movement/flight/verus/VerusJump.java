package me.kansio.client.modules.impl.movement.flight.verus;

import me.kansio.client.modules.impl.movement.flight.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class VerusJump extends FlightMode
{
    private /* synthetic */ double startY;
    
    @Override
    public void onUpdate(final UpdateEvent lIlllllIIIIIIl) {
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lIllllIllIlIll) {
        if (lIllllIllIlIll.getBlock() instanceof BlockAir && lIllllIllIlIll.getY() <= this.startY) {
            final double lIllllIlllIIIl = lIllllIllIlIll.getX();
            final double lIllllIlllIIII = lIllllIllIlIll.getY();
            final double lIllllIllIllll = lIllllIllIlIll.getZ();
            lIllllIllIlIll.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIllllIlllIIIl, lIllllIlllIIII, lIllllIllIllll));
        }
    }
    
    public VerusJump() {
        super("VerusJump");
    }
    
    @Override
    public void onEnable() {
        this.startY = VerusJump.mc.thePlayer.posY - 1.0;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onMove(final MoveEvent lIllllIllllIII) {
        if (VerusJump.mc.thePlayer.onGround && VerusJump.mc.thePlayer.isMoving()) {
            lIllllIllllIII.setMotionY(VerusJump.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
        }
    }
}
