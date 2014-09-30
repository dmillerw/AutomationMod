package dmillerw.factory.tile.prefab;

import dmillerw.factory.helper.InventoryHelper;
import dmillerw.factory.network.nbt.NBTHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCore extends TileEntity {

    protected NBTHandler nbtHandler;

    public TileCore() {
        nbtHandler = new NBTHandler(this, TileCore.class, true);
    }

    public void onBlockBroken() {
        if (this instanceof IInventory) {
            InventoryHelper.dropInventory((IInventory) this, worldObj, xCoord, yCoord, zCoord);
        }
    }

    public void onNeighborChanged() {

    }

    public void markForUpdate() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        nbtHandler.readAllFromNBT(nbtTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtHandler.writeAllToNBT(nbtTagCompound);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbtHandler.writeDescriptionToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        nbtHandler.readDescriptionFromNBT(pkt.func_148857_g());
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
}