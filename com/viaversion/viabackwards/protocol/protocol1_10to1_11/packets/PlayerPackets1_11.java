package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

public class PlayerPackets1_11
{
    private static final ValueTransformer<Short, Float> TO_NEW_FLOAT;
    
    public void register(final Protocol1_10To1_11 protocol) {
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)protocol).registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                final int action;
                JsonElement message;
                String legacy;
                JsonElement message2;
                this.handler(wrapper -> {
                    action = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                    if (action == 2) {
                        message = wrapper.read(Type.COMPONENT);
                        wrapper.clearPacket();
                        wrapper.setId(ClientboundPackets1_9_3.CHAT_MESSAGE.ordinal());
                        legacy = LegacyComponentSerializer.legacySection().serialize(((ComponentSerializer<I, Component, String>)GsonComponentSerializer.gson()).deserialize(message.toString()));
                        message2 = new JsonObject();
                        message2.getAsJsonObject().addProperty("text", legacy);
                        wrapper.write(Type.COMPONENT, message2);
                        wrapper.write(Type.BYTE, (Byte)2);
                    }
                    else if (action > 2) {
                        wrapper.set(Type.VAR_INT, 0, action - 1);
                    }
                });
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9_3, C2, S1, S2>)protocol).registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> wrapper.read((Type<Object>)Type.VAR_INT));
            }
        });
        ((AbstractProtocol<C1, C2, S1, ServerboundPackets1_9_3>)protocol).registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map((Type<Object>)Type.UNSIGNED_BYTE, (ValueTransformer<Object, Object>)PlayerPackets1_11.TO_NEW_FLOAT);
                this.map((Type<Object>)Type.UNSIGNED_BYTE, (ValueTransformer<Object, Object>)PlayerPackets1_11.TO_NEW_FLOAT);
                this.map((Type<Object>)Type.UNSIGNED_BYTE, (ValueTransformer<Object, Object>)PlayerPackets1_11.TO_NEW_FLOAT);
            }
        });
    }
    
    static {
        TO_NEW_FLOAT = new ValueTransformer<Short, Float>() {
            @Override
            public Float transform(final PacketWrapper wrapper, final Short inputValue) throws Exception {
                return inputValue / 15.0f;
            }
        };
    }
}
