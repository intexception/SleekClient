package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.entity.player.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.player.hackerdetect.checks.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import me.kansio.client.value.*;

@ModuleData(name = "Hacker Detect", category = ModuleCategory.PLAYER, description = "Detects Cheaters Useing Client Side AC")
public class HackerDetect extends Module
{
    private static /* synthetic */ HackerDetect instance;
    public /* synthetic */ ModeValue theme;
    private /* synthetic */ HashMap<EntityPlayer, Integer> violations;
    private /* synthetic */ double cageYValue;
    
    public void setCageYValue(final double lllllllllllllllllllllIlllIIlllII) {
        this.cageYValue = lllllllllllllllllllllIlllIIlllII;
    }
    
    @Override
    public void onDisable() {
        final int lllllllllllllllllllllIlllIllllIl = ((Value<Integer>)this.theme).getValue();
        long lllllllllllllllllllllIlllIllllII = -1;
        switch (((String)lllllllllllllllllllllIlllIllllIl).hashCode()) {
            case 79969970: {
                if (((String)lllllllllllllllllllllIlllIllllIl).equals("Sleek")) {
                    lllllllllllllllllllllIlllIllllII = 0;
                    break;
                }
                break;
            }
            case 82544993: {
                if (((String)lllllllllllllllllllllIlllIllllIl).equals("Verus")) {
                    lllllllllllllllllllllIlllIllllII = 1;
                    break;
                }
                break;
            }
            case 64733: {
                if (((String)lllllllllllllllllllllIlllIllllIl).equals("AGC")) {
                    lllllllllllllllllllllIlllIllllII = 2;
                    break;
                }
                break;
            }
            case 1671735356: {
                if (((String)lllllllllllllllllllllIlllIllllIl).equals("Ghostly")) {
                    lllllllllllllllllllllIlllIllllII = 3;
                    break;
                }
                break;
            }
        }
        switch (lllllllllllllllllllllIlllIllllII) {
            case 0L: {
                ChatUtil.logNoPrefix("§7[§b§lSleekAC§7] §bAlerts Disabled");
                break;
            }
            case 1L: {
                ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
                break;
            }
            case 2L: {
                ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
                break;
            }
            case 3L: {
                ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
                break;
            }
        }
    }
    
    @Override
    public void onEnable() {
        final long lllllllllllllllllllllIllllIIIlII = ((Value<Long>)this.theme).getValue();
        double lllllllllllllllllllllIllllIIIIll = -1;
        switch (((String)lllllllllllllllllllllIllllIIIlII).hashCode()) {
            case 79969970: {
                if (((String)lllllllllllllllllllllIllllIIIlII).equals("Sleek")) {
                    lllllllllllllllllllllIllllIIIIll = 0;
                    break;
                }
                break;
            }
            case 82544993: {
                if (((String)lllllllllllllllllllllIllllIIIlII).equals("Verus")) {
                    lllllllllllllllllllllIllllIIIIll = 1;
                    break;
                }
                break;
            }
            case 64733: {
                if (((String)lllllllllllllllllllllIllllIIIlII).equals("AGC")) {
                    lllllllllllllllllllllIllllIIIIll = 2;
                    break;
                }
                break;
            }
            case 1671735356: {
                if (((String)lllllllllllllllllllllIllllIIIlII).equals("Ghostly")) {
                    lllllllllllllllllllllIllllIIIIll = 3;
                    break;
                }
                break;
            }
        }
        switch (lllllllllllllllllllllIllllIIIIll) {
            case 0.0: {
                ChatUtil.logNoPrefix("§7[§b§lSleekAC§7] §bAlerts Enabled");
                break;
            }
            case 1.0: {
                ChatUtil.logNoPrefix("§9You are now viewing alerts");
                break;
            }
            case 2.0: {
                ChatUtil.logNoPrefix("§9You are now viewing alerts");
                break;
            }
            case 3.0: {
                ChatUtil.logNoPrefix("§9You are now viewing alerts");
                break;
            }
        }
    }
    
    public HackerDetect() {
        this.theme = new ModeValue("Theme", this, new String[] { "Sleek", "Verus", "AGC", "Ghostly" });
        this.violations = new HashMap<EntityPlayer, Integer>();
        HackerDetect.instance = this;
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lllllllllllllllllllllIlllIllIllI) {
        if (HackerDetect.mc.thePlayer.ticksExisted < 5) {
            this.violations.clear();
        }
        for (final Check lllllllllllllllllllllIlllIlllIII : Client.getInstance().getCheckManager().getChecks()) {
            lllllllllllllllllllllIlllIlllIII.onUpdate();
        }
    }
    
    public HashMap<EntityPlayer, Integer> getViolations() {
        return this.violations;
    }
    
    public static HackerDetect getInstance() {
        return HackerDetect.instance;
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lllllllllllllllllllllIlllIlIIlll) {
        for (final Check lllllllllllllllllllllIlllIlIllIl : Client.getInstance().getCheckManager().getChecks()) {
            lllllllllllllllllllllIlllIlIllIl.onPacket(lllllllllllllllllllllIlllIlIIlll);
        }
        if (lllllllllllllllllllllIlllIlIIlll.getPacket() instanceof S02PacketChat) {
            final S02PacketChat lllllllllllllllllllllIlllIlIlIll = lllllllllllllllllllllIlllIlIIlll.getPacket();
            final String lllllllllllllllllllllIlllIlIlIlI = lllllllllllllllllllllIlllIlIlIll.getChatComponent().getFormattedText();
            if (lllllllllllllllllllllIlllIlIlIlI.contains("Cages open in:")) {
                for (final Check lllllllllllllllllllllIlllIlIllII : Client.getInstance().getCheckManager().getChecks()) {
                    lllllllllllllllllllllIlllIlIllII.onBlocksMCGameStartTick();
                }
            }
        }
    }
    
    public double getCageYValue() {
        return this.cageYValue;
    }
}
