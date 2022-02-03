package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.item.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.kansio.client.utils.chat.*;
import com.google.common.eventbus.*;
import me.kansio.client.value.*;

@ModuleData(name = "Fast Bow", category = ModuleCategory.COMBAT, description = "Shoots your bow faster")
public class FastBow extends Module
{
    private /* synthetic */ boolean wasShooting;
    private /* synthetic */ NumberValue<Integer> packets;
    private /* synthetic */ BooleanValue value;
    private /* synthetic */ ModeValue mode;
    private /* synthetic */ int lastSlot;
    private /* synthetic */ int serverSideSlot;
    
    public FastBow() {
        this.wasShooting = false;
        this.packets = new NumberValue<Integer>("Packets", this, 20, 0, 1000, 1);
        this.value = new BooleanValue("Hold Bow", this, true);
        this.mode = new ModeValue("Mode", this, new String[] { "Ghostly", "Vanilla" });
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llIIlIIlllIII) {
        if (FastBow.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (FastBow.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                if (this.value.getValue()) {
                    if (FastBow.mc.thePlayer.onGround && FastBow.mc.thePlayer.getCurrentEquippedItem() != null && FastBow.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && FastBow.mc.gameSettings.keyBindUseItem.pressed) {
                        PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(FastBow.mc.thePlayer.inventory.getCurrentItem()));
                        final String s = this.mode.getValue();
                        long llIIlIIllIlIl = -1;
                        switch (s.hashCode()) {
                            case 1897755483: {
                                if (s.equals("Vanilla")) {
                                    llIIlIIllIlIl = 0;
                                    break;
                                }
                                break;
                            }
                            case 1671735356: {
                                if (s.equals("Ghostly")) {
                                    llIIlIIllIlIl = 1;
                                    break;
                                }
                                break;
                            }
                        }
                        switch (llIIlIIllIlIl) {
                            case 0L: {
                                for (int llIIlIlIIIlII = 0; llIIlIlIIIlII < this.packets.getValue(); ++llIIlIlIIIlII) {
                                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer());
                                }
                                break;
                            }
                            case 1L: {
                                if (FastBow.mc.thePlayer.ticksExisted % 6 == 0) {
                                    final double llIIlIlIIIIlI = FastBow.mc.thePlayer.posX;
                                    final double llIIlIlIIIIIl = FastBow.mc.thePlayer.posY + 1.0E-9;
                                    final double llIIlIlIIIIII = FastBow.mc.thePlayer.posZ;
                                    for (int llIIlIlIIIIll = 0; llIIlIlIIIIll < this.packets.getValue(); ++llIIlIlIIIIll) {
                                        FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(llIIlIlIIIIlI, llIIlIlIIIIIl, llIIlIlIIIIII, FastBow.mc.thePlayer.rotationYaw, FastBow.mc.thePlayer.rotationPitch, true));
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                }
                else {
                    if (!this.hasBow()) {
                        return;
                    }
                    final int llIIlIIlllIlI = this.getBowSlot();
                    if (llIIlIIlllIlI == -1) {
                        return;
                    }
                    this.serverSideSlot = llIIlIIlllIlI;
                    this.serverSideSlot = llIIlIIlllIlI;
                    PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(llIIlIIlllIlI));
                    PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlllIlI)));
                    final long llIIlIIllIlIl = ((Value<Long>)this.mode).getValue();
                    float llIIlIIllIlII = -1;
                    switch (((String)llIIlIIllIlIl).hashCode()) {
                        case 1897755483: {
                            if (((String)llIIlIIllIlIl).equals("Vanilla")) {
                                llIIlIIllIlII = 0;
                                break;
                            }
                            break;
                        }
                        case 1671735356: {
                            if (((String)llIIlIIllIlIl).equals("Ghostly")) {
                                llIIlIIllIlII = 1;
                                break;
                            }
                            break;
                        }
                    }
                    switch (llIIlIIllIlII) {
                        case 0.0f: {
                            for (int llIIlIIllllll = 0; llIIlIIllllll < this.packets.getValue(); ++llIIlIIllllll) {
                                PacketUtil.sendPacketNoEvent(new C03PacketPlayer());
                            }
                            break;
                        }
                        case 1.0f: {
                            if (FastBow.mc.thePlayer.ticksExisted % 5 == 0) {
                                final double llIIlIIllllIl = FastBow.mc.thePlayer.posX;
                                final double llIIlIIllllII = FastBow.mc.thePlayer.posY + 1.0E-9;
                                final double llIIlIIlllIll = FastBow.mc.thePlayer.posZ;
                                for (int llIIlIIlllllI = 0; llIIlIIlllllI < this.packets.getValue(); ++llIIlIIlllllI) {
                                    FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(llIIlIIllllIl, llIIlIIllllII, llIIlIIlllIll, FastBow.mc.thePlayer.rotationYaw, FastBow.mc.thePlayer.rotationPitch, true));
                                }
                                break;
                            }
                            break;
                        }
                    }
                    PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    this.wasShooting = true;
                }
            }
            else if (this.wasShooting) {
                PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(this.lastSlot));
                this.serverSideSlot = this.lastSlot;
                this.wasShooting = false;
                ChatUtil.log("revert");
            }
        }
    }
    
    public int getBowSlot() {
        for (int llIIlIIlIIlll = 0; llIIlIIlIIlll < 8; ++llIIlIIlIIlll) {
            if (FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlIIlll) != null && FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlIIlll).getItem() instanceof ItemBow) {
                return llIIlIIlIIlll;
            }
        }
        return -1;
    }
    
    public boolean hasBow() {
        for (int llIIlIIlIlIll = 0; llIIlIIlIlIll < 8; ++llIIlIIlIlIll) {
            if (FastBow.mc.thePlayer.inventory != null) {
                if (FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlIlIll) != null) {
                    if (FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlIlIll).getItem() != null) {
                        if (FastBow.mc.thePlayer.inventory.getStackInSlot(llIIlIIlIlIll).getItem() instanceof ItemBow) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
