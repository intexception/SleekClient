package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import java.nio.*;
import me.kansio.client.value.value.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.render.*;
import net.minecraft.util.*;
import javax.vecmath.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import me.kansio.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import com.google.common.eventbus.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.value.*;

@ModuleData(name = "ESP", category = ModuleCategory.VISUALS, description = "Shows player locations")
public class ESP extends Module
{
    public final /* synthetic */ BooleanValue players;
    public final /* synthetic */ BooleanValue chests;
    public final /* synthetic */ BooleanValue animals;
    public final /* synthetic */ BooleanValue mobs;
    public final /* synthetic */ List collectedEntities;
    private final /* synthetic */ IntBuffer viewport;
    public final /* synthetic */ BooleanValue outline;
    private final /* synthetic */ int color;
    public final /* synthetic */ BooleanValue droppedItems;
    private final /* synthetic */ FloatBuffer projection;
    private final /* synthetic */ FloatBuffer vector;
    public final /* synthetic */ BooleanValue localPlayer;
    private final /* synthetic */ int backgroundColor;
    public final /* synthetic */ BooleanValue healthBar;
    public final /* synthetic */ BooleanValue invisibles;
    private final /* synthetic */ int black;
    public final /* synthetic */ ModeValue boxMode;
    private final /* synthetic */ FloatBuffer modelview;
    public final /* synthetic */ BooleanValue fillInside;
    public final /* synthetic */ BooleanValue tag;
    public final /* synthetic */ BooleanValue armorBar;
    
    private Vector3d project2D(final int lIIlIIlI, final double lIIlIllI, final double lIIlIIII, final double lIIIllll) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject((float)lIIlIllI, (float)lIIlIIII, (float)lIIIllll, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / lIIlIIlI, (Display.getHeight() - this.vector.get(1)) / lIIlIIlI, this.vector.get(2)) : null;
    }
    
    private void collectEntities() {
        this.collectedEntities.clear();
        final List lIlIIlII = ESP.mc.theWorld.loadedEntityList;
        for (int lIlIIIll = 0, lIlIIllI = lIlIIlII.size(); lIlIIIll < lIlIIllI; ++lIlIIIll) {
            final Entity lIlIIlll = lIlIIlII.get(lIlIIIll);
            if (this.isValid(lIlIIlll)) {
                this.collectedEntities.add(lIlIIlll);
            }
        }
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent llllIIlI) {
        GL11.glPushMatrix();
        this.collectEntities();
        final float llllIIIl = ESP.mc.timer.renderPartialTicks;
        final ScaledResolution llllIIII = new ScaledResolution(ESP.mc);
        final int lllIllll = llllIIII.getScaleFactor();
        final double lllIlllI = lllIllll / Math.pow(lllIllll, 2.0);
        GL11.glScaled(lllIlllI, lllIlllI, lllIlllI);
        final int lllIllIl = this.black;
        final int lllIllII = this.color;
        final int lllIlIll = this.backgroundColor;
        final float lllIlIlI = 0.65f;
        final float lllIlIIl = 1.0f / lllIlIlI;
        final FontRenderer lllIlIII = ESP.mc.fontRendererObj;
        final RenderManager lllIIlll = ESP.mc.getRenderManager();
        final EntityRenderer lllIIllI = ESP.mc.entityRenderer;
        final boolean lllIIlIl = this.tag.getValue();
        final boolean lllIIlII = this.outline.getValue();
        final boolean lllIIIll = this.healthBar.getValue();
        final boolean lllIIIlI = this.armorBar.getValue();
        final boolean lllIIIIl = this.boxMode.getValue().equalsIgnoreCase("box");
        final List lllIIIII = this.collectedEntities;
        for (int llIlllll = 0, llllIlII = lllIIIII.size(); llIlllll < llllIlII; ++llIlllll) {
            final Entity llllIlIl = lllIIIII.get(llIlllll);
            if (this.isValid(llllIlIl) && RenderUtils.isInViewFrustrum(llllIlIl)) {
                final double llllllIl = RenderUtils.interpolate(llllIlIl.posX, llllIlIl.lastTickPosX, llllIIIl);
                final double llllllII = RenderUtils.interpolate(llllIlIl.posY, llllIlIl.lastTickPosY, llllIIIl);
                final double lllllIll = RenderUtils.interpolate(llllIlIl.posZ, llllIlIl.lastTickPosZ, llllIIIl);
                final double lllllIlI = llllIlIl.width / 1.5;
                final double lllllIIl = llllIlIl.height + (llllIlIl.isSneaking() ? -0.3 : 0.2);
                final AxisAlignedBB lllllIII = new AxisAlignedBB(llllllIl - lllllIlI, llllllII, lllllIll - lllllIlI, llllllIl + lllllIlI, llllllII + lllllIIl, lllllIll + lllllIlI);
                final List<Vector3d> llllIlll = Arrays.asList(new Vector3d(lllllIII.minX, lllllIII.minY, lllllIII.minZ), new Vector3d(lllllIII.minX, lllllIII.maxY, lllllIII.minZ), new Vector3d(lllllIII.maxX, lllllIII.minY, lllllIII.minZ), new Vector3d(lllllIII.maxX, lllllIII.maxY, lllllIII.minZ), new Vector3d(lllllIII.minX, lllllIII.minY, lllllIII.maxZ), new Vector3d(lllllIII.minX, lllllIII.maxY, lllllIII.maxZ), new Vector3d(lllllIII.maxX, lllllIII.minY, lllllIII.maxZ), new Vector3d(lllllIII.maxX, lllllIII.maxY, lllllIII.maxZ));
                lllIIllI.setupCameraTransform(llllIIIl, 0);
                Vector4d llllIllI = null;
                for (Vector3d lIIIllIIl : llllIlll) {
                    final Vector3d lIIIllIII = lIIIllIIl;
                    lIIIllIIl = this.project2D(lllIllll, lIIIllIIl.x - lllIIlll.viewerPosX, lIIIllIIl.y - lllIIlll.viewerPosY, lIIIllIIl.z - lllIIlll.viewerPosZ);
                    if (lIIIllIIl != null && lIIIllIIl.z >= 0.0 && lIIIllIIl.z < 1.0) {
                        if (llllIllI == null) {
                            llllIllI = new Vector4d(lIIIllIIl.x, lIIIllIIl.y, lIIIllIIl.z, 0.0);
                        }
                        llllIllI.x = Math.min(lIIIllIIl.x, llllIllI.x);
                        llllIllI.y = Math.min(lIIIllIIl.y, llllIllI.y);
                        llllIllI.z = Math.max(lIIIllIIl.x, llllIllI.z);
                        llllIllI.w = Math.max(lIIIllIIl.y, llllIllI.w);
                    }
                }
                if (llllIllI != null) {
                    lllIIllI.setupOverlayRendering();
                    final double lIIIIIIlI = llllIllI.x;
                    final double lIIIIIIIl = llllIllI.y;
                    final double lIIIIIIII = llllIllI.z;
                    final double llllllll = llllIllI.w;
                    if (lllIIlII) {
                        if (Objects.equals(((Value<Object>)this.boxMode).getValue(), "Box")) {
                            if (this.fillInside.getValue()) {
                                Gui.drawRect(lIIIIIIlI - 1.0, lIIIIIIIl, lIIIIIIII, llllllll, new Color(0, 0, 0, 100).getRGB());
                            }
                            Gui.drawRect(lIIIIIIlI - 1.0, lIIIIIIIl, lIIIIIIlI + 0.5, llllllll + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 1.0, lIIIIIIIl - 0.5, lIIIIIIII + 0.5, lIIIIIIIl + 0.5 + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIII - 0.5 - 0.5, lIIIIIIIl, lIIIIIIII + 0.5, llllllll + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 1.0, llllllll - 0.5 - 0.5, lIIIIIIII + 0.5, llllllll + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 0.5, lIIIIIIIl, lIIIIIIlI + 0.5 - 0.5, llllllll, lllIllII);
                            Gui.drawRect(lIIIIIIlI, llllllll - 0.5, lIIIIIIII, llllllll, lllIllII);
                            Gui.drawRect(lIIIIIIlI - 0.5, lIIIIIIIl, lIIIIIIII, lIIIIIIIl + 0.5, lllIllII);
                            Gui.drawRect(lIIIIIIII - 0.5, lIIIIIIIl, lIIIIIIII, llllllll, lllIllII);
                        }
                        else {
                            if (this.fillInside.getValue()) {
                                Gui.drawRect(lIIIIIIlI - 1.0, lIIIIIIIl, lIIIIIIII, llllllll, new Color(0, 0, 0, 100).getRGB());
                            }
                            Gui.drawRect(lIIIIIIlI + 0.5, lIIIIIIIl, lIIIIIIlI - 1.0, lIIIIIIIl + (llllllll - lIIIIIIIl) / 4.0 + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 1.0, llllllll, lIIIIIIlI + 0.5, llllllll - (llllllll - lIIIIIIIl) / 4.0 - 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 1.0, lIIIIIIIl - 0.5, lIIIIIIlI + (lIIIIIIII - lIIIIIIlI) / 3.0 + 0.5, lIIIIIIIl + 1.0, lllIllIl);
                            Gui.drawRect(lIIIIIIII - (lIIIIIIII - lIIIIIIlI) / 3.0 - 0.5, lIIIIIIIl - 0.5, lIIIIIIII, lIIIIIIIl + 1.0, lllIllIl);
                            Gui.drawRect(lIIIIIIII - 1.0, lIIIIIIIl, lIIIIIIII + 0.5, lIIIIIIIl + (llllllll - lIIIIIIIl) / 4.0 + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIII - 1.0, llllllll, lIIIIIIII + 0.5, llllllll - (llllllll - lIIIIIIIl) / 4.0 - 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI - 1.0, llllllll - 1.0, lIIIIIIlI + (lIIIIIIII - lIIIIIIlI) / 3.0 + 0.5, llllllll + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIII - (lIIIIIIII - lIIIIIIlI) / 3.0 - 0.5, llllllll - 1.0, lIIIIIIII + 0.5, llllllll + 0.5, lllIllIl);
                            Gui.drawRect(lIIIIIIlI, lIIIIIIIl, lIIIIIIlI - 0.5, lIIIIIIIl + (llllllll - lIIIIIIIl) / 4.0, lllIllII);
                            Gui.drawRect(lIIIIIIlI, llllllll, lIIIIIIlI - 0.5, llllllll - (llllllll - lIIIIIIIl) / 4.0, lllIllII);
                            Gui.drawRect(lIIIIIIlI - 0.5, lIIIIIIIl, lIIIIIIlI + (lIIIIIIII - lIIIIIIlI) / 3.0, lIIIIIIIl + 0.5, lllIllII);
                            Gui.drawRect(lIIIIIIII - (lIIIIIIII - lIIIIIIlI) / 3.0, lIIIIIIIl, lIIIIIIII, lIIIIIIIl + 0.5, lllIllII);
                            Gui.drawRect(lIIIIIIII - 0.5, lIIIIIIIl, lIIIIIIII, lIIIIIIIl + (llllllll - lIIIIIIIl) / 4.0, lllIllII);
                            Gui.drawRect(lIIIIIIII - 0.5, llllllll, lIIIIIIII, llllllll - (llllllll - lIIIIIIIl) / 4.0, lllIllII);
                            Gui.drawRect(lIIIIIIlI, llllllll - 0.5, lIIIIIIlI + (lIIIIIIII - lIIIIIIlI) / 3.0, llllllll, lllIllII);
                            Gui.drawRect(lIIIIIIII - (lIIIIIIII - lIIIIIIlI) / 3.0, llllllll - 0.5, lIIIIIIII - 0.5, llllllll, lllIllII);
                        }
                    }
                    final boolean lllllllI = llllIlIl instanceof EntityLivingBase;
                    if (lllllllI) {
                        final EntityLivingBase lIIIlIllI = (EntityLivingBase)llllIlIl;
                        if (lllIIIll) {
                            float lIIIlIlIl = lIIIlIllI.getHealth();
                            final float lIIIlIlII = lIIIlIllI.getMaxHealth();
                            if (lIIIlIlIl > lIIIlIlII) {
                                lIIIlIlIl = lIIIlIlII;
                            }
                            final double lIIIlIIll = lIIIlIlIl / lIIIlIlII;
                            final double lIIIlIIlI = (llllllll - lIIIIIIIl) * lIIIlIIll;
                            Gui.drawRect(lIIIIIIlI - 3.5, lIIIIIIIl - 0.5, lIIIIIIlI - 1.5, llllllll + 0.5, lllIlIll);
                            if (lIIIlIlIl > 0.0f) {
                                final int lIIIlIlll = new Color(73, 255, 12).getRGB();
                                Gui.drawRect(lIIIIIIlI - 3.0, llllllll, lIIIIIIlI - 2.0, llllllll - lIIIlIIlI, lIIIlIlll);
                                final float lIIIlIIIl = lIIIlIllI.getAbsorptionAmount();
                                if (lIIIlIIIl > 0.0f) {
                                    Gui.drawRect(lIIIIIIlI - 3.0, llllllll, lIIIIIIlI - 2.0, llllllll - (llllllll - lIIIIIIIl) / 6.0 * lIIIlIIIl / 2.0, new Color(Potion.absorption.getLiquidColor()).getRGB());
                                }
                            }
                        }
                    }
                    if (lllIIlIl) {
                        final float lIIIlIIII = 10.0f;
                        String lIIIIllll = Client.getInstance().getFriendManager().isFriend(llllIlIl.getName()) ? Client.getInstance().getFriendManager().getFriend(llllIlIl.getName()).getDisplayName() : (Client.getInstance().getUsers().containsKey(llllIlIl.getName()) ? Client.getInstance().getUsers().get(llllIlIl.getName()) : llllIlIl.getName());
                        if (llllIlIl instanceof EntityItem) {
                            lIIIIllll = ((EntityItem)llllIlIl).getEntityItem().getDisplayName();
                        }
                        String lIIIIlllI = "";
                        if (llllIlIl instanceof EntityPlayer) {
                            lIIIIlllI = (this.isFriendly((EntityPlayer)llllIlIl) ? "§b" : (Client.getInstance().getUsers().containsKey(llllIlIl.getName()) ? "§a" : "§c"));
                        }
                        if (llllIlIl instanceof EntityPlayer && Client.getInstance().getTargetManager().isTarget((EntityPlayer)llllIlIl)) {
                            lIIIIlllI = "§4";
                        }
                        final double lIIIIllII = (lIIIIIIII - lIIIIIIlI) / 2.0;
                        final double lIIIIlIll = lllIlIII.getStringWidth(lIIIIllll) * lllIlIlI;
                        final float lIIIIllIl = (float)((lIIIIIIlI + lIIIIllII - lIIIIlIll / 2.0) * lllIlIIl);
                        final float lIIIIlIlI = (float)(lIIIIIIIl * lllIlIIl) - lIIIlIIII;
                        GL11.glPushMatrix();
                        GL11.glScalef(lllIlIlI, lllIlIlI, lllIlIlI);
                        if (lllllllI) {
                            Gui.drawRect(lIIIIllIl - 2.0f, lIIIIlIlI - 2.0f, lIIIIllIl + lIIIIlIll * lllIlIIl + 2.0, lIIIIlIlI + 9.0f, new Color(0, 0, 0, 140).getRGB());
                        }
                        lllIlIII.drawStringWithShadow(String.valueOf(new StringBuilder().append(lIIIIlllI).append(lIIIIllll)), lIIIIllIl, lIIIIlIlI, -1);
                        GL11.glPopMatrix();
                    }
                    if (lllIIIlI) {
                        if (lllllllI) {
                            final EntityLivingBase lIIIIlIII = (EntityLivingBase)llllIlIl;
                            final float lIIIIIlll = (float)lIIIIlIII.getTotalArmorValue();
                            final double lIIIIlIIl = (lIIIIIIII - lIIIIIIlI) * lIIIIIlll / 20.0;
                            Gui.drawRect(lIIIIIIlI - 0.5, llllllll + 1.5, lIIIIIIlI - 0.5 + lIIIIIIII - lIIIIIIlI + 1.0, llllllll + 1.5 + 2.0, lllIlIll);
                            if (lIIIIIlll > 0.0f) {
                                Gui.drawRect(lIIIIIIlI, llllllll + 2.0, lIIIIIIlI + lIIIIlIIl, llllllll + 3.0, 16777215);
                            }
                        }
                        else if (llllIlIl instanceof EntityItem) {
                            final ItemStack lIIIIIIll = ((EntityItem)llllIlIl).getEntityItem();
                            if (lIIIIIIll.isItemStackDamageable()) {
                                final int lIIIIIllI = lIIIIIIll.getMaxDamage();
                                final float lIIIIIlIl = (float)(lIIIIIllI - lIIIIIIll.getItemDamage());
                                final double lIIIIIlII = (lIIIIIIII - lIIIIIIlI) * lIIIIIlIl / lIIIIIllI;
                                Gui.drawRect(lIIIIIIlI - 0.5, llllllll + 1.5, lIIIIIIlI - 0.5 + lIIIIIIII - lIIIIIIlI + 1.0, llllllll + 1.5 + 2.0, lllIlIll);
                                Gui.drawRect(lIIIIIIlI, llllllll + 2.0, lIIIIIIlI + lIIIIIlII, llllllll + 3.0, new Color(0, 200, 208, 255).getRGB());
                            }
                        }
                    }
                }
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        lllIIllI.setupOverlayRendering();
    }
    
    private boolean isFriendly(final EntityPlayer lIlIllIl) {
        return Client.getInstance().getFriendManager().isFriend(lIlIllIl.getName());
    }
    
    private boolean isValid(final Entity lIIIlIll) {
        return (lIIIlIll != ESP.mc.thePlayer || (this.localPlayer.getValue() && ESP.mc.gameSettings.thirdPersonView != 0)) && !lIIIlIll.isDead && (this.invisibles.getValue() || !lIIIlIll.isInvisible()) && ((this.droppedItems.getValue() && lIIIlIll instanceof EntityItem && ESP.mc.thePlayer.getDistanceToEntity(lIIIlIll) < 10.0f) || (this.animals.getValue() && lIIIlIll instanceof EntityAnimal) || (this.players.getValue() && lIIIlIll instanceof EntityPlayer) || (this.mobs.getValue() && (lIIIlIll instanceof EntityMob || lIIIlIll instanceof EntitySlime || lIIIlIll instanceof EntityDragon || lIIIlIll instanceof EntityGolem)));
    }
    
    public ESP() {
        this.outline = new BooleanValue("Outline", this, true);
        this.boxMode = new ModeValue("Box Type", this, new String[] { "Corners", "Box" });
        this.fillInside = new BooleanValue("Fill Box", this, false);
        this.tag = new BooleanValue("Name", this, true);
        this.healthBar = new BooleanValue("Health Bar", this, true);
        this.armorBar = new BooleanValue("Armor Bar", this, true);
        this.localPlayer = new BooleanValue("Local", this, true);
        this.players = new BooleanValue("Players", this, true);
        this.invisibles = new BooleanValue("Invisibles", this, false);
        this.mobs = new BooleanValue("Monsters", this, true);
        this.animals = new BooleanValue("Animals", this, false);
        this.chests = new BooleanValue("Chests", this, false);
        this.droppedItems = new BooleanValue("Dropped Items", this, false);
        this.collectedEntities = new ArrayList();
        this.viewport = GLAllocation.createDirectIntBuffer(16);
        this.modelview = GLAllocation.createDirectFloatBuffer(16);
        this.projection = GLAllocation.createDirectFloatBuffer(16);
        this.vector = GLAllocation.createDirectFloatBuffer(4);
        this.color = Color.WHITE.getRGB();
        this.backgroundColor = new Color(0, 0, 0, 120).getRGB();
        this.black = Color.BLACK.getRGB();
    }
}
