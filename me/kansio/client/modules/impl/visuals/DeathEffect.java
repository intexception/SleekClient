package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import me.kansio.client.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Death Effect", category = ModuleCategory.VISUALS, description = "Custom death effects")
public class DeathEffect extends Module
{
    public /* synthetic */ BooleanValue memesound;
    /* synthetic */ String[] deathMessage;
    public /* synthetic */ BooleanValue soundon;
    public /* synthetic */ ModeValue mode;
    public /* synthetic */ ModeValue mememode;
    
    public void playSound(final String llIIlIlIIIIIll) {
        DeathEffect.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(String.valueOf(new StringBuilder().append("deatheffect.").append(llIIlIlIIIIIll.toLowerCase().replace(" ", "")))), 1.0f));
    }
    
    public DeathEffect() {
        super("DeathEffect", ModuleCategory.VISUALS);
        this.mode = new ModeValue("Effect Mode", this, new String[] { "Blood", "Lightning" });
        this.soundon = new BooleanValue("Play Sound", this, false);
        this.memesound = new BooleanValue("Meme Sounds", this, false);
        this.mememode = new ModeValue("Meme Mode", this, this.memesound, new String[] { "OOF", "N Word1", "N Word2", "Win Error", "Yoda", "GTA" });
        this.deathMessage = new String[] { "foi jogado no void por", "morreu para", "was slain by", "slain by", "was killed by", "hit the ground too hard thanks to", "was shot by" };
        this.register(this.mode, this.soundon, this.memesound, this.mememode);
    }
    
    @Subscribe
    public void onChat(final PacketEvent llIIlIlIIlIIII) {
        if (!this.memesound.getValue()) {
            return;
        }
        if (llIIlIlIIlIIII.getPacket() instanceof S02PacketChat) {
            final S02PacketChat llIIlIlIIlIlII = llIIlIlIIlIIII.getPacket();
            final String llIIlIlIIlIIll = llIIlIlIIlIlII.getChatComponent().getUnformattedText();
            final String[] llIIlIlIIlIIlI = llIIlIlIIlIIll.split(" ");
            final byte llIIlIlIIIlIlI = (Object)this.deathMessage;
            final long llIIlIlIIIlIIl = llIIlIlIIIlIlI.length;
            for (byte llIIlIlIIIlIII = 0; llIIlIlIIIlIII < llIIlIlIIIlIIl; ++llIIlIlIIIlIII) {
                final String llIIlIlIIlIlIl = llIIlIlIIIlIlI[llIIlIlIIIlIII];
                if (llIIlIlIIlIIll.contains(llIIlIlIIlIlIl) && llIIlIlIIlIIll.contains(DeathEffect.mc.thePlayer.getName()) && !llIIlIlIIlIIlI[0].equalsIgnoreCase(DeathEffect.mc.thePlayer.getName())) {
                    this.playSound(this.mememode.getValue());
                }
            }
        }
    }
}
