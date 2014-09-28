package dmillerw.factory.block.prefab;

import dmillerw.factory.lib.ModTab;
import dmillerw.factory.tile.prefab.TileCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public abstract class BlockTileCore extends BlockContainer {

    public BlockTileCore(Material material) {
        super(material);

        setCreativeTab(ModTab.TAB);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileCore) {
            ((TileCore) tileEntity).onNeighborChanged();
        }
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileCore) {
            ((TileCore) tileEntity).onBlockBroken();
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);
}
