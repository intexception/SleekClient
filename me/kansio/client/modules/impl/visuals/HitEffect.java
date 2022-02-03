package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.combat.*;
import com.google.common.eventbus.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import me.kansio.client.utils.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import me.kansio.client.value.*;

@ModuleData(name = "Hit Effect", category = ModuleCategory.VISUALS, description = "Custom attack effects")
public class HitEffect extends Module
{
    public /* synthetic */ BooleanValue hitmarkersound;
    public /* synthetic */ BooleanValue crit;
    public /* synthetic */ NumberValue<Integer> critnum;
    public /* synthetic */ ModeValue mode;
    public /* synthetic */ ModeValue hitmarkermode;
    public /* synthetic */ NumberValue<Integer> modenum;
    public /* synthetic */ NumberValue<Integer> enchnum;
    public /* synthetic */ BooleanValue ench;
    
    @Subscribe
    public void onUpdate(final UpdateEvent llIlIIlIlIll) {
        if (KillAura.target != null && KillAura.target.hurtTime > 9) {
            this.doParticle(KillAura.target);
            this.doCrack(KillAura.target);
            if (this.hitmarkersound.getValue()) {
                this.doSound(KillAura.target);
            }
        }
    }
    
    public void doSound(final EntityLivingBase llIIlllllllI) {
        final double llIIllllllIl = llIIlllllllI.posX;
        final double llIIllllllII = llIIlllllllI.posY;
        final double llIIlllllIll = llIIlllllllI.posZ;
        final float llIIllllIlIl = ((Value<Float>)this.hitmarkermode).getValue();
        String llIIllllIlII = (String)(-1);
        switch (((String)llIIllllIlIl).hashCode()) {
            case 64280026: {
                if (((String)llIIllllIlIl).equals("Blood")) {
                    llIIllllIlII = (String)0;
                    break;
                }
                break;
            }
            case 153379805: {
                if (((String)llIIllllIlIl).equals("Call Of Duty")) {
                    llIIllllIlII = (String)1;
                    break;
                }
                break;
            }
            case 79940188: {
                if (((String)llIIllllIlIl).equals("Skeet")) {
                    llIIllllIlII = (String)2;
                    break;
                }
                break;
            }
        }
        switch (llIIllllIlII) {
            case 0L: {
                HitEffect.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("dig.stone"), (float)llIIllllllIl, (float)llIIllllllII, (float)llIIlllllIll));
                break;
            }
            case 1L: {
                HitEffect.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("hitmarker.callofduty"), (float)llIIllllllIl, (float)llIIllllllII, (float)llIIlllllIll));
                break;
            }
            case 2L: {
                HitEffect.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("hitmarker.skeet"), (float)llIIllllllIl, (float)llIIllllllII, (float)llIIlllllIll));
                break;
            }
        }
    }
    
    public void doCrack(final EntityLivingBase llIlIIIIlllI) {
        final double llIlIIIlIIlI = llIlIIIIlllI.posX;
        final double llIlIIIlIIIl = llIlIIIIlllI.posY;
        final double llIlIIIlIIII = llIlIIIIlllI.posZ;
        final boolean llIlIIIIlIlI = ((Value<Boolean>)this.mode).getValue();
        char llIlIIIIlIIl = (char)(-1);
        switch (((String)llIlIIIIlIlI).hashCode()) {
            case 64280026: {
                if (((String)llIlIIIIlIlI).equals("Blood")) {
                    llIlIIIIlIIl = '\0';
                    break;
                }
                break;
            }
            case 2433880: {
                if (((String)llIlIIIIlIlI).equals("None")) {
                    llIlIIIIlIIl = '\u0001';
                    break;
                }
                break;
            }
        }
        switch (llIlIIIIlIIl) {
            case '\0': {
                for (int llIlIIIlIlIl = 0; llIlIIIlIlIl < this.modenum.getValue(); ++llIlIIIlIlIl) {
                    final World llIlIIIlIllI = llIlIIIIlllI.getEntityWorld();
                    llIlIIIlIllI.spawnParticle(EnumParticleTypes.BLOCK_CRACK, llIlIIIlIIlI + MathUtil.getRandomInRange(-0.5, 0.5), llIlIIIlIIIl + MathUtil.getRandomInRange(-1, 1), llIlIIIlIIII + MathUtil.getRandomInRange(-0.5, 0.5), 23.0, 23.0, 23.0, 152);
                }
                break;
            }
        }
    }
    
    public void doParticle(final EntityLivingBase llIlIIlIIIIl) {
        if (this.crit.getValue()) {
            for (int llIlIIlIIllI = 0; llIlIIlIIllI < this.critnum.getValue(); ++llIlIIlIIllI) {
                HitEffect.mc.thePlayer.onCriticalHit(llIlIIlIIIIl);
            }
        }
        if (this.ench.getValue()) {
            for (int llIlIIlIIlIl = 0; llIlIIlIIlIl < this.enchnum.getValue(); ++llIlIIlIIlIl) {
                HitEffect.mc.thePlayer.onEnchantmentCritical(llIlIIlIIIIl);
            }
        }
    }
    
    public HitEffect() {
        this.mode = new ModeValue("HitMarker Mode", this, new String[] { "Blood", "None" });
        this.modenum = new NumberValue<Integer>("Amount", this, 1, 1, 20, 1);
        this.hitmarkersound = new BooleanValue("Play Sound", this, false);
        this.hitmarkermode = new ModeValue("Sound Mode", this, this.hitmarkersound, new String[] { "Blood", "Call Of Duty", "Skeet" });
        this.crit = new BooleanValue("Criticals", this, false);
        this.critnum = new NumberValue<Integer>("Amount", this, 1, 1, 10, 1, this.crit);
        this.ench = new BooleanValue("Enchants", this, false);
        this.enchnum = new NumberValue<Integer>("Amount", this, 1, 1, 10, 1, this.ench);
    }
}
