package com.viaversion.viaversion.velocity.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.util.*;
import com.viaversion.viaversion.velocity.storage.*;
import com.viaversion.viaversion.api.connection.*;

public class VelocityBossBarProvider extends BossBarProvider
{
    @Override
    public void handleAdd(final UserConnection user, final UUID barUUID) {
        if (user.has(VelocityStorage.class)) {
            final VelocityStorage storage = user.get(VelocityStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().add(barUUID);
            }
        }
    }
    
    @Override
    public void handleRemove(final UserConnection user, final UUID barUUID) {
        if (user.has(VelocityStorage.class)) {
            final VelocityStorage storage = user.get(VelocityStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().remove(barUUID);
            }
        }
    }
}
