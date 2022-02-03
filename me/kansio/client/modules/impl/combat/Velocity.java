package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Velocity", category = ModuleCategory.COMBAT, description = "Allows you to modify your knockback")
public class Velocity extends Module
{
    private /* synthetic */ NumberValue<Double> v;
    public /* synthetic */ BooleanValue explotion;
    private /* synthetic */ ModeValue modeValue;
    private /* synthetic */ NumberValue<Double> h;
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.modeValue.getValueAsString()));
    }
    
    public Velocity() {
        this.v = new NumberValue<Double>("Vertical", this, 100.0, 0.0, 100.0, 1.0);
        this.h = new NumberValue<Double>("Horizontal", this, 100.0, 0.0, 100.0, 1.0);
        this.modeValue = new ModeValue("Mode", this, new String[] { "Packet" });
        this.explotion = new BooleanValue("Explosion", this, true);
    }
    
    @Subscribe
    public void onPacket(final PacketEvent llllllllllllllllllllIIIIIIlIlIll) {
        if (Velocity.mc.thePlayer == null || Velocity.mc.theWorld == null) {
            return;
        }
        final double llllllllllllllllllllIIIIIIlIlIII = (double)this.modeValue.getValueAsString();
        int llllllllllllllllllllIIIIIIlIIlll = -1;
        switch (((String)llllllllllllllllllllIIIIIIlIlIII).hashCode()) {
            case -1911998296: {
                if (((String)llllllllllllllllllllIIIIIIlIlIII).equals("Packet")) {
                    llllllllllllllllllllIIIIIIlIIlll = 0;
                    break;
                }
                break;
            }
        }
        switch (llllllllllllllllllllIIIIIIlIIlll) {
            case 0: {
                if (llllllllllllllllllllIIIIIIlIlIll.getPacket() instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity llllllllllllllllllllIIIIIIlIllIl = llllllllllllllllllllIIIIIIlIlIll.getPacket();
                    llllllllllllllllllllIIIIIIlIlIll.setCancelled(Velocity.mc.theWorld != null && Velocity.mc.thePlayer != null && Velocity.mc.theWorld.getEntityByID(llllllllllllllllllllIIIIIIlIllIl.getEntityID()) == Velocity.mc.thePlayer);
                    break;
                }
                if (llllllllllllllllllllIIIIIIlIlIll.getPacket() instanceof S27PacketExplosion && this.explotion.getValue()) {
                    llllllllllllllllllllIIIIIIlIlIll.setCancelled(true);
                    break;
                }
                break;
            }
        }
    }
}
