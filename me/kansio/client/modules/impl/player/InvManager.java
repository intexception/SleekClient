package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.math.*;
import java.util.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import com.google.common.eventbus.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.enchantment.*;
import me.kansio.client.gui.clickgui.ui.clickgui.frame.*;
import me.kansio.client.utils.network.*;
import net.minecraft.item.*;
import me.kansio.client.value.*;
import net.minecraft.potion.*;

@ModuleData(name = "Inventory Manager", category = ModuleCategory.PLAYER, description = "Automatically manages your inventory")
public class InvManager extends Module
{
    private /* synthetic */ NumberValue<Double> aSwordDelay;
    private final /* synthetic */ List[] allArmor;
    private /* synthetic */ BooleanValue autoArmorInInv;
    private final /* synthetic */ List<Integer> allSwords;
    private /* synthetic */ int bestSwordSlot;
    private final /* synthetic */ Stopwatch stopwatch;
    private /* synthetic */ BooleanValue autoArmor;
    private final /* synthetic */ List<Integer> trash;
    private /* synthetic */ Stopwatch swordStop;
    private /* synthetic */ BooleanValue autoSword;
    private /* synthetic */ BooleanValue aSwordInInv;
    private /* synthetic */ int[] bestArmorSlot;
    private /* synthetic */ BooleanValue invCleaner;
    private /* synthetic */ NumberValue<Double> autoArmorDelay;
    private /* synthetic */ BooleanValue disableIfSlimeBall;
    private /* synthetic */ Stopwatch armorStop;
    private /* synthetic */ NumberValue<Double> invCleanerDelay;
    private /* synthetic */ Stopwatch invStop;
    private /* synthetic */ BooleanValue invCleanerInInv;
    
    private boolean isTrash(final ItemStack lIIIlllIIlllll) {
        return lIIIlllIIlllll.getItem().getUnlocalizedName().contains("tnt") || lIIIlllIIlllll.getDisplayName().contains("frog") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("stick") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("string") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("flint") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("feather") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("bucket") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("snow") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("enchant") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("exp") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("shears") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("arrow") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("anvil") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("torch") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("seeds") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("leather") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("boat") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("fishing") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("wheat") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("flower") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("record") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("note") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("sugar") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("wire") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("trip") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("slime") || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("web") || lIIIlllIIlllll.getItem() instanceof ItemGlassBottle || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("piston") || (lIIIlllIIlllll.getItem().getUnlocalizedName().contains("potion") && this.isBadPotion(lIIIlllIIlllll)) || lIIIlllIIlllll.getItem() instanceof ItemEgg || (lIIIlllIIlllll.getItem().getUnlocalizedName().contains("bow") && !lIIIlllIIlllll.getDisplayName().contains("Kit")) || lIIIlllIIlllll.getItem().getUnlocalizedName().contains("Raw");
    }
    
    private void collectBestArmor() {
        final int[] lIIIllIllIIlII = new int[4];
        this.bestArmorSlot = new int[4];
        Arrays.fill(lIIIllIllIIlII, -1);
        Arrays.fill(this.bestArmorSlot, -1);
        for (int lIIIllIllIIIll = 0; lIIIllIllIIIll < this.bestArmorSlot.length; ++lIIIllIllIIIll) {
            final ItemStack lIIIllIllIlIlI = InvManager.mc.thePlayer.inventory.armorItemInSlot(lIIIllIllIIIll);
            this.allArmor[lIIIllIllIIIll] = new ArrayList();
            if (lIIIllIllIlIlI != null && lIIIllIllIlIlI.getItem() != null && lIIIllIllIlIlI.getItem() instanceof ItemArmor) {
                final ItemArmor lIIIllIllIllII = (ItemArmor)lIIIllIllIlIlI.getItem();
                final int lIIIllIllIlIll = lIIIllIllIllII.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { lIIIllIllIlIlI }, DamageSource.generic);
                lIIIllIllIIlII[lIIIllIllIIIll] = lIIIllIllIlIll;
            }
        }
        for (int lIIIllIllIIIll = 0; lIIIllIllIIIll < 36; ++lIIIllIllIIIll) {
            final ItemStack lIIIllIllIIllI = InvManager.mc.thePlayer.inventory.getStackInSlot(lIIIllIllIIIll);
            if (lIIIllIllIIllI != null && lIIIllIllIIllI.getItem() != null && lIIIllIllIIllI.getItem() instanceof ItemArmor) {
                final ItemArmor lIIIllIllIlIII = (ItemArmor)lIIIllIllIIllI.getItem();
                final int lIIIllIllIIlll = 3 - lIIIllIllIlIII.armorType;
                this.allArmor[lIIIllIllIIlll].add(lIIIllIllIIIll);
                final int lIIIllIllIlIIl = lIIIllIllIlIII.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { lIIIllIllIIllI }, DamageSource.generic);
                if (lIIIllIllIIlII[lIIIllIllIIlll] < lIIIllIllIlIIl) {
                    lIIIllIllIIlII[lIIIllIllIIlll] = lIIIllIllIlIIl;
                    this.bestArmorSlot[lIIIllIllIIlll] = lIIIllIllIIIll;
                }
            }
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIIIlllllIIIII) {
        if (!lIIIlllllIIIII.isPre()) {
            return;
        }
        if (InvManager.mc.currentScreen instanceof GuiChest) {
            return;
        }
        if (InvManager.mc.thePlayer.isMoving() || !InvManager.mc.thePlayer.onGround) {
            return;
        }
        if (this.disableIfSlimeBall.getValue() && (InvManager.mc.thePlayer.inventory.hasItem(Items.compass) || InvManager.mc.thePlayer.inventory.hasItem(Items.slime_ball))) {
            return;
        }
        this.collectItems();
        this.collectBestArmor();
        this.collectTrash();
        final int lIIIllllIlllll = this.trash.size();
        final boolean lIIIllllIllllI = lIIIllllIlllll > 0;
        final int lIIIllllIlllIl = InvManager.mc.thePlayer.inventoryContainer.windowId;
        final int lIIIllllIlllII = this.bestSwordSlot;
        if (this.autoArmor.getValue()) {
            if (!this.autoArmorInInv.getValue()) {
                this.equipArmor(this.armorStop, this.autoArmorDelay.getValue().intValue());
            }
            else if (InvManager.mc.currentScreen instanceof GuiInventory) {
                this.equipArmor(this.armorStop, this.autoArmorDelay.getValue().intValue());
            }
        }
        if (this.autoSword.getValue()) {
            if (!this.aSwordInInv.getValue()) {
                this.autoSwordThing(lIIIllllIlllII, this.swordStop, lIIIllllIlllIl);
            }
            else if (InvManager.mc.currentScreen instanceof GuiInventory) {
                this.autoSwordThing(lIIIllllIlllII, this.swordStop, lIIIllllIlllIl);
            }
        }
        if (this.invCleaner.getValue()) {
            if (!this.invCleanerInInv.getValue()) {
                this.invCleanerThing(this.invStop, this.invCleanerDelay.getValue().intValue(), this.trash, lIIIllllIlllIl);
            }
            else if (InvManager.mc.currentScreen instanceof GuiInventory) {
                this.invCleanerThing(this.invStop, this.invCleanerDelay.getValue().intValue(), this.trash, lIIIllllIlllIl);
            }
        }
    }
    
    private void equipArmor(final Stopwatch lIIIllllIIIllI, final int lIIIllllIIlIII) {
        for (int lIIIllllIIlIll = 9; lIIIllllIIlIll < 45; ++lIIIllllIIlIll) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(lIIIllllIIlIll).getHasStack()) {
                final ItemStack lIIIllllIIllII = InvManager.mc.thePlayer.inventoryContainer.getSlot(lIIIllllIIlIll).getStack();
                if (lIIIllllIIllII.getItem() instanceof ItemArmor) {
                    if (this.getArmorItemsEquipSlot(lIIIllllIIllII, false) != -1) {
                        if (InvManager.mc.thePlayer.getEquipmentInSlot(this.getArmorItemsEquipSlot(lIIIllllIIllII, true)) == null) {
                            if (lIIIllllIIIllI.timeElapsed(lIIIllllIIlIII)) {
                                InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                                InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, lIIIllllIIlIll, 0, 0, InvManager.mc.thePlayer);
                                InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, this.getArmorItemsEquipSlot(lIIIllllIIllII, false), 0, 0, InvManager.mc.thePlayer);
                                lIIIllllIIIllI.resetTime();
                                return;
                            }
                        }
                        else {
                            final ItemStack lIIIllllIIllIl = InvManager.mc.thePlayer.getEquipmentInSlot(this.getArmorItemsEquipSlot(lIIIllllIIllII, true));
                            if (this.compareProtection(lIIIllllIIllII, lIIIllllIIllIl) == lIIIllllIIllII) {
                                System.out.println(String.valueOf(new StringBuilder().append("Stack in slot : ").append(lIIIllllIIllII.getUnlocalizedName())));
                                if (lIIIllllIIIllI.timeElapsed(lIIIllllIIlIII)) {
                                    final int lIIIllllIIlllI = lIIIllllIIlIll;
                                    InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, lIIIllllIIlllI, 0, 0, InvManager.mc.thePlayer);
                                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, this.getArmorItemsEquipSlot(lIIIllllIIllII, false), 0, 0, InvManager.mc.thePlayer);
                                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, lIIIllllIIlllI, 0, 0, InvManager.mc.thePlayer);
                                    InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
                                    lIIIllllIIIllI.resetTime();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private ItemStack compareProtection(final ItemStack lIIIlllIIlIIIl, final ItemStack lIIIlllIIlIIII) {
        if (!(lIIIlllIIlIIIl.getItem() instanceof ItemArmor) && !(lIIIlllIIlIIII.getItem() instanceof ItemArmor)) {
            return null;
        }
        if (!(lIIIlllIIlIIIl.getItem() instanceof ItemArmor)) {
            return lIIIlllIIlIIII;
        }
        if (!(lIIIlllIIlIIII.getItem() instanceof ItemArmor)) {
            return lIIIlllIIlIIIl;
        }
        if (this.getArmorProtection(lIIIlllIIlIIIl) > this.getArmorProtection(lIIIlllIIlIIII)) {
            return lIIIlllIIlIIIl;
        }
        if (this.getArmorProtection(lIIIlllIIlIIII) > this.getArmorProtection(lIIIlllIIlIIIl)) {
            return lIIIlllIIlIIII;
        }
        return null;
    }
    
    @Override
    public void onEnable() {
    }
    
    private float getDamageLevel(final ItemStack lIIIllIIlIlIlI) {
        if (lIIIllIIlIlIlI.getItem() instanceof ItemSword) {
            final ItemSword lIIIllIIlIlllI = (ItemSword)lIIIllIIlIlIlI.getItem();
            final float lIIIllIIlIllIl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, lIIIllIIlIlIlI) * 1.25f;
            final float lIIIllIIlIllII = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, lIIIllIIlIlIlI) * 1.5f;
            return lIIIllIIlIlllI.getDamageVsEntity() + lIIIllIIlIllIl + lIIIllIIlIllII;
        }
        return 0.0f;
    }
    
    private void invCleanerThing(final Stopwatch lIIIlllIllIlII, final int lIIIlllIllIIll, final List<Integer> lIIIlllIllIllI, final int lIIIlllIllIIIl) {
        if (!(InvManager.mc.currentScreen instanceof GuiInventory) || !(InvManager.mc.currentScreen instanceof ClickGUI)) {
            PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
        }
        for (final Integer lIIIlllIlllIlI : lIIIlllIllIllI) {
            if (lIIIlllIllIlII.timeElapsed(lIIIlllIllIIll)) {
                InvManager.mc.playerController.windowClick(lIIIlllIllIIIl, (lIIIlllIlllIlI < 9) ? (lIIIlllIlllIlI + 36) : ((int)lIIIlllIlllIlI), 1, 4, InvManager.mc.thePlayer);
                lIIIlllIllIlII.resetTime();
            }
        }
        if (!(InvManager.mc.currentScreen instanceof GuiInventory)) {
            PacketUtil.sendPacketNoEvent(new C0DPacketCloseWindow(lIIIlllIllIIIl));
        }
    }
    
    private boolean isValidItem(final ItemStack lIIIllIlIIIlII) {
        return lIIIllIlIIIlII.getDisplayName().startsWith("§a") || lIIIllIlIIIlII.getItem() instanceof ItemArmor || lIIIllIlIIIlII.getItem() instanceof ItemEnderPearl || lIIIllIlIIIlII.getItem() instanceof ItemSword || lIIIllIlIIIlII.getItem() instanceof ItemTool || lIIIllIlIIIlII.getItem() instanceof ItemFood || (lIIIllIlIIIlII.getItem() instanceof ItemPotion && !this.isBadPotion(lIIIllIlIIIlII)) || lIIIllIlIIIlII.getItem() instanceof ItemBlock || lIIIllIlIIIlII.getDisplayName().contains("Play") || lIIIllIlIIIlII.getDisplayName().contains("Game") || lIIIllIlIIIlII.getDisplayName().contains("Right Click");
    }
    
    public void collectItems() {
        this.bestSwordSlot = -1;
        this.allSwords.clear();
        float lIIIllIllllIIl = -1.0f;
        for (int lIIIllIllllIll = 0; lIIIllIllllIll < 36; ++lIIIllIllllIll) {
            final ItemStack lIIIllIlllllII = InvManager.mc.thePlayer.inventory.getStackInSlot(lIIIllIllllIll);
            if (lIIIllIlllllII != null && lIIIllIlllllII.getItem() != null && lIIIllIlllllII.getItem() instanceof ItemSword) {
                final float lIIIllIlllllIl = this.getDamageLevel(lIIIllIlllllII);
                this.allSwords.add(lIIIllIllllIll);
                if (lIIIllIllllIIl < lIIIllIlllllIl) {
                    lIIIllIllllIIl = lIIIllIlllllIl;
                    this.bestSwordSlot = lIIIllIllllIll;
                }
            }
        }
    }
    
    private void collectTrash() {
        this.trash.clear();
        for (int lIIIllIlIIlllI = 0; lIIIllIlIIlllI < 36; ++lIIIllIlIIlllI) {
            final ItemStack lIIIllIlIlIlIl = InvManager.mc.thePlayer.inventory.getStackInSlot(lIIIllIlIIlllI);
            if (lIIIllIlIlIlIl != null && lIIIllIlIlIlIl.getItem() != null && !(lIIIllIlIlIlIl.getItem() instanceof ItemBook) && !lIIIllIlIlIlIl.getDisplayName().contains("Hype") && !lIIIllIlIlIlIl.getDisplayName().contains("Jogadores") && !lIIIllIlIlIlIl.getDisplayName().startsWith("§6") && !lIIIllIlIlIlIl.getDisplayName().contains("Emerald") && !lIIIllIlIlIlIl.getDisplayName().contains("Gadget") && !this.isValidItem(lIIIllIlIlIlIl)) {
                this.trash.add(lIIIllIlIIlllI);
            }
        }
        for (int lIIIllIlIIlllI = 0; lIIIllIlIIlllI < this.allArmor.length; ++lIIIllIlIIlllI) {
            final List lIIIllIlIlIIIl = this.allArmor[lIIIllIlIIlllI];
            if (lIIIllIlIlIIIl != null) {
                for (int lIIIllIlIlIIlI = 0, lIIIllIlIlIIll = lIIIllIlIlIIIl.size(); lIIIllIlIlIIlI < lIIIllIlIlIIll; ++lIIIllIlIlIIlI) {
                    final Integer lIIIllIlIlIlII = lIIIllIlIlIIIl.get(lIIIllIlIlIIlI);
                    if (lIIIllIlIlIlII != this.bestArmorSlot[lIIIllIlIIlllI]) {
                        this.trash.add(lIIIllIlIlIlII);
                    }
                }
            }
        }
        for (int lIIIllIlIIlllI = 0; lIIIllIlIIlllI < this.allSwords.size(); ++lIIIllIlIIlllI) {
            final Integer lIIIllIlIlIIII = this.allSwords.get(lIIIllIlIIlllI);
            if (lIIIllIlIlIIII != this.bestSwordSlot) {
                this.trash.add(lIIIllIlIlIIII);
            }
        }
    }
    
    private double getArmorProtection(final ItemStack lIIIlllIIIlIII) {
        if (!(lIIIlllIIIlIII.getItem() instanceof ItemArmor)) {
            return 0.0;
        }
        final ItemArmor lIIIlllIIIIlll = (ItemArmor)lIIIlllIIIlIII.getItem();
        final double lIIIlllIIIIllI = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, lIIIlllIIIlIII);
        return lIIIlllIIIIlll.damageReduceAmount + (6.0 + lIIIlllIIIIllI * lIIIlllIIIIllI) * 0.75 / 3.0;
    }
    
    public InvManager() {
        this.autoSword = new BooleanValue("AutoSword", this, false);
        this.aSwordInInv = new BooleanValue("Sword Only in Inv", this, false, (Value)this.autoSword);
        this.aSwordDelay = new NumberValue<Double>("AutoSword Delay", this, 25.0, 0.0, 1000.0, 1.0, this.autoSword);
        this.invCleaner = new BooleanValue("Inv Cleaner", this, false);
        this.invCleanerInInv = new BooleanValue("Clean Only in Inv", this, false, (Value)this.invCleaner);
        this.invCleanerDelay = new NumberValue<Double>("InvCleaner Delay", this, 25.0, 0.0, 1000.0, 1.0, this.invCleaner);
        this.autoArmor = new BooleanValue("Auto Armor", this, false);
        this.autoArmorInInv = new BooleanValue("Armor Only in Inv", this, false, (Value)this.autoArmor);
        this.autoArmorDelay = new NumberValue<Double>("AutoArmor Delay", this, 25.0, 0.0, 1000.0, 1.0, this.autoArmor);
        this.disableIfSlimeBall = new BooleanValue("Disable if compass", this, true);
        this.armorStop = new Stopwatch();
        this.invStop = new Stopwatch();
        this.swordStop = new Stopwatch();
        this.stopwatch = new Stopwatch();
        this.allSwords = new ArrayList<Integer>();
        this.allArmor = new List[4];
        this.trash = new ArrayList<Integer>();
    }
    
    private void autoSwordThing(final int lIIIlllIlIIlIl, final Stopwatch lIIIlllIlIlIII, final int lIIIlllIlIIIll) {
        if (lIIIlllIlIIlIl != -1 && lIIIlllIlIlIII.timeElapsed(this.aSwordDelay.getValue().intValue())) {
            InvManager.mc.playerController.windowClick(lIIIlllIlIIIll, (lIIIlllIlIIlIl < 9) ? (lIIIlllIlIIlIl + 36) : lIIIlllIlIIlIl, 0, 2, InvManager.mc.thePlayer);
            lIIIlllIlIlIII.resetTime();
        }
    }
    
    private int getArmorItemsEquipSlot(final ItemStack lIIIlllIIllIIl, final boolean lIIIlllIIllIII) {
        if (lIIIlllIIllIIl.getUnlocalizedName().contains("helmet")) {
            return lIIIlllIIllIII ? 4 : 5;
        }
        if (lIIIlllIIllIIl.getUnlocalizedName().contains("chestplate")) {
            return lIIIlllIIllIII ? 3 : 6;
        }
        if (lIIIlllIIllIIl.getUnlocalizedName().contains("leggings")) {
            return lIIIlllIIllIII ? 2 : 7;
        }
        if (lIIIlllIIllIIl.getUnlocalizedName().contains("boots")) {
            return lIIIlllIIllIII ? 1 : 8;
        }
        return -1;
    }
    
    private boolean isBadPotion(final ItemStack lIIIllIIlllIII) {
        if (lIIIllIIlllIII != null && lIIIllIIlllIII.getItem() instanceof ItemPotion) {
            final ItemPotion lIIIllIIlllIlI = (ItemPotion)lIIIllIIlllIII.getItem();
            if (ItemPotion.isSplash(lIIIllIIlllIII.getItemDamage())) {
                for (final Object lIIIllIIlllIll : lIIIllIIlllIlI.getEffects(lIIIllIIlllIII)) {
                    final PotionEffect lIIIllIIllllII = (PotionEffect)lIIIllIIlllIll;
                    if (lIIIllIIllllII.getPotionID() == Potion.poison.getId() || lIIIllIIllllII.getPotionID() == Potion.harm.getId() || lIIIllIIllllII.getPotionID() == Potion.moveSlowdown.getId() || lIIIllIIllllII.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
