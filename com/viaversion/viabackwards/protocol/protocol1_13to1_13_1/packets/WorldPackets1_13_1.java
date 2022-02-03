package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class WorldPackets1_13_1
{
    public static void register(final Protocol protocol) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION);
        protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        final Chunk chunk = wrapper.passthrough((Type<Chunk>)new Chunk1_13Type(clientWorld));
                        for (final ChunkSection section : chunk.getSections()) {
                            if (section != null) {
                                for (int i = 0; i < section.getPaletteSize(); ++i) {
                                    section.setPaletteEntry(i, protocol.getMappingData().getNewBlockStateId(section.getPaletteEntry(i)));
                                }
                            }
                        }
                    }
                });
            }
        });
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_13.EFFECT, 1010, 2001);
    }
}
