package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.math.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.inventory.*;
import me.kansio.client.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import com.google.common.eventbus.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import me.kansio.client.utils.player.*;
import net.minecraft.entity.player.*;

@ModuleData(name = "AutoPot", category = ModuleCategory.COMBAT, description = "Pots for you")
public class AutoPot extends Module
{
    private final /* synthetic */ ModeValue enumValue;
    private final /* synthetic */ BooleanValue skywars;
    private final /* synthetic */ NumberValue<Integer> minHealth;
    private final /* synthetic */ Object[] badPotionArray;
    private final /* synthetic */ NumberValue<Integer> delay;
    private static /* synthetic */ boolean isPotting;
    private final /* synthetic */ Stopwatch timer;
    
    private boolean invCheckMisc() {
        for (int lllllllllllllllllllllIIlIlllIlIl = 0; lllllllllllllllllllllIIlIlllIlIl < 45; ++lllllllllllllllllllllIIlIlllIlIl) {
            if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIlIlllIlIl).getHasStack()) {
                if (this.getPotionDataMisc(AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIlIlllIlIl).getStack()) != this.badPotionArray) {
                    final Object[] lllllllllllllllllllllIIlIlllIlll = this.getPotionDataMisc(AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIlIlllIlIl).getStack());
                    final int lllllllllllllllllllllIIlIlllIllI = (int)lllllllllllllllllllllIIlIlllIlll[1];
                    if (lllllllllllllllllllllIIlIlllIllI == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private Object[] getPotionDataMisc(final ItemStack lllllllllllllllllllllIIlIllIIIll) {
        if (lllllllllllllllllllllIIlIllIIIll == null || !(lllllllllllllllllllllIIlIllIIIll.getItem() instanceof ItemPotion) || !ItemPotion.isSplash(lllllllllllllllllllllIIlIllIIIll.getMetadata())) {
            return this.badPotionArray;
        }
        final ItemPotion lllllllllllllllllllllIIlIllIIllI = (ItemPotion)lllllllllllllllllllllIIlIllIIIll.getItem();
        final List<PotionEffect> lllllllllllllllllllllIIlIllIIlIl = lllllllllllllllllllllIIlIllIIllI.getEffects(lllllllllllllllllllllIIlIllIIIll);
        for (final PotionEffect lllllllllllllllllllllIIlIllIlIIl : lllllllllllllllllllllIIlIllIIlIl) {
            if (lllllllllllllllllllllIIlIllIlIIl.getPotionID() == Potion.moveSpeed.id) {
                return new Object[] { true, lllllllllllllllllllllIIlIllIlIIl.getPotionID(), lllllllllllllllllllllIIlIllIIIll };
            }
        }
        return this.badPotionArray;
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" §7").append(this.enumValue.getValue()));
    }
    
    private void refillPotionsMisc() {
        if (this.invCheckMisc()) {
            for (int lllllllllllllllllllllIIlIIllIIII = 0; lllllllllllllllllllllIIlIIllIIII < 45; ++lllllllllllllllllllllIIlIIllIIII) {
                final Slot lllllllllllllllllllllIIlIIllIIIl = AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIlIIllIIII);
                if (lllllllllllllllllllllIIlIIllIIIl.getHasStack()) {
                    if (lllllllllllllllllllllIIlIIllIIII != 37) {
                        if (this.getPotionDataMisc(lllllllllllllllllllllIIlIIllIIIl.getStack()) != this.badPotionArray) {
                            this.swap(lllllllllllllllllllllIIlIIllIIII);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public AutoPot() {
        this.badPotionArray = new Object[] { false };
        this.timer = new Stopwatch();
        this.enumValue = new ModeValue("Mode", this, new String[] { "Down", "Up", "Jump", "Instant Jump" });
        this.skywars = new BooleanValue("Skywars", this, false);
        this.minHealth = new NumberValue<Integer>("Min Health", this, 12, 5, 20, 1);
        this.delay = new NumberValue<Integer>("Delay", this, 150, 50, 1000, 25);
    }
    
    public static void setPotting(final boolean lllllllllllllllllllllIIllIIIllIl) {
        AutoPot.isPotting = lllllllllllllllllllllIIllIIIllIl;
    }
    
    private boolean invCheck() {
        for (int lllllllllllllllllllllIIllIIIIlIl = 0; lllllllllllllllllllllIIllIIIIlIl < 45; ++lllllllllllllllllllllIIllIIIIlIl) {
            if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIllIIIIlIl).getHasStack()) {
                if (this.getPotionData(AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIllIIIIlIl).getStack()) != this.badPotionArray) {
                    final Object[] lllllllllllllllllllllIIllIIIIlll = this.getPotionData(AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIllIIIIlIl).getStack());
                    final int lllllllllllllllllllllIIllIIIIllI = (int)lllllllllllllllllllllIIllIIIIlll[1];
                    if (lllllllllllllllllllllIIllIIIIllI == Potion.regeneration.id || lllllllllllllllllllllIIllIIIIllI == Potion.heal.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private Object[] getPotionData(final ItemStack lllllllllllllllllllllIIlIlIlIllI) {
        if (lllllllllllllllllllllIIlIlIlIllI == null || !(lllllllllllllllllllllIIlIlIlIllI.getItem() instanceof ItemPotion) || !ItemPotion.isSplash(lllllllllllllllllllllIIlIlIlIllI.getMetadata())) {
            return this.badPotionArray;
        }
        final ItemPotion lllllllllllllllllllllIIlIlIlIlIl = (ItemPotion)lllllllllllllllllllllIIlIlIlIllI.getItem();
        final List<PotionEffect> lllllllllllllllllllllIIlIlIlIlII = lllllllllllllllllllllIIlIlIlIlIl.getEffects(lllllllllllllllllllllIIlIlIlIllI);
        for (final PotionEffect lllllllllllllllllllllIIlIlIllIII : lllllllllllllllllllllIIlIlIlIlII) {
            if (lllllllllllllllllllllIIlIlIllIII.getPotionID() == Potion.heal.id || lllllllllllllllllllllIIlIlIllIII.getPotionID() == Potion.regeneration.id) {
                return new Object[] { true, lllllllllllllllllllllIIlIlIllIII.getPotionID(), lllllllllllllllllllllIIlIlIlIllI };
            }
        }
        return this.badPotionArray;
    }
    
    private void refillPotions() {
        if (this.invCheck()) {
            for (int lllllllllllllllllllllIIlIIlllIIl = 0; lllllllllllllllllllllIIlIIlllIIl < 45; ++lllllllllllllllllllllIIlIIlllIIl) {
                final Slot lllllllllllllllllllllIIlIIlllIlI = AutoPot.mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIIlIIlllIIl);
                if (lllllllllllllllllllllIIlIIlllIlI.getHasStack()) {
                    if (lllllllllllllllllllllIIlIIlllIIl != 37) {
                        if (this.getPotionData(lllllllllllllllllllllIIlIIlllIlI.getStack()) != this.badPotionArray) {
                            this.swap(lllllllllllllllllllllIIlIIlllIIl);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lllllllllllllllllllllIIlIlIIlIlI) {
        if (!AutoPot.mc.thePlayer.onGround) {
            return;
        }
        if (this.skywars.getValue() && AutoPot.mc.theWorld.getBlockState(new BlockPos(AutoPot.mc.thePlayer).down()).getBlock() instanceof BlockGlass) {
            return;
        }
        if (!lllllllllllllllllllllIIlIlIIlIlI.isPre()) {
            if (isPotting()) {
                PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(1));
                PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.getHeldItem()));
                PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(AutoPot.mc.thePlayer.inventory.currentItem));
                setPotting(false);
            }
            return;
        }
        if (AutoPot.mc.thePlayer.getHealth() <= this.minHealth.getValue() && this.invCheck()) {
            if (this.timer.timeElapsed(1000L + this.delay.getValue())) {
                this.useMode(lllllllllllllllllllllIIlIlIIlIlI);
                this.refillPotions();
                setPotting(true);
                this.timer.resetTime();
            }
        }
        else {
            if (AutoPot.mc.thePlayer.isPotionActive(Potion.moveSpeed) || !this.invCheckMisc()) {
                return;
            }
            if (this.timer.timeElapsed(1000L + this.delay.getValue())) {
                this.useMode(lllllllllllllllllllllIIlIlIIlIlI);
                this.refillPotionsMisc();
                setPotting(true);
                this.timer.resetTime();
            }
        }
    }
    
    private void useMode(final UpdateEvent lllllllllllllllllllllIIlIlIIIIII) {
        final Exception lllllllllllllllllllllIIlIIllllll = (Exception)this.enumValue.getValue();
        double lllllllllllllllllllllIIlIIlllllI = -1;
        switch (((String)lllllllllllllllllllllIIlIIllllll).hashCode()) {
            case 2136258: {
                if (((String)lllllllllllllllllllllIIlIIllllll).equals("Down")) {
                    lllllllllllllllllllllIIlIIlllllI = 0;
                    break;
                }
                break;
            }
            case 2747: {
                if (((String)lllllllllllllllllllllIIlIIllllll).equals("Up")) {
                    lllllllllllllllllllllIIlIIlllllI = 1;
                    break;
                }
                break;
            }
            case 2320462: {
                if (((String)lllllllllllllllllllllIIlIIllllll).equals("Jump")) {
                    lllllllllllllllllllllIIlIIlllllI = 2;
                    break;
                }
                break;
            }
            case -150664371: {
                if (((String)lllllllllllllllllllllIIlIIllllll).equals("Instant Jump")) {
                    lllllllllllllllllllllIIlIIlllllI = 3;
                    break;
                }
                break;
            }
        }
        switch (lllllllllllllllllllllIIlIIlllllI) {
            case 0.0: {
                lllllllllllllllllllllIIlIlIIIIII.setRotationPitch(90.0f);
                break;
            }
            case 1.0: {
                lllllllllllllllllllllIIlIlIIIIII.setRotationPitch(-90.0f);
                break;
            }
            case 2.0: {
                if (!Client.getInstance().getModuleManager().getModuleByClass((Class<? extends Speed>)Speed.class).isToggled() && AutoPot.mc.thePlayer.onGround && !AutoPot.mc.thePlayer.isMoving()) {
                    AutoPot.mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f);
                }
                lllllllllllllllllllllIIlIlIIIIII.setRotationPitch(-90.0f);
                break;
            }
            case 3.0: {
                if (!Client.getInstance().getModuleManager().getModuleByClass((Class<? extends Speed>)Speed.class).isToggled() && AutoPot.mc.thePlayer.onGround && !AutoPot.mc.thePlayer.isMoving()) {
                    AutoPot.mc.thePlayer.setPosition(AutoPot.mc.thePlayer.posX, AutoPot.mc.thePlayer.posY + PlayerUtil.getMotion(0.42f), AutoPot.mc.thePlayer.posZ);
                }
                lllllllllllllllllllllIIlIlIIIIII.setRotationPitch(-90.0f);
                break;
            }
        }
    }
    
    private void swap(final int lllllllllllllllllllllIIlIlllllII) {
        AutoPot.mc.playerController.windowClick(AutoPot.mc.thePlayer.inventoryContainer.windowId, lllllllllllllllllllllIIlIlllllII, 1, 2, AutoPot.mc.thePlayer);
    }
    
    public static boolean isPotting() {
        return AutoPot.isPotting;
    }
}
