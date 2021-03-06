package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockFlowerPot extends BlockContainer
{
    public static final PropertyInteger LEGACY_DATA;
    public static final PropertyEnum<EnumFlowerType> CONTENTS;
    
    public BlockFlowerPot() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFlowerPot.CONTENTS, EnumFlowerType.EMPTY).withProperty((IProperty<Comparable>)BlockFlowerPot.LEGACY_DATA, 0));
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("item.flowerPot.name");
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float f = 0.375f;
        final float f2 = f / 2.0f;
        this.setBlockBounds(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, f, 0.5f + f2);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityFlowerPot) {
            final Item item = ((TileEntityFlowerPot)tileentity).getFlowerPotItem();
            if (item instanceof ItemBlock) {
                return Block.getBlockFromItem(item).colorMultiplier(worldIn, pos, renderPass);
            }
        }
        return 16777215;
    }
    
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final ItemStack itemstack = playerIn.inventory.getCurrentItem();
        if (itemstack == null || !(itemstack.getItem() instanceof ItemBlock)) {
            return false;
        }
        final TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        if (tileentityflowerpot == null) {
            return false;
        }
        if (tileentityflowerpot.getFlowerPotItem() != null) {
            return false;
        }
        final Block block = Block.getBlockFromItem(itemstack.getItem());
        if (!this.canNotContain(block, itemstack.getMetadata())) {
            return false;
        }
        tileentityflowerpot.setFlowerPotData(itemstack.getItem(), itemstack.getMetadata());
        tileentityflowerpot.markDirty();
        worldIn.markBlockForUpdate(pos);
        playerIn.triggerAchievement(StatList.field_181736_T);
        if (!playerIn.capabilities.isCreativeMode) {
            final ItemStack itemStack = itemstack;
            if (--itemStack.stackSize <= 0) {
                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
            }
        }
        return true;
    }
    
    private boolean canNotContain(final Block blockIn, final int meta) {
        return blockIn == Blocks.yellow_flower || blockIn == Blocks.red_flower || blockIn == Blocks.cactus || blockIn == Blocks.brown_mushroom || blockIn == Blocks.red_mushroom || blockIn == Blocks.sapling || blockIn == Blocks.deadbush || (blockIn == Blocks.tallgrass && meta == BlockTallGrass.EnumType.FERN.getMeta());
    }
    
    @Override
    public Item getItem(final World worldIn, final BlockPos pos) {
        final TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotItem() : Items.flower_pot;
    }
    
    @Override
    public int getDamageValue(final World worldIn, final BlockPos pos) {
        final TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotData() : 0;
    }
    
    @Override
    public boolean isFlowerPot() {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
    
    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        if (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) {
            Block.spawnAsEntity(worldIn, pos, new ItemStack(tileentityflowerpot.getFlowerPotItem(), 1, tileentityflowerpot.getFlowerPotData()));
        }
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (player.capabilities.isCreativeMode) {
            final TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
            if (tileentityflowerpot != null) {
                tileentityflowerpot.setFlowerPotData(null, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Items.flower_pot;
    }
    
    private TileEntityFlowerPot getTileEntity(final World worldIn, final BlockPos pos) {
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        return (tileentity instanceof TileEntityFlowerPot) ? ((TileEntityFlowerPot)tileentity) : null;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        Block block = null;
        int i = 0;
        switch (meta) {
            case 1: {
                block = Blocks.red_flower;
                i = BlockFlower.EnumFlowerType.POPPY.getMeta();
                break;
            }
            case 2: {
                block = Blocks.yellow_flower;
                break;
            }
            case 3: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.OAK.getMetadata();
                break;
            }
            case 4: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.SPRUCE.getMetadata();
                break;
            }
            case 5: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.BIRCH.getMetadata();
                break;
            }
            case 6: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.JUNGLE.getMetadata();
                break;
            }
            case 7: {
                block = Blocks.red_mushroom;
                break;
            }
            case 8: {
                block = Blocks.brown_mushroom;
                break;
            }
            case 9: {
                block = Blocks.cactus;
                break;
            }
            case 10: {
                block = Blocks.deadbush;
                break;
            }
            case 11: {
                block = Blocks.tallgrass;
                i = BlockTallGrass.EnumType.FERN.getMeta();
                break;
            }
            case 12: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.ACACIA.getMetadata();
                break;
            }
            case 13: {
                block = Blocks.sapling;
                i = BlockPlanks.EnumType.DARK_OAK.getMetadata();
                break;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock(block), i);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFlowerPot.CONTENTS, BlockFlowerPot.LEGACY_DATA });
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue((IProperty<Integer>)BlockFlowerPot.LEGACY_DATA);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        EnumFlowerType blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityFlowerPot) {
            final TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
            final Item item = tileentityflowerpot.getFlowerPotItem();
            if (item instanceof ItemBlock) {
                final int i = tileentityflowerpot.getFlowerPotData();
                final Block block = Block.getBlockFromItem(item);
                if (block == Blocks.sapling) {
                    switch (BlockPlanks.EnumType.byMetadata(i)) {
                        case OAK: {
                            blockflowerpot$enumflowertype = EnumFlowerType.OAK_SAPLING;
                            break;
                        }
                        case SPRUCE: {
                            blockflowerpot$enumflowertype = EnumFlowerType.SPRUCE_SAPLING;
                            break;
                        }
                        case BIRCH: {
                            blockflowerpot$enumflowertype = EnumFlowerType.BIRCH_SAPLING;
                            break;
                        }
                        case JUNGLE: {
                            blockflowerpot$enumflowertype = EnumFlowerType.JUNGLE_SAPLING;
                            break;
                        }
                        case ACACIA: {
                            blockflowerpot$enumflowertype = EnumFlowerType.ACACIA_SAPLING;
                            break;
                        }
                        case DARK_OAK: {
                            blockflowerpot$enumflowertype = EnumFlowerType.DARK_OAK_SAPLING;
                            break;
                        }
                        default: {
                            blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (block == Blocks.tallgrass) {
                    switch (i) {
                        case 0: {
                            blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH;
                            break;
                        }
                        case 2: {
                            blockflowerpot$enumflowertype = EnumFlowerType.FERN;
                            break;
                        }
                        default: {
                            blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (block == Blocks.yellow_flower) {
                    blockflowerpot$enumflowertype = EnumFlowerType.DANDELION;
                }
                else if (block == Blocks.red_flower) {
                    switch (BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, i)) {
                        case POPPY: {
                            blockflowerpot$enumflowertype = EnumFlowerType.POPPY;
                            break;
                        }
                        case BLUE_ORCHID: {
                            blockflowerpot$enumflowertype = EnumFlowerType.BLUE_ORCHID;
                            break;
                        }
                        case ALLIUM: {
                            blockflowerpot$enumflowertype = EnumFlowerType.ALLIUM;
                            break;
                        }
                        case HOUSTONIA: {
                            blockflowerpot$enumflowertype = EnumFlowerType.HOUSTONIA;
                            break;
                        }
                        case RED_TULIP: {
                            blockflowerpot$enumflowertype = EnumFlowerType.RED_TULIP;
                            break;
                        }
                        case ORANGE_TULIP: {
                            blockflowerpot$enumflowertype = EnumFlowerType.ORANGE_TULIP;
                            break;
                        }
                        case WHITE_TULIP: {
                            blockflowerpot$enumflowertype = EnumFlowerType.WHITE_TULIP;
                            break;
                        }
                        case PINK_TULIP: {
                            blockflowerpot$enumflowertype = EnumFlowerType.PINK_TULIP;
                            break;
                        }
                        case OXEYE_DAISY: {
                            blockflowerpot$enumflowertype = EnumFlowerType.OXEYE_DAISY;
                            break;
                        }
                        default: {
                            blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (block == Blocks.red_mushroom) {
                    blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_RED;
                }
                else if (block == Blocks.brown_mushroom) {
                    blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_BROWN;
                }
                else if (block == Blocks.deadbush) {
                    blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH;
                }
                else if (block == Blocks.cactus) {
                    blockflowerpot$enumflowertype = EnumFlowerType.CACTUS;
                }
            }
        }
        return state.withProperty(BlockFlowerPot.CONTENTS, blockflowerpot$enumflowertype);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    static {
        LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
        CONTENTS = PropertyEnum.create("contents", EnumFlowerType.class);
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        EMPTY("empty"), 
        POPPY("rose"), 
        BLUE_ORCHID("blue_orchid"), 
        ALLIUM("allium"), 
        HOUSTONIA("houstonia"), 
        RED_TULIP("red_tulip"), 
        ORANGE_TULIP("orange_tulip"), 
        WHITE_TULIP("white_tulip"), 
        PINK_TULIP("pink_tulip"), 
        OXEYE_DAISY("oxeye_daisy"), 
        DANDELION("dandelion"), 
        OAK_SAPLING("oak_sapling"), 
        SPRUCE_SAPLING("spruce_sapling"), 
        BIRCH_SAPLING("birch_sapling"), 
        JUNGLE_SAPLING("jungle_sapling"), 
        ACACIA_SAPLING("acacia_sapling"), 
        DARK_OAK_SAPLING("dark_oak_sapling"), 
        MUSHROOM_RED("mushroom_red"), 
        MUSHROOM_BROWN("mushroom_brown"), 
        DEAD_BUSH("dead_bush"), 
        FERN("fern"), 
        CACTUS("cactus");
        
        private final String name;
        
        private EnumFlowerType(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
