package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.client.ClientProxy;
import dmillerw.factory.tile.TileMoving;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class BlockMoving extends BlockTileCore {

    public BlockMoving() {
        super(Material.rock);
    }

    @Override
    public int getRenderType() {
        return ClientProxy.movingRenderId;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMoving();
    }
}
