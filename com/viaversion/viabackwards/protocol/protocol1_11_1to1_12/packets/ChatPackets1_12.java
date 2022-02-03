package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public class ChatPackets1_12 extends RewriterBase<Protocol1_11_1To1_12>
{
    private final ComponentRewriter componentRewriter;
    
    public ChatPackets1_12(final Protocol1_11_1To1_12 protocol) {
        super(protocol);
        this.componentRewriter = new ComponentRewriter() {
            @Override
            protected void handleTranslate(final JsonObject object, final String translate) {
                final String text = AdvancementTranslations.get(translate);
                if (text != null) {
                    object.addProperty("translate", text);
                }
            }
        };
    }
    
    @Override
    protected void registerPackets() {
        ((AbstractProtocol<ClientboundPackets1_12, C2, S1, S2>)this.protocol).registerClientbound(ClientboundPackets1_12.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                final JsonElement element;
                this.handler(wrapper -> {
                    element = wrapper.passthrough(Type.COMPONENT);
                    ChatPackets1_12.this.componentRewriter.processText(element);
                });
            }
        });
    }
}
