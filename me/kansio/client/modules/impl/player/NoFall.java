package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.play.client.*;
import com.google.common.eventbus.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.value.*;

@ModuleData(name = "No Fall", category = ModuleCategory.PLAYER, description = "Disables fall damage")
public class NoFall extends Module
{
    private /* synthetic */ ModeValue mode;
    
    @Subscribe
    public void onPacket(final PacketEvent llllllllllllllllllllllIlIllIIlIl) {
        final byte llllllllllllllllllllllIlIllIIIlI = (byte)this.mode.getValueAsString();
        float llllllllllllllllllllllIlIllIIIIl = -1;
        switch (((String)llllllllllllllllllllllIlIllIIIlI).hashCode()) {
            case -1911998296: {
                if (((String)llllllllllllllllllllllIlIllIIIlI).equals("Packet")) {
                    llllllllllllllllllllllIlIllIIIIl = 0;
                    break;
                }
                break;
            }
        }
        switch (llllllllllllllllllllllIlIllIIIIl) {
            case 0.0f: {
                if (NoFall.mc.thePlayer.fallDistance > 2.0f && llllllllllllllllllllllIlIllIIlIl.getPacket() instanceof C03PacketPlayer) {
                    final C03PacketPlayer llllllllllllllllllllllIlIllIIlll = llllllllllllllllllllllIlIllIIlIl.getPacket();
                    llllllllllllllllllllllIlIllIIlll.onGround = true;
                    break;
                }
                break;
            }
        }
    }
    
    @Subscribe
    public void onCollide(final BlockCollisionEvent llllllllllllllllllllllIlIllllIlI) {
        final char llllllllllllllllllllllIlIllllIIl = ((Value<Character>)this.mode).getValue();
        double llllllllllllllllllllllIlIllllIII = -1;
        switch (((String)llllllllllllllllllllllIlIllllIIl).hashCode()) {
            case -1680865250: {
                if (((String)llllllllllllllllllllllIlIllllIIl).equals("Collide")) {
                    llllllllllllllllllllllIlIllllIII = 0;
                    break;
                }
                break;
            }
        }
        switch (llllllllllllllllllllllIlIllllIII) {
            case 0.0: {
                final Flight llllllllllllllllllllllIlIllllllI = (Flight)Client.getInstance().getModuleManager().getModuleByName("Flight");
                if (llllllllllllllllllllllIlIllllllI.isToggled()) {
                    return;
                }
                if (NoFall.mc.gameSettings.keyBindSneak.pressed) {
                    return;
                }
                if (NoFall.mc.thePlayer.fallDistance > 2.5) {
                    llllllllllllllllllllllIlIllllIlI.setAxisAlignedBB(new AxisAlignedBB(-2.0, -1.0, -2.0, 2.0, 1.0, 2.0).offset(llllllllllllllllllllllIlIllllIlI.getX(), llllllllllllllllllllllIlIllllIlI.getY(), llllllllllllllllllllllIlIllllIlI.getZ()));
                    break;
                }
                break;
            }
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llllllllllllllllllllllIlIllIllll) {
        if (llllllllllllllllllllllIlIllIllll.isPre() && NoFall.mc.thePlayer.fallDistance > 2.0f) {
            final short llllllllllllllllllllllIlIllIlllI = (short)this.mode.getValueAsString();
            String llllllllllllllllllllllIlIllIllIl = (String)(-1);
            switch (((String)llllllllllllllllllllllIlIllIlllI).hashCode()) {
                case 80099049: {
                    if (((String)llllllllllllllllllllllIlIllIlllI).equals("Spoof")) {
                        llllllllllllllllllllllIlIllIllIl = (String)0;
                        break;
                    }
                    break;
                }
            }
            switch (llllllllllllllllllllllIlIllIllIl) {
                case 0L: {
                    llllllllllllllllllllllIlIllIllll.setOnGround(true);
                    break;
                }
            }
        }
    }
    
    public NoFall() {
        this.mode = new ModeValue("Mode", this, new String[] { "Packet", "Spoof", "Collide" });
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
}
