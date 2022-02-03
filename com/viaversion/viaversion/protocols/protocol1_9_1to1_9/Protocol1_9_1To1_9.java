package com.viaversion.viaversion.protocols.protocol1_9_1to1_9;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_9_1To1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9>
{
    public Protocol1_9_1To1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }
    
    @Override
    protected void registerPackets() {
        ((AbstractProtocol<ClientboundPackets1_9, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
            }
        });
        ((AbstractProtocol<ClientboundPackets1_9, C2, S1, S2>)this).registerClientbound(ClientboundPackets1_9.SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    @Override
                    public void handle(final PacketWrapper wrapper) throws Exception {
                        final int sound = wrapper.get((Type<Integer>)Type.VAR_INT, 0);
                        if (sound >= 415) {
                            wrapper.set(Type.VAR_INT, 0, sound + 1);
                        }
                    }
                });
            }
        });
    }
}
