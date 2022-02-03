package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import net.minecraft.client.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.eventbus.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

@ModuleData(name = "Auto Tool", category = ModuleCategory.PLAYER, description = "Automatically Switches To The Tool Required")
public class AutoTool extends Module
{
    public static int getBestSword(final Entity lIllIIlllIlI) {
        final int lIllIIlllIIl = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        int lIllIIlllIII = -1;
        float lIllIIllIlll = 1.0f;
        for (byte lIllIIlllIll = 0; lIllIIlllIll < 9; ++lIllIIlllIll) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = lIllIIlllIll;
            final ItemStack lIllIIllllII = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (lIllIIllllII != null && lIllIIllllII.getItem() instanceof ItemSword) {
                final float lIllIIllllIl = getItemDamage(lIllIIllllII);
                if (lIllIIllllIl > lIllIIllIlll) {
                    lIllIIllIlll = lIllIIllllIl;
                    lIllIIlllIII = lIllIIlllIll;
                }
            }
        }
        if (lIllIIlllIII != -1) {
            return lIllIIlllIII;
        }
        return lIllIIlllIIl;
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIllIlIllIlI) {
        if (lIllIlIllIlI.getPacketDirection() == PacketDirection.OUTBOUND && lIllIlIllIlI.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity lIllIllIIIIl = lIllIlIllIlI.getPacket();
            final EntityLivingBase lIllIllIIIII = (EntityLivingBase)lIllIllIIIIl.getEntityFromWorld(AutoTool.mc.theWorld);
            if (lIllIllIIIIl.getAction() == C02PacketUseEntity.Action.ATTACK) {
                AutoTool.mc.thePlayer.inventory.currentItem = getBestSword(lIllIllIIIII);
                AutoTool.mc.playerController.updateController();
            }
        }
        if (AutoTool.mc.gameSettings.keyBindAttack.pressed && AutoTool.mc.objectMouseOver != null) {
            final BlockPos lIllIlIlllll = AutoTool.mc.objectMouseOver.getBlockPos();
            if (lIllIlIlllll != null) {
                updateTool(lIllIlIlllll);
            }
        }
    }
    
    public static void updateTool(final BlockPos lIllIlIIllIl) {
        final Block lIllIlIIllII = AutoTool.mc.theWorld.getBlockState(lIllIlIIllIl).getBlock();
        float lIllIlIIlIll = 1.0f;
        int lIllIlIIlIlI = -1;
        for (int lIllIlIIlllI = 0; lIllIlIIlllI < 9; ++lIllIlIIlllI) {
            final ItemStack lIllIlIIllll = AutoTool.mc.thePlayer.inventory.mainInventory[lIllIlIIlllI];
            if (lIllIlIIllll != null && lIllIlIIllll.getStrVsBlock(lIllIlIIllII) > lIllIlIIlIll) {
                lIllIlIIlIll = lIllIlIIllll.getStrVsBlock(lIllIlIIllII);
                lIllIlIIlIlI = lIllIlIIlllI;
            }
        }
        if (lIllIlIIlIlI != -1) {
            AutoTool.mc.thePlayer.inventory.currentItem = lIllIlIIlIlI;
        }
    }
    
    public static float getItemDamage(final ItemStack lIllIIlIllII) {
        if (lIllIIlIllII.getItem() instanceof ItemSword) {
            double lIllIIlIlllI = 4.0 + ((ItemSword)lIllIIlIllII.getItem()).getDamageVsEntity();
            lIllIIlIlllI += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, lIllIIlIllII) * 1.25;
            return (float)lIllIIlIlllI;
        }
        if (lIllIIlIllII.getItem() instanceof ItemTool) {
            double lIllIIlIllIl = 1.0 + ((ItemTool)lIllIIlIllII.getItem()).getToolMaterial().getDamageVsEntity();
            lIllIIlIllIl += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, lIllIIlIllII) * 1.25f;
            return (float)lIllIIlIllIl;
        }
        return 1.0f;
    }
}
