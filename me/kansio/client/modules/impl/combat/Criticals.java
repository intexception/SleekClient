package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.google.common.eventbus.*;
import me.kansio.client.utils.network.*;

@ModuleData(name = "Criticals", category = ModuleCategory.COMBAT, description = "Automatically deals criticals")
public class Criticals extends Module
{
    private /* synthetic */ ModeValue mode;
    public final /* synthetic */ double[] packetValues;
    private final /* synthetic */ BooleanValue c06;
    
    void sendPacket(final double lIllllIIIlllI, final double lIllllIIIllIl, final double lIllllIIIIlII, final boolean lIllllIIIlIll) {
        final double lIllllIIIlIlI = Criticals.mc.thePlayer.posX + lIllllIIIlllI;
        final double lIllllIIIlIIl = Criticals.mc.thePlayer.posY + lIllllIIIllIl;
        final double lIllllIIIlIII = Criticals.mc.thePlayer.posZ + lIllllIIIIlII;
        if (this.c06.getValue()) {
            Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(lIllllIIIlIlI, lIllllIIIlIIl, lIllllIIIlIII, Criticals.mc.thePlayer.rotationYaw, Criticals.mc.thePlayer.rotationPitch, lIllllIIIlIll));
        }
        else {
            Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(lIllllIIIlIlI, lIllllIIIlIIl, lIllllIIIlIII, lIllllIIIlIll));
        }
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIlllllIIlIlI) {
        final Flight lIlllllIIlIIl = (Flight)Client.getInstance().getModuleManager().getModuleByName("Flight");
        if (lIlllllIIlIIl.isToggled()) {
            return;
        }
        if (lIlllllIIlIlI.getPacket() instanceof C02PacketUseEntity && lIlllllIIlIlI.getPacket().getAction() == C02PacketUseEntity.Action.ATTACK) {
            final C02PacketUseEntity lIlllllIIllIl = lIlllllIIlIlI.getPacket();
            final Entity lIlllllIIllII = lIlllllIIllIl.getEntityFromWorld(Criticals.mc.theWorld);
            if (Criticals.mc.thePlayer.onGround && lIlllllIIllII.hurtResistantTime != -1) {
                this.doCritical();
                lIlllllIIllII.hurtResistantTime = -1;
            }
        }
    }
    
    public Criticals() {
        this.mode = new ModeValue("Mode", this, new String[] { "Packet", "Verus", "MiniJump", "Jump" });
        this.c06 = new BooleanValue("C06", this, true);
        this.packetValues = new double[] { 0.0625, 0.0, 0.05, 0.0 };
    }
    
    public void doCritical() {
        final char lIllllIIlllIl = (char)this.mode.getValueAsString();
        short lIllllIIlllII = -1;
        switch (((String)lIllllIIlllIl).hashCode()) {
            case -1911998296: {
                if (((String)lIllllIIlllIl).equals("Packet")) {
                    lIllllIIlllII = 0;
                    break;
                }
                break;
            }
            case 82544993: {
                if (((String)lIllllIIlllIl).equals("Verus")) {
                    lIllllIIlllII = 1;
                    break;
                }
                break;
            }
            case -1295454907: {
                if (((String)lIllllIIlllIl).equals("MiniJump")) {
                    lIllllIIlllII = 2;
                    break;
                }
                break;
            }
            case 2320462: {
                if (((String)lIllllIIlllIl).equals("Jump")) {
                    lIllllIIlllII = 3;
                    break;
                }
                break;
            }
        }
        switch (lIllllIIlllII) {
            case 0: {
                final int lIllllIIllIll = (Object)this.packetValues;
                final byte lIllllIIllIlI = (byte)lIllllIIllIll.length;
                for (boolean lIllllIIllIIl = false; (lIllllIIllIIl ? 1 : 0) < lIllllIIllIlI; ++lIllllIIllIIl) {
                    final double lIllllIlIIIII = lIllllIIllIll[lIllllIIllIIl];
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + lIllllIlIIIII, Criticals.mc.thePlayer.posZ, false));
                }
                break;
            }
            case 1: {
                if (!Criticals.mc.thePlayer.onGround) {
                    return;
                }
                this.sendPacket(0.0, 0.11, 0.0, false);
                this.sendPacket(0.0, 0.1100013579, 0.0, false);
                this.sendPacket(0.0, 1.3579E-6, 0.0, false);
                break;
            }
            case 2: {
                if (Criticals.mc.thePlayer.onGround) {
                    Criticals.mc.thePlayer.motionY = 0.2199999988079071;
                    break;
                }
                break;
            }
            case 3: {
                if (!Criticals.mc.thePlayer.onGround) {
                    return;
                }
                Criticals.mc.thePlayer.jump();
                break;
            }
        }
    }
}
