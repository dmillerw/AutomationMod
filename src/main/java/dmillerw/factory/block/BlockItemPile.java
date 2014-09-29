package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.helper.WorldHelper;
import dmillerw.factory.tile.TileItemPile;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class BlockItemPile extends BlockTileCore {

    public static void placePile(World world, WorldHelper.BlockPos blockPos, ItemStack stack) {
        if (world.isAirBlock(blockPos.x, blockPos.y, blockPos.z)) {
            world.setBlock(blockPos.x, blockPos.y, blockPos.z, ModBlocks.itemPile);
            TileItemPile tileItemPile = (TileItemPile) world.getTileEntity(blockPos.x, blockPos.y, blockPos.z);
            tileItemPile.setInventorySlotContents(0, stack.copy());
        }
    }

    public BlockItemPile() {
        super(Material.rock);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileItemPile();
    }
}
