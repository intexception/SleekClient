package me.kansio.client.utils.block;

import me.kansio.client.utils.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class BlockUtil extends Util
{
    public static Block getBlockAt(final BlockPos lIlIlllIlllllI) {
        return BlockUtil.mc.theWorld.getBlockState(lIlIlllIlllllI).getBlock();
    }
}
