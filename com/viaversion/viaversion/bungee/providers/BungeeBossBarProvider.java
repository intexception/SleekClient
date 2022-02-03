package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.util.*;
import com.viaversion.viaversion.bungee.storage.*;
import com.viaversion.viaversion.api.connection.*;

public class BungeeBossBarProvider extends BossBarProvider
{
    @Override
    public void handleAdd(final UserConnection user, final UUID barUUID) {
        if (user.has(BungeeStorage.class)) {
            final BungeeStorage storage = user.get(BungeeStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().add(barUUID);
            }
        }
    }
    
    @Override
    public void handleRemove(final UserConnection user, final UUID barUUID) {
        if (user.has(BungeeStorage.class)) {
            final BungeeStorage storage = user.get(BungeeStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().remove(barUUID);
            }
        }
    }
}
