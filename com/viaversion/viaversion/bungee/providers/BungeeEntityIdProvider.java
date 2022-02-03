package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.bungee.storage.*;
import net.md_5.bungee.api.connection.*;

public class BungeeEntityIdProvider extends EntityIdProvider
{
    private static Method getClientEntityId;
    
    @Override
    public int getEntityId(final UserConnection user) throws Exception {
        final BungeeStorage storage = user.get(BungeeStorage.class);
        final ProxiedPlayer player = storage.getPlayer();
        return (int)BungeeEntityIdProvider.getClientEntityId.invoke(player, new Object[0]);
    }
    
    static {
        try {
            BungeeEntityIdProvider.getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException | ClassNotFoundException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
}
