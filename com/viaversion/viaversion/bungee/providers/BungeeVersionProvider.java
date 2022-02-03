package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.base.*;
import com.viaversion.viaversion.util.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import net.md_5.bungee.api.*;

public class BungeeVersionProvider extends BaseVersionProvider
{
    private static Class<?> ref;
    
    @Override
    public int getClosestServerProtocol(final UserConnection user) throws Exception {
        if (BungeeVersionProvider.ref == null) {
            return super.getClosestServerProtocol(user);
        }
        final List<Integer> list = ReflectionUtil.getStatic(BungeeVersionProvider.ref, "SUPPORTED_VERSION_IDS", (Class<List<Integer>>)List.class);
        final List<Integer> sorted = new ArrayList<Integer>(list);
        Collections.sort(sorted);
        final ProtocolInfo info = user.getProtocolInfo();
        if (sorted.contains(info.getProtocolVersion())) {
            return info.getProtocolVersion();
        }
        if (info.getProtocolVersion() < sorted.get(0)) {
            return getLowestSupportedVersion();
        }
        for (final Integer protocol : Lists.reverse((List)sorted)) {
            if (info.getProtocolVersion() > protocol && ProtocolVersion.isRegistered(protocol)) {
                return protocol;
            }
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + info.getProtocolVersion());
        return info.getProtocolVersion();
    }
    
    public static int getLowestSupportedVersion() {
        try {
            final List<Integer> list = ReflectionUtil.getStatic(BungeeVersionProvider.ref, "SUPPORTED_VERSION_IDS", (Class<List<Integer>>)List.class);
            return list.get(0);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return ProxyServer.getInstance().getProtocolVersion();
    }
    
    static {
        try {
            BungeeVersionProvider.ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Could not detect the ProtocolConstants class");
            e.printStackTrace();
        }
    }
}
