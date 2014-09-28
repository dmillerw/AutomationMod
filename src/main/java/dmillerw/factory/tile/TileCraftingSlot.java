package dmillerw.factory.tile;

import dmillerw.factory.block.ModBlocks;
import dmillerw.factory.helper.ArrayHelper;
import dmillerw.factory.helper.WorldHelper;
import dmillerw.factory.network.nbt.NBTHandler;
import dmillerw.factory.tile.prefab.TileCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileCraftingSlot extends TileCore implements IInventory {

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
        if (worldObj.isRemote) return;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            ItemStack[] craftingMatrix = buildMatrix();
            int size = craftingMatrix.length == 9 ? 3 : craftingMatrix.length == 4 ? 2 : 0;
            for (int i=0; i<4; i++) {
                if (tryCrafting(craftingMatrix)) {
                    break;
                } else {
                    craftingMatrix = ArrayHelper.rotateArray(ItemStack.class, craftingMatrix, size);
                }
            }
        }
    }

    private boolean tryCrafting(ItemStack[] craftingMatrix) {
        int size = craftingMatrix.length == 9 ? 3 : craftingMatrix.length == 4 ? 2 : 0;
        InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer p_75145_1_) {
                return true;
            }
        }, size, size);
        for (int i=0; i<craftingMatrix.length; i++) {
            inventoryCrafting.setInventorySlotContents(i, craftingMatrix[i]);
        }
        ItemStack result = CraftingManager.getInstance().findMatchingRecipe(inventoryCrafting, worldObj);
        if (result != null) {
            craftAndClean(result, size);
            return true;
        } else {
            return false;
        }
    }

    private void craftAndClean(ItemStack result, int size) {
        int xCoordinate = xCoord;
        boolean lowestX = false;
        while (!lowestX) {
            if (worldObj.getBlock(xCoordinate, yCoord, zCoord) != ModBlocks.craftingSlot) {
                lowestX = true;
                break;
            }
            xCoordinate--;
        }

        int zCoordinate = zCoord;
        boolean lowestZ = false;
        while (!lowestZ) {
            if (worldObj.getBlock(xCoord, yCoord, zCoordinate) != ModBlocks.craftingSlot) {
                lowestZ = true;
                break;
            }
            zCoordinate--;
        }

        xCoordinate++;
        zCoordinate++;

        boolean finished = false;

        for (int x=0; x<size; x++) {
            for (int z=0; z<size; z++) {
                if (worldObj.getBlock(xCoordinate + x, yCoord, zCoordinate + z) == ModBlocks.craftingSlot) {
                    for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
                        TileEntity tileEntity = WorldHelper.getOffsetTileEntity(worldObj, xCoordinate + x, yCoord, zCoordinate + z, forgeDirection);
                        if (tileEntity != null && !(tileEntity instanceof TileCraftingSlot) && tileEntity instanceof IInventory) {
                            boolean skip = false;
                            // Special check case for hoppers
                            if (tileEntity instanceof TileEntityHopper) {
                                ForgeDirection facing = ForgeDirection.getOrientation(tileEntity.getBlockMetadata());
                                if (WorldHelper.getOffsetBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, facing) == ModBlocks.craftingSlot) {
                                    skip = true;
                                }
                            }
                            if (!finished && !skip && TileEntityHopper.func_145889_a((IInventory) tileEntity, result, forgeDirection.getOpposite().ordinal()) == null) {
                                finished = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (finished) {
            for (int x=0; x<size; x++) {
                for (int z=0; z<size; z++) {
                    if (worldObj.getBlock(xCoordinate + x, yCoord, zCoordinate + z) == ModBlocks.craftingSlot) {
                        TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) worldObj.getTileEntity(xCoordinate + x, yCoord, zCoordinate + z);
                        ItemStack contents = tileCraftingSlot.contents[0];
                        if (contents != null) {
                            contents.stackSize--;
                            if (contents.stackSize <= 0) {
                                tileCraftingSlot.contents[0] = null;
                            }
                            tileCraftingSlot.markForUpdate();
                        }
                    }
                }
            }
        }
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

        if (type == -1) {
            matrix = new ItemStack[0];
            return matrix;
        }

        // First, try and figure out the size of the matrix
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
                            foundCorner2 = true;
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

        // Now that we (maybe) now the size, we try and verify it. We do this by finding the corner closest to low coordinate values
        // Then iterating through the matrix size to check if all the necessary blocks are in place

        // First, we move down as far as we can on the X axis
        int xCoordinate = xCoord;
        boolean lowestX = false;
        while (!lowestX) {
            if (worldObj.getBlock(xCoordinate, yCoord, zCoord) != ModBlocks.craftingSlot) {
                lowestX = true;
                break;
            }
            xCoordinate--;
        }

        // Then we do the same for the Z axis
        int zCoordinate = zCoord;
        boolean lowestZ = false;
        while (!lowestZ) {
            if (worldObj.getBlock(xCoord, yCoord, zCoordinate) != ModBlocks.craftingSlot) {
                lowestZ = true;
                break;
            }
            zCoordinate--;
        }

        // Bump the coordinates back up to actually find the block
        xCoordinate++;
        zCoordinate++;

        for (int x=0; x<size; x++) {
            for (int z=0; z<size; z++) {
                if (worldObj.getBlock(xCoordinate + x, yCoord, zCoordinate + z) != ModBlocks.craftingSlot) {
                    return new ItemStack[0];
                }
            }
        }

        matrix = new ItemStack[size * size];

        for (int x=0; x<size; x++) {
            for (int z=0; z<size; z++) {
                if (worldObj.getBlock(xCoordinate + x, yCoord, zCoordinate + z) == ModBlocks.craftingSlot) {
                    TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) worldObj.getTileEntity(xCoordinate + x, yCoord, zCoordinate + z);
                    matrix[x + z * size] = tileCraftingSlot.contents[0];
                }
            }
        }

        return matrix;
    }


    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return contents[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int size) {
        if (contents[slot] != null) {
            ItemStack itemstack;

            if (contents[slot].stackSize <= size) {
                itemstack = contents[slot];
                contents[slot] = null;
                return itemstack;
            } else {
                itemstack = contents[slot].splitStack(size);

                if (contents[slot].stackSize == 0) {
                    contents[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (contents[slot] != null) {
            ItemStack itemstack = contents[slot];
            contents[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        contents[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }
}
