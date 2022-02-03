package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.*;

public class BlockSoulSand extends Block
{
    public BlockSoulSand() {
        super(Material.sand, MapColor.brownColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
        final float f = 0.125f;
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - f, pos.getZ() + 1);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        final NoSlowEvent event = new NoSlowEvent(NoSlowEvent.Type.SOULSAND);
        Client.getInstance().getEventBus().post((Object)event);
        if (event.isCancelled()) {
            return;
        }
        entityIn.motionX *= 0.4;
        entityIn.motionZ *= 0.4;
    }
}
