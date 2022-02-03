package com.viaversion.viaversion.velocity.service;

import com.viaversion.viaversion.velocity.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.*;
import java.util.*;
import com.velocitypowered.api.proxy.server.*;
import java.util.concurrent.*;

public class ProtocolDetectorService implements Runnable
{
    private static final Map<String, Integer> detectedProtocolIds;
    private static ProtocolDetectorService instance;
    
    public ProtocolDetectorService() {
        ProtocolDetectorService.instance = this;
    }
    
    public static Integer getProtocolId(final String serverName) {
        final Map<String, Integer> servers = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
        final Integer protocol = servers.get(serverName);
        if (protocol != null) {
            return protocol;
        }
        final Integer detectedProtocol = ProtocolDetectorService.detectedProtocolIds.get(serverName);
        if (detectedProtocol != null) {
            return detectedProtocol;
        }
        final Integer defaultProtocol = servers.get("default");
        if (defaultProtocol != null) {
            return defaultProtocol;
        }
        try {
            return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ProtocolVersion.v1_8.getVersion();
        }
    }
    
    @Override
    public void run() {
        for (final RegisteredServer serv : VelocityPlugin.PROXY.getAllServers()) {
            probeServer(serv);
        }
    }
    
    public static void probeServer(final RegisteredServer serverInfo) {
        final String key = serverInfo.getServerInfo().getName();
        final String s;
        Map<String, Integer> servers;
        Integer protocol;
        serverInfo.ping().thenAccept(serverPing -> {
            if (serverPing != null && serverPing.getVersion() != null) {
                ProtocolDetectorService.detectedProtocolIds.put(s, serverPing.getVersion().getProtocol());
                if (((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) {
                    servers = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
                    protocol = servers.get(s);
                    if (protocol == null || protocol != serverPing.getVersion().getProtocol()) {
                        synchronized (Via.getPlatform().getConfigurationProvider()) {
                            servers.put(s, serverPing.getVersion().getProtocol());
                        }
                        Via.getPlatform().getConfigurationProvider().saveConfig();
                    }
                }
            }
        });
    }
    
    public static Map<String, Integer> getDetectedIds() {
        return new HashMap<String, Integer>(ProtocolDetectorService.detectedProtocolIds);
    }
    
    public static ProtocolDetectorService getInstance() {
        return ProtocolDetectorService.instance;
    }
    
    static {
        detectedProtocolIds = new ConcurrentHashMap<String, Integer>();
    }
}
