package com.viaversion.viabackwards.protocol.protocol1_10to1_11;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_10To1_11 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityPackets1_11 entityPackets;
    private BlockItemPackets1_11 blockItemPackets;
    
    public Protocol1_10To1_11() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityPackets = new EntityPackets1_11(this);
    }
    
    @Override
    protected void registerPackets() {
        (this.blockItemPackets = new BlockItemPackets1_11(this)).register();
        this.entityPackets.register();
        new PlayerPackets1_11().register(this);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerNamedSound(ClientboundPackets1_9_3.NAMED_SOUND);
        soundRewriter.registerSound(ClientboundPackets1_9_3.SOUND);
    }
    
    @Override
    public void init(final UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_11Types.EntityType.PLAYER, true));
        if (!user.has(WindowTracker.class)) {
            user.put(new WindowTracker());
        }
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_10To1_11.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_11 getEntityRewriter() {
        return this.entityPackets;
    }
    
    @Override
    public BlockItemPackets1_11 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.11", "1.10", null, true);
    }
}
