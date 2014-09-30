package dmillerw.factory.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.factory.block.item.ItemBlockConveyor;
import dmillerw.factory.lib.ModInfo;
import dmillerw.factory.tile.*;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class ModBlocks {

    public static Block craftingSlot;
    public static Block itemPile;
    public static Block conveyor;
    public static Block moving;
    public static Block socket;

    public static void initialize() {
        craftingSlot = new BlockCraftingSlot().setBlockName("craftingSlot");
        GameRegistry.registerBlock(craftingSlot, "craftingSlot");
        GameRegistry.registerTileEntity(TileCraftingSlot.class, ModInfo.prefix("craftingSlot"));

        itemPile = new BlockItemPile().setBlockName("itemPile");
        GameRegistry.registerBlock(itemPile, "itemPile");
        GameRegistry.registerTileEntity(TileItemPile.class, ModInfo.prefix("itemPile"));

        conveyor = new BlockConveyor().setBlockName("conveyor");
        GameRegistry.registerBlock(conveyor, ItemBlockConveyor.class, "conveyor");
        GameRegistry.registerTileEntity(TileConveyor.class, ModInfo.prefix("conveyor"));

        moving = new BlockMoving().setBlockName("moving");
        GameRegistry.registerBlock(moving, "moving");
        GameRegistry.registerTileEntity(TileMoving.class, ModInfo.prefix("moving"));

        socket = new BlockSocket().setBlockName("socket");
        GameRegistry.registerBlock(socket, "socket");
        GameRegistry.registerTileEntity(TileSocket.class, ModInfo.prefix("socket"));
    }
}
