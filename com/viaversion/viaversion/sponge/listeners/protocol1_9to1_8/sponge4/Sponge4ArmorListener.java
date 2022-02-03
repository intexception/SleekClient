package com.viaversion.viaversion.sponge.listeners.protocol1_9to1_8.sponge4;

import com.viaversion.viaversion.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.protocol.*;
import org.spongepowered.api.entity.living.player.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import org.spongepowered.api.event.item.inventory.*;
import org.spongepowered.api.event.filter.cause.*;
import org.spongepowered.api.item.inventory.transaction.*;
import org.spongepowered.api.item.inventory.*;
import java.util.*;
import org.spongepowered.api.event.*;
import org.spongepowered.api.event.action.*;
import org.spongepowered.api.event.network.*;
import org.spongepowered.api.event.entity.living.humanoid.player.*;
import org.spongepowered.api.event.entity.*;
import org.spongepowered.api.world.*;
import com.viaversion.viaversion.api.*;
import org.spongepowered.api.*;

public class Sponge4ArmorListener extends ViaListener
{
    private static Field entityIdField;
    private static final UUID ARMOR_ATTRIBUTE;
    
    public Sponge4ArmorListener() {
        super(Protocol1_9To1_8.class);
    }
    
    public void sendArmorUpdate(final Player player) {
        if (!this.isOnPipe(player.getUniqueId())) {
            return;
        }
        int armor = 0;
        armor += this.calculate(player.getHelmet());
        armor += this.calculate(player.getChestplate());
        armor += this.calculate(player.getLeggings());
        armor += this.calculate(player.getBoots());
        final PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_PROPERTIES, null, this.getUserConnection(player.getUniqueId()));
        try {
            wrapper.write(Type.VAR_INT, this.getEntityId(player));
            wrapper.write(Type.INT, 1);
            wrapper.write(Type.STRING, "generic.armor");
            wrapper.write(Type.DOUBLE, 0.0);
            wrapper.write(Type.VAR_INT, 1);
            wrapper.write(Type.UUID, Sponge4ArmorListener.ARMOR_ATTRIBUTE);
            wrapper.write(Type.DOUBLE, (double)armor);
            wrapper.write(Type.BYTE, (Byte)0);
            wrapper.scheduleSend(Protocol1_9To1_8.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int calculate(final Optional<ItemStack> itemStack) {
        if (itemStack.isPresent()) {
            return ArmorType.findByType(itemStack.get().getItem().getType().getId()).getArmorPoints();
        }
        return 0;
    }
    
    @Listener
    public void onInventoryClick(final ClickInventoryEvent e, @Root final Player player) {
        for (final SlotTransaction transaction : e.getTransactions()) {
            if (ArmorType.isArmor(((ItemStackSnapshot)transaction.getFinal()).getType().getId()) || ArmorType.isArmor(((ItemStackSnapshot)e.getCursorTransaction().getFinal()).getType().getId())) {
                this.sendDelayedArmorUpdate(player);
                break;
            }
        }
    }
    
    @Listener
    public void onInteract(final InteractEvent event, @Root final Player player) {
        if (player.getItemInHand().isPresent() && ArmorType.isArmor(player.getItemInHand().get().getItem().getId())) {
            this.sendDelayedArmorUpdate(player);
        }
    }
    
    @Listener
    public void onJoin(final ClientConnectionEvent.Join e) {
        this.sendArmorUpdate(e.getTargetEntity());
    }
    
    @Listener
    public void onRespawn(final RespawnPlayerEvent e) {
        this.sendDelayedArmorUpdate(e.getTargetEntity());
    }
    
    @Listener
    public void onWorldChange(final DisplaceEntityEvent.Teleport e) {
        if (!(e.getTargetEntity() instanceof Player)) {
            return;
        }
        if (!((World)e.getFromTransform().getExtent()).getUniqueId().equals(((World)e.getToTransform().getExtent()).getUniqueId())) {
            this.sendArmorUpdate((Player)e.getTargetEntity());
        }
    }
    
    public void sendDelayedArmorUpdate(final Player player) {
        if (!this.isOnPipe(player.getUniqueId())) {
            return;
        }
        Via.getPlatform().runSync(new Runnable() {
            @Override
            public void run() {
                Sponge4ArmorListener.this.sendArmorUpdate(player);
            }
        });
    }
    
    @Override
    public void register() {
        if (this.isRegistered()) {
            return;
        }
        Sponge.getEventManager().registerListeners((Object)Via.getPlatform(), (Object)this);
        this.setRegistered(true);
    }
    
    protected int getEntityId(final Player p) {
        try {
            if (Sponge4ArmorListener.entityIdField == null) {
                (Sponge4ArmorListener.entityIdField = p.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("field_145783_c")).setAccessible(true);
            }
            return Sponge4ArmorListener.entityIdField.getInt(p);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Could not get the entity id, please report this on our Github");
            e.printStackTrace();
            Via.getPlatform().getLogger().severe("Could not get the entity id, please report this on our Github");
            return -1;
        }
    }
    
    static {
        ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
    }
}
