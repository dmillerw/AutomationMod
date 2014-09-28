package dmillerw.factory.tile;

import dmillerw.factory.network.nbt.NBTHandler;
import dmillerw.factory.tile.prefab.TileCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class TileItemPile extends TileCore implements IInventory {

    @NBTHandler.NBTData
    @NBTHandler.DescriptionData
    @NBTHandler.ArraySize(1)
    public ItemStack[] contents = new ItemStack[1];

    @Override
    public void markDirty() {
        super.markDirty();
        markForUpdate();
    }

    private void update() {
        if (contents[0] == null || contents[0].stackSize <= 0) {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public boolean canUpdate() {
        return false;
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
                update();
                return itemstack;
            } else {
                itemstack = contents[slot].splitStack(size);
                if (contents[slot].stackSize == 0) {
                    contents[slot] = null;
                }
                update();
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
            update();
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
        update();
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
