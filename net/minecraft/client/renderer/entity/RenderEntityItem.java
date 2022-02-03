package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import me.kansio.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;

public class RenderEntityItem extends Render<EntityItem>
{
    private final RenderItem itemRenderer;
    public static long tick;
    public static double rotation;
    public static Random random;
    private Random field_177079_e;
    
    public RenderEntityItem(final RenderManager renderManagerIn, final RenderItem p_i46167_2_) {
        super(renderManagerIn);
        this.field_177079_e = new Random();
        this.itemRenderer = p_i46167_2_;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    private int func_177077_a(final EntityItem itemIn, final double p_177077_2_, final double p_177077_4_, final double p_177077_6_, final float p_177077_8_, final IBakedModel p_177077_9_) {
        final ItemStack itemstack = itemIn.getEntityItem();
        final Item item = itemstack.getItem();
        if (item == null) {
            return 0;
        }
        final boolean flag = p_177077_9_.isGui3d();
        final int i = this.func_177078_a(itemstack);
        final float f = 0.25f;
        final float f2 = MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0f + itemIn.hoverStart) * 0.1f + 0.1f;
        final float f3 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
        GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f2 + 0.25f * f3, (float)p_177077_6_);
        if (flag || this.renderManager.options != null) {
            final float f4 = ((itemIn.getAge() + p_177077_8_) / 20.0f + itemIn.hoverStart) * 57.295776f;
            GlStateManager.rotate(f4, 0.0f, 1.0f, 0.0f);
        }
        if (!flag) {
            final float f5 = -0.0f * (i - 1) * 0.5f;
            final float f6 = -0.0f * (i - 1) * 0.5f;
            final float f7 = -0.046875f * (i - 1) * 0.5f;
            GlStateManager.translate(f5, f6, f7);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return i;
    }
    
    private int func_177078_a(final ItemStack stack) {
        int i = 1;
        if (stack.stackSize > 48) {
            i = 5;
        }
        else if (stack.stackSize > 32) {
            i = 4;
        }
        else if (stack.stackSize > 16) {
            i = 3;
        }
        else if (stack.stackSize > 1) {
            i = 2;
        }
        return i;
    }
    
    @Override
    public void doRender(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (Client.getInstance().getModuleManager().getModuleByName("Item Physics").isToggled()) {
            final Minecraft mc = Minecraft.getMinecraft();
            RenderEntityItem.rotation = (System.nanoTime() - partialTicks) / 5.0E13;
            if (!mc.inGameHasFocus) {
                RenderEntityItem.rotation = 0.0;
            }
            final EntityItem item = entity;
            final ItemStack itemstack = item.getEntityItem();
            int i;
            if (itemstack != null && itemstack.getItem() != null) {
                i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
            }
            else {
                i = 187;
            }
            RenderEntityItem.random.setSeed(i);
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.getEntityTexture(entity));
            Minecraft.getMinecraft().getTextureManager().getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            final IBakedModel ibakedmodel = mc.getRenderItem().getItemModelMesher().getItemModel(itemstack);
            final boolean flag1 = ibakedmodel.isGui3d();
            final boolean is3D = ibakedmodel.isGui3d();
            final int j = this.func_177078_a(itemstack);
            GlStateManager.translate((float)x, (float)y, (float)z);
            if (ibakedmodel.isGui3d()) {
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
            }
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(item.rotationYaw, 0.0f, 0.0f, 1.0f);
            if (is3D) {
                GlStateManager.translate(0.0, 0.0, -0.08);
            }
            else {
                GlStateManager.translate(0.0, 0.0, -0.04);
            }
            if (is3D || mc.getRenderManager().options != null) {
                if (is3D) {
                    if (!item.onGround) {
                        final double rotation = RenderEntityItem.rotation * 2.0;
                        final EntityItem entityItem = item;
                        entityItem.rotationPitch += (float)rotation;
                    }
                }
                else if (!Double.isNaN(item.posX) && !Double.isNaN(item.posY) && !Double.isNaN(item.posZ) && item.worldObj != null) {
                    if (item.onGround) {
                        item.rotationPitch = 0.0f;
                    }
                    else {
                        final double rotation = RenderEntityItem.rotation * 2.0;
                        final EntityItem entityItem2 = item;
                        entityItem2.rotationPitch += (float)rotation;
                    }
                }
                GlStateManager.rotate(item.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            for (int k = 0; k < j; ++k) {
                if (flag1) {
                    GlStateManager.pushMatrix();
                    if (k > 0) {
                        final float f7 = (RenderEntityItem.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        final float f8 = (RenderEntityItem.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        final float f9 = (RenderEntityItem.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        GlStateManager.translate(f7, f8, f9);
                    }
                    mc.getRenderItem().renderItem(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                }
                else {
                    GlStateManager.pushMatrix();
                    mc.getRenderItem().renderItem(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                    GlStateManager.translate(0.0f, 0.0f, 0.05375f);
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.getEntityTexture(entity));
            Minecraft.getMinecraft().getTextureManager().getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }
        else {
            final ItemStack itemstack2 = entity.getEntityItem();
            this.field_177079_e.setSeed(187L);
            boolean flag2 = false;
            if (this.bindEntityTexture(entity)) {
                this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
                flag2 = true;
            }
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            final IBakedModel ibakedmodel2 = this.itemRenderer.getItemModelMesher().getItemModel(itemstack2);
            for (int i = this.func_177077_a(entity, x, y, z, partialTicks, ibakedmodel2), l = 0; l < i; ++l) {
                if (ibakedmodel2.isGui3d()) {
                    GlStateManager.pushMatrix();
                    if (l > 0) {
                        final float f10 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        final float f11 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        final float f12 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                        GlStateManager.translate(f10, f11, f12);
                    }
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    ibakedmodel2.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                    this.itemRenderer.renderItem(itemstack2, ibakedmodel2);
                    GlStateManager.popMatrix();
                }
                else {
                    GlStateManager.pushMatrix();
                    ibakedmodel2.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                    this.itemRenderer.renderItem(itemstack2, ibakedmodel2);
                    GlStateManager.popMatrix();
                    final float f13 = ibakedmodel2.getItemCameraTransforms().ground.scale.x;
                    final float f14 = ibakedmodel2.getItemCameraTransforms().ground.scale.y;
                    final float f15 = ibakedmodel2.getItemCameraTransforms().ground.scale.z;
                    GlStateManager.translate(0.0f * f13, 0.0f * f14, 0.046875f * f15);
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            this.bindEntityTexture(entity);
            if (flag2) {
                this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
            }
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityItem entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    static {
        RenderEntityItem.random = new Random();
    }
}
