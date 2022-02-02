package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public class Protocol1_9_4To1_10 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
{
    public static final BackwardsMappings MAPPINGS;
    private static final ValueTransformer<Float, Short> TO_OLD_PITCH;
    private final EntityPackets1_10 entityPackets;
    private final BlockItemPackets1_10 blockItemPackets;
    
    public Protocol1_9_4To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityPackets = new EntityPackets1_10(this);
        this.blockItemPackets = new BlockItemPackets1_10(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityPackets.register();
        this.blockItemPackets.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map((Type<Object>)Type.FLOAT, (ValueTransformer<Object, Object>)Protocol1_9_4To1_10.TO_OLD_PITCH);
                this.handler(soundRewriter.getNamedSoundHandler());
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map((Type<Object>)Type.FLOAT, (ValueTransformer<Object, Object>)Protocol1_9_4To1_10.TO_OLD_PITCH);
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_9_3>)this).registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.VAR_INT);
            }
        });
    }
    
    @Override
    public void init(final UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_10Types.EntityType.PLAYER));
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_9_4To1_10.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_10 getEntityRewriter() {
        return this.entityPackets;
    }
    
    @Override
    public BlockItemPackets1_10 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.10", "1.9.4", null, true);
        TO_OLD_PITCH = new ValueTransformer<Float, Short>() {
            @Override
            public Short transform(final PacketWrapper packetWrapper, final Float inputValue) throws Exception {
                return (short)Math.round(inputValue * 63.5f);
            }
        };
    }
}
