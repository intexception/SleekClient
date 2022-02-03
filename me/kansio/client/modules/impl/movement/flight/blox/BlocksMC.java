package me.kansio.client.modules.impl.movement.flight.blox;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;

public class BlocksMC extends FlightMode
{
    @Override
    public void onMove(final MoveEvent lIlllIlIIllllI) {
        if (BlocksMC.mc.thePlayer.ticksExisted % 15 == 0) {
            BlocksMC.mc.thePlayer.motionY = 0.0;
            lIlllIlIIllllI.setMotionY(0.41999998688698);
            PlayerUtil.setMotion(this.getFlight().getSpeed().getValue().floatValue());
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIlllIlIlIIlII) {
        super.onUpdate(lIlllIlIlIIlII);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lIlllIlIIIllIl) {
        if (BlocksMC.mc.thePlayer.ticksExisted % 15 != 0 && lIlllIlIIIllIl.getBlock() instanceof BlockAir) {
            if (BlocksMC.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lIlllIlIIlIIIl = lIlllIlIIIllIl.getX();
            final double lIlllIlIIlIIII = lIlllIlIIIllIl.getY();
            final double lIlllIlIIIllll = lIlllIlIIIllIl.getZ();
            lIlllIlIIIllIl.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lIlllIlIIlIIIl, lIlllIlIIlIIII, lIlllIlIIIllll));
        }
    }
    
    public BlocksMC() {
        super("BlocksMC");
    }
    
    @Override
    public void onPacket(final PacketEvent lIlllIlIIllIII) {
        super.onPacket(lIlllIlIIllIII);
    }
}
