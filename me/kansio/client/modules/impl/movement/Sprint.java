package me.kansio.client.modules.impl.movement;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.play.client.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.value.*;

@ModuleData(name = "Sprint", category = ModuleCategory.MOVEMENT, description = "Automatically sprints")
public class Sprint extends Module
{
    private /* synthetic */ ModeValue mode;
    private /* synthetic */ boolean skip;
    private final /* synthetic */ BooleanValue keepSprint;
    
    @Subscribe
    public void onPacket(final PacketEvent llllllllllllllllllllIllllIIlllll) {
        if (llllllllllllllllllllIllllIIlllll.getPacketDirection().name().equalsIgnoreCase("INBOUND") && !(llllllllllllllllllllIllllIIlllll.getPacket() instanceof C03PacketPlayer) && this.keepSprint.getValue() && llllllllllllllllllllIllllIIlllll.getPacket() instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction llllllllllllllllllllIllllIlIIIll = llllllllllllllllllllIllllIIlllll.getPacket();
            if (llllllllllllllllllllIllllIIlllll.getPacket().getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                llllllllllllllllllllIllllIIlllll.setCancelled(true);
            }
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent llllllllllllllllllllIllllIlIlIlI) {
        if (Sprint.mc.thePlayer.isSneaking()) {
            return;
        }
        final int llllllllllllllllllllIllllIlIlIII = ((Value<Integer>)this.mode).getValue();
        int llllllllllllllllllllIllllIlIIlll = -1;
        switch (((String)llllllllllllllllllllIllllIlIlIII).hashCode()) {
            case 73298841: {
                if (((String)llllllllllllllllllllIllllIlIlIII).equals("Legit")) {
                    llllllllllllllllllllIllllIlIIlll = 0;
                    break;
                }
                break;
            }
            case 2461753: {
                if (((String)llllllllllllllllllllIllllIlIlIII).equals("Omni")) {
                    llllllllllllllllllllIllllIlIIlll = 1;
                    break;
                }
                break;
            }
        }
        switch (llllllllllllllllllllIllllIlIIlll) {
            case 0: {
                if (!this.skip) {
                    Sprint.mc.thePlayer.setSprinting(!Sprint.mc.thePlayer.isCollidedHorizontally && !Sprint.mc.thePlayer.isSneaking() && Sprint.mc.thePlayer.getFoodStats().getFoodLevel() > 5 && Sprint.mc.gameSettings.keyBindForward.pressed);
                    break;
                }
                this.skip = false;
                break;
            }
            case 1: {
                if (Sprint.mc.thePlayer.isMoving() && !Sprint.mc.thePlayer.isSprinting()) {
                    Sprint.mc.thePlayer.setSprinting(true);
                    break;
                }
                break;
            }
        }
    }
    
    public BooleanValue getKeepSprint() {
        return this.keepSprint;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Sprint.mc.thePlayer.setSprinting(false);
    }
    
    public Sprint() {
        this.mode = new ModeValue("Mode", this, new String[] { "Legit", "Omni" });
        this.keepSprint = new BooleanValue("Keep Sprint", this, true);
    }
}
