package dmillerw.factory.helper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class WorldHelper {

    public static class BlockPos {
        public static BlockPos fromTileEntity(TileEntity tileEntity) {
            return new BlockPos(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        }

        public final int x;
        public final int y;
        public final int z;

        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static Block getBlock(World world, BlockPos blockPos) {
        return world.getBlock(blockPos.x, blockPos.y, blockPos.z);
    }

    public static int getBlockMetadata(World world, BlockPos blockPos) {
        return world.getBlockMetadata(blockPos.x, blockPos.y, blockPos.z);
    }

    public static TileEntity getTileEntity(World world, BlockPos blockPos) {
        return world.getTileEntity(blockPos.x, blockPos.y, blockPos.z);
    }

    public static void setBlock(World world, BlockPos blockPos, Block block) {
        world.setBlock(blockPos.x, blockPos.y, blockPos.z, block);
    }

    public static boolean isAir(World world, BlockPos blockPos) {
        return world.isAirBlock(blockPos.x, blockPos.y, blockPos.z);
    }

    public static BlockPos getOffsetPosition(BlockPos blockPos, ForgeDirection forgeDirection) {
        return new BlockPos(blockPos.x + forgeDirection.offsetX, blockPos.y + forgeDirection.offsetY, blockPos.z + forgeDirection.offsetZ);
    }
    
    public static Block getOffsetBlock(World world, BlockPos blockPos, ForgeDirection forgeDirection) {
        return getBlock(world, getOffsetPosition(blockPos, forgeDirection));
    }

    public static int getOffsetBlockMetadata(World world, BlockPos blockPos, ForgeDirection forgeDirection) {
        return getBlockMetadata(world, getOffsetPosition(blockPos, forgeDirection));
    }

    public static TileEntity getOffsetTileEntity(World world, BlockPos blockPos, ForgeDirection forgeDirection) {
        return getTileEntity(world, getOffsetPosition(blockPos, forgeDirection));
    }
    
    public static void setOffsetBlock(World world, BlockPos blockPos, ForgeDirection forgeDirection, Block block) {
        setBlock(world, getOffsetPosition(blockPos, forgeDirection), block);
    }

    public static boolean isOffsetAir(World world, BlockPos blockPos, ForgeDirection forgeDirection) {
        return isAir(world, getOffsetPosition(blockPos, forgeDirection));
    }
}
