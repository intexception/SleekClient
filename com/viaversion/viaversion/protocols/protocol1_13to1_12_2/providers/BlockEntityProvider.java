package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockEntityProvider implements Provider
{
    private final Map<String, BlockEntityHandler> handlers;
    
    public BlockEntityProvider() {
        (this.handlers = new HashMap<String, BlockEntityHandler>()).put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:command_block", new CommandBlockHandler());
    }
    
    public int transform(final UserConnection user, final Position position, final CompoundTag tag, final boolean sendUpdate) throws Exception {
        final Tag idTag = tag.get("id");
        if (idTag == null) {
            return -1;
        }
        final String id = (String)idTag.getValue();
        final BlockEntityHandler handler = this.handlers.get(id);
        if (handler == null) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Unhandled BlockEntity " + id + " full tag: " + tag);
            }
            return -1;
        }
        final int newBlock = handler.transform(user, tag);
        if (sendUpdate && newBlock != -1) {
            this.sendBlockChange(user, position, newBlock);
        }
        return newBlock;
    }
    
    private void sendBlockChange(final UserConnection user, final Position position, final int blockId) throws Exception {
        final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, user);
        wrapper.write(Type.POSITION, position);
        wrapper.write(Type.VAR_INT, blockId);
        wrapper.send(Protocol1_13To1_12_2.class);
    }
    
    public interface BlockEntityHandler
    {
        int transform(final UserConnection p0, final CompoundTag p1);
    }
}
