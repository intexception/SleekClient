package me.kansio.client.modules.impl.world;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.combat.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import me.kansio.client.utils.rotations.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Breaker", category = ModuleCategory.WORLD, description = "Breaks beds and cakes")
public class Breaker extends Module
{
    @Subscribe
    public void onUpdate(final UpdateEvent llllllllllllllllllllllllllIllIII) {
        if (KillAura.target != null) {
            return;
        }
        for (int llllllllllllllllllllllllllIllllI = 7, llllllllllllllllllllllllllIlllII = -llllllllllllllllllllllllllIllllI; llllllllllllllllllllllllllIlllII < llllllllllllllllllllllllllIllllI; ++llllllllllllllllllllllllllIlllII) {
            for (int lllllllllllllllllllllllllllIIIII = llllllllllllllllllllllllllIllllI; lllllllllllllllllllllllllllIIIII > -llllllllllllllllllllllllllIllllI; --lllllllllllllllllllllllllllIIIII) {
                for (int lllllllllllllllllllllllllllIIIlI = -llllllllllllllllllllllllllIllllI; lllllllllllllllllllllllllllIIIlI < llllllllllllllllllllllllllIllllI; ++lllllllllllllllllllllllllllIIIlI) {
                    final int lllllllllllllllllllllllllllIllII = (int)Breaker.mc.thePlayer.posX + llllllllllllllllllllllllllIlllII;
                    final int lllllllllllllllllllllllllllIlIlI = (int)Breaker.mc.thePlayer.posY + lllllllllllllllllllllllllllIIIII;
                    final int lllllllllllllllllllllllllllIlIII = (int)Breaker.mc.thePlayer.posZ + lllllllllllllllllllllllllllIIIlI;
                    final BlockPos lllllllllllllllllllllllllllIIllI = new BlockPos(lllllllllllllllllllllllllllIllII, lllllllllllllllllllllllllllIlIlI, lllllllllllllllllllllllllllIlIII);
                    final Block lllllllllllllllllllllllllllIIlII = Breaker.mc.theWorld.getBlockState(lllllllllllllllllllllllllllIIllI).getBlock();
                    if ((lllllllllllllllllllllllllllIIlII.getBlockState().getBlock() == Block.getBlockById(92) || lllllllllllllllllllllllllllIIlII.getBlockState().getBlock() == Blocks.bed) && Breaker.mc.thePlayer.ticksExisted % 3 == 0) {
                        final Rotation lllllllllllllllllllllllllllIllIl = AimUtil.attemptFacePosition(lllllllllllllllllllllllllllIIllI.getX(), lllllllllllllllllllllllllllIIllI.getY(), lllllllllllllllllllllllllllIIllI.getZ());
                        llllllllllllllllllllllllllIllIII.setRotationYaw(lllllllllllllllllllllllllllIllIl.getRotationYaw());
                        llllllllllllllllllllllllllIllIII.setRotationPitch(lllllllllllllllllllllllllllIllIl.getRotationPitch());
                        Breaker.mc.thePlayer.swingItem();
                        Breaker.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, lllllllllllllllllllllllllllIIllI, EnumFacing.NORTH));
                        Breaker.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, lllllllllllllllllllllllllllIIllI, EnumFacing.NORTH));
                    }
                }
            }
        }
    }
}
