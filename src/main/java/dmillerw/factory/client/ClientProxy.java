package dmillerw.factory.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.factory.client.render.tile.RenderTileCraftingSlot;
import dmillerw.factory.client.render.tile.RenderTileItemPile;
import dmillerw.factory.core.CommonProxy;
import dmillerw.factory.tile.TileCraftingSlot;
import dmillerw.factory.tile.TileItemPile;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientRegistry.bindTileEntitySpecialRenderer(TileCraftingSlot.class, new RenderTileCraftingSlot());
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemPile.class, new RenderTileItemPile());
    }
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
