package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Anti Bot", category = ModuleCategory.COMBAT, description = "Hides bots")
public class AntiBot extends Module
{
    @Subscribe
    public void onUpdate(final UpdateEvent llllllllllllllllllllIlIIllllIIII) {
        for (final EntityPlayer llllllllllllllllllllIlIIllllIIlI : AntiBot.mc.theWorld.playerEntities) {
            if (llllllllllllllllllllIlIIllllIIlI != null && llllllllllllllllllllIlIIllllIIlI.isInvisible() && llllllllllllllllllllIlIIllllIIlI != AntiBot.mc.thePlayer) {
                AntiBot.mc.theWorld.removeEntity(llllllllllllllllllllIlIIllllIIlI);
            }
        }
    }
}
