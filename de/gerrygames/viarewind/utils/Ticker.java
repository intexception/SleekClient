package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import com.viaversion.viaversion.api.connection.*;

public class Ticker
{
    private static boolean init;
    
    public static void init() {
        if (Ticker.init) {
            return;
        }
        synchronized (Ticker.class) {
            if (Ticker.init) {
                return;
            }
            Ticker.init = true;
        }
        final Class clazz;
        final Stream<StorableObject> stream;
        final Class<Object> clazz2;
        final Stream<StorableObject> stream2;
        Via.getPlatform().runRepeatingSync(() -> Via.getManager().getConnectionManager().getConnections().forEach(user -> {
            user.getStoredObjects().values().stream();
            Objects.requireNonNull(clazz);
            stream.filter(clazz::isInstance);
            Objects.requireNonNull((Class<Tickable>)clazz2);
            stream2.map((Function<? super StorableObject, ?>)clazz2::cast).forEach(Tickable::tick);
        }), 1L);
    }
    
    static {
        Ticker.init = false;
    }
}
