package me.kansio.client.modules.impl.world;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.*;
import me.kansio.client.utils.rotations.*;
import net.minecraft.potion.*;
import me.kansio.client.utils.player.*;
import javax.vecmath.*;
import net.minecraft.client.entity.*;
import com.google.common.eventbus.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.visuals.*;
import java.awt.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.utils.font.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.event.impl.*;

@ModuleData(name = "Scaffold", category = ModuleCategory.WORLD, description = "Places blocks under you")
public class Scaffold extends Module
{
    private /* synthetic */ int lastSlot;
    private final /* synthetic */ Stopwatch delayTimer;
    private /* synthetic */ NumberValue<Integer> expansion;
    private /* synthetic */ int animation;
    private /* synthetic */ boolean didPlaceBlock;
    private /* synthetic */ NumberValue<Integer> delay;
    private /* synthetic */ BooleanValue swing;
    private final /* synthetic */ Stopwatch sneakTimer;
    private /* synthetic */ int startSlot;
    public /* synthetic */ BooleanValue safewalk;
    public /* synthetic */ BooleanValue keepY;
    private /* synthetic */ BooleanValue info;
    private final /* synthetic */ Stopwatch towerTimer;
    private /* synthetic */ BlockEntry lastBlockEntry;
    private /* synthetic */ BooleanValue tower;
    private /* synthetic */ BlockEntry blockEntry;
    private /* synthetic */ BooleanValue sprint;
    private /* synthetic */ ModeValue modeValue;
    
    private boolean isPlaceable(final ItemStack lIIllllIIlIlll) {
        if (lIIllllIIlIlll != null && lIIllllIIlIlll.getItem() instanceof ItemBlock) {
            final Block lIIllllIIllIlI = ((ItemBlock)lIIllllIIlIlll.getItem()).getBlock();
            return !(lIIllllIIllIlI instanceof BlockNote) && !(lIIllllIIllIlI instanceof BlockFurnace) && !lIIllllIIllIlI.getLocalizedName().equalsIgnoreCase("Crafting Table") && !(lIIllllIIllIlI instanceof BlockWeb) && !(lIIllllIIllIlI instanceof BlockFence) && !(lIIllllIIllIlI instanceof BlockFenceGate) && !(lIIllllIIllIlI instanceof BlockSlab) && !(lIIllllIIllIlI instanceof BlockStairs);
        }
        return true;
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.modeValue.getValueAsString()));
    }
    
    @Override
    public void onEnable() {
        this.delayTimer.resetTime();
        final ScaledResolution lIlIIIIIlIIlIl = RenderUtils.getResolution();
        this.animation = 0;
        final short lIlIIIIIlIIIlI = (short)this.modeValue.getValueAsString();
        String lIlIIIIIlIIIIl = (String)(-1);
        switch (((String)lIlIIIIIlIIIlI).hashCode()) {
            case 82544993: {
                if (((String)lIlIIIIIlIIIlI).equals("Verus")) {
                    lIlIIIIIlIIIIl = (String)0;
                    break;
                }
                break;
            }
            case 68597: {
                if (((String)lIlIIIIIlIIIlI).equals("Dev")) {
                    lIlIIIIIlIIIIl = (String)1;
                    break;
                }
                break;
            }
            case 77184: {
                if (((String)lIlIIIIIlIIIlI).equals("NEW")) {
                    lIlIIIIIlIIIIl = (String)2;
                    break;
                }
                break;
            }
            case 77115: {
                if (((String)lIlIIIIIlIIIlI).equals("NCP")) {
                    lIlIIIIIlIIIIl = (String)3;
                    break;
                }
                break;
            }
            case 2089667258: {
                if (((String)lIlIIIIIlIIIlI).equals("Expand")) {
                    lIlIIIIIlIIIIl = (String)4;
                    break;
                }
                break;
            }
        }
        switch (lIlIIIIIlIIIIl) {
            case 0L: {
                this.blockEntry = null;
                this.didPlaceBlock = false;
                this.startSlot = Scaffold.mc.thePlayer.inventory.currentItem;
                if (this.getSlotWithBlock() > -1) {
                    Scaffold.mc.thePlayer.inventory.currentItem = this.getSlotWithBlock();
                }
                this.lastSlot = this.getSlotWithBlock();
                break;
            }
            case 1L:
            case 2L:
            case 3L: {
                this.blockEntry = null;
                this.startSlot = Scaffold.mc.thePlayer.inventory.currentItem;
                if (this.getSlotWithBlock() > -1) {
                    Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.getSlotWithBlock()));
                }
                this.lastSlot = this.getSlotWithBlock();
                break;
            }
            case 4L: {
                this.startSlot = Scaffold.mc.thePlayer.inventory.currentItem;
                if (this.getSlotWithBlock() > -1) {
                    Scaffold.mc.thePlayer.inventory.currentItem = this.getSlotWithBlock();
                }
                this.lastSlot = this.getSlotWithBlock();
                break;
            }
        }
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIIlllllllIlII) {
        final int lIIlllllllIIll = (int)this.modeValue.getValueAsString();
        double lIIlllllllIIlI = -1;
        switch (((String)lIIlllllllIIll).hashCode()) {
            case -1721492669: {
                if (((String)lIIlllllllIIll).equals("Vulcan")) {
                    lIIlllllllIIlI = 0;
                    break;
                }
                break;
            }
            case 82544993: {
                if (((String)lIIlllllllIIll).equals("Verus")) {
                    lIIlllllllIIlI = 1;
                    break;
                }
                break;
            }
            case 2089790266: {
                if (((String)lIIlllllllIIll).equals("Extend")) {
                    lIIlllllllIIlI = 2;
                    break;
                }
                break;
            }
            case 77184: {
                if (((String)lIIlllllllIIll).equals("NEW")) {
                    lIIlllllllIIlI = 3;
                    break;
                }
                break;
            }
            case 77115: {
                if (((String)lIIlllllllIIll).equals("NCP")) {
                    lIIlllllllIIlI = 4;
                    break;
                }
                break;
            }
            case 68597: {
                if (((String)lIIlllllllIIll).equals("Dev")) {
                    lIIlllllllIIlI = 5;
                    break;
                }
                break;
            }
        }
        switch (lIIlllllllIIlI) {
            case 0.0:
            case 1.0: {
                Vector2f lIlIIIIIIIIllI = null;
                if (Scaffold.mc.thePlayer.isMoving()) {
                    Scaffold.mc.thePlayer.forceSprinting(this.sprint.getValue());
                }
                if (this.blockEntry != null) {
                    lIlIIIIIIIIllI = RotationUtil.getRotations(this.getPositionByFace(this.blockEntry.getPosition(), this.blockEntry.getFacing()));
                    lIlIIIIIIIIllI.setY(90.0f);
                }
                final BlockEntry lIlIIIIIIIIlIl = this.find(new Vec3(0.0, 0.0, 0.0));
                if (lIlIIIIIIIIlIl == null) {
                    return;
                }
                this.blockEntry = lIlIIIIIIIIlIl;
                if (lIlIIIIIIIIllI != null) {
                    lIIlllllllIlII.setRotationYaw(lIlIIIIIIIIllI.x);
                    lIIlllllllIlII.setRotationPitch(lIlIIIIIIIIllI.y);
                }
                final int lIlIIIIIIIIlII = this.getSlotWithBlock();
                if (this.getBlockCount() < 1 && this.didPlaceBlock) {
                    final EntityPlayerSP thePlayer = Scaffold.mc.thePlayer;
                    thePlayer.motionY -= 20.0;
                    this.didPlaceBlock = false;
                    return;
                }
                if (this.modeValue.getValueAsString().equalsIgnoreCase("vulcan") && this.sneakTimer.timeElapsed(1000L)) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
                    if (this.sneakTimer.timeElapsed(1100L)) {
                        Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
                        this.sneakTimer.resetTime();
                    }
                }
                if (this.blockEntry == null || lIlIIIIIIIIllI == null || lIlIIIIIIIIlII <= -1 || !lIIlllllllIlII.isPre()) {
                    break;
                }
                if (this.lastSlot != lIlIIIIIIIIlII) {
                    Scaffold.mc.thePlayer.inventory.currentItem = lIlIIIIIIIIlII;
                    this.lastSlot = lIlIIIIIIIIlII;
                }
                this.placeBlockVerus(this.blockEntry.getPosition().add(0, 0, 0), this.blockEntry.getFacing(), lIlIIIIIIIIlII, this.swing.getValue());
                if (!this.tower.getValue() || Scaffold.mc.thePlayer.isPotionActive(Potion.jump) || Scaffold.mc.thePlayer.isMoving() || !Scaffold.mc.gameSettings.keyBindJump.pressed) {
                    break;
                }
                Scaffold.mc.thePlayer.motionY = 0.5199999809265137;
                final EntityPlayerSP thePlayer2 = Scaffold.mc.thePlayer;
                final EntityPlayerSP thePlayer3 = Scaffold.mc.thePlayer;
                final double n = 0.0;
                thePlayer3.motionX = n;
                thePlayer2.motionZ = n;
                if (this.towerTimer.timeElapsed(1500L)) {
                    this.towerTimer.resetTime();
                    Scaffold.mc.thePlayer.motionY = -1.2799999713897705;
                    break;
                }
                break;
            }
            case 2.0: {
                final Vector2f lIlIIIIIIIIIII = null;
                if (Scaffold.mc.thePlayer.isMoving()) {
                    Scaffold.mc.thePlayer.forceSprinting(this.sprint.getValue());
                }
                final int lIIlllllllllll = this.getSlotWithBlock();
                if (lIIlllllllllll > -1 && lIIlllllllIlII.isPre()) {
                    if (this.lastSlot != lIIlllllllllll) {
                        Scaffold.mc.thePlayer.inventory.currentItem = lIIlllllllllll;
                        this.lastSlot = lIIlllllllllll;
                    }
                    for (int lIlIIIIIIIIIIl = this.expansion.getValue() * 5, lIlIIIIIIIIIlI = 0; lIlIIIIIIIIIlI < lIlIIIIIIIIIIl; ++lIlIIIIIIIIIlI) {
                        final BlockEntry lIlIIIIIIIIIll = this.findExpand(new Vec3(Scaffold.mc.thePlayer.motionX * lIlIIIIIIIIIlI, 0.0, Scaffold.mc.thePlayer.motionZ * lIlIIIIIIIIIlI), lIlIIIIIIIIIlI);
                        if (lIlIIIIIIIIIll != null) {}
                    }
                    break;
                }
                break;
            }
            case 3.0:
            case 4.0: {
                if (this.lastBlockEntry != null && this.blockEntry != null) {
                    final Vector2f lIIllllllllllI = RotationUtil.getRotations(this.getPositionByFace(this.lastBlockEntry.getPosition(), this.lastBlockEntry.getFacing()));
                    if (Scaffold.mc.thePlayer.isMoving()) {
                        Scaffold.mc.thePlayer.forceSprinting(this.sprint.getValue());
                    }
                    if (this.modeValue.getValue().equals("NCP") || this.modeValue.getValue().equals("New")) {
                        lIIlllllllIlII.setRotationYaw(lIIllllllllllI.x);
                        lIIlllllllIlII.setRotationPitch(lIIllllllllllI.y);
                        Scaffold.mc.thePlayer.rotationYawHead = lIIllllllllllI.x;
                        Scaffold.mc.thePlayer.renderPitchHead = lIIllllllllllI.y;
                    }
                    else {
                        lIIlllllllIlII.setRotationYaw(RotationUtil.getRotations(this.getPositionByFace(this.blockEntry.getPosition(), this.blockEntry.getFacing())).x);
                        lIIlllllllIlII.setRotationPitch(80.0f);
                    }
                    final BlockEntry lIIlllllllllIl = this.find(new Vec3(0.0, 0.0, 0.0));
                    if (lIIlllllllllIl == null) {
                        return;
                    }
                    this.blockEntry = lIIlllllllllIl;
                }
                final BlockEntry lIIllllllllIll = this.find(new Vec3(0.0, 0.0, 0.0));
                this.lastBlockEntry = lIIllllllllIll;
                if (lIIlllllllIlII.isPre() && lIIllllllllIll != null) {
                    final int lIIlllllllllII = this.getSlotWithBlock();
                    if (lIIlllllllllII > -1) {
                        if (this.lastSlot != lIIlllllllllII) {
                            Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(lIIlllllllllII));
                            this.lastSlot = lIIlllllllllII;
                        }
                        if (this.placeBlock(lIIllllllllIll.getPosition().add(0, 0, 0), lIIllllllllIll.getFacing(), lIIlllllllllII, this.swing.getValue()) && this.tower.getValue() && !Scaffold.mc.thePlayer.isPotionActive(Potion.jump) && !Scaffold.mc.thePlayer.isMoving() && Scaffold.mc.gameSettings.keyBindJump.pressed) {
                            Scaffold.mc.thePlayer.motionY = 0.41999998688697815;
                            final EntityPlayerSP thePlayer4 = Scaffold.mc.thePlayer;
                            final EntityPlayerSP thePlayer5 = Scaffold.mc.thePlayer;
                            final double n2 = 0.0;
                            thePlayer5.motionX = n2;
                            thePlayer4.motionZ = n2;
                            if (this.towerTimer.timeElapsed(1500L)) {
                                this.towerTimer.resetTime();
                                Scaffold.mc.thePlayer.motionY = -0.2800000011920929;
                            }
                        }
                    }
                    break;
                }
                break;
            }
            case 5.0: {
                Vector2f lIIllllllllIlI = null;
                Scaffold.mc.thePlayer.forceSprinting(false);
                PlayerUtil.setMotion(0.005);
                if (this.blockEntry != null) {
                    lIIllllllllIlI = RotationUtil.getRotations(this.getPositionByFace(this.blockEntry.getPosition(), this.blockEntry.getFacing()));
                    lIIllllllllIlI.setY(90.0f);
                }
                final BlockEntry lIIllllllllIIl = this.find(new Vec3(0.0, 0.0, 0.0));
                if (lIIllllllllIIl == null) {
                    return;
                }
                this.blockEntry = lIIllllllllIIl;
                if (lIIllllllllIlI != null) {
                    lIIlllllllIlII.setRotationYaw(lIIllllllllIlI.x);
                    lIIlllllllIlII.setRotationPitch(lIIllllllllIlI.y);
                }
                final int lIIllllllllIII = this.getSlotWithBlock();
                if (this.getBlockCount() < 1 && this.didPlaceBlock) {
                    final EntityPlayerSP thePlayer6 = Scaffold.mc.thePlayer;
                    thePlayer6.motionY -= 20.0;
                    this.didPlaceBlock = false;
                    return;
                }
                if (this.blockEntry != null && lIIllllllllIlI != null && lIIllllllllIII > -1 && lIIlllllllIlII.isPre()) {
                    if (this.lastSlot != lIIllllllllIII) {
                        Scaffold.mc.thePlayer.inventory.currentItem = lIIllllllllIII;
                        this.lastSlot = lIIllllllllIII;
                    }
                    this.placeBlockVerus(this.blockEntry.getPosition().add(0, 0, 0), this.blockEntry.getFacing(), lIIllllllllIII, this.swing.getValue());
                    break;
                }
                break;
            }
        }
    }
    
    public Scaffold() {
        this.delayTimer = new Stopwatch();
        this.towerTimer = new Stopwatch();
        this.sneakTimer = new Stopwatch();
        this.safewalk = new BooleanValue("Safewalk", this, true);
        this.keepY = new BooleanValue("Keep Y", this, false);
        this.modeValue = new ModeValue("Mode", this, new String[] { "Verus", "New", "NCP", "Vulcan", "Dev" });
        this.swing = new BooleanValue("Swing", this, true);
        this.sprint = new BooleanValue("Sprint", this, false);
        this.tower = new BooleanValue("Tower", this, true);
        this.info = new BooleanValue("Show Info", this, true);
        this.delay = new NumberValue<Integer>("Delay", this, 0, 0, 9000, 1);
        this.expansion = new NumberValue<Integer>("Expansion", this, 4, 1, 6, 1);
        this.animation = 0;
    }
    
    public Vec3 getPositionByFace(final BlockPos lIIlllIlIlllII, final EnumFacing lIIlllIlIlIlll) {
        final Vec3 lIIlllIlIllIlI = new Vec3(lIIlllIlIlIlll.getDirectionVec().getX() / 2.0, lIIlllIlIlIlll.getDirectionVec().getY() / 2.0, lIIlllIlIlIlll.getDirectionVec().getZ() / 2.0);
        final Vec3 lIIlllIlIllIIl = new Vec3(lIIlllIlIlllII.getX() + 0.5, lIIlllIlIlllII.getY() + 0.75, lIIlllIlIlllII.getZ() + 0.5);
        return lIIlllIlIllIIl.add(lIIlllIlIllIlI);
    }
    
    @Override
    public void onDisable() {
        final double lIlIIIIIIllIll = (double)this.modeValue.getValueAsString();
        double lIlIIIIIIllIlI = -1;
        switch (((String)lIlIIIIIIllIll).hashCode()) {
            case 82544993: {
                if (((String)lIlIIIIIIllIll).equals("Verus")) {
                    lIlIIIIIIllIlI = 0;
                    break;
                }
                break;
            }
            case 77184: {
                if (((String)lIlIIIIIIllIll).equals("NEW")) {
                    lIlIIIIIIllIlI = 1;
                    break;
                }
                break;
            }
            case 68597: {
                if (((String)lIlIIIIIIllIll).equals("Dev")) {
                    lIlIIIIIIllIlI = 2;
                    break;
                }
                break;
            }
            case 77115: {
                if (((String)lIlIIIIIIllIll).equals("NCP")) {
                    lIlIIIIIIllIlI = 3;
                    break;
                }
                break;
            }
            case 2089790266: {
                if (((String)lIlIIIIIIllIll).equals("Extend")) {
                    lIlIIIIIIllIlI = 4;
                    break;
                }
                break;
            }
        }
        switch (lIlIIIIIIllIlI) {
            case 1.0:
            case 2.0:
            case 3.0: {
                Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.startSlot));
                this.lastBlockEntry = null;
                break;
            }
            case 4.0: {
                if (Scaffold.mc.thePlayer.inventory.currentItem != this.startSlot) {
                    Scaffold.mc.thePlayer.inventory.currentItem = this.startSlot;
                    break;
                }
                break;
            }
        }
    }
    
    public int getBlockCount() {
        int lIIllllIIIIllI = 0;
        for (int lIIllllIIIlIII = 0; lIIllllIIIlIII < 8; ++lIIllllIIIlIII) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(lIIllllIIIlIII) != null) {
                if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(lIIllllIIIlIII) != null) {
                    final ItemStack lIIllllIIIlIIl = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(lIIllllIIIlIII);
                    if (lIIllllIIIlIIl.getItem() instanceof ItemBlock) {
                        lIIllllIIIIllI += lIIllllIIIlIIl.stackSize;
                    }
                }
            }
        }
        return lIIllllIIIIllI;
    }
    
    public int getSlotWithBlock() {
        for (int lIIllllIIlIIIl = 0; lIIllllIIlIIIl < 9; ++lIIllllIIlIIIl) {
            final ItemStack lIIllllIIlIIlI = Scaffold.mc.thePlayer.inventory.mainInventory[lIIllllIIlIIIl];
            if (this.isPlaceable(lIIllllIIlIIlI)) {
                if (lIIllllIIlIIlI != null) {
                    if (lIIllllIIlIIlI.getItem() instanceof ItemAnvilBlock) {
                        continue;
                    }
                    if (lIIllllIIlIIlI.getItem() instanceof ItemBlock && ((ItemBlock)lIIllllIIlIIlI.getItem()).getBlock() instanceof BlockSand) {
                        continue;
                    }
                }
                if (lIIllllIIlIIlI != null && lIIllllIIlIIlI.getItem() instanceof ItemBlock) {
                    if (((ItemBlock)lIIllllIIlIIlI.getItem()).getBlock().maxY - ((ItemBlock)lIIllllIIlIIlI.getItem()).getBlock().minY == 1.0 || lIIllllIIlIIlI.getItem() instanceof ItemAnvilBlock) {
                        return lIIllllIIlIIIl;
                    }
                }
            }
        }
        return -1;
    }
    
    private boolean rayTrace(final Vec3 lIIlllIIIllIlI, final Vec3 lIIlllIIIllIIl) {
        final Vec3 lIIlllIIIllIII = lIIlllIIIllIIl.subtract(lIIlllIIIllIlI);
        final int lIIlllIIIlIlll = 10;
        final double lIIlllIIIlIllI = lIIlllIIIllIII.xCoord / lIIlllIIIlIlll;
        final double lIIlllIIIlIlIl = lIIlllIIIllIII.yCoord / lIIlllIIIlIlll;
        final double lIIlllIIIlIlII = lIIlllIIIllIII.zCoord / lIIlllIIIlIlll;
        Vec3 lIIlllIIIlIIll = lIIlllIIIllIlI;
        for (int lIIlllIIIlllII = 0; lIIlllIIIlllII < lIIlllIIIlIlll; ++lIIlllIIIlllII) {
            final BlockPos lIIlllIIIlllll = new BlockPos(lIIlllIIIlIIll = lIIlllIIIlIIll.addVector(lIIlllIIIlIllI, lIIlllIIIlIlIl, lIIlllIIIlIlII));
            final IBlockState lIIlllIIIllllI = Scaffold.mc.theWorld.getBlockState(lIIlllIIIlllll);
            if (!(lIIlllIIIllllI.getBlock() instanceof BlockLiquid)) {
                if (!(lIIlllIIIllllI.getBlock() instanceof BlockAir)) {
                    AxisAlignedBB lIIlllIIIlllIl = lIIlllIIIllllI.getBlock().getCollisionBoundingBox(Scaffold.mc.theWorld, lIIlllIIIlllll, lIIlllIIIllllI);
                    if (lIIlllIIIlllIl == null) {
                        lIIlllIIIlllIl = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    }
                    if (lIIlllIIIlllIl.offset(lIIlllIIIlllll).isVecInside(lIIlllIIIlIIll)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent lIlIIIIIIlIIll) {
        if (this.info.getValue() && Client.getInstance().getModuleManager().getModuleByName("HUD").isToggled()) {
            final HUD lIlIIIIIIlIllI = (HUD)Client.getInstance().getModuleManager().getModuleByName("HUD");
            final ScaledResolution lIlIIIIIIlIlIl = RenderUtils.getResolution();
            RenderUtils.drawRect(lIlIIIIIIlIlIl.getScaledWidth() / 2 - 30, lIlIIIIIIlIlIl.getScaledHeight() / 2 + 50 + this.animation, 20 + Scaffold.mc.fontRendererObj.getStringWidth(String.valueOf(new StringBuilder().append(this.getBlockCount()).append(""))) + 10, 30.0, new Color(0, 0, 0, 105).getRGB());
            RenderUtils.drawRect(lIlIIIIIIlIlIl.getScaledWidth() / 2 - 30, lIlIIIIIIlIlIl.getScaledHeight() / 2 + 50 + this.animation, 20 + Scaffold.mc.fontRendererObj.getStringWidth(String.valueOf(new StringBuilder().append(this.getBlockCount()).append(""))) + 10, 1.0, ColorPalette.GREEN.getColor().getRGB());
            if (lIlIIIIIIlIllI.font.getValue()) {
                Fonts.Verdana.drawString(String.valueOf(new StringBuilder().append(this.getBlockCount()).append("")), (float)(lIlIIIIIIlIlIl.getScaledWidth() / 2 - 5), (float)(lIlIIIIIIlIlIl.getScaledHeight() / 2 + 61 + this.animation), -1);
                Fonts.Verdana.drawString("Blocks", (float)(lIlIIIIIIlIlIl.getScaledWidth() / 2 - 15), (float)(lIlIIIIIIlIlIl.getScaledHeight() / 2 + 71 + this.animation), -1);
            }
            else {
                Scaffold.mc.fontRendererObj.drawString(String.valueOf(new StringBuilder().append(this.getBlockCount()).append("")), lIlIIIIIIlIlIl.getScaledWidth() / 2 - 5, lIlIIIIIIlIlIl.getScaledHeight() / 2 + 61 + this.animation, -1);
                Scaffold.mc.fontRendererObj.drawString("Blocks", lIlIIIIIIlIlIl.getScaledWidth() / 2 - 15, lIlIIIIIIlIlIl.getScaledHeight() / 2 + 71 + this.animation, -1);
            }
            Scaffold.mc.getRenderItem().renderItemIntoGUI(Scaffold.mc.thePlayer.inventory.getStackInSlot(this.getSlotWithBlock()), lIlIIIIIIlIlIl.getScaledWidth() / 2 - 28, lIlIIIIIIlIlIl.getScaledHeight() / 2 + 57 + this.animation);
        }
    }
    
    public BlockEntry find(final Vec3 lIIlllIIlllIIl) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: anewarray       Lnet/minecraft/util/EnumFacing;
        //     5: dup            
        //     6: iconst_0       
        //     7: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //    10: aastore        
        //    11: dup            
        //    12: iconst_1       
        //    13: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //    16: aastore        
        //    17: dup            
        //    18: iconst_2       
        //    19: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //    22: aastore        
        //    23: dup            
        //    24: iconst_3       
        //    25: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //    28: aastore        
        //    29: dup            
        //    30: iconst_4       
        //    31: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    34: aastore        
        //    35: dup            
        //    36: iconst_5       
        //    37: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //    40: aastore        
        //    41: astore_2        /* lIIlllIIllllIl */
        //    42: new             Lnet/minecraft/util/BlockPos;
        //    45: dup            
        //    46: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    49: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    52: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionVector:()Lnet/minecraft/util/Vec3;
        //    55: aload_1         /* lIIlllIIlllllI */
        //    56: invokevirtual   net/minecraft/util/Vec3.add:(Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/Vec3;
        //    59: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/util/Vec3;)V
        //    62: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //    65: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    68: astore_3        /* lIIlllIIllllII */
        //    69: invokestatic    net/minecraft/util/EnumFacing.values:()[Lnet/minecraft/util/EnumFacing;
        //    72: astore          4
        //    74: aload           4
        //    76: arraylength    
        //    77: istore          lIIlllIIllIlIl
        //    79: iconst_0       
        //    80: istore          lIIlllIIllIlII
        //    82: iload           lIIlllIIllIlII
        //    84: iload           lIIlllIIllIlIl
        //    86: if_icmpge       183
        //    89: aload           4
        //    91: iload           lIIlllIIllIlII
        //    93: aaload         
        //    94: astore          lIIlllIlIIIlII
        //    96: aload_3         /* lIIlllIIllllII */
        //    97: aload           lIIlllIlIIIlII
        //    99: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   102: astore          lIIlllIlIIIlIl
        //   104: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   107: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   110: aload           lIIlllIlIIIlIl
        //   112: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   115: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   120: instanceof      Lnet/minecraft/block/BlockAir;
        //   123: ifne            177
        //   126: aload_0         /* lIIlllIIllllll */
        //   127: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   130: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   133: fconst_0       
        //   134: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getLook:(F)Lnet/minecraft/util/Vec3;
        //   137: aload_0         /* lIIlllIIllllll */
        //   138: aload           lIIlllIlIIIlIl
        //   140: aload_2         /* lIIlllIIllllIl */
        //   141: aload           lIIlllIlIIIlII
        //   143: invokevirtual   net/minecraft/util/EnumFacing.ordinal:()I
        //   146: aaload         
        //   147: invokevirtual   me/kansio/client/modules/impl/world/Scaffold.getPositionByFace:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/Vec3;
        //   150: invokespecial   me/kansio/client/modules/impl/world/Scaffold.rayTrace:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Z
        //   153: ifne            159
        //   156: goto            177
        //   159: new             Lme/kansio/client/modules/impl/world/Scaffold$BlockEntry;
        //   162: dup            
        //   163: aload_0         /* lIIlllIIllllll */
        //   164: aload           lIIlllIlIIIlIl
        //   166: aload_2         /* lIIlllIIllllIl */
        //   167: aload           lIIlllIlIIIlII
        //   169: invokevirtual   net/minecraft/util/EnumFacing.ordinal:()I
        //   172: aaload         
        //   173: invokespecial   me/kansio/client/modules/impl/world/Scaffold$BlockEntry.<init>:(Lme/kansio/client/modules/impl/world/Scaffold;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   176: areturn        
        //   177: iinc            lIIlllIIllIlII, 1
        //   180: goto            82
        //   183: iconst_4       
        //   184: anewarray       Lnet/minecraft/util/BlockPos;
        //   187: dup            
        //   188: iconst_0       
        //   189: new             Lnet/minecraft/util/BlockPos;
        //   192: dup            
        //   193: iconst_m1      
        //   194: iconst_0       
        //   195: iconst_0       
        //   196: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   199: aastore        
        //   200: dup            
        //   201: iconst_1       
        //   202: new             Lnet/minecraft/util/BlockPos;
        //   205: dup            
        //   206: iconst_1       
        //   207: iconst_0       
        //   208: iconst_0       
        //   209: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   212: aastore        
        //   213: dup            
        //   214: iconst_2       
        //   215: new             Lnet/minecraft/util/BlockPos;
        //   218: dup            
        //   219: iconst_0       
        //   220: iconst_0       
        //   221: iconst_m1      
        //   222: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   225: aastore        
        //   226: dup            
        //   227: iconst_3       
        //   228: new             Lnet/minecraft/util/BlockPos;
        //   231: dup            
        //   232: iconst_0       
        //   233: iconst_0       
        //   234: iconst_1       
        //   235: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   238: aastore        
        //   239: astore          lIIlllIIlllIll
        //   241: aload           lIIlllIIlllIll
        //   243: astore          lIIlllIIllIlIl
        //   245: aload           lIIlllIIllIlIl
        //   247: arraylength    
        //   248: istore          lIIlllIIllIlII
        //   250: iconst_0       
        //   251: istore          7
        //   253: iload           7
        //   255: iload           lIIlllIIllIlII
        //   257: if_icmpge       430
        //   260: aload           lIIlllIIllIlIl
        //   262: iload           7
        //   264: aaload         
        //   265: astore          lIIlllIlIIIIII
        //   267: aload_3         /* lIIlllIIllllII */
        //   268: aload           lIIlllIlIIIIII
        //   270: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   273: iconst_0       
        //   274: aload           lIIlllIlIIIIII
        //   276: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   279: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   282: astore          lIIlllIlIIIIIl
        //   284: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   287: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   290: aload           lIIlllIlIIIIIl
        //   292: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   295: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   300: instanceof      Lnet/minecraft/block/BlockAir;
        //   303: ifne            309
        //   306: goto            424
        //   309: invokestatic    net/minecraft/util/EnumFacing.values:()[Lnet/minecraft/util/EnumFacing;
        //   312: astore          lIIlllIIllIIII
        //   314: aload           lIIlllIIllIIII
        //   316: arraylength    
        //   317: istore          lIIlllIIlIllll
        //   319: iconst_0       
        //   320: istore          lIIlllIIlIlllI
        //   322: iload           lIIlllIIlIlllI
        //   324: iload           lIIlllIIlIllll
        //   326: if_icmpge       424
        //   329: aload           lIIlllIIllIIII
        //   331: iload           lIIlllIIlIlllI
        //   333: aaload         
        //   334: astore          lIIlllIlIIIIlI
        //   336: aload           lIIlllIlIIIIIl
        //   338: aload           lIIlllIlIIIIlI
        //   340: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   343: astore          lIIlllIlIIIIll
        //   345: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   348: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   351: aload           lIIlllIlIIIIll
        //   353: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   356: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   361: instanceof      Lnet/minecraft/block/BlockAir;
        //   364: ifne            418
        //   367: aload_0         /* lIIlllIIllllll */
        //   368: getstatic       me/kansio/client/modules/impl/world/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   371: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   374: fconst_0       
        //   375: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getLook:(F)Lnet/minecraft/util/Vec3;
        //   378: aload_0         /* lIIlllIIllllll */
        //   379: aload           lIIlllIlIIIIII
        //   381: aload_2         /* lIIlllIIllllIl */
        //   382: aload           lIIlllIlIIIIlI
        //   384: invokevirtual   net/minecraft/util/EnumFacing.ordinal:()I
        //   387: aaload         
        //   388: invokevirtual   me/kansio/client/modules/impl/world/Scaffold.getPositionByFace:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/Vec3;
        //   391: invokespecial   me/kansio/client/modules/impl/world/Scaffold.rayTrace:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Z
        //   394: ifne            400
        //   397: goto            418
        //   400: new             Lme/kansio/client/modules/impl/world/Scaffold$BlockEntry;
        //   403: dup            
        //   404: aload_0         /* lIIlllIIllllll */
        //   405: aload           lIIlllIlIIIIll
        //   407: aload_2         /* lIIlllIIllllIl */
        //   408: aload           lIIlllIlIIIIlI
        //   410: invokevirtual   net/minecraft/util/EnumFacing.ordinal:()I
        //   413: aaload         
        //   414: invokespecial   me/kansio/client/modules/impl/world/Scaffold$BlockEntry.<init>:(Lme/kansio/client/modules/impl/world/Scaffold;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   417: areturn        
        //   418: iinc            lIIlllIIlIlllI, 1
        //   421: goto            322
        //   424: iinc            7, 1
        //   427: goto            253
        //   430: aconst_null    
        //   431: areturn        
        //    StackMapTable: 00 0B FF 00 52 00 07 07 00 02 07 01 0D 07 03 47 07 01 4B 07 03 47 01 01 00 00 FD 00 4C 07 01 EB 07 01 4B 11 F9 00 05 FF 00 45 00 08 07 00 02 07 01 0D 07 03 47 07 01 4B 07 03 48 07 03 48 01 01 00 00 FD 00 37 07 01 4B 07 01 4B FE 00 0C 07 03 47 01 01 FD 00 4D 07 01 EB 07 01 4B 11 FF 00 05 00 0A 07 00 02 07 01 0D 07 03 47 07 01 4B 07 03 48 07 03 48 01 01 07 01 4B 07 01 4B 00 00 F9 00 05
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.assembler.ir.StackMappingVisitor.push(StackMappingVisitor.java:290)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:833)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean placeBlock(final BlockPos lIIllllIlIlIIl, final EnumFacing lIIllllIlIlIII, final int lIIllllIlIIlll, final boolean lIIllllIlIIIIl) {
        if (this.delayTimer.timeElapsed(this.delay.getValue())) {
            this.delayTimer.resetTime();
            final BlockPos lIIllllIlIllII = lIIllllIlIlIIl.offset(lIIllllIlIlIII);
            final EnumFacing[] lIIllllIlIlIll = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
            if (this.rayTrace(Scaffold.mc.thePlayer.getLook(0.0f), this.getPositionByFace(lIIllllIlIllII, lIIllllIlIlIll[lIIllllIlIlIII.ordinal()]))) {
                final ItemStack lIIllllIlIlllI = Scaffold.mc.thePlayer.inventory.getStackInSlot(lIIllllIlIIlll);
                final Vec3 lIIllllIlIllIl = new Vec3(lIIllllIlIlIIl).add(new Vec3(0.5, 0.5, 0.5)).add(new Vec3(lIIllllIlIlIII.getDirectionVec()).scale(0.5f));
                if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, lIIllllIlIlllI, lIIllllIlIlIIl, lIIllllIlIlIII, new Vec3(lIIllllIlIllIl.xCoord, lIIllllIlIllIl.yCoord, lIIllllIlIllIl.zCoord))) {
                    if (lIIllllIlIIIIl) {
                        Scaffold.mc.thePlayer.swingItem();
                    }
                    else {
                        Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean placeBlockVerus(final BlockPos lIIlllIlllIIlI, final EnumFacing lIIlllIllIllII, final int lIIlllIllIlIll, final boolean lIIlllIllIllll) {
        if (this.delayTimer.timeElapsed(this.delay.getValue())) {
            this.delayTimer.resetTime();
            final BlockPos lIIlllIlllIlIl = lIIlllIlllIIlI.offset(lIIlllIllIllII);
            final EnumFacing[] lIIlllIlllIlII = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
            if (this.rayTrace(Scaffold.mc.thePlayer.getLook(0.0f), this.getPositionByFace(lIIlllIlllIlIl, lIIlllIlllIlII[lIIlllIllIllII.ordinal()]))) {
                final ItemStack lIIlllIllllIII = Scaffold.mc.thePlayer.inventory.getStackInSlot(lIIlllIllIlIll);
                final float lIIlllIlllIlll = MathUtil.getRandomInRange(0.3f, 0.5f);
                final Vec3 lIIlllIlllIllI = new Vec3(lIIlllIlllIIlI).add(lIIlllIlllIlll, lIIlllIlllIlll, lIIlllIlllIlll).add(new Vec3(lIIlllIllIllII.getDirectionVec()).scale(lIIlllIlllIlll));
                if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, lIIlllIllllIII, lIIlllIlllIIlI, lIIlllIllIllII, new Vec3(lIIlllIlllIllI.xCoord, lIIlllIlllIllI.yCoord, lIIlllIlllIllI.zCoord))) {
                    if (lIIlllIllIllll) {
                        Scaffold.mc.thePlayer.swingItem();
                    }
                    else {
                        Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public BlockEntry findExpand(final Vec3 lIIlllllIIllII, final int lIIlllllIIlIll) {
        final EnumFacing[] lIIlllllIIlIlI = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
        final BlockPos lIIlllllIIlIIl = new BlockPos(Scaffold.mc.thePlayer.getPositionVector().add(lIIlllllIIllII)).offset(EnumFacing.DOWN);
        if (!(Scaffold.mc.theWorld.getBlockState(lIIlllllIIlIIl).getBlock() instanceof BlockAir)) {
            return null;
        }
        final EnumFacing[] values = EnumFacing.values();
        final int length = values.length;
        for (byte lIIlllllIIIIIl = 0; lIIlllllIIIIIl < length; ++lIIlllllIIIIIl) {
            final EnumFacing lIIlllllIlIlII = values[lIIlllllIIIIIl];
            final BlockPos lIIlllllIlIlIl = lIIlllllIIlIIl.offset(lIIlllllIlIlII);
            if (!(Scaffold.mc.theWorld.getBlockState(lIIlllllIlIlIl).getBlock() instanceof BlockAir) && this.rayTrace(Scaffold.mc.thePlayer.getLook(0.0f), this.getPositionByFace(lIIlllllIlIlIl, lIIlllllIIlIlI[lIIlllllIlIlII.ordinal()]))) {
                return new BlockEntry(lIIlllllIlIlIl, lIIlllllIIlIlI[lIIlllllIlIlII.ordinal()]);
            }
        }
        for (int lIIlllllIIlllI = 0; lIIlllllIIlllI < lIIlllllIIlIll; ++lIIlllllIIlllI) {
            final byte lIIlllllIIIIIl;
            final BlockPos[] lIIlllllIIllll = (Object)(lIIlllllIIIIIl = (byte)(Object)new BlockPos[] { new BlockPos(-lIIlllllIIlllI, 0, 0), new BlockPos(lIIlllllIIlllI, 0, 0), new BlockPos(0, 0, -lIIlllllIIlllI), new BlockPos(0, 0, lIIlllllIIlllI) });
            final int length2 = lIIlllllIIIIIl.length;
            for (long lIIllllIllllll = 0; lIIllllIllllll < length2; ++lIIllllIllllll) {
                final BlockPos lIIlllllIlIIII = lIIlllllIIIIIl[lIIllllIllllll];
                final BlockPos lIIlllllIlIIIl = lIIlllllIIlIIl.add(lIIlllllIlIIII.getX(), 0, lIIlllllIlIIII.getZ());
                if (Scaffold.mc.theWorld.getBlockState(lIIlllllIlIIIl).getBlock() instanceof BlockAir) {
                    final byte lIIllllIllllII = (Object)EnumFacing.values();
                    final String lIIllllIlllIll = (String)lIIllllIllllII.length;
                    for (boolean lIIllllIlllIlI = false; lIIllllIlllIlI < lIIllllIlllIll; ++lIIllllIlllIlI) {
                        final EnumFacing lIIlllllIlIIlI = lIIllllIllllII[lIIllllIlllIlI];
                        final BlockPos lIIlllllIlIIll = lIIlllllIlIIIl.offset(lIIlllllIlIIlI);
                        if (!(Scaffold.mc.theWorld.getBlockState(lIIlllllIlIIll).getBlock() instanceof BlockAir) && this.rayTrace(Scaffold.mc.thePlayer.getLook(0.0f), this.getPositionByFace(lIIlllllIlIIII, lIIlllllIIlIlI[lIIlllllIlIIlI.ordinal()]))) {
                            return new BlockEntry(lIIlllllIlIIll, lIIlllllIIlIlI[lIIlllllIlIIlI.ordinal()]);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    @Subscribe
    public void onKeyboard(final KeyboardEvent lIIllllllIlIIl) {
        if (!this.sprint.getValue() && lIIllllllIlIIl.getKeyCode() == 29) {
            lIIllllllIlIIl.setCancelled(true);
        }
    }
    
    public class BlockEntry
    {
        private final /* synthetic */ BlockPos position;
        private final /* synthetic */ EnumFacing facing;
        
        public BlockPos getPosition() {
            return this.position;
        }
        
        BlockEntry(final BlockPos lIIIIIIIllIlII, final EnumFacing lIIIIIIIllIIll) {
            this.position = lIIIIIIIllIlII;
            this.facing = lIIIIIIIllIIll;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
    }
}
