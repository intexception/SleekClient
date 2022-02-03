package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.*;
import com.velocitypowered.api.plugin.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.velocity.providers.*;
import com.viaversion.viaversion.velocity.listeners.*;
import com.viaversion.viaversion.velocity.service.*;

public class VelocityViaLoader implements ViaPlatformLoader
{
    @Override
    public void load() {
        final Object plugin = VelocityPlugin.PROXY.getPluginManager().getPlugin("viaversion").flatMap(PluginContainer::getInstance).get();
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use((Class<VelocityMovementTransmitter>)MovementTransmitterProvider.class, new VelocityMovementTransmitter());
            Via.getManager().getProviders().use((Class<VelocityBossBarProvider>)BossBarProvider.class, new VelocityBossBarProvider());
        }
        Via.getManager().getProviders().use((Class<VelocityVersionProvider>)VersionProvider.class, new VelocityVersionProvider());
        VelocityPlugin.PROXY.getEventManager().register(plugin, (Object)new UpdateListener());
        final int pingInterval = ((VelocityViaConfig)Via.getPlatform().getConf()).getVelocityPingInterval();
        if (pingInterval > 0) {
            Via.getPlatform().runRepeatingSync(new ProtocolDetectorService(), pingInterval * 20L);
        }
    }
    
    @Override
    public void unload() {
    }
}
