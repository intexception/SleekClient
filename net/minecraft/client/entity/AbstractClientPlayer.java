package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import optifine.*;
import net.minecraft.entity.ai.attributes.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private NetworkPlayerInfo playerInfo;
    private ResourceLocation locationOfCape;
    private String nameClear;
    private static final String __OBFID = "CL_00000935";
    
    public AbstractClientPlayer(final World worldIn, final GameProfile playerProfile) {
        super(worldIn, playerProfile);
        this.locationOfCape = null;
        this.nameClear = null;
        this.nameClear = playerProfile.getName();
        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }
        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }
    
    @Override
    public boolean isSpectator() {
        final NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        return networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }
    
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo != null && networkplayerinfo.hasLocationSkin();
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
    }
    
    public ResourceLocation getLocationCape() {
        final Cape capes = (Cape)Client.getInstance().getModuleManager().getModuleByName("Cape");
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.locationOfCape != null) {
            return (capes.isToggled() && capes.canRender(this)) ? capes.getCape() : this.locationOfCape;
        }
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? null : ((capes.isToggled() && capes.canRender(this)) ? capes.getCape() : networkplayerinfo.getLocationCape());
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocationIn, final String username) {
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(resourceLocationIn);
        if (object == null) {
            object = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(username)), new ImageBufferDownload());
            texturemanager.loadTexture(resourceLocationIn, (ITextureObject)object);
        }
        return (ThreadDownloadImageData)object;
    }
    
    public static ResourceLocation getLocationSkin(final String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }
    
    public String getSkinType() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkplayerinfo.getSkinType();
    }
    
    public float getFovModifier() {
        float f = 1.0f;
        if (this.capabilities.isFlying) {
            f *= 1.1f;
        }
        final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f *= (float)((iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final int i = this.getItemInUseDuration();
            float f2 = i / 20.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            else {
                f2 *= f2;
            }
            f *= 1.0f - f2 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, f) : f;
    }
    
    public String getNameClear() {
        return this.nameClear;
    }
    
    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }
    
    public void setLocationOfCape(final ResourceLocation p_setLocationOfCape_1_) {
        this.locationOfCape = p_setLocationOfCape_1_;
    }
}
