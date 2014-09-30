package dmillerw.factory.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import dmillerw.factory.client.ClientProxy;
import dmillerw.factory.tile.TileMoving;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

/**
 * @author dmillerw
 */
public class RenderBlockMoving implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        TileMoving tileMoving = (TileMoving) world.getTileEntity(x, y, z);
        if (tileMoving != null) {
            try {
                renderer.renderBlockByRenderType(tileMoving.block, x, y, z);
            } catch (Exception ex) {
                renderer.renderStandardBlock(Blocks.stone, x, y, z);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.movingRenderId;
    }
}
