package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;
import net.minecraft.util.*;

public class Mush extends FlightMode
{
    /* synthetic */ boolean blinking;
    /* synthetic */ double speedy;
    private /* synthetic */ ArrayList<? extends C03PacketPlayer> c03Packets;
    
    @Override
    public void onMove(final MoveEvent lllIlllIIIll) {
        if (Mush.mc.gameSettings.keyBindJump.isKeyDown()) {
            Mush.mc.thePlayer.motionY = 1.0;
        }
        else if (Mush.mc.gameSettings.keyBindSneak.isKeyDown()) {
            Mush.mc.thePlayer.motionY = -1.0;
        }
        PlayerUtil.setMotion(Math.max(this.speedy, PlayerUtil.getVerusBaseSpeed()));
    }
    
    public Mush() {
        super("Mush");
        this.speedy = 2.5;
        this.blinking = false;
        this.c03Packets = new ArrayList<C03PacketPlayer>();
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent lllIlIlIIlII) {
        if (lllIlIlIIlII.getBlock() instanceof BlockAir) {
            if (Mush.mc.thePlayer.isSneaking()) {
                return;
            }
            final double lllIlIlIlllI = lllIlIlIIlII.getX();
            final double lllIlIlIllII = lllIlIlIIlII.getY();
            final double lllIlIlIlIlI = lllIlIlIIlII.getZ();
            if (lllIlIlIllII < Mush.mc.thePlayer.posY) {
                lllIlIlIIlII.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(lllIlIlIlllI, lllIlIlIllII, lllIlIlIlIlI));
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.speedy = this.getFlight().getSpeed().getValue();
        Mush.mc.timer.timerSpeed = this.getFlight().getTimer().getValue().floatValue();
        this.blinking = this.getFlight().getBlink().getValue();
    }
    
    @Override
    public void onPacket(final PacketEvent lllIllIlllII) {
        if (this.blinking && lllIllIlllII.getPacket() instanceof C03PacketPlayer) {
            this.c03Packets.add(lllIllIlllII.getPacket());
        }
    }
    
    public void stopBlink() {
        for (final C03PacketPlayer lllIllIIlIII : this.c03Packets) {
            PacketUtil.sendPacketNoEvent(lllIllIIlIII);
        }
        this.c03Packets.clear();
        this.blinking = false;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.stopBlink();
    }
    
    @Override
    public void onUpdate(final UpdateEvent lllIlllIIlll) {
        if (Mush.mc.thePlayer.ticksExisted % 18 == 0) {
            this.stopBlink();
        }
        if (Mush.mc.thePlayer.isMoving()) {
            if (Mush.mc.timer.timerSpeed > 1.0f) {
                final Timer timer = Mush.mc.timer;
                timer.timerSpeed -= (float)0.01;
            }
            if (this.speedy > 0.22) {
                this.speedy -= 0.01;
            }
        }
        else {
            TimerUtil.Reset();
            this.speedy = 0.0;
        }
    }
}
