package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;

public interface VersionedPacketTransformer<C extends ClientboundPacketType, S extends ServerboundPacketType>
{
    boolean send(final PacketWrapper p0) throws Exception;
    
    boolean send(final UserConnection p0, final C p1, final Consumer<PacketWrapper> p2) throws Exception;
    
    boolean send(final UserConnection p0, final S p1, final Consumer<PacketWrapper> p2) throws Exception;
    
    boolean scheduleSend(final PacketWrapper p0) throws Exception;
    
    boolean scheduleSend(final UserConnection p0, final C p1, final Consumer<PacketWrapper> p2) throws Exception;
    
    boolean scheduleSend(final UserConnection p0, final S p1, final Consumer<PacketWrapper> p2) throws Exception;
    
    PacketWrapper transform(final PacketWrapper p0) throws Exception;
    
    PacketWrapper transform(final UserConnection p0, final C p1, final Consumer<PacketWrapper> p2) throws Exception;
    
    PacketWrapper transform(final UserConnection p0, final S p1, final Consumer<PacketWrapper> p2) throws Exception;
}
