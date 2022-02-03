package com.viaversion.viaversion.bukkit.platform;

import org.bukkit.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.util.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.platform.*;
import com.viaversion.viaversion.bukkit.handlers.*;
import org.bukkit.plugin.*;
import java.util.*;
import io.netty.channel.*;

public class BukkitViaInjector extends LegacyViaInjector
{
    private boolean protocolLib;
    
    @Override
    public void inject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.setPaperChannelInitializeListener();
            return;
        }
        super.inject();
    }
    
    @Override
    public void uninject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.removePaperChannelInitializeListener();
            return;
        }
        super.uninject();
    }
    
    @Override
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_PROTOCOL_METHOD) {
            return Bukkit.getUnsafe().getProtocolVersion();
        }
        final Class<?> serverClazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        final Object server = ReflectionUtil.invokeStatic(serverClazz, "getServer");
        final Class<?> pingClazz = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
        Object ping = null;
        for (final Field field : serverClazz.getDeclaredFields()) {
            if (field.getType() == pingClazz) {
                field.setAccessible(true);
                ping = field.get(server);
                break;
            }
        }
        final Class<?> serverDataClass = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
        Object serverData = null;
        for (final Field field2 : pingClazz.getDeclaredFields()) {
            if (field2.getType() == serverDataClass) {
                field2.setAccessible(true);
                serverData = field2.get(ping);
                break;
            }
        }
        for (final Field field2 : serverDataClass.getDeclaredFields()) {
            if (field2.getType() == Integer.TYPE) {
                field2.setAccessible(true);
                final int protocolVersion = (int)field2.get(serverData);
                if (protocolVersion != -1) {
                    return protocolVersion;
                }
            }
        }
        throw new RuntimeException("Failed to get server");
    }
    
    @Override
    public String getDecoderName() {
        return this.protocolLib ? "protocol_lib_decoder" : "decoder";
    }
    
    @Override
    protected Object getServerConnection() throws ReflectiveOperationException {
        final Class<?> serverClass = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        final Class<?> connectionClass = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
        final Object server = ReflectionUtil.invokeStatic(serverClass, "getServer");
        for (final Method method : serverClass.getDeclaredMethods()) {
            if (method.getReturnType() == connectionClass) {
                if (method.getParameterTypes().length == 0) {
                    final Object connection = method.invoke(server, new Object[0]);
                    if (connection != null) {
                        return connection;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    protected WrappedChannelInitializer createChannelInitializer(final ChannelInitializer<Channel> oldInitializer) {
        return new BukkitChannelInitializer(oldInitializer);
    }
    
    @Override
    protected void blame(final ChannelHandler bootstrapAcceptor) throws ReflectiveOperationException {
        final ClassLoader classLoader = bootstrapAcceptor.getClass().getClassLoader();
        if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
            final PluginDescriptionFile description = ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class);
            throw new RuntimeException("Unable to inject, due to " + bootstrapAcceptor.getClass().getName() + ", try without the plugin " + description.getName() + "?");
        }
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
    }
    
    public boolean isBinded() {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return true;
        }
        try {
            final Object connection = this.getServerConnection();
            if (connection == null) {
                return false;
            }
            for (final Field field : connection.getClass().getDeclaredFields()) {
                if (List.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    final List<?> value = (List<?>)field.get(connection);
                    synchronized (value) {
                        if (!value.isEmpty() && value.get(0) instanceof ChannelFuture) {
                            return true;
                        }
                    }
                }
            }
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void setProtocolLib(final boolean protocolLib) {
        this.protocolLib = protocolLib;
    }
}
