package com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.sponge4;

import com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.*;
import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.item.inventory.*;

public class Sponge4ItemGrabber implements ItemGrabber
{
    @Override
    public ItemStack getItem(final Player player) {
        return player.getItemInHand().orElse(null);
    }
}
