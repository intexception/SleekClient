package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.*;

public interface SimpleProtocol extends Protocol<DummyPacketTypes, DummyPacketTypes, DummyPacketTypes, DummyPacketTypes>
{
    public enum DummyPacketTypes implements ClientboundPacketType, ServerboundPacketType
    {
        @Override
        public int getId() {
            return this.ordinal();
        }
        
        @Override
        public String getName() {
            return this.name();
        }
        
        @Override
        public Direction direction() {
            throw new UnsupportedOperationException();
        }
    }
}
