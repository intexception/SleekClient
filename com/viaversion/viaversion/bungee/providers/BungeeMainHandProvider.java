package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import net.md_5.bungee.api.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import net.md_5.bungee.api.connection.*;

public class BungeeMainHandProvider extends MainHandProvider
{
    private static Method getSettings;
    private static Method setMainHand;
    
    @Override
    public void setMainHand(final UserConnection user, final int hand) {
        final ProtocolInfo info = user.getProtocolInfo();
        if (info == null || info.getUuid() == null) {
            return;
        }
        final ProxiedPlayer player = ProxyServer.getInstance().getPlayer(info.getUuid());
        if (player == null) {
            return;
        }
        try {
            final Object settings = BungeeMainHandProvider.getSettings.invoke(player, new Object[0]);
            if (settings != null) {
                BungeeMainHandProvider.setMainHand.invoke(settings, hand);
            }
        }
        catch (IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
    
    static {
        BungeeMainHandProvider.getSettings = null;
        BungeeMainHandProvider.setMainHand = null;
        try {
            BungeeMainHandProvider.getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", (Class<?>[])new Class[0]);
            BungeeMainHandProvider.setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", Integer.TYPE);
        }
        catch (Exception ex) {}
    }
}
