package dmillerw.factory.tile;

import dmillerw.factory.network.nbt.NBTHandler;
import dmillerw.factory.tile.prefab.TileCore;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

/**
 * @author dmillerw
 */
public class TileMoving extends TileCore {

    @NBTHandler.NBTData(true)
    public Block block;

    @NBTHandler.NBTData(true)
    public int meta;

    @NBTHandler.NBTData(false)
    public NBTTagCompound nbtTagCompound;

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
    }
}
