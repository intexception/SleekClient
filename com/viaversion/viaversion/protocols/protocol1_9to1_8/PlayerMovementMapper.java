package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.type.*;

public class PlayerMovementMapper implements PacketHandler
{
    @Override
    public void handle(final PacketWrapper wrapper) throws Exception {
        final MovementTracker tracker = wrapper.user().get(MovementTracker.class);
        tracker.incrementIdlePacket();
        if (wrapper.is(Type.BOOLEAN, 0)) {
            tracker.setGround(wrapper.get((Type<Boolean>)Type.BOOLEAN, 0));
        }
    }
}
