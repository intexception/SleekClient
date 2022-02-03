package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;

public class SpawnerHandler implements BlockEntityProvider.BlockEntityHandler
{
    @Override
    public int transform(final UserConnection user, final CompoundTag tag) {
        if (tag.contains("SpawnData") && tag.get("SpawnData") instanceof CompoundTag) {
            final CompoundTag data = tag.get("SpawnData");
            if (data.contains("id") && data.get("id") instanceof StringTag) {
                final StringTag s = data.get("id");
                s.setValue(EntityNameRewriter.rewrite(s.getValue()));
            }
        }
        return -1;
    }
}
