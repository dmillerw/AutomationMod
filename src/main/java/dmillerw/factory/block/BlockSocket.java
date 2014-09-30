package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.client.ClientProxy;
import dmillerw.factory.tile.TileSocket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class BlockSocket extends BlockTileCore {

    private static final double MIN = 0.15D;
    private static final double MAX = 0.85D;

    public static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {
            AxisAlignedBB.getBoundingBox(0, 0, 0, 1, MIN, 1),
            AxisAlignedBB.getBoundingBox(0, MAX, 0, 1, 1, 1),
            AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, MIN),
            AxisAlignedBB.getBoundingBox(0, 0, MAX, 1, 1, 1),
            AxisAlignedBB.getBoundingBox(0, 0, 0, MIN, 1, 1),
            AxisAlignedBB.getBoundingBox(MAX, 0 ,0, 1, 1, 1)
    };

    public BlockSocket() {
        super(Material.iron);

        setLightOpacity(0);
    }

    @Override
    public int getRenderType() {
        return ClientProxy.socketRenderId;
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
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        AxisAlignedBB aabb = BlockSocket.BOUNDING_BOXES[world.getBlockMetadata(x, y, z)];
        setBlockBounds((float)aabb.minX, (float)aabb.minY, (float)aabb.minZ, (float)aabb.maxX, (float)aabb.maxY, (float)aabb.maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return BlockSocket.BOUNDING_BOXES[world.getBlockMetadata(x, y, z)].getOffsetBoundingBox(x, y, z);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return BlockSocket.BOUNDING_BOXES[world.getBlockMetadata(x, y, z)].getOffsetBoundingBox(x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        ForgeDirection forgeDirection = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
        int attachedX = x + forgeDirection.offsetX;
        int attachedY = y + forgeDirection.offsetY;
        int attachedZ = z + forgeDirection.offsetZ;
        if (world.isAirBlock(attachedX, attachedY, attachedZ) || !world.isSideSolid(attachedX, attachedY, attachedZ, forgeDirection.getOpposite())) {
            dropBlockAsItem(world, x, y, z, new ItemStack(this));
            world.setBlockToAir(x, y, z);
            return;
        }
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float fx, float fy, float fz, int meta) {
        return ForgeDirection.getOrientation(side).getOpposite().ordinal();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSocket();
    }
}
