package dmillerw.automation.helper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class WorldHelper {

    public static Block getOffsetBlock(World world, int x, int y, int z, ForgeDirection offset) {
        return world.getBlock(x + offset.offsetX, y + offset.offsetY, z + offset.offsetZ);
    }

    public static int getOffsetBlockMetadata(World world, int x, int y, int z, ForgeDirection offset) {
        return world.getBlockMetadata(x + offset.offsetX, y + offset.offsetY, z + offset.offsetZ);
    }

    public static TileEntity getOffsetTileEntity(World world, int x, int y, int z, ForgeDirection offset) {
        return world.getTileEntity(x + offset.offsetX, y + offset.offsetY, z + offset.offsetZ);
    }
}
