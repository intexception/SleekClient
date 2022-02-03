package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;
import com.google.common.eventbus.*;
import me.kansio.client.value.*;

@ModuleData(name = "No Rotate", category = ModuleCategory.PLAYER, description = "Prevents the server from setting your head pos")
public class NoRotate extends Module
{
    private /* synthetic */ ModeValue mode;
    
    public NoRotate() {
        this.mode = new ModeValue("Mode", this, new String[] { "Cancel", "Packet", "Spoof" });
    }
    
    @Subscribe
    public void onSendPacket(final PacketEvent llllIlllIlIIl) {
        final byte llllIlllIlIII = ((Value<Byte>)this.mode).getValue();
        char llllIlllIIlll = (char)(-1);
        switch (((String)llllIlllIlIII).hashCode()) {
            case 2011110042: {
                if (((String)llllIlllIlIII).equals("Cancel")) {
                    llllIlllIIlll = '\0';
                    break;
                }
                break;
            }
            case -1911998296: {
                if (((String)llllIlllIlIII).equals("Packet")) {
                    llllIlllIIlll = '\u0001';
                    break;
                }
                break;
            }
            case 80099049: {
                if (((String)llllIlllIlIII).equals("Spoof")) {
                    llllIlllIIlll = '\u0002';
                    break;
                }
                break;
            }
        }
        switch (llllIlllIIlll) {
            case '\0': {
                if (llllIlllIlIIl.getPacket() instanceof S08PacketPlayerPosLook) {
                    final S08PacketPlayerPosLook llllIlllIllll = llllIlllIlIIl.getPacket();
                    if (NoRotate.mc.thePlayer != null && NoRotate.mc.theWorld != null && NoRotate.mc.thePlayer.rotationYaw != -180.0f && NoRotate.mc.thePlayer.rotationPitch != 0.0f) {
                        llllIlllIlIIl.setCancelled(true);
                    }
                    break;
                }
                break;
            }
            case '\u0001': {
                if (llllIlllIlIIl.getPacket() instanceof S08PacketPlayerPosLook) {
                    final S08PacketPlayerPosLook llllIlllIlllI = llllIlllIlIIl.getPacket();
                    if (NoRotate.mc.thePlayer != null && NoRotate.mc.theWorld != null && NoRotate.mc.thePlayer.rotationYaw != -180.0f && NoRotate.mc.thePlayer.rotationPitch != 0.0f) {
                        llllIlllIlllI.yaw = NoRotate.mc.thePlayer.rotationYaw;
                        llllIlllIlllI.pitch = NoRotate.mc.thePlayer.rotationPitch;
                    }
                    break;
                }
                break;
            }
            case '\u0002': {
                if (!(llllIlllIlIIl.getPacket() instanceof S08PacketPlayerPosLook)) {
                    break;
                }
                final S08PacketPlayerPosLook llllIlllIllIl = llllIlllIlIIl.getPacket();
                if (NoRotate.mc.thePlayer != null && NoRotate.mc.theWorld != null && NoRotate.mc.thePlayer.rotationYaw != -180.0f && NoRotate.mc.thePlayer.rotationPitch != 0.0f) {
                    llllIlllIllIl.yaw = NoRotate.mc.thePlayer.rotationYaw;
                    llllIlllIllIl.pitch = NoRotate.mc.thePlayer.rotationPitch;
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(NoRotate.mc.thePlayer.posX, NoRotate.mc.thePlayer.posY, NoRotate.mc.thePlayer.posZ, llllIlllIllIl.getYaw(), llllIlllIllIl.getPitch(), false));
                    break;
                }
                break;
            }
        }
    }
}
