package me.kansio.client.modules.impl.combat;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.entity.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.*;
import org.lwjgl.opengl.*;

@ModuleData(name = "Target Strafe", category = ModuleCategory.COMBAT, description = "Automatically strafes around the killaura target")
public class TargetStrafe extends Module
{
    public /* synthetic */ NumberValue<Double> green;
    public /* synthetic */ NumberValue<Double> blue;
    public /* synthetic */ BooleanValue autoF5;
    public /* synthetic */ NumberValue<Double> radius;
    public /* synthetic */ NumberValue<Double> width;
    public /* synthetic */ BooleanValue jump;
    public /* synthetic */ NumberValue<Double> red;
    public /* synthetic */ BooleanValue control;
    public static /* synthetic */ double dir;
    public /* synthetic */ BooleanValue render;
    
    public TargetStrafe() {
        this.autoF5 = new BooleanValue("Auto F5", this, false);
        this.jump = new BooleanValue("On Jump", this, false);
        this.control = new BooleanValue("Controllable", this, false);
        this.render = new BooleanValue("Render", this, false);
        this.radius = new NumberValue<Double>("Radius", this, 3.0, 0.1, 5.0, 0.1);
        this.width = new NumberValue<Double>("Width", this, 1.0, 0.1, 5.0, 0.1, this.render);
        this.red = new NumberValue<Double>("Red", this, 100.0, 0.0, 255.0, 1.0, this.render);
        this.green = new NumberValue<Double>("Green", this, 100.0, 0.0, 255.0, 1.0, this.render);
        this.blue = new NumberValue<Double>("Blue", this, 100.0, 0.0, 255.0, 1.0, this.render);
    }
    
    private void invertStrafe() {
        TargetStrafe.dir = -TargetStrafe.dir;
    }
    
    @Override
    public void onDisable() {
        TargetStrafe.dir = -1.0;
    }
    
    @Subscribe
    public void onRender(final Render3DEvent lllIIlIIllll) {
        if (this.canStrafe() && this.render.getValue()) {
            this.drawCircle(KillAura.target, TargetStrafe.mc.timer.renderPartialTicks);
        }
    }
    
    static {
        TargetStrafe.dir = -1.0;
    }
    
    @Subscribe
    public void onMotion(final UpdateEvent lllIIlIlIIll) {
        if (this.canStrafe() && this.autoF5.getValue()) {
            TargetStrafe.mc.gameSettings.thirdPersonView = 1;
        }
        else if (!this.canStrafe() && this.autoF5.getValue()) {
            TargetStrafe.mc.gameSettings.thirdPersonView = 0;
        }
        if (lllIIlIlIIll.isPre()) {
            if (this.control.getValue()) {
                if (TargetStrafe.mc.gameSettings.keyBindLeft.isPressed()) {
                    TargetStrafe.dir = 1.0;
                }
                else if (TargetStrafe.mc.gameSettings.keyBindRight.isPressed()) {
                    TargetStrafe.dir = -1.0;
                }
            }
            if (TargetStrafe.mc.thePlayer.isCollidedHorizontally) {
                this.invertStrafe();
            }
        }
    }
    
    public boolean canStrafe() {
        if (!Client.getInstance().getModuleManager().getModuleByName("KillAura").isToggled()) {
            return false;
        }
        if (!this.isToggled()) {
            return false;
        }
        if (this.jump.getValue()) {
            return TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown() && KillAura.target != null;
        }
        return KillAura.target != null;
    }
    
    private void drawCircle(final Entity lllIIIlllIIl, final float lllIIIlllIII) {
        GL11.glPushMatrix();
        GL11.glColor3d((double)this.red.getValue(), (double)this.green.getValue(), (double)this.blue.getValue());
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(this.width.getValue().floatValue());
        GL11.glBegin(3);
        final double lllIIIlllllI = lllIIIlllIIl.lastTickPosX + (lllIIIlllIIl.posX - lllIIIlllIIl.lastTickPosX) * lllIIIlllIII - TargetStrafe.mc.getRenderManager().viewerPosX;
        final double lllIIIllllIl = lllIIIlllIIl.lastTickPosY + (lllIIIlllIIl.posY - lllIIIlllIIl.lastTickPosY) * lllIIIlllIII - TargetStrafe.mc.getRenderManager().viewerPosY;
        final double lllIIIllllII = lllIIIlllIIl.lastTickPosZ + (lllIIIlllIIl.posZ - lllIIIlllIIl.lastTickPosZ) * lllIIIlllIII - TargetStrafe.mc.getRenderManager().viewerPosZ;
        final double lllIIIlllIll = 6.283185307179586;
        for (int lllIIlIIIIlI = 0; lllIIlIIIIlI <= 90; ++lllIIlIIIIlI) {
            GL11.glVertex3d(lllIIIlllllI + (this.radius.getValue() - 0.5) * Math.cos(lllIIlIIIIlI * 6.283185307179586 / 45.0), lllIIIllllIl, lllIIIllllII + (this.radius.getValue() - 0.5) * Math.sin(lllIIlIIIIlI * 6.283185307179586 / 45.0));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
}
