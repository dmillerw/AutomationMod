package dmillerw.factory.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import dmillerw.factory.block.BlockSocket;
import dmillerw.factory.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * @author dmillerw
 */
public class RenderBlockSocket implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon icon = Blocks.stone.getIcon(0, 0);

        renderer.setRenderBounds(0, 0, 0, 1, 0.15, 1);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 1, 0);
        renderer.renderFaceYPos(block, -0.5, -0.5, -0.5, icon);
        tessellator.setNormal(0, -1, 0);
        renderer.renderFaceYNeg(block, -0.5, -0.5, -0.5, icon);
        tessellator.setNormal(1, 0, 0);
        renderer.renderFaceXPos(block, -0.5, -0.5, -0.5, icon);
        tessellator.setNormal(-1, 0, 0);
        renderer.renderFaceXNeg(block, -0.5, -0.5, -0.5, icon);
        tessellator.setNormal(0, 0, 1);
        renderer.renderFaceZPos(block, -0.5, -0.5, -0.5, icon);
        tessellator.setNormal(0, 0, -1);
        renderer.renderFaceZNeg(block, -0.5, -0.5, -0.5, icon);
        tessellator.draw();

        renderer.setRenderBoundsFromBlock(block);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        renderer.setOverrideBlockTexture(Blocks.stone.getIcon(0, 0));

        AxisAlignedBB aabb = BlockSocket.BOUNDING_BOXES[world.getBlockMetadata(x, y, z)];
        renderer.setRenderBounds(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);

        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBoundsFromBlock(block);
        renderer.clearOverrideBlockTexture();

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.socketRenderId;
    }
}
