package com.viaversion.viaversion.bungee.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import net.md_5.bungee.event.*;

public class ElytraPatch implements Listener
{
    @EventHandler(priority = 32)
    public void onServerConnected(final ServerConnectedEvent event) {
        final UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(event.getPlayer().getUniqueId());
        if (user == null) {
            return;
        }
        try {
            if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                final EntityTracker1_9 tracker = user.getEntityTracker(Protocol1_9To1_8.class);
                final int entityId = tracker.getProvidedEntityId();
                final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, null, user);
                wrapper.write(Type.VAR_INT, entityId);
                wrapper.write(Types1_9.METADATA_LIST, Collections.singletonList(new Metadata(0, MetaType1_9.Byte, 0)));
                wrapper.scheduleSend(Protocol1_9To1_8.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
