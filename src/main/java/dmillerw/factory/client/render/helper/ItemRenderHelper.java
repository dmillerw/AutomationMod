package dmillerw.factory.client.render.helper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author dmillerw
 */
public class ItemRenderHelper {

    private static final RenderItem renderItem = new RenderItem();
    private static final RenderBlocks renderBlocks = new RenderBlocks();

    public static void renderFlatItem(ItemStack itemstack, World world) {
        if (itemstack == null || world == null) return;

        int br = 16 << 20 | 16 << 4;
        int x = br % 65536;
        int y = br / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, x * 0.8F, y * 0.8F);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable( GL12.GL_RESCALE_NORMAL );
        Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);

        if (itemstack != null) {
            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, itemstack);
            entityitem.getEntityItem().stackSize = 1;

            entityitem.hoverStart = 0;
            entityitem.age = 0;
            entityitem.rotationYaw = 0;

            GL11.glPushMatrix();
            GL11.glTranslatef(0, -0.04F, 0);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (itemstack.isItemEnchanted() || itemstack.getItem().requiresMultipleRenderPasses()) {
                GL11.glTranslatef(0.0f, -0.05f, -0.25f);
                GL11.glScalef(1.0f / 1.5f, 1.0f / 1.5f, 1.0f / 1.5f);
                GL11.glScalef(1.0f, -1.0f, 0.005f);

                Block block = Block.getBlockFromItem(itemstack.getItem());
                if ((itemstack.getItemSpriteNumber() == 0 && block != null && RenderBlocks.renderItemIn3d(block.getRenderType()))) {
                    GL11.glRotatef(25.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(15.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
                }

                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.ENTITY);
                if (customRenderer != null && !(itemstack.getItem() instanceof ItemBlock)) {
                    if (customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.ENTITY, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D)) {
                        GL11.glTranslatef(0, -0.04F, 0);
                        GL11.glScalef(0.7f, 0.7f, 0.7f);
                        GL11.glRotatef(35, 1, 0, 0);
                        GL11.glRotatef(45, 0, 1, 0);
                        GL11.glRotatef(-90, 0, 1, 0);
                    }
                } else if (itemstack.getItem() instanceof ItemBlock) {
                    GL11.glTranslatef(0, -0.04F, 0);
                    GL11.glScalef(1.1f, 1.1f, 1.1f);
                    GL11.glRotatef(-90, 0, 1, 0);
                } else {
                    GL11.glTranslatef(0, -0.14F, 0);
                    GL11.glScalef(0.8f, 0.8f, 0.8f);
                }

                RenderItem.renderInFrame = true;
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
            } else {
                GL11.glScalef(1.0f / 42.0f, 1.0f / 42.0f, 1.0f / 42.0f);
                GL11.glTranslated(-8.0, -10.2, -10.4);
                GL11.glScalef(1.0f, 1.0f, 0.005f);

                RenderItem.renderInFrame = false;
                FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                if (!ForgeHooksClient.renderInventoryItem(renderBlocks, Minecraft.getMinecraft().renderEngine, itemstack, true, 0, (float) 0, (float) 0)) {
                    renderItem.renderItemIntoGUI(fr, Minecraft.getMinecraft().renderEngine, itemstack, 0, 0, false);
                }
            }

            GL11.glPopMatrix();
        }
    }
}
