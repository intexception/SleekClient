package me.kansio.client.modules.impl.world;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.inventory.*;
import com.google.common.eventbus.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;

@ModuleData(name = "Chest Stealer", category = ModuleCategory.WORLD, description = "Automatically steals stuff from chests")
public class ChestStealer extends Module
{
    private /* synthetic */ Stopwatch delayCounter;
    private /* synthetic */ NumberValue<Integer> delay;
    private /* synthetic */ BooleanValue checkChest;
    
    @Override
    public void onEnable() {
        this.delayCounter.resetTime();
    }
    
    private boolean isTrash(final ItemStack lIIllIIlIllI) {
        return lIIllIIlIllI.getItem().getUnlocalizedName().contains("tnt") || lIIllIIlIllI.getDisplayName().contains("frog") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("stick") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("string") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("flint") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("feather") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("bucket") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("snow") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("enchant") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("exp") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("shears") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("arrow") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("anvil") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("torch") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("seeds") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("leather") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("boat") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("fishing") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("wheat") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("flower") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("record") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("note") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("sugar") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("wire") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("trip") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("slime") || lIIllIIlIllI.getItem().getUnlocalizedName().contains("web") || lIIllIIlIllI.getItem() instanceof ItemGlassBottle || lIIllIIlIllI.getItem().getUnlocalizedName().contains("piston") || (lIIllIIlIllI.getItem().getUnlocalizedName().contains("potion") && this.isBadPotion(lIIllIIlIllI)) || lIIllIIlIllI.getItem() instanceof ItemEgg || (lIIllIIlIllI.getItem().getUnlocalizedName().contains("bow") && !lIIllIIlIllI.getDisplayName().contains("Kit")) || lIIllIIlIllI.getItem().getUnlocalizedName().contains("Raw");
    }
    
    private boolean isChestEmpty(final GuiChest lIIllIlIIIll) {
        for (int lIIllIlIIlll = 0; lIIllIlIIlll < lIIllIlIIIll.lowerChestInventory.getSizeInventory(); ++lIIllIlIIlll) {
            final ItemStack lIIllIlIlIII = lIIllIlIIIll.lowerChestInventory.getStackInSlot(lIIllIlIIlll);
            if (lIIllIlIlIII != null && !this.isTrash(lIIllIlIlIII)) {
                return false;
            }
        }
        return true;
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIIllIllIIIl) {
        if (ChestStealer.mc.currentScreen instanceof GuiChest) {
            if (this.delayCounter.timeElapsed(this.delay.getValue())) {
                final GuiChest lIIllIllIlII = (GuiChest)ChestStealer.mc.currentScreen;
                if (this.checkChest.getValue() && !lIIllIllIlII.lowerChestInventory.getDisplayName().getUnformattedText().contains("Chest") && !lIIllIllIlII.lowerChestInventory.getDisplayName().getUnformattedText().contains("LOW") && !lIIllIllIlII.lowerChestInventory.getDisplayName().getUnformattedText().contains("coffre")) {
                    this.delayCounter.resetTime();
                    return;
                }
                if (this.isChestEmpty(lIIllIllIlII) || this.isInventoryFull()) {
                    ChestStealer.mc.thePlayer.closeScreen();
                }
                for (int lIIllIllIlIl = 0; lIIllIllIlIl < lIIllIllIlII.lowerChestInventory.getSizeInventory(); ++lIIllIllIlIl) {
                    final ItemStack lIIllIllIllI = lIIllIllIlII.lowerChestInventory.getStackInSlot(lIIllIllIlIl);
                    if (lIIllIllIllI != null && this.delayCounter.timeElapsed(this.delay.getValue())) {
                        if (!this.isTrash(lIIllIllIllI)) {
                            ChestStealer.mc.playerController.windowClick(lIIllIllIlII.inventorySlots.windowId, lIIllIllIlIl, 0, 1, ChestStealer.mc.thePlayer);
                            this.delayCounter.resetTime();
                        }
                    }
                }
                this.delayCounter.resetTime();
            }
        }
        else if (ChestStealer.mc.currentScreen instanceof GuiCrafting && this.delayCounter.timeElapsed(this.delay.getValue())) {
            final GuiCrafting lIIllIllIIll = (GuiCrafting)ChestStealer.mc.currentScreen;
            ChestStealer.mc.playerController.windowClick(lIIllIllIIll.inventorySlots.windowId, 0, 0, 1, ChestStealer.mc.thePlayer);
            this.delayCounter.resetTime();
        }
    }
    
    private boolean isBadPotion(final ItemStack lIIllIIIlIIl) {
        if (lIIllIIIlIIl != null && lIIllIIIlIIl.getItem() instanceof ItemPotion) {
            final ItemPotion lIIllIIIllII = (ItemPotion)lIIllIIIlIIl.getItem();
            if (ItemPotion.isSplash(lIIllIIIlIIl.getItemDamage())) {
                for (final Object lIIllIIIllIl : lIIllIIIllII.getEffects(lIIllIIIlIIl)) {
                    final PotionEffect lIIllIIIlllI = (PotionEffect)lIIllIIIllIl;
                    if (lIIllIIIlllI.getPotionID() == Potion.poison.getId() || lIIllIIIlllI.getPotionID() == Potion.harm.getId() || lIIllIIIlllI.getPotionID() == Potion.moveSlowdown.getId() || lIIllIIIlllI.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public ChestStealer() {
        this.checkChest = new BooleanValue("Check Chest", this, true);
        this.delay = new NumberValue<Integer>("Delay", this, 25, 0, 1000, 1);
        this.delayCounter = new Stopwatch();
    }
    
    private boolean isInventoryFull() {
        for (int lIIllIIlllIl = 9; lIIllIIlllIl <= 44; ++lIIllIIlllIl) {
            final ItemStack lIIllIIllllI = ChestStealer.mc.thePlayer.inventoryContainer.getSlot(lIIllIIlllIl).getStack();
            if (lIIllIIllllI == null) {
                return false;
            }
        }
        return true;
    }
}
