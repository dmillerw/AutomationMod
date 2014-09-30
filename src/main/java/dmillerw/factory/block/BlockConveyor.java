package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.tile.TileConveyor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockConveyor extends BlockTileCore {

    public BlockConveyor() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileConveyor();
    }
}
