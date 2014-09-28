package dmillerw.automation.tile;

import dmillerw.automation.block.ModBlocks;
import dmillerw.automation.helper.WorldHelper;
import dmillerw.automation.network.nbt.NBTHandler;
import dmillerw.automation.tile.prefab.TileCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileCraftingSlot extends TileCore {

    private static final ForgeDirection[] SIDES = new ForgeDirection[] {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST};

    public static final byte CORNER = 0;
    public static final byte SIDE = 1;
    public static final byte CENTER = 2;

    @NBTHandler.NBTData
    @NBTHandler.DescriptionData
    @NBTHandler.ArraySize(1)
    public ItemStack[] contents = new ItemStack[1];

    @Override
    public void onNeighborChanged() {
        tryCrafting();
    }

    private void tryCrafting() {
        ItemStack[] craftingMatrix = buildMatrix();
    }

    public byte getType() {
        int temp = 0;
        for (ForgeDirection forgeDirection : SIDES) {
            Block block = WorldHelper.getOffsetBlock(worldObj, xCoord, yCoord, zCoord, forgeDirection);
            if (block == ModBlocks.craftingSlot) {
                temp++;
            }
        }

        if (temp == 2) { // If there's only two adjacent blocks, we're a corner piece
            return CORNER;
        } else if (temp == 3) { // If there's three, we're a side piece (impossible with 2x2 grids)
            return SIDE;
        } else if (temp == 4) { // If there's four, we're a center piece (again, impossible with 2x2 grids)
            return CENTER;
        } else {
            return -1;
        }
    }

    private ItemStack[] buildMatrix() {
        ItemStack[] matrix;

        int size = 0;
        byte type = getType();

        // First, try and figure out where we are in the matrix
        if (type == CORNER) {
            // If we're a corner, we can easily determine if we're a 2x2 or a 3x3
            for (ForgeDirection forgeDirection : SIDES) {
                Block block = WorldHelper.getOffsetBlock(worldObj, xCoord, yCoord, zCoord, forgeDirection);
                if (block == ModBlocks.craftingSlot) {
                    TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) WorldHelper.getOffsetTileEntity(worldObj, xCoord, yCoord, zCoord, forgeDirection);
                    byte offsetType = tileCraftingSlot.getType();

                    if (offsetType == CORNER) {
                        size = 2;
                    } else if (offsetType == SIDE) {
                        size = 3;
                    }
                }
            }
        } else if (type == SIDE) {
            // If we're an edge, we can only ever be a 3x3
            boolean foundCorner1 = false;
            boolean foundCorner2 = false;
            boolean foundCenter = false;

            for (ForgeDirection forgeDirection : SIDES) {
                Block block = WorldHelper.getOffsetBlock(worldObj, xCoord, yCoord, zCoord, forgeDirection);
                if (block == ModBlocks.craftingSlot) {
                    TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) WorldHelper.getOffsetTileEntity(worldObj, xCoord, yCoord, zCoord, forgeDirection);
                    byte offsetType = tileCraftingSlot.getType();

                    if (offsetType == CORNER) {
                        if (!foundCorner1) {
                            foundCorner1 = true;
                        } else {
                            foundCorner2 = true;;
                        }
                    } else if (offsetType == CENTER) {
                        foundCenter = true;
                    }
                }
            }

            if (foundCorner1 && foundCorner2 && foundCenter) {
                size = 3;
            }
        }

        System.out.println(size);

        matrix = new ItemStack[size * size];

        return matrix;
    }
}
