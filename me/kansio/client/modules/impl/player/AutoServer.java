package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import me.kansio.client.value.*;

@ModuleData(name = "Auto Server", category = ModuleCategory.PLAYER, description = "Automatically does actions on certain servers")
public class AutoServer extends Module
{
    private /* synthetic */ ModeValue modeValue;
    private /* synthetic */ boolean hasWorldChanged;
    private /* synthetic */ boolean hasClickedKitSelector;
    private /* synthetic */ boolean hasClickedAutoPlay;
    private /* synthetic */ boolean hasSelectedKit;
    private /* synthetic */ ModeValue kitValue;
    
    public AutoServer() {
        this.hasClickedAutoPlay = false;
        this.hasSelectedKit = false;
        this.hasWorldChanged = false;
        this.hasClickedKitSelector = false;
        this.modeValue = new ModeValue("Server", this, new String[] { "BlocksMC" });
        this.kitValue = new ModeValue("Kit", this, this.modeValue, new String[] { "BlocksMC" }, new String[] { "Armorer", "Knight" });
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIIlIIlIllIlIl) {
        if (AutoServer.mc.thePlayer.ticksExisted < 5) {
            this.hasClickedAutoPlay = false;
            this.hasSelectedKit = false;
            this.hasWorldChanged = false;
            this.hasClickedKitSelector = false;
        }
        final long lIIlIIlIllIIll = ((Value<Long>)this.modeValue).getValue();
        short lIIlIIlIllIIlI = -1;
        switch (((String)lIIlIIlIllIIll).hashCode()) {
            case -599920196: {
                if (((String)lIIlIIlIllIIll).equals("BlocksMC")) {
                    lIIlIIlIllIIlI = 0;
                    break;
                }
                break;
            }
        }
        switch (lIIlIIlIllIIlI) {
            case 0: {
                if (!this.hasSelectedKit && !this.hasClickedKitSelector && AutoServer.mc.thePlayer.ticksExisted > 5) {
                    for (int lIIlIIlIllIlll = 0; lIIlIIlIllIlll < AutoServer.mc.thePlayer.inventory.mainInventory.length; ++lIIlIIlIllIlll) {
                        final ItemStack lIIlIIlIlllIII = AutoServer.mc.thePlayer.inventory.getStackInSlot(lIIlIIlIllIlll);
                        if (lIIlIIlIlllIII != null) {
                            if (lIIlIIlIlllIII.getDisplayName().contains("Kit Selector")) {
                                if (AutoServer.mc.currentScreen != null) {
                                    return;
                                }
                                if (!(AutoServer.mc.thePlayer.inventory.getStackInSlot(0).getItem() instanceof ItemSkull)) {
                                    AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(0));
                                }
                                else {
                                    AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(1));
                                }
                                AutoServer.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(lIIlIIlIlllIII));
                                AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(AutoServer.mc.thePlayer.inventory.currentItem));
                            }
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIIlIIllIIIllI) {
        final short lIIlIIllIIIIll = (short)this.modeValue.getValueAsString();
        int lIIlIIllIIIIlI = -1;
        switch (((String)lIIlIIllIIIIll).hashCode()) {
            case -599920196: {
                if (((String)lIIlIIllIIIIll).equals("BlocksMC")) {
                    lIIlIIllIIIIlI = 0;
                    break;
                }
                break;
            }
            case -1248403467: {
                if (((String)lIIlIIllIIIIll).equals("Hypixel")) {
                    lIIlIIllIIIIlI = 1;
                    break;
                }
                break;
            }
        }
        switch (lIIlIIllIIIIlI) {
            case 0: {
                final Packet lIIlIIllIIlIII = lIIlIIllIIIllI.getPacket();
                if (lIIlIIllIIIllI.getPacket() instanceof S2FPacketSetSlot) {
                    final ItemStack lIIlIIllIIlIll = lIIlIIllIIIllI.getPacket().func_149174_e();
                    final int lIIlIIllIIlIlI = lIIlIIllIIIllI.getPacket().func_149173_d();
                    if (lIIlIIllIIlIll == null) {
                        return;
                    }
                    if (lIIlIIllIIlIll.getDisplayName() != null && lIIlIIllIIlIll.getDisplayName().contains("Play Again")) {
                        AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(7));
                        for (int lIIlIIllIIllIl = 0; lIIlIIllIIllIl < 2; ++lIIlIIllIIllIl) {
                            AutoServer.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(lIIlIIllIIlIll));
                        }
                        this.hasWorldChanged = true;
                        AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(AutoServer.mc.thePlayer.inventory.currentItem));
                    }
                    if (!this.hasSelectedKit && lIIlIIllIIlIll.getDisplayName() != null && lIIlIIllIIlIll.getDisplayName().contains("Kit Selector")) {
                        if (AutoServer.mc.currentScreen != null) {
                            return;
                        }
                        AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(0));
                        for (int lIIlIIllIIllII = 0; lIIlIIllIIllII < 2; ++lIIlIIllIIllII) {
                            AutoServer.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(lIIlIIllIIlIll));
                        }
                        AutoServer.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(AutoServer.mc.thePlayer.inventory.currentItem));
                    }
                }
                if (lIIlIIllIIIllI.getPacket() instanceof S2DPacketOpenWindow) {
                    final S2DPacketOpenWindow lIIlIIllIIlIIl = lIIlIIllIIIllI.getPacket();
                    if (lIIlIIllIIlIIl.getWindowTitle().getFormattedText().contains("Kits")) {
                        final int lIIlIIlIllllll = ((Value<Integer>)this.kitValue).getValue();
                        int n = -1;
                        switch (((String)lIIlIIlIllllll).hashCode()) {
                            case 926003724: {
                                if (((String)lIIlIIlIllllll).equals("Armorer")) {
                                    n = 0;
                                    break;
                                }
                                break;
                            }
                            case -2042963283: {
                                if (((String)lIIlIIlIllllll).equals("Knight")) {
                                    n = 1;
                                    break;
                                }
                                break;
                            }
                        }
                        switch (n) {
                            case 0: {
                                AutoServer.mc.playerController.windowClick(lIIlIIllIIlIIl.getWindowId(), 0, 1, 0, AutoServer.mc.thePlayer);
                                break;
                            }
                            case 1: {
                                AutoServer.mc.playerController.windowClick(lIIlIIllIIlIIl.getWindowId(), 18, 1, 0, AutoServer.mc.thePlayer);
                                break;
                            }
                        }
                        this.hasSelectedKit = true;
                    }
                    break;
                }
                break;
            }
        }
    }
}
