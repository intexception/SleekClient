package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.entity.*;

public class PlayerPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_14.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(wrapper -> wrapper.write(Type.LONG, 0L));
            }
        });
        protocol.registerClientbound(ClientboundPackets1_14.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                final EntityTracker tracker;
                final int entityId;
                this.handler(wrapper -> {
                    tracker = wrapper.user().getEntityTracker(Protocol1_15To1_14_4.class);
                    entityId = wrapper.get((Type<Integer>)Type.INT, 0);
                    tracker.addEntity(entityId, Entity1_15Types.PLAYER);
                    return;
                });
                this.handler(wrapper -> wrapper.write(Type.LONG, 0L));
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(wrapper -> wrapper.write(Type.BOOLEAN, !Via.getConfig().is1_15InstantRespawn()));
            }
        });
    }
}
