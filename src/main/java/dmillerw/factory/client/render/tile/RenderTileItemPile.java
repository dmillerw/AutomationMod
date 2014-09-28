package dmillerw.factory.client.render.tile;

import dmillerw.factory.tile.TileItemPile;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderTileItemPile extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
        TileItemPile tileItemPile = (TileItemPile) tile;
        ItemStack contents = tileItemPile.contents[0];
        if (contents != null) {
            EntityItem entityItem = new EntityItem(tile.getWorldObj());
            entityItem.setEntityItemStack(contents);
            entityItem.hoverStart = 0;

            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y, z + 0.5);
            RenderManager.instance.renderEntityWithPosYaw(entityItem, 0, 0, 0, 0, 0);
            GL11.glPopMatrix();
        }
    }
}
