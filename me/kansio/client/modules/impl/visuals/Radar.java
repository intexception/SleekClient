package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import me.kansio.client.utils.render.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import me.kansio.client.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Radar", category = ModuleCategory.VISUALS, description = "Shows player locations on the hud")
public class Radar extends Module
{
    public /* synthetic */ BooleanValue players;
    public /* synthetic */ BooleanValue invisible;
    public /* synthetic */ BooleanValue animals;
    public /* synthetic */ BooleanValue monsters;
    
    public Radar() {
        this.players = new BooleanValue("Players", this, true);
        this.monsters = new BooleanValue("Monsters", this, false);
        this.animals = new BooleanValue("Animals", this, false);
        this.invisible = new BooleanValue("Invisibles", this, false);
    }
    
    @Subscribe
    public void onRenderOverlay(final RenderOverlayEvent lIIlIIIIllll) {
        if (Radar.mc.theWorld == null || Radar.mc.thePlayer == null) {
            return;
        }
        final int lIIlIIIIlllI = 5;
        final int lIIlIIIIllIl = 70;
        final int lIIlIIIIllII = lIIlIIIIlllI + 100;
        final int lIIlIIIIlIll = lIIlIIIIllIl + 100;
        GL11.glPushMatrix();
        RenderUtils.drawRoundedRect(lIIlIIIIlllI, lIIlIIIIllIl, 100.0, lIIlIIIIlIll - lIIlIIIIllIl, 3.0, new Color(0, 0, 0, 100).getRGB());
        RenderUtils.drawRect(lIIlIIIIlllI, lIIlIIIIllIl - 1, 100.0, 1.0, ColorUtils.getColorFromHud(3).getRGB());
        RenderUtils.draw2DPolygon(lIIlIIIIllII / 2 + 3, lIIlIIIIllIl + 52, 5.0, 3, -1);
        GL11.glEnable(3089);
        final int lIIlIIIIlIlI = new ScaledResolution(Radar.mc).getScaleFactor();
        GL11.glScissor(lIIlIIIIlllI * lIIlIIIIlIlI, Radar.mc.displayHeight - lIIlIIIIlIlI * 170, lIIlIIIIllII * lIIlIIIIlIlI - lIIlIIIIlIlI * 5, lIIlIIIIlIlI * 100);
        for (final Entity lIIlIIIlIIIl : Radar.mc.theWorld.loadedEntityList) {
            if (lIIlIIIlIIIl instanceof EntityMob && !this.monsters.getValue()) {
                continue;
            }
            if (lIIlIIIlIIIl instanceof EntityAnimal && !this.animals.getValue()) {
                continue;
            }
            if (lIIlIIIlIIIl instanceof EntityVillager && !this.animals.getValue()) {
                continue;
            }
            if (lIIlIIIlIIIl.isInvisible() && !this.invisible.getValue()) {
                continue;
            }
            if (lIIlIIIlIIIl == Radar.mc.thePlayer) {
                continue;
            }
            final double lIIlIIIllIIl = Radar.mc.thePlayer.getDistanceSqToEntity(lIIlIIIlIIIl);
            if (lIIlIIIllIIl > 360.0) {
                continue;
            }
            final double lIIlIIIllIII = lIIlIIIlIIIl.posX - Radar.mc.thePlayer.posX;
            final double lIIlIIIlIlll = lIIlIIIlIIIl.posZ - Radar.mc.thePlayer.posZ;
            final double lIIlIIIlIllI = Math.atan2(lIIlIIIllIII, lIIlIIIlIlll) * 57.295780181884766;
            final double lIIlIIIlIlIl = (Radar.mc.thePlayer.rotationYaw + lIIlIIIlIllI) % 360.0 * 0.01745329238474369;
            final double lIIlIIIlIlII = lIIlIIIllIIl / 5.0;
            final double lIIlIIIlIIll = lIIlIIIlIlII * Math.sin(lIIlIIIlIlIl);
            final double lIIlIIIlIIlI = lIIlIIIlIlII * Math.cos(lIIlIIIlIlIl);
            RenderUtils.draw2DPolygon(lIIlIIIIllII / 2 + 3 - lIIlIIIlIIll, lIIlIIIIllIl + 52 - lIIlIIIlIIlI, 3.0, 4, Client.getInstance().getFriendManager().isFriend(lIIlIIIlIIIl.getName()) ? Color.cyan.getRGB() : Color.red.getRGB());
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
}
