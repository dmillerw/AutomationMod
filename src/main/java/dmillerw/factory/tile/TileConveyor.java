package dmillerw.factory.tile;

import dmillerw.factory.block.ModBlocks;
import dmillerw.factory.network.nbt.NBTHandler;
import dmillerw.factory.tile.prefab.TileCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileConveyor extends TileCore {

    @NBTHandler.NBTData(true)
    public ForgeDirection orientation = ForgeDirection.UNKNOWN;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0) {
            Block block = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
            int meta = worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord);
            if (block != null && block != Blocks.air && block != ModBlocks.moving) {
                worldObj.setBlock(xCoord, yCoord + 1, zCoord, ModBlocks.moving, meta, 3);
                TileMoving tileMoving = (TileMoving) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
                if (tileMoving != null) {
                    tileMoving.block = block;
                    tileMoving.meta = meta;
                    tileMoving.markDirty();
                    tileMoving.markForUpdate();
                }
            }
        }
    }
}
