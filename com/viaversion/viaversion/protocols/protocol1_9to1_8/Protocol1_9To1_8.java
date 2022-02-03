package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;

public class Protocol1_9To1_8 extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_9, ServerboundPackets1_8, ServerboundPackets1_9>
{
    public static final ValueTransformer<String, JsonElement> FIX_JSON;
    private final EntityRewriter metadataRewriter;
    
    public Protocol1_9To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
        this.metadataRewriter = new MetadataRewriter1_9To1_8(this);
    }
    
    public static JsonElement fixJson(String line) {
        if (line == null || line.equalsIgnoreCase("null")) {
            line = "{\"text\":\"\"}";
        }
        else {
            if ((!line.startsWith("\"") || !line.endsWith("\"")) && (!line.startsWith("{") || !line.endsWith("}"))) {
                return constructJson(line);
            }
            if (line.startsWith("\"") && line.endsWith("\"")) {
                line = "{\"text\":" + line + "}";
            }
        }
        try {
            return GsonUtil.getGson().fromJson(line, JsonObject.class);
        }
        catch (Exception e) {
            if (Via.getConfig().isForceJsonTransform()) {
                return constructJson(line);
            }
            Via.getPlatform().getLogger().warning("Invalid JSON String: \"" + line + "\" Please report this issue to the ViaVersion Github: " + e.getMessage());
            return GsonUtil.getGson().fromJson("{\"text\":\"\"}", JsonObject.class);
        }
    }
    
    private static JsonElement constructJson(final String text) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        return jsonObject;
    }
    
    public static Item getHandItem(final UserConnection info) {
        return Via.getManager().getProviders().get(HandItemProvider.class).getHandItem(info);
    }
    
    public static boolean isSword(final int id) {
        return id == 267 || id == 268 || id == 272 || id == 276 || id == 283;
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    if (!wrapper.isReadable(Type.COMPONENT, 0)) {
                        wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(wrapper.read(Type.STRING)));
                    }
                });
            }
        });
        SpawnPackets.register(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
    }
    
    @Override
    public void register(final ViaProviders providers) {
        providers.register(HandItemProvider.class, new HandItemProvider());
        providers.register(CommandBlockProvider.class, new CommandBlockProvider());
        providers.register(EntityIdProvider.class, new EntityIdProvider());
        providers.register(BossBarProvider.class, new BossBarProvider());
        providers.register(MainHandProvider.class, new MainHandProvider());
        providers.register(CompressionProvider.class, new CompressionProvider());
        providers.require(MovementTransmitterProvider.class);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_9(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new MovementTracker());
        userConnection.put(new InventoryTracker());
        userConnection.put(new CommandBlockStorage());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
    
    static {
        FIX_JSON = new ValueTransformer<String, JsonElement>() {
            @Override
            public JsonElement transform(final PacketWrapper wrapper, final String line) {
                return Protocol1_9To1_8.fixJson(line);
            }
        };
    }
}
