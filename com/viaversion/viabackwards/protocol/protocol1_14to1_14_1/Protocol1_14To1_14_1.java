package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.data.entity.*;

public class Protocol1_14To1_14_1 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
{
    private final EntityRewriter entityRewriter;
    
    public Protocol1_14To1_14_1() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
        this.entityRewriter = new EntityPackets1_14_1(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
    }
    
    @Override
    public void init(final UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_15Types.PLAYER));
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
}
