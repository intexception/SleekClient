package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import net.minecraft.client.settings.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Inventory Move", category = ModuleCategory.PLAYER, description = "Move whilst having an inventory open")
public class InvMove extends Module
{
    public /* synthetic */ ModeValue mode;
    private final /* synthetic */ KeyBinding[] keyBindings;
    public /* synthetic */ BooleanValue noclose;
    
    @Subscribe
    public void onPacket(final PacketEvent lIIIlIIlIllIIl) {
        if (this.noclose.getValue()) {
            if (lIIIlIIlIllIIl.getPacket() instanceof S2EPacketCloseWindow && InvMove.mc.currentScreen instanceof GuiInventory) {
                lIIIlIIlIllIIl.setCancelled(true);
            }
            if (InvMove.mc.currentScreen == null || InvMove.mc.currentScreen instanceof GuiChat) {
                return;
            }
        }
        final int lIIIlIIlIlIllI = (Object)this.keyBindings;
        final Exception lIIIlIIlIlIlIl = (Exception)lIIIlIIlIlIllI.length;
        for (byte lIIIlIIlIlIlII = 0; lIIIlIIlIlIlII < lIIIlIIlIlIlIl; ++lIIIlIIlIlIlII) {
            final KeyBinding lIIIlIIlIllIll = lIIIlIIlIlIllI[lIIIlIIlIlIlII];
            lIIIlIIlIllIll.pressed = Keyboard.isKeyDown(lIIIlIIlIllIll.getKeyCode());
        }
    }
    
    public InvMove() {
        this.mode = new ModeValue("Mode", this, new String[] { "Vanilla" });
        this.noclose = new BooleanValue("No Close", this, true);
        this.keyBindings = new KeyBinding[] { InvMove.mc.gameSettings.keyBindForward, InvMove.mc.gameSettings.keyBindRight, InvMove.mc.gameSettings.keyBindLeft, InvMove.mc.gameSettings.keyBindBack, InvMove.mc.gameSettings.keyBindJump, InvMove.mc.gameSettings.keyBindSprint };
    }
}
