package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.protocol.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.function.*;
import io.netty.channel.*;
import java.util.*;

public interface UserConnection
{
     <T extends StorableObject> T get(final Class<T> p0);
    
    boolean has(final Class<? extends StorableObject> p0);
    
    void put(final StorableObject p0);
    
    Collection<EntityTracker> getEntityTrackers();
    
     <T extends EntityTracker> T getEntityTracker(final Class<? extends Protocol> p0);
    
    void addEntityTracker(final Class<? extends Protocol> p0, final EntityTracker p1);
    
    void clearStoredObjects();
    
    void sendRawPacket(final ByteBuf p0);
    
    void scheduleSendRawPacket(final ByteBuf p0);
    
    ChannelFuture sendRawPacketFuture(final ByteBuf p0);
    
    PacketTracker getPacketTracker();
    
    void disconnect(final String p0);
    
    void sendRawPacketToServer(final ByteBuf p0);
    
    void scheduleSendRawPacketToServer(final ByteBuf p0);
    
    boolean checkServerboundPacket();
    
    boolean checkClientboundPacket();
    
    default boolean checkIncomingPacket() {
        return this.isClientSide() ? this.checkClientboundPacket() : this.checkServerboundPacket();
    }
    
    default boolean checkOutgoingPacket() {
        return this.isClientSide() ? this.checkServerboundPacket() : this.checkClientboundPacket();
    }
    
    boolean shouldTransformPacket();
    
    void transformClientbound(final ByteBuf p0, final Function<Throwable, Exception> p1) throws Exception;
    
    void transformServerbound(final ByteBuf p0, final Function<Throwable, Exception> p1) throws Exception;
    
    default void transformOutgoing(final ByteBuf buf, final Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (this.isClientSide()) {
            this.transformServerbound(buf, cancelSupplier);
        }
        else {
            this.transformClientbound(buf, cancelSupplier);
        }
    }
    
    default void transformIncoming(final ByteBuf buf, final Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (this.isClientSide()) {
            this.transformClientbound(buf, cancelSupplier);
        }
        else {
            this.transformServerbound(buf, cancelSupplier);
        }
    }
    
    long getId();
    
    Channel getChannel();
    
    ProtocolInfo getProtocolInfo();
    
    Map<Class<?>, StorableObject> getStoredObjects();
    
    boolean isActive();
    
    void setActive(final boolean p0);
    
    boolean isPendingDisconnect();
    
    void setPendingDisconnect(final boolean p0);
    
    boolean isClientSide();
    
    boolean shouldApplyBlockProtocol();
    
    boolean isPacketLimiterEnabled();
    
    void setPacketLimiterEnabled(final boolean p0);
    
    UUID generatePassthroughToken();
}
