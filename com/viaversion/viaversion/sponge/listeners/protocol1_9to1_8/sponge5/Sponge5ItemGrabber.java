package com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.sponge5;

import com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.*;
import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.item.inventory.*;
import org.spongepowered.api.data.type.*;

public class Sponge5ItemGrabber implements ItemGrabber
{
    @Override
    public ItemStack getItem(final Player player) {
        return player.getItemInHand(HandTypes.MAIN_HAND).orElse(null);
    }
}
