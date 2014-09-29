package dmillerw.factory.client.render.tile;

import dmillerw.factory.client.render.helper.ItemRenderHelper;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderTileCraftingSlot extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partial) {
        TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) tileEntity;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x + 0.5F, y + 0.765, z + 0.6125F);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

        ItemRenderHelper.renderFlatItem(tileCraftingSlot.contents[0], tileCraftingSlot.getWorldObj());

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
