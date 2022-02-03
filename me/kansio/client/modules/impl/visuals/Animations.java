package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.client.renderer.*;
import me.kansio.client.value.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;

@ModuleData(name = "Animations", category = ModuleCategory.VISUALS, description = "Custom client animations")
public class Animations extends Module
{
    public /* synthetic */ BooleanValue smoothhit;
    private /* synthetic */ float rotate;
    public /* synthetic */ NumberValue<Float> scale;
    public /* synthetic */ BooleanValue attackanim;
    public /* synthetic */ NumberValue<Integer> slowdown;
    private /* synthetic */ ModeValue modeblockanim;
    
    private void doBlockTransformations() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public Animations() {
        this.attackanim = new BooleanValue("Attack Animations", this, false);
        this.smoothhit = new BooleanValue("Smooth Hit", this, false, (Value)this.attackanim);
        this.scale = new NumberValue<Float>("Scale", this, 1.0f, 0.0f, 2.0f, 0.1f, this.attackanim);
        this.slowdown = new NumberValue<Integer>("Swing Speed", this, 1, -4, 12, 1, this.attackanim);
        this.modeblockanim = new ModeValue("Block Mode", this, this.attackanim, new String[] { "Normal", "Hide", "1.7", "Ethereal", "Stella", "Interia", "Styles", "Slide", "Lucky", "Remix", "Swang", "Down", "Knife", "Exhi", "oHare", "oHare2", "Wizzard", "Lennox", "ETB", "Spin", "Rotate" });
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.modeblockanim.getValue()));
    }
    
    private void func_178103_d(final float llIlIlIIlIIlII) {
        GlStateManager.translate(-0.5f, llIlIlIIlIIlII, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public void render(final ItemStack llIlIIlIlIIlIl, final float llIlIIlIlIIlII) {
        ++this.rotate;
        final float llIlIIlIlIIIll = 1.0f - (Animations.mc.getItemRenderer().prevEquippedProgress + (Animations.mc.getItemRenderer().equippedProgress - Animations.mc.getItemRenderer().prevEquippedProgress) * llIlIIlIlIIlII);
        final EntityPlayerSP llIlIIlIlIIIlI = Animations.mc.thePlayer;
        final float llIlIIlIlIIIIl = llIlIIlIlIIIlI.getSwingProgress(llIlIIlIlIIlII);
        final float llIlIIlIlIIIII = llIlIIlIlIIIlI.prevRotationPitch + (llIlIIlIlIIIlI.rotationPitch - llIlIIlIlIIIlI.prevRotationPitch) * llIlIIlIlIIlII;
        final float llIlIIlIIlllll = llIlIIlIlIIIlI.prevRotationYaw + (llIlIIlIlIIIlI.rotationYaw - llIlIIlIlIIIlI.prevRotationYaw) * llIlIIlIlIIlII;
        final float llIlIIlIIllllI = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.141592653589793));
        final float llIlIIlIIlllIl = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1415927f);
        if (this.attackanim.getValue()) {
            GlStateManager.scale(this.scale.getValue(), this.scale.getValue(), this.scale.getValue());
            final boolean llIlIIlIIlIIll = (boolean)this.modeblockanim.getValue().toUpperCase();
            short llIlIIlIIlIIlI = -1;
            switch (((String)llIlIIlIIlIIll).hashCode()) {
                case -1808503203: {
                    if (((String)llIlIIlIIlIIll).equals("Stella")) {
                        llIlIIlIIlIIlI = 0;
                        break;
                    }
                    break;
                }
                case 48570: {
                    if (((String)llIlIIlIIlIIll).equals("1.7")) {
                        llIlIIlIIlIIlI = 1;
                        break;
                    }
                    break;
                }
                case -1618921772: {
                    if (((String)llIlIIlIIlIIll).equals("INTERIA")) {
                        llIlIIlIIlIIlI = 2;
                        break;
                    }
                    break;
                }
                case -636740214: {
                    if (((String)llIlIIlIIlIIll).equals("ETHEREAL")) {
                        llIlIIlIIlIIlI = 3;
                        break;
                    }
                    break;
                }
                case -1838445342: {
                    if (((String)llIlIIlIIlIIll).equals("STYLES")) {
                        llIlIIlIIlIIlI = 4;
                        break;
                    }
                    break;
                }
                case -1986416409: {
                    if (((String)llIlIIlIIlIIll).equals("NORMAL")) {
                        llIlIIlIIlIIlI = 5;
                        break;
                    }
                    break;
                }
                case 2217282: {
                    if (((String)llIlIIlIIlIIll).equals("HIDE")) {
                        llIlIIlIIlIIlI = 6;
                        break;
                    }
                    break;
                }
                case 79309014: {
                    if (((String)llIlIIlIIlIIll).equals("SWANG")) {
                        llIlIIlIIlIIlI = 7;
                        break;
                    }
                    break;
                }
                case 78988689: {
                    if (((String)llIlIIlIIlIIll).equals("SLIDE")) {
                        llIlIIlIIlIIlI = 8;
                        break;
                    }
                    break;
                }
                case 72786632: {
                    if (((String)llIlIIlIIlIIll).equals("LUCKY")) {
                        llIlIIlIIlIIlI = 9;
                        break;
                    }
                    break;
                }
                case 71660165: {
                    if (((String)llIlIIlIIlIIll).equals("KNIFE")) {
                        llIlIIlIIlIIlI = 10;
                        break;
                    }
                    break;
                }
                case 2104482: {
                    if (((String)llIlIIlIIlIIll).equals("DOWN")) {
                        llIlIIlIIlIIlI = 11;
                        break;
                    }
                    break;
                }
                case 2142452: {
                    if (((String)llIlIIlIIlIIll).equals("EXHI")) {
                        llIlIIlIIlIIlI = 12;
                        break;
                    }
                    break;
                }
                case -1964753449: {
                    if (((String)llIlIIlIIlIIll).equals("OHARE2")) {
                        llIlIIlIIlIIlI = 13;
                        break;
                    }
                    break;
                }
                case 77860649: {
                    if (((String)llIlIIlIIlIIll).equals("REMIX")) {
                        llIlIIlIIlIIlI = 14;
                        break;
                    }
                    break;
                }
                case 75168187: {
                    if (((String)llIlIIlIIlIIll).equals("OHARE")) {
                        llIlIIlIIlIIlI = 15;
                        break;
                    }
                    break;
                }
                case 2079200097: {
                    if (((String)llIlIIlIIlIIll).equals("WIZZARD")) {
                        llIlIIlIIlIIlI = 16;
                        break;
                    }
                    break;
                }
                case -2053027678: {
                    if (((String)llIlIIlIIlIIll).equals("LENNOX")) {
                        llIlIIlIIlIIlI = 17;
                        break;
                    }
                    break;
                }
                case 68979: {
                    if (((String)llIlIIlIIlIIll).equals("ETB")) {
                        llIlIIlIIlIIlI = 18;
                        break;
                    }
                    break;
                }
                case 2551874: {
                    if (((String)llIlIIlIIlIIll).equals("SPIN")) {
                        llIlIIlIIlIIlI = 19;
                        break;
                    }
                    break;
                }
                case -1871851173: {
                    if (((String)llIlIIlIIlIIll).equals("ROTATE")) {
                        llIlIIlIIlIIlI = 20;
                        break;
                    }
                    break;
                }
            }
            switch (llIlIIlIIlIIlI) {
                case 0: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, 0.0f);
                    this.func_178103_d(0.2f);
                    GlStateManager.translate(0.1f, 0.2f, 0.3f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 30.0f, -5.0f, 0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 10.0f, 1.0f, -0.4f, -0.5f);
                    break;
                }
                case 1: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(0.0f, llIlIIlIlIIIIl);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 2: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(0.05f, llIlIIlIlIIIIl);
                    GlStateManager.translate(-0.5f, 0.5f, 0.0f);
                    GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, 0.0f);
                    this.func_178103_d(0.2f);
                    GlStateManager.translate(-0.05f, 0.2f, 0.2f);
                    GlStateManager.rotate(-llIlIIlIIlllIl * 70.0f / 2.0f, -8.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIIlllIl * 70.0f, 1.0f, -0.4f, -0.0f);
                    break;
                }
                case 4: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, 0.0f);
                    this.func_178103_d(0.2f);
                    final float llIlIIlIllIlIl = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.141592653589793));
                    GlStateManager.translate(-0.05f, 0.2f, 0.0f);
                    GlStateManager.rotate(-llIlIIlIllIlIl * 70.0f / 2.0f, -8.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIllIlIl * 70.0f, 1.0f, -0.4f, -0.0f);
                    break;
                }
                case 5: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(0.0f, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    final float llIlIIlIllIlII = MathHelper.sin(llIlIIlIlIIIIl * llIlIIlIlIIIIl * 0.3215927f);
                    final float llIlIIlIllIIll = MathHelper.sin(MathHelper.sqrt_float(0.0f) * 0.3215927f);
                    GlStateManager.translate(-0.0f, -0.0f, 0.2f);
                    break;
                }
                case 6: {
                    Animations.mc.getItemRenderer().func_178105_d(llIlIIlIlIIIIl);
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, llIlIIlIlIIIIl);
                    break;
                }
                case 7: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 2.0f, llIlIIlIlIIIIl);
                    final float llIlIIlIllIIlI = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.141592653589793));
                    GlStateManager.rotate(llIlIIlIllIIlI * 30.0f / 2.0f, -llIlIIlIllIIlI, -0.0f, 9.0f);
                    GlStateManager.rotate(llIlIIlIllIIlI * 40.0f, 1.0f, -llIlIIlIllIIlI / 2.0f, -0.0f);
                    this.func_178103_d(0.4f);
                    break;
                }
                case 8: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(0.0f, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    final float llIlIIlIllIIIl = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1415927f);
                    GlStateManager.translate(-0.05f, -0.0f, 0.35f);
                    GlStateManager.rotate(-llIlIIlIllIIIl * 60.0f / 2.0f, -15.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIllIIIl * 70.0f, 1.0f, -0.4f, -0.0f);
                    break;
                }
                case 9: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(0.0f, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    final float llIlIIlIllIIII = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 0.3215927f);
                    GlStateManager.translate(-0.05f, -0.0f, 0.3f);
                    GlStateManager.rotate(-llIlIIlIllIIII * 60.0f / 2.0f, -15.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIllIIII * 70.0f, 1.0f, -0.4f, -0.0f);
                    break;
                }
                case 10: {
                    GlStateManager.translate(0.41f, -0.25f, -0.5555557f);
                    GlStateManager.translate(0.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(35.0f, 0.0f, 1.5f, 0.0f);
                    final float llIlIIlIlIllll = MathHelper.sin(llIlIIlIlIIIIl * llIlIIlIlIIIIl / 64.0f * 3.1415927f);
                    final float llIlIIlIlIlllI = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) / 0.4f * 3.1415927f);
                    final float llIlIIlIlIllIl = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) / 0.4f * 3.1415927f);
                    GlStateManager.rotate(llIlIIlIlIllll * -5.0f, 0.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(llIlIIlIlIlllI * -12.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.rotate(llIlIIlIlIlllI * -65.0f, 1.0f, 0.0f, 0.0f);
                    final float llIlIIlIlIllII = 0.295f;
                    GlStateManager.scale(llIlIIlIlIllII, llIlIIlIlIllII, llIlIIlIlIllII);
                    break;
                }
                case 11: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIIl - 0.125f, 0.0f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 55.0f / 2.0f, -8.0f, 0.4f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 45.0f, 1.0f, llIlIIlIIllllI / 2.0f, -0.0f);
                    GlStateManager.translate(0.0f, 0.1f, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 12: {
                    final float llIlIIlIlIlIll = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1));
                    GL11.glTranslated(-0.1, 0.1, 0.0);
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 2.0f, 0.0f);
                    GlStateManager.rotate(-llIlIIlIlIlIll * 40.0f / 2.0f, llIlIIlIlIlIll / 2.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIlIlIll * 30.0f, 1.0f, llIlIIlIlIlIll / 2.0f, -0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 13: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIIl, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    GlStateManager.translate(-0.05f, 0.6f, 0.3f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 70.0f / 2.0f, -8.0f, -0.0f, 9.0f);
                    GlStateManager.rotate(-llIlIIlIIllllI * 70.0f, 1.5f, -0.4f, -0.0f);
                    break;
                }
                case 14: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 2.0f, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    GL11.glRotatef(-llIlIIlIIllllI * 50.0f / 2.0f, -8.0f, 0.4f, 9.0f);
                    GL11.glRotatef(-llIlIIlIIllllI * 40.0f, 1.5f, -0.4f, -0.0f);
                    break;
                }
                case 15: {
                    final float llIlIIlIlIlIlI = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1415927f);
                    GL11.glTranslated(-0.05, 0.0, -0.25);
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 2.0f, 0.0f);
                    GlStateManager.rotate(-llIlIIlIlIlIlI * 60.0f, 2.0f, -llIlIIlIlIlIlI * 2.0f, -0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 16: {
                    final float llIlIIlIlIlIIl = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1));
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 3.0f, 0.0f);
                    GlStateManager.rotate(llIlIIlIlIlIIl * 30.0f / 1.0f, llIlIIlIlIlIIl / -1.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(llIlIIlIlIlIIl * 10.0f / 10.0f, -llIlIIlIlIlIIl / -1.0f, 1.0f, 0.0f);
                    GL11.glTranslated(0.0, 0.4, 0.0);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 17: {
                    final float llIlIIlIlIlIII = MathHelper.sin((float)(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1));
                    GL11.glTranslated(0.0, 0.125, -0.1);
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 3.0f, 0.0f);
                    GlStateManager.rotate(-llIlIIlIlIlIII * 75.0f / 4.5f, llIlIIlIlIlIII / 3.0f, -2.4f, 5.0f);
                    GlStateManager.rotate(-llIlIIlIlIlIII * 75.0f, 1.5f, llIlIIlIlIlIII / 3.0f, -0.0f);
                    GlStateManager.rotate(llIlIIlIlIlIII * 72.5f / 2.25f, llIlIIlIlIlIII / 3.0f, -2.7f, 5.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    break;
                }
                case 18: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    final float llIlIIlIlIIlll = MathHelper.sin(MathHelper.sqrt_float(llIlIIlIlIIIIl) * 3.1415927f);
                    GlStateManager.translate(-0.05f, 0.6f, 0.3f);
                    GlStateManager.rotate(-llIlIIlIlIIlll * 80.0f / 2.0f, -4.0f, -0.0f, 18.0f);
                    GlStateManager.rotate(-llIlIIlIlIIlll * 70.0f, 1.5f, -0.4f, -0.0f);
                    break;
                }
                case 19: {
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll, 0.0f);
                    Animations.mc.getItemRenderer().func_178103_d();
                    GL11.glRotatef(this.rotate, this.rotate, 0.0f, this.rotate);
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    this.rotate += this.slowdown.getValue();
                    break;
                }
                case 20: {
                    GL11.glTranslated(-0.04, 0.1, 0.0);
                    Animations.mc.getItemRenderer().transformFirstPersonItem(llIlIIlIlIIIll / 2.5f, 0.0f);
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.2f);
                    GlStateManager.rotate(this.rotate, 0.0f, -1.0f, 0.0f);
                    break;
                }
            }
        }
    }
}
