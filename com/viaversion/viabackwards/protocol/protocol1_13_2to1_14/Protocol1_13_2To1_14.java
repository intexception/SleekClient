package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.protocol.*;

public class Protocol1_13_2To1_14 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_13, ServerboundPackets1_14, ServerboundPackets1_13>
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_14 blockItemPackets;
    
    public Protocol1_13_2To1_14() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
        this.entityRewriter = new EntityPackets1_14(this);
        this.translatableRewriter = new TranslatableRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_14To1_13_2> protocolClass = Protocol1_14To1_13_2.class;
        final BackwardsMappings mappings = Protocol1_13_2To1_14.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(protocolClass, mappings::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_14.BOSSBAR);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_14.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_14.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_14.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_14.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_14.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_14.DECLARE_COMMANDS);
        (this.blockItemPackets = new BlockItemPackets1_14(this)).register();
        this.entityRewriter.register();
        new PlayerPackets1_14(this).register();
        new SoundPackets1_14(this).register();
        new StatisticsRewriter(this).register(ClientboundPackets1_14.STATISTICS);
        ((AbstractProtocol<ClientboundPackets1_14, C2, S1, S2>)this).cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
        ((AbstractProtocol<ClientboundPackets1_14, C2, S1, S2>)this).cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE);
        ((AbstractProtocol<ClientboundPackets1_14, C2, S1, S2>)this).cancelClientbound(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
        ((AbstractProtocol<ClientboundPackets1_14, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_14.TAGS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        for (int blockTagsSize = wrapper.passthrough((Type<Integer>)Type.VAR_INT), i = 0; i < blockTagsSize; ++i) {
                            wrapper.passthrough(Type.STRING);
                            final int[] blockIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j = 0; j < blockIds.length; ++j) {
                                final int id = blockIds[j];
                                final int blockId = Protocol1_13_2To1_14.this.getMappingData().getNewBlockId(id);
                                blockIds[j] = blockId;
                            }
                        }
                        for (int itemTagsSize = wrapper.passthrough((Type<Integer>)Type.VAR_INT), k = 0; k < itemTagsSize; ++k) {
                            wrapper.passthrough(Type.STRING);
                            final int[] itemIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int l = 0; l < itemIds.length; ++l) {
                                final int itemId = itemIds[l];
                                final int oldId = Protocol1_13_2To1_14.this.getMappingData().getItemMappings().get(itemId);
                                itemIds[l] = oldId;
                            }
                        }
                        for (int fluidTagsSize = wrapper.passthrough((Type<Integer>)Type.VAR_INT), m = 0; m < fluidTagsSize; ++m) {
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                        for (int entityTagsSize = wrapper.read((Type<Integer>)Type.VAR_INT), i2 = 0; i2 < entityTagsSize; ++i2) {
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                    }
                });
            }
        });
        ((Protocol<ClientboundPackets1_14, ClientboundPackets1_13, S1, S2>)this).registerClientbound(ClientboundPackets1_14.UPDATE_LIGHT, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int x = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final int z = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final int skyLightMask = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final int blockLightMask = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final int emptySkyLightMask = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final int emptyBlockLightMask = wrapper.read((Type<Integer>)Type.VAR_INT);
                        final byte[][] skyLight = new byte[16][];
                        if (this.isSet(skyLightMask, 0)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        for (int i = 0; i < 16; ++i) {
                            if (this.isSet(skyLightMask, i + 1)) {
                                skyLight[i] = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            }
                            else if (this.isSet(emptySkyLightMask, i + 1)) {
                                skyLight[i] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                        }
                        if (this.isSet(skyLightMask, 17)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        final byte[][] blockLight = new byte[16][];
                        if (this.isSet(blockLightMask, 0)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        for (int j = 0; j < 16; ++j) {
                            if (this.isSet(blockLightMask, j + 1)) {
                                blockLight[j] = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            }
                            else if (this.isSet(emptyBlockLightMask, j + 1)) {
                                blockLight[j] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                        }
                        if (this.isSet(blockLightMask, 17)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        wrapper.user().get(ChunkLightStorage.class).setStoredLight(skyLight, blockLight, x, z);
                        wrapper.cancel();
                    }
                    
                    private boolean isSet(final int mask, final int i) {
                        return (mask & 1 << i) != 0x0;
                    }
                });
            }
        });
    }
    
    @Override
    public void init(final UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_14Types.PLAYER, true));
        if (!user.has(ChunkLightStorage.class)) {
            user.put(new ChunkLightStorage(user));
        }
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_13_2To1_14.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_14 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.14", "1.13.2", Protocol1_14To1_13_2.class, true);
    }
}
