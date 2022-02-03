package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import me.kansio.client.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.friend.*;
import com.google.common.eventbus.*;

@ModuleData(name = "MCF", category = ModuleCategory.PLAYER, description = "Middle click a player to add them as a friend")
public class MCF extends Module
{
    @Subscribe
    public void onMouse(final MouseEvent llIIIIllIll) {
        if (llIIIIllIll.getButton() == 2 && MCF.mc.objectMouseOver != null && MCF.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer llIIIIlllll = (EntityPlayer)MCF.mc.objectMouseOver.entityHit;
            final String llIIIIllllI = llIIIIlllll.getName();
            if (Client.getInstance().getFriendManager().isFriend(llIIIIllllI)) {
                Client.getInstance().getFriendManager().removeFriend(llIIIIllllI);
                ChatUtil.log(String.valueOf(new StringBuilder().append("Removed ").append(llIIIIllllI).append(" as a friend!")));
            }
            else {
                Client.getInstance().getFriendManager().addFriend(new Friend(llIIIIllllI, llIIIIllllI));
                ChatUtil.log(String.valueOf(new StringBuilder().append("Added ").append(llIIIIllllI).append(" as a friend!")));
            }
        }
    }
}
