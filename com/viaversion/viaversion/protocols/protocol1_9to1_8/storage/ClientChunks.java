package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.google.common.collect.*;

public class ClientChunks extends StoredObject
{
    private final Set<Long> loadedChunks;
    
    public ClientChunks(final UserConnection connection) {
        super(connection);
        this.loadedChunks = (Set<Long>)Sets.newConcurrentHashSet();
    }
    
    public static long toLong(final int msw, final int lsw) {
        return ((long)msw << 32) + lsw + 2147483648L;
    }
    
    public Set<Long> getLoadedChunks() {
        return this.loadedChunks;
    }
}
