package dmillerw.factory.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.factory.lib.ModInfo;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class ModBlocks {

    public static Block craftingSlot;

    public static void initialize() {
        craftingSlot = new BlockCraftingSlot().setBlockName("craftingSlot");
        GameRegistry.registerBlock(craftingSlot, "craftingSlot");
        GameRegistry.registerTileEntity(TileCraftingSlot.class, ModInfo.prefix("craftingSlot"));
    }
}