package com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viaversion.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.protocol.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import org.bukkit.potion.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import org.bukkit.event.*;

public class EntityToggleGlideListener extends ViaBukkitListener
{
    private boolean swimmingMethodExists;
    
    public EntityToggleGlideListener(final ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_15To1_14_4.class);
        try {
            Player.class.getMethod("isSwimming", (Class<?>[])new Class[0]);
            this.swimmingMethodExists = true;
        }
        catch (NoSuchMethodException ex) {}
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void entityToggleGlide(final EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        final Player player = (Player)event.getEntity();
        if (!this.isOnPipe(player)) {
            return;
        }
        if (event.isGliding() && event.isCancelled()) {
            final PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, null, this.getUserConnection(player));
            try {
                packet.write(Type.VAR_INT, player.getEntityId());
                byte bitmask = 0;
                if (player.getFireTicks() > 0) {
                    bitmask |= 0x1;
                }
                if (player.isSneaking()) {
                    bitmask |= 0x2;
                }
                if (player.isSprinting()) {
                    bitmask |= 0x8;
                }
                if (this.swimmingMethodExists && player.isSwimming()) {
                    bitmask |= 0x10;
                }
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    bitmask |= 0x20;
                }
                if (player.isGlowing()) {
                    bitmask |= 0x40;
                }
                packet.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata(0, Types1_14.META_TYPES.byteType, bitmask)));
                packet.scheduleSend(Protocol1_15To1_14_4.class);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
