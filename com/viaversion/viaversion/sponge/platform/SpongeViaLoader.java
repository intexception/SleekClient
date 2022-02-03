package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.*;
import com.viaversion.viaversion.api.platform.*;
import org.spongepowered.api.*;
import com.viaversion.viaversion.sponge.listeners.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.sponge4.*;
import com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.sponge5.*;
import com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.*;
import com.viaversion.viaversion.sponge.providers.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.util.*;
import java.util.function.*;
import org.spongepowered.api.event.*;

public class SpongeViaLoader implements ViaPlatformLoader
{
    private final SpongePlugin plugin;
    private final Set<Object> listeners;
    private final Set<PlatformTask> tasks;
    
    public SpongeViaLoader(final SpongePlugin plugin) {
        this.listeners = new HashSet<Object>();
        this.tasks = new HashSet<PlatformTask>();
        this.plugin = plugin;
    }
    
    private void registerListener(final Object listener) {
        Sponge.getEventManager().registerListeners((Object)this.plugin, this.storeListener(listener));
    }
    
    private <T> T storeListener(final T listener) {
        this.listeners.add(listener);
        return listener;
    }
    
    @Override
    public void load() {
        this.registerListener(new UpdateListener());
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            try {
                Class.forName("org.spongepowered.api.event.entity.DisplaceEntityEvent");
                this.storeListener(new Sponge4ArmorListener()).register();
            }
            catch (ClassNotFoundException e) {
                this.storeListener(new Sponge5ArmorListener(this.plugin)).register();
            }
            this.storeListener(new DeathListener(this.plugin)).register();
            this.storeListener(new BlockListener(this.plugin)).register();
            if (this.plugin.getConf().isItemCache()) {
                this.tasks.add(Via.getPlatform().runRepeatingSync(new HandItemCache(), 2L));
                HandItemCache.CACHE = true;
            }
        }
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use((Class<SpongeViaMovementTransmitter>)MovementTransmitterProvider.class, new SpongeViaMovementTransmitter());
            Via.getManager().getProviders().use((Class<SpongeViaLoader$1>)HandItemProvider.class, new HandItemProvider() {
                @Override
                public Item getHandItem(final UserConnection info) {
                    if (HandItemCache.CACHE) {
                        return HandItemCache.getHandItem(info.getProtocolInfo().getUuid());
                    }
                    return super.getHandItem(info);
                }
            });
        }
    }
    
    @Override
    public void unload() {
        final Set<Object> listeners = this.listeners;
        final EventManager eventManager = Sponge.getEventManager();
        Objects.requireNonNull(eventManager);
        listeners.forEach(eventManager::unregisterListeners);
        this.listeners.clear();
        this.tasks.forEach(PlatformTask::cancel);
        this.tasks.clear();
    }
}
