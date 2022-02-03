package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import net.minecraft.entity.player.*;
import me.kansio.client.utils.chat.*;
import com.google.common.eventbus.*;
import java.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.render.*;
import java.awt.*;
import net.minecraft.client.gui.*;

@ModuleData(name = "Staff Detector", category = ModuleCategory.EXPLOIT, description = "Checks if there's a staff member in your game")
public class StaffDetect extends Module
{
    private /* synthetic */ BooleanValue announce;
    private /* synthetic */ ArrayList<String> staffInMatch;
    private /* synthetic */ int amount;
    private /* synthetic */ List<String> verusKnownStaff;
    
    @Subscribe
    public void onTick(final UpdateEvent lIlllIIIllIlII) {
        if (StaffDetect.mc.thePlayer.ticksExisted > 5) {
            for (final EntityPlayer lIlllIIIllIllI : StaffDetect.mc.theWorld.playerEntities) {
                if (this.staffInMatch.contains(lIlllIIIllIllI.getName())) {
                    continue;
                }
                for (final String lIlllIIIllIlll : this.verusKnownStaff) {
                    if (lIlllIIIllIllI.getName().contains(lIlllIIIllIlll)) {
                        this.staffInMatch.add(lIlllIIIllIllI.getName());
                        this.amount = this.staffInMatch.size();
                        ChatUtil.logNoPrefix(String.valueOf(new StringBuilder().append("§4§l[Staff Detect]: §c").append(lIlllIIIllIlll).append(" §fis in your game!")));
                        if (!this.announce.getValue()) {
                            continue;
                        }
                        StaffDetect.mc.thePlayer.sendChatMessage(String.valueOf(new StringBuilder().append("[Sleek Staff Detector] Found a staff member in the lobby: ").append(lIlllIIIllIlll)));
                    }
                }
            }
        }
        else {
            this.staffInMatch.clear();
            this.amount = 0;
        }
    }
    
    public StaffDetect() {
        this.announce = new BooleanValue("Announce", this, true);
        this.staffInMatch = new ArrayList<String>();
        this.verusKnownStaff = Arrays.asList("0ayt", "0hHaze", "0hMustafa", "0StefanSalvatore", "0Taha", "0YH_", "1A7mad1", "1A7MD_PvP", "1Ab0oDx", "1Ahmd", "1ASSASSINATION_", "1ayt", "1Brhom", "1Cloud_", "1Daykel", "1ELY_", "1F5aMH___3oo", "1HeyImHasson_", "1Hussain", "1LaB", "1Levaai", "1Lweez", "1M0ha", "1M7mdz", "1M7mmD", "1mAhMeeD", "1MasterOogway", "1Mshari", "1Neres", "1RealFadi", "1Selver", "1Sinqx", "1Sweet", "1Terix", "1Tz3bo", "1xM7moD_", "1_Nr", "2gfs", "38eD", "39be", "3AmOdi_", "3Mmr", "420m2tt", "420syr1a", "502x", "502z", "5aQxx", "7bx_", "7sO", "90fa", "a4lf", "a7madx7craft", "a7mm", "ABBGEEN", "AbduIlah", "Abdulaziz187", "Aboz3bl", "AbuA7md506", "AdamViP_", "AG_99", "Ahmed_1b", "Ahmmd", "Aiiden", "AlmondMilky", "AngelRana", "Aparamillos", "arbawii", "AssassinTime", "aXav", "AYm13579", "Ayrinea", "b3ed", "Ba6ee5man", "baderr", "BaSiL_123", "Bastic", "BinRashood", "Bl2ck", "Bl2q", "BlackOurs", "Blood_Artz", "bodi66699", "ByNEK", "cKoro", "comsterr", "Creegam", "Crlexs", "dazaiscatgirl", "DetectiveDnxx", "DetectiveFoxTY", "DetectiveMuhamed", "Dizibre", "Dqrkfall", "ebararh", "Eissaa", "EmirhanBoss", "Enorm1ty", "Enormities", "EthqnInMC", "Ev2n", "EyesO_Diamond", "Faisaal", "FANOR_SY", "FaRidok", "FastRank", "FernandoEscobar", "FireLigtning", "FlyMeToMoon", "Foxy__Boy", "Frqosh", "GzazhMan", "HeWantMeee", "hlla", "i7_e", "IamCsO", "iDhoom", "idpc", "iEvlect", "iikimo", "iikuya", "ilybb0", "iMehdi_", "ImM7MAD", "ImortalM3x", "iS3od_", "iSlo0oMx2", "Its_Qassem", "ItzFHD", "iTz_AbODe2", "Ix20", "ixBander", "Jinaaan", "judeh_gamer", "JustGetOutDude", "JustKreem", "KaaReeeM", "KingHOYT", "Laeria", "Le3b0ody", "leeleeeleeeleee", "lt1x", "Lunching", "Lwzi", "M4rwaan", "m6m6_", "m7mdxjw", "M7mmd", "M7_m", "martadella", "MastersLouis__", "MayBe1ForAGer", "MeAndOnlyMee", "MezzBek", "Mieeteer", "MightyM7MD", "MightySaeed", "MK_F16", "Mlazm_", "mokgii", "MrProfessor_T", "MShkLJe", "Mt2b", "Muhameed", "Mvhammed", "mzh", "N15_", "N29r", "NaRqC", "nejem", "nickdimos", "NotMissYou_", "Nwwf", "oBIXT", "OnlyMoHqMeD__", "oq_ba", "pauseflow", "PerfectRod_", "policewomen", "PotatoSoublaki", "puffingstardust", "qMoha2nd", "Raceth", "RADVN", "Rayleiigh", "resci", "rjassassin", "rkqx", "rr2ot", "Rvgeraz", "S3mm", "S3rvox", "Sarout", "SenpaiAhmed", "Severity_", "SheWantMeee", "SKZ96", "Sp0tzy_", "SpecialAmeer", "Stay1High", "StrengthSimp", "TheBlackTime", "TheDaddyRank", "Tibbz_BGamer", "Tostiebramkaas", "UmmDrRep", "UmmIvy", "Violeet", "Vrqq", "VTomas", "vxom", "V_Death_V", "w7r", "WalriderTime", "wl3d", "xA7md_7rb", "xBeshoo", "xIBerryPlayz", "xiiNinja", "xiiRadi", "xImTaiG_", "xL2d", "xMz7", "xx1k", "xZomik", "Y2serr", "yff3", "yosife_7Y", "YouwantMeThis", "yQuack", "ZANAD", "zFlokenzVEVO", "_Cignus_", "_JustMix", "_N3", "_Skofy", "_Surfers_");
    }
    
    public ArrayList<String> getStaffInMatch() {
        return this.staffInMatch;
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent lIlllIIIlIlIll) {
        final ScaledResolution lIlllIIIlIlIlI = RenderUtils.getResolution();
        if (this.staffInMatch.size() != 0 && this.amount != 0) {
            RenderUtils.drawRect(lIlllIIIlIlIlI.getScaledWidth() / 2 - 80, lIlllIIIlIlIlI.getScaledHeight() / 2 - 21, 167.0, 1.0, new Color(255, 0, 0).getRGB());
            RenderUtils.drawRect(lIlllIIIlIlIlI.getScaledWidth() / 2 - 80, lIlllIIIlIlIlI.getScaledHeight() / 2 - 20, 167.0, 40.0, new Color(0, 0, 0, 100).getRGB());
            if (this.amount > 1) {
                StaffDetect.mc.fontRendererObj.drawStringWithShadow("§4§lWarning:", (float)(lIlllIIIlIlIlI.getScaledWidth() / 2 - 76), (float)(lIlllIIIlIlIlI.getScaledHeight() / 2 - 14), -1);
                StaffDetect.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("§f").append(this.amount).append(" §cStaff members §fwere detected!")), (float)(lIlllIIIlIlIlI.getScaledWidth() / 2 - 76), (float)(lIlllIIIlIlIlI.getScaledHeight() / 2), -1);
            }
            else {
                StaffDetect.mc.fontRendererObj.drawStringWithShadow("§4§lWarning:", (float)(lIlllIIIlIlIlI.getScaledWidth() / 2 - 70), (float)(lIlllIIIlIlIlI.getScaledHeight() / 2 - 14), -1);
                StaffDetect.mc.fontRendererObj.drawStringWithShadow("§fA §cStaff member §fwas detected!", (float)(lIlllIIIlIlIlI.getScaledWidth() / 2 - 70), (float)(lIlllIIIlIlIlI.getScaledHeight() / 2), -1);
            }
        }
    }
}
