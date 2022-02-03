package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;

public final class EntityPackets extends EntityRewriter<Protocol1_18To1_17_1>
{
    public EntityPackets(final Protocol1_18To1_17_1 protocol) {
        super(protocol);
    }
    
    public void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
        ((AbstractProtocol<ClientboundPackets1_17_1, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17_1.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                final int chunkRadius;
                this.handler(wrapper -> {
                    chunkRadius = wrapper.passthrough((Type<Integer>)Type.VAR_INT);
                    wrapper.write(Type.VAR_INT, chunkRadius);
                    return;
                });
                this.handler(EntityPackets.this.worldDataTrackerHandler(1));
                this.handler(EntityPackets.this.biomeSizeTracker());
            }
        });
        ((AbstractProtocol<ClientboundPackets1_17_1, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_17_1.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                final String world;
                final EntityTracker tracker;
                this.handler(wrapper -> {
                    world = wrapper.get(Type.STRING, 0);
                    tracker = ((com.viaversion.viaversion.api.rewriter.EntityRewriter<Protocol>)EntityPackets.this).tracker(wrapper.user());
                    if (!world.equals(tracker.currentWorld())) {
                        wrapper.user().get(ChunkLightStorage.class).clear();
                    }
                    return;
                });
                this.handler(EntityPackets.this.worldDataTrackerHandler(0));
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        Particle particle;
        this.filter().handler((event, meta) -> {
            meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
            if (meta.metaType() == Types1_18.META_TYPES.particleType) {
                particle = (Particle)meta.getValue();
                if (particle.getId() == 2) {
                    particle.setId(3);
                    particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7754));
                }
                else if (particle.getId() == 3) {
                    particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7786));
                }
                else {
                    this.rewriteParticle(particle);
                }
            }
            return;
        });
        this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null);
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_17Types.getTypeFromId(type);
    }
}
