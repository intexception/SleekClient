package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import optifine.*;
import java.util.*;

public class BlockLeavesBase extends Block
{
    protected boolean fancyGraphics;
    private static final String __OBFID = "CL_00000326";
    private static Map mapOriginalOpacity;
    
    protected BlockLeavesBase(final Material materialIn, final boolean fancyGraphics) {
        super(materialIn);
        this.fancyGraphics = fancyGraphics;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return (!Config.isCullFacesLeaves() || worldIn.getBlockState(pos).getBlock() != this) && super.shouldSideBeRendered(worldIn, pos, side);
    }
    
    public static void setLightOpacity(final Block p_setLightOpacity_0_, final int p_setLightOpacity_1_) {
        if (!BlockLeavesBase.mapOriginalOpacity.containsKey(p_setLightOpacity_0_)) {
            BlockLeavesBase.mapOriginalOpacity.put(p_setLightOpacity_0_, p_setLightOpacity_0_.getLightOpacity());
        }
        p_setLightOpacity_0_.setLightOpacity(p_setLightOpacity_1_);
    }
    
    public static void restoreLightOpacity(final Block p_restoreLightOpacity_0_) {
        if (BlockLeavesBase.mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
            final int i = BlockLeavesBase.mapOriginalOpacity.get(p_restoreLightOpacity_0_);
            setLightOpacity(p_restoreLightOpacity_0_, i);
        }
    }
    
    static {
        BlockLeavesBase.mapOriginalOpacity = new IdentityHashMap();
    }
}
