package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.version.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public interface ProtocolManager
{
    ServerProtocolVersion getServerProtocolVersion();
    
     <T extends Protocol> T getProtocol(final Class<T> p0);
    
    default Protocol getProtocol(final ProtocolVersion clientVersion, final ProtocolVersion serverVersion) {
        return this.getProtocol(clientVersion.getVersion(), serverVersion.getVersion());
    }
    
    Protocol getProtocol(final int p0, final int p1);
    
    Protocol getBaseProtocol();
    
    Protocol getBaseProtocol(final int p0);
    
    default boolean isBaseProtocol(final Protocol protocol) {
        return protocol.isBaseProtocol();
    }
    
    void registerProtocol(final Protocol p0, final ProtocolVersion p1, final ProtocolVersion p2);
    
    void registerProtocol(final Protocol p0, final List<Integer> p1, final int p2);
    
    void registerBaseProtocol(final Protocol p0, final Range<Integer> p1);
    
    List<ProtocolPathEntry> getProtocolPath(final int p0, final int p1);
    
     <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(final ProtocolVersion p0, final Class<C> p1, final Class<S> p2);
    
    boolean onlyCheckLoweringPathEntries();
    
    void setOnlyCheckLoweringPathEntries(final boolean p0);
    
    int getMaxProtocolPathSize();
    
    void setMaxProtocolPathSize(final int p0);
    
    SortedSet<Integer> getSupportedVersions();
    
    boolean isWorkingPipe();
    
    void completeMappingDataLoading(final Class<? extends Protocol> p0) throws Exception;
    
    boolean checkForMappingCompletion();
    
    void addMappingLoaderFuture(final Class<? extends Protocol> p0, final Runnable p1);
    
    void addMappingLoaderFuture(final Class<? extends Protocol> p0, final Class<? extends Protocol> p1, final Runnable p2);
    
    CompletableFuture<Void> getMappingLoaderFuture(final Class<? extends Protocol> p0);
    
    PacketWrapper createPacketWrapper(final PacketType p0, final ByteBuf p1, final UserConnection p2);
    
    @Deprecated
    PacketWrapper createPacketWrapper(final int p0, final ByteBuf p1, final UserConnection p2);
}
