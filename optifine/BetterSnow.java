package optifine;

import net.minecraft.client.resources.model.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer;
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
    }
    
    public static IBakedModel getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static IBlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }
    
    public static boolean shouldRender(final IBlockAccess p_shouldRender_0_, final Block p_shouldRender_1_, final IBlockState p_shouldRender_2_, final BlockPos p_shouldRender_3_) {
        return checkBlock(p_shouldRender_1_, p_shouldRender_2_) && hasSnowNeighbours(p_shouldRender_0_, p_shouldRender_3_);
    }
    
    private static boolean hasSnowNeighbours(final IBlockAccess p_hasSnowNeighbours_0_, final BlockPos p_hasSnowNeighbours_1_) {
        final Block block = Blocks.snow_layer;
        return (p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.north()).getBlock() == block || p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.south()).getBlock() == block || p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.west()).getBlock() == block || p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.east()).getBlock() == block) && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.down()).getBlock().isOpaqueCube();
    }
    
    private static boolean checkBlock(final Block p_checkBlock_0_, final IBlockState p_checkBlock_1_) {
        if (p_checkBlock_0_.isFullCube()) {
            return false;
        }
        if (p_checkBlock_0_.isOpaqueCube()) {
            return false;
        }
        if (p_checkBlock_0_ instanceof BlockSnow) {
            return false;
        }
        if (p_checkBlock_0_ instanceof BlockBush && (p_checkBlock_0_ instanceof BlockDoublePlant || p_checkBlock_0_ instanceof BlockFlower || p_checkBlock_0_ instanceof BlockMushroom || p_checkBlock_0_ instanceof BlockSapling || p_checkBlock_0_ instanceof BlockTallGrass)) {
            return true;
        }
        if (p_checkBlock_0_ instanceof BlockFence || p_checkBlock_0_ instanceof BlockFenceGate || p_checkBlock_0_ instanceof BlockFlowerPot || p_checkBlock_0_ instanceof BlockPane || p_checkBlock_0_ instanceof BlockReed || p_checkBlock_0_ instanceof BlockWall) {
            return true;
        }
        if (p_checkBlock_0_ instanceof BlockRedstoneTorch && p_checkBlock_1_.getValue((IProperty<Comparable>)BlockTorch.FACING) == EnumFacing.UP) {
            return true;
        }
        if (p_checkBlock_0_ instanceof BlockLever) {
            final Object object = p_checkBlock_1_.getValue(BlockLever.FACING);
            if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z) {
                return true;
            }
        }
        return false;
    }
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
}
