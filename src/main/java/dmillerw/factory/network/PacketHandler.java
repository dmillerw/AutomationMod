package dmillerw.factory.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import dmillerw.factory.lib.ModInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

/**
 * @author dmillerw
 */
public class PacketHandler {

    private static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);

    public static void initialize() {

    }

    private static int descriminator = 0;

    /**
     * This method assumes that the same class is used for both the message and the message handler
     */
    /*private static <P extends IMessage & IMessageHandler> void registerMessage(Class<P> clazz, PacketSide side) {
        if (side == PacketSide.CLIENT || side == PacketSide.BOTH) {
            CHANNEL.registerMessage(clazz, clazz, descriminator, Side.CLIENT);
        }
        if (side == PacketSide.SERVER || side == PacketSide.BOTH) {
            CHANNEL.registerMessage(clazz, clazz, descriminator, Side.SERVER);
        }
        descriminator++;
    }*/

    public static void sendToServer(IMessage message) {
        CHANNEL.sendToServer(message);
    }

    public static void sendToPlayer(IMessage message, EntityPlayerMP player) {
        CHANNEL.sendTo(message, player);
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        CHANNEL.sendToAllAround(message, point);
    }

    public static void sendToDimension(IMessage message, int dimension) {
        CHANNEL.sendToDimension(message, dimension);
    }

    public static void sendToAll(IMessage message) {
        CHANNEL.sendToAll(message);
    }

    public static Packet toVanillaPacket(IMessage message) {
        return CHANNEL.getPacketFrom(message);
    }

    private static enum PacketSide {
        CLIENT,
        SERVER,
        BOTH
    }
}
