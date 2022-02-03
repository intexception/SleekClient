package me.kansio.client.utils.combat;

import me.kansio.client.utils.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import me.kansio.client.*;
import net.minecraft.scoreboard.*;

public class FightUtil extends Util
{
    public static boolean canHit(final double lIIIlIIIIllllI) {
        return Math.random() <= lIIIlIIIIllllI;
    }
    
    public static List<EntityLivingBase> getMultipleTargets(final double lIIIlIIIIIIlll, final boolean lIIIlIIIIIlllI, final boolean lIIIlIIIIIIlIl, final boolean lIIIlIIIIIIlII, final boolean lIIIlIIIIIIIll, final boolean lIIIlIIIIIlIlI, final boolean lIIIlIIIIIlIIl) {
        final List<EntityLivingBase> lIIIlIIIIIlIII = new ArrayList<EntityLivingBase>();
        for (final Entity lIIIlIIIIlIIII : FightUtil.mc.theWorld.loadedEntityList) {
            if (!(lIIIlIIIIlIIII instanceof EntityLivingBase)) {
                continue;
            }
            final EntityLivingBase lIIIlIIIIlIIIl = (EntityLivingBase)lIIIlIIIIlIIII;
            if (lIIIlIIIIlIIIl == FightUtil.mc.thePlayer || FightUtil.mc.thePlayer.getDistanceToEntity(lIIIlIIIIlIIIl) > lIIIlIIIIIIlll || (!lIIIlIIIIlIIIl.canEntityBeSeen(FightUtil.mc.thePlayer) && !lIIIlIIIIIIIll) || lIIIlIIIIlIIIl.isDead || lIIIlIIIIlIIIl instanceof EntityArmorStand || lIIIlIIIIlIIIl instanceof EntityVillager || (lIIIlIIIIlIIIl instanceof EntityAnimal && !lIIIlIIIIIIlII) || (lIIIlIIIIlIIIl instanceof EntitySquid && !lIIIlIIIIIIlII) || (lIIIlIIIIlIIIl instanceof EntityPlayer && !lIIIlIIIIIlllI) || (lIIIlIIIIlIIIl instanceof EntityMob && !lIIIlIIIIIlIlI) || (lIIIlIIIIlIIIl instanceof EntitySlime && !lIIIlIIIIIlIlI) || (Client.getInstance().getFriendManager().isFriend(lIIIlIIIIlIIIl.getName()) && !lIIIlIIIIIIlIl)) {
                continue;
            }
            if (lIIIlIIIIlIIIl.isInvisible() && !lIIIlIIIIIlIIl) {
                continue;
            }
            if (lIIIlIIIIIlIII.size() > 5) {
                continue;
            }
            lIIIlIIIIIlIII.add(lIIIlIIIIlIIIl);
        }
        return lIIIlIIIIIlIII;
    }
    
    public static boolean isOnSameTeam(final EntityLivingBase lIIIIllllIIlII) {
        if (lIIIIllllIIlII.getTeam() != null && FightUtil.mc.thePlayer.getTeam() != null) {
            final Team lIIIIllllIIlll = lIIIIllllIIlII.getTeam();
            final Team lIIIIllllIIllI = FightUtil.mc.thePlayer.getTeam();
            return !lIIIIllllIIlII.getName().contains("UPGRADES") && !lIIIIllllIIlII.getName().contains("SHOP") && lIIIIllllIIlll == lIIIIllllIIllI;
        }
        return false;
    }
    
    public static boolean isValid(final EntityLivingBase lIIIIlllllIIII, final double lIIIIlllllIlIl, final boolean lIIIIllllIlllI, final boolean lIIIIlllllIIll, final boolean lIIIIlllllIIlI, final boolean lIIIIlllllIIIl) {
        return FightUtil.mc.thePlayer.getDistanceToEntity(lIIIIlllllIIII) <= lIIIIlllllIlIl && !lIIIIlllllIIII.isDead && !(lIIIIlllllIIII instanceof EntityArmorStand) && !(lIIIIlllllIIII instanceof EntityVillager) && (!(lIIIIlllllIIII instanceof EntityPlayer) || lIIIIlllllIIll) && (!(lIIIIlllllIIII instanceof EntityAnimal) || lIIIIlllllIIlI) && (!(lIIIIlllllIIII instanceof EntityMob) || lIIIIlllllIIIl) && (!lIIIIlllllIIII.isInvisible() || lIIIIllllIlllI) && lIIIIlllllIIII != FightUtil.mc.thePlayer;
    }
}
