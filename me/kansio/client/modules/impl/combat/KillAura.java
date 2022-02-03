package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import javax.vecmath.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.math.*;
import net.minecraft.network.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.*;
import me.kansio.client.utils.rotations.*;
import me.kansio.client.utils.combat.*;
import net.minecraft.util.*;
import me.kansio.client.gui.notification.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;
import me.kansio.client.*;
import org.apache.commons.lang3.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import me.kansio.client.utils.network.*;
import me.kansio.client.value.*;

@ModuleData(name = "Killaura", category = ModuleCategory.COMBAT, description = "Automatically attacks nearby entities")
public class KillAura extends Module
{
    public /* synthetic */ Vector2f currentRotation;
    public static /* synthetic */ EntityLivingBase target;
    public /* synthetic */ BooleanValue walls;
    public /* synthetic */ NumberValue<Double> cprandom;
    public /* synthetic */ NumberValue chance;
    public /* synthetic */ ModeValue rotatemode;
    public /* synthetic */ BooleanValue animals;
    private /* synthetic */ int index;
    public /* synthetic */ NumberValue<Double> swingrage;
    public /* synthetic */ ModeValue swingmode;
    public static /* synthetic */ boolean isBlocking;
    public /* synthetic */ NumberValue<Double> autoblockRange;
    public final /* synthetic */ Stopwatch attackTimer;
    private /* synthetic */ Rotation lastRotation;
    public /* synthetic */ ModeValue targethudmode;
    public static /* synthetic */ boolean swinging;
    public /* synthetic */ BooleanValue players;
    public /* synthetic */ BooleanValue friends;
    public /* synthetic */ BooleanValue monsters;
    public /* synthetic */ ModeValue mode;
    private /* synthetic */ boolean canBlock;
    public /* synthetic */ NumberValue<Double> cps;
    public /* synthetic */ BooleanValue invisible;
    public /* synthetic */ BooleanValue gcd;
    public /* synthetic */ ModeValue autoblockmode;
    public /* synthetic */ ModeValue targetPriority;
    public /* synthetic */ BooleanValue targethud;
    
    @Subscribe
    public void onPacket(final PacketEvent llIIllllIIIIl) {
        final Packet llIIllllIIIll = llIIllllIIIIl.getPacket();
        if (KillAura.isBlocking && ((llIIllllIIIll instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)llIIllllIIIll).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) || llIIllllIIIll instanceof C08PacketPlayerBlockPlacement)) {
            llIIllllIIIIl.setCancelled(true);
        }
        if (llIIllllIIIll instanceof C09PacketHeldItemChange) {
            KillAura.isBlocking = false;
        }
        if (this.gcd.getValue() && KillAura.target != null && llIIllllIIIIl.getPacket() instanceof C03PacketPlayer && llIIllllIIIIl.getPacket().getRotating()) {
            final C03PacketPlayer llIIllllIlIIl = llIIllllIIIIl.getPacket();
            final float llIIllllIlIII = (float)(0.005 * KillAura.mc.gameSettings.mouseSensitivity / 0.005);
            final double llIIllllIIlll = llIIllllIlIII * 0.6 + 0.2;
            final double llIIllllIIllI = llIIllllIlIII * llIIllllIlIII * llIIllllIlIII * 1.2;
            final C03PacketPlayer c03PacketPlayer = llIIllllIlIIl;
            c03PacketPlayer.pitch -= (float)(llIIllllIlIIl.pitch % llIIllllIIllI);
            final C03PacketPlayer c03PacketPlayer2 = llIIllllIlIIl;
            c03PacketPlayer2.yaw -= (float)(llIIllllIlIIl.yaw % llIIllllIIllI);
        }
    }
    
    @Subscribe
    public void doHoldBlock(final UpdateEvent llIlIIlIIIIII) {
        if (this.autoblockmode.getValue().equalsIgnoreCase("Hold")) {
            KillAura.mc.gameSettings.keyBindUseItem.pressed = (KillAura.target != null);
        }
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent llIIlllIllIII) {
        if (KillAura.target == null) {
            return;
        }
        if (this.targethud.getValue()) {
            TargetHUD.draw(llIIlllIllIII, KillAura.target);
        }
    }
    
    public KillAura() {
        this.attackTimer = new Stopwatch();
        this.mode = new ModeValue("Mode", this, new String[] { "Smart" });
        this.targetPriority = new ModeValue("Target Priority", this, new String[] { "None", "Distance", "Armor", "HurtTime", "Health" });
        this.rotatemode = new ModeValue("Rotation Mode", this, new String[] { "None", "Default", "Down", "NCP", "AAC", "GWEN" });
        this.swingrage = new NumberValue<Double>("Swing Range", this, 3.0, 1.0, 9.0, 0.1);
        this.autoblockRange = new NumberValue<Double>("Block Range", this, 3.0, 1.0, 12.0, 0.1);
        this.cps = new NumberValue<Double>("CPS", this, 12.0, 1.0, 20.0, 1.0);
        this.cprandom = new NumberValue<Double>("Randomize CPS", this, 3.0, 0.0, 10.0, 1.0);
        this.chance = new NumberValue("Hit Chance", this, (T)100, (T)0, (T)100, (T)1);
        this.swingmode = new ModeValue("Swing Mode", this, new String[] { "Client", "Server" });
        this.autoblockmode = new ModeValue("Autoblock Mode", this, new String[] { "None", "Real", "Verus", "Fake" });
        this.gcd = new BooleanValue("GCD", this, false);
        this.targethud = new BooleanValue("TargetHud", this, false);
        this.targethudmode = new ModeValue("TargetHud Mode", this, this.targethud, new String[] { "Sleek", "Moon" });
        this.players = new BooleanValue("Players", this, true);
        this.friends = new BooleanValue("Friends", this, true);
        this.animals = new BooleanValue("Animals", this, true);
        this.monsters = new BooleanValue("Monsters", this, true);
        this.invisible = new BooleanValue("Invisibles", this, true);
        this.walls = new BooleanValue("Walls", this, true);
        this.currentRotation = null;
    }
    
    public void aimAtTarget(final UpdateEvent llIIlllllIlll, final String llIIlllllIllI, final Entity llIIlllllIlIl) {
        Rotation llIIllllllIlI = AimUtil.getRotationsRandom((EntityLivingBase)llIIlllllIlIl);
        if (this.lastRotation == null) {
            this.lastRotation = llIIllllllIlI;
            this.attackTimer.resetTime();
            return;
        }
        Rotation llIIllllllIIl = llIIllllllIlI;
        llIIllllllIlI = this.lastRotation;
        final double llIIlllllIIlI = (double)llIIlllllIllI.toUpperCase();
        double llIIlllllIIIl = -1;
        switch (((String)llIIlllllIIlI).hashCode()) {
            case -2032180703: {
                if (((String)llIIlllllIIlI).equals("DEFAULT")) {
                    llIIlllllIIIl = 0;
                    break;
                }
                break;
            }
            case 2104482: {
                if (((String)llIIlllllIIlI).equals("DOWN")) {
                    llIIlllllIIIl = 1;
                    break;
                }
                break;
            }
            case 77115: {
                if (((String)llIIlllllIIlI).equals("NCP")) {
                    llIIlllllIIIl = 2;
                    break;
                }
                break;
            }
            case 64547: {
                if (((String)llIIlllllIIlI).equals("AAC")) {
                    llIIlllllIIIl = 3;
                    break;
                }
                break;
            }
            case 2200985: {
                if (((String)llIIlllllIIlI).equals("GWEN")) {
                    llIIlllllIIIl = 4;
                    break;
                }
                break;
            }
        }
        switch (llIIlllllIIIl) {
            case 0.0: {
                llIIlllllIlll.setRotationYaw(llIIllllllIlI.getRotationYaw());
                llIIlllllIlll.setRotationPitch(llIIllllllIlI.getRotationPitch());
                break;
            }
            case 1.0: {
                llIIllllllIIl = new Rotation(KillAura.mc.thePlayer.rotationYaw, 90.0f);
                llIIlllllIlll.setRotationPitch(90.0f);
                break;
            }
            case 2.0: {
                llIIllllllIIl = (this.lastRotation = (llIIllllllIlI = Rotation.fromFacing((EntityLivingBase)llIIlllllIlIl)));
                llIIlllllIlll.setRotationYaw(llIIllllllIlI.getRotationYaw());
                break;
            }
            case 3.0: {
                llIIllllllIlI = new Rotation(KillAura.mc.thePlayer.rotationYaw, llIIllllllIIl.getRotationPitch());
                llIIlllllIlll.setRotationPitch(llIIllllllIlI.getRotationPitch());
                break;
            }
            case 4.0: {
                llIIllllllIIl = ((KillAura.mc.thePlayer.ticksExisted % 5 == 0) ? AimUtil.getRotationsRandom((EntityLivingBase)llIIlllllIlIl) : this.lastRotation);
                llIIlllllIlll.setRotationYaw(llIIllllllIIl.getRotationYaw());
                llIIlllllIlll.setRotationPitch(llIIllllllIIl.getRotationPitch());
                break;
            }
        }
        this.lastRotation = llIIllllllIIl;
    }
    
    private void unblock() {
        KillAura.isBlocking = false;
    }
    
    public static boolean isSwinging() {
        return KillAura.swinging;
    }
    
    @Override
    public void onEnable() {
        this.index = 0;
        this.lastRotation = null;
        KillAura.target = null;
        this.attackTimer.resetTime();
    }
    
    @Subscribe
    public void onMotion(final UpdateEvent llIlIIIIllIII) {
        final List<EntityLivingBase> llIlIIIIlllII = FightUtil.getMultipleTargets(this.swingrage.getValue(), this.players.getValue(), this.friends.getValue(), this.animals.getValue(), this.walls.getValue(), this.monsters.getValue(), this.invisible.getValue());
        if (KillAura.mc.currentScreen != null) {
            return;
        }
        if (KillAura.isBlocking && KillAura.target == null) {
            this.unblock();
            KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (KillAura.mc.thePlayer.ticksExisted < 5 && this.isToggled()) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "World Change!", "Killaura disabled", 1));
            this.toggle();
        }
        final List<EntityLivingBase> llIlIIIIllIll = FightUtil.getMultipleTargets(this.autoblockRange.getValue(), this.players.getValue(), this.friends.getValue(), this.animals.getValue(), this.walls.getValue(), this.monsters.getValue(), this.invisible.getValue());
        llIlIIIIlllII.removeIf(llIIllIllllIl -> llIIllIllllIl.getName().contains("[NPC]"));
        final ItemStack llIlIIIIllIlI = KillAura.mc.thePlayer.getHeldItem();
        this.canBlock = (!llIlIIIIllIll.isEmpty() && llIlIIIIllIlI != null && llIlIIIIllIlI.getItem() instanceof ItemSword);
        if (llIlIIIIllIII.isPre()) {
            KillAura.target = null;
        }
        if (llIlIIIIlllII.isEmpty()) {
            this.index = 0;
            KillAura.isBlocking = false;
        }
        else {
            if (this.index >= llIlIIIIlllII.size()) {
                this.index = 0;
            }
            if (this.canBlock) {
                final boolean llIlIIIIlIlII = ((Value<Boolean>)this.autoblockmode).getValue();
                char llIlIIIIlIIll = (char)(-1);
                switch (((String)llIlIIIIlIlII).hashCode()) {
                    case 2543038: {
                        if (((String)llIlIIIIlIlII).equals("Real")) {
                            llIlIIIIlIIll = '\0';
                            break;
                        }
                        break;
                    }
                    case 2182005: {
                        if (((String)llIlIIIIlIlII).equals("Fake")) {
                            llIlIIIIlIIll = '\u0001';
                            break;
                        }
                        break;
                    }
                }
                switch (llIlIIIIlIIll) {
                    case '\0': {
                        if (!llIlIIIIllIII.isPre()) {
                            KillAura.mc.playerController.sendUseItem(KillAura.mc.thePlayer, KillAura.mc.theWorld, KillAura.mc.thePlayer.getHeldItem());
                            KillAura.isBlocking = true;
                            break;
                        }
                        break;
                    }
                    case '\u0001': {
                        KillAura.isBlocking = true;
                        break;
                    }
                }
            }
            if (llIlIIIIllIII.isPre()) {
                final boolean llIlIIIIlIlII = ((Value<Boolean>)this.mode).getValue();
                char llIlIIIIlIIll = (char)(-1);
                switch (((String)llIlIIIIlIlII).hashCode()) {
                    case 79996329: {
                        if (((String)llIlIIIIlIlII).equals("Smart")) {
                            llIlIIIIlIIll = '\0';
                            break;
                        }
                        break;
                    }
                    case -889473228: {
                        if (((String)llIlIIIIlIlII).equals("switch")) {
                            llIlIIIIlIIll = '\u0001';
                            break;
                        }
                        break;
                    }
                }
                switch (llIlIIIIlIIll) {
                    case '\0': {
                        final long llIlIIIIlIIlI = (long)this.targetPriority.getValue().toLowerCase();
                        boolean llIlIIIIlIIIl = -1 != 0;
                        switch (((String)llIlIIIIlIIlI).hashCode()) {
                            case 288459765: {
                                if (((String)llIlIIIIlIIlI).equals("distance")) {
                                    llIlIIIIlIIIl = false;
                                    break;
                                }
                                break;
                            }
                            case 93086015: {
                                if (((String)llIlIIIIlIIlI).equals("armor")) {
                                    llIlIIIIlIIIl = true;
                                    break;
                                }
                                break;
                            }
                            case 701808476: {
                                if (((String)llIlIIIIlIIlI).equals("hurttime")) {
                                    llIlIIIIlIIIl = (2 != 0);
                                    break;
                                }
                                break;
                            }
                            case -1221262756: {
                                if (((String)llIlIIIIlIIlI).equals("health")) {
                                    llIlIIIIlIIIl = (3 != 0);
                                    break;
                                }
                                break;
                            }
                        }
                        switch (llIlIIIIlIIIl) {
                            case 0: {
                                llIlIIIIlllII.sort(Comparator.comparingInt(llIIlllIIIIII -> (int)(-llIIlllIIIIII.getDistanceToEntity(KillAura.mc.thePlayer))));
                                break;
                            }
                            case 1: {
                                llIlIIIIlllII.sort(Comparator.comparingInt(llIIlllIIIIll -> -llIIlllIIIIll.getTotalArmorValue()));
                                break;
                            }
                            case 2: {
                                llIlIIIIlllII.sort(Comparator.comparingInt(llIIlllIIIlll -> -llIIlllIIIlll.hurtResistantTime));
                                break;
                            }
                            case 3: {
                                llIlIIIIlllII.sort(Comparator.comparingInt(llIIlllIIlIlI -> (int)(-llIIlllIIlIlI.getHealth())));
                                break;
                            }
                        }
                        Collections.reverse(llIlIIIIlllII);
                        KillAura.target = llIlIIIIlllII.get(0);
                        final List<EntityLivingBase> list;
                        llIlIIIIlllII.forEach(llIIlllIIllII -> {
                            if (llIIlllIIllII instanceof EntityPlayer && Client.getInstance().getTargetManager().isTarget((EntityPlayer)KillAura.target)) {
                                KillAura.target = list.get(list.indexOf(llIIlllIIllII));
                            }
                            return;
                        });
                        break;
                    }
                    case '\u0001': {
                        KillAura.target = llIlIIIIlllII.get(this.index);
                        if (KillAura.target == null) {
                            KillAura.target = llIlIIIIlllII.get(0);
                            break;
                        }
                        break;
                    }
                }
                this.aimAtTarget(llIlIIIIllIII, this.rotatemode.getValue(), KillAura.target);
            }
            if (llIlIIIIllIII.isPre()) {
                final boolean llIlIIIIlllll = this.attackTimer.timeElapsed((long)(1000.0 / this.cps.getValue()));
                if (llIlIIIIlllll) {
                    if (this.cps.getValue() > 9.0) {
                        this.cps.setValue(this.cps.getValue() - RandomUtils.nextInt(0, this.cprandom.getValue().intValue()));
                    }
                    else {
                        this.cps.setValue(this.cps.getValue() + RandomUtils.nextInt(0, this.cprandom.getValue().intValue()));
                    }
                    final char llIlIIIIlIIll = ((Value<Character>)this.mode).getValue();
                    long llIlIIIIlIIlI = -1;
                    switch (((String)llIlIIIIlIIll).hashCode()) {
                        case -1805606060: {
                            if (((String)llIlIIIIlIIll).equals("Switch")) {
                                llIlIIIIlIIlI = 0;
                                break;
                            }
                            break;
                        }
                        case 79996329: {
                            if (((String)llIlIIIIlIIll).equals("Smart")) {
                                llIlIIIIlIIlI = 1;
                                break;
                            }
                            break;
                        }
                    }
                    switch (llIlIIIIlIIlI) {
                        case 0L: {
                            if (llIlIIIIlllll && this.attack(KillAura.target, this.chance.getValue().intValue(), this.autoblockmode.getValue())) {
                                ++this.index;
                                this.attackTimer.resetTime();
                                break;
                            }
                            break;
                        }
                        case 1L: {
                            if (llIlIIIIlllll && this.attack(KillAura.target, this.chance.getValue().intValue(), this.autoblockmode.getValue())) {
                                this.attackTimer.resetTime();
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
    
    private boolean attack(final EntityLivingBase llIlIIIIIllII, final double llIlIIIIIIlll, final String llIlIIIIIlIlI) {
        if (FightUtil.canHit(llIlIIIIIIlll / 100.0)) {
            if (this.swingmode.getValue().equalsIgnoreCase("client")) {
                KillAura.mc.thePlayer.swingItem();
            }
            else if (this.swingmode.getValue().equalsIgnoreCase("server")) {
                KillAura.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            }
            KillAura.mc.playerController.attackEntity(KillAura.mc.thePlayer, llIlIIIIIllII);
            if (!KillAura.isBlocking && this.autoblockmode.getValue().equalsIgnoreCase("verus")) {
                PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.inventory.getCurrentItem()));
                KillAura.isBlocking = true;
            }
            return true;
        }
        KillAura.mc.thePlayer.swingItem();
        return false;
    }
    
    @Override
    public void onDisable() {
        if (KillAura.isBlocking) {
            this.unblock();
        }
        KillAura.isBlocking = false;
        KillAura.mc.gameSettings.keyBindUseItem.pressed = false;
        KillAura.swinging = false;
        this.currentRotation = null;
        KillAura.target = null;
        if (!KillAura.mc.thePlayer.isBlocking()) {
            KillAura.isBlocking = false;
            KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
