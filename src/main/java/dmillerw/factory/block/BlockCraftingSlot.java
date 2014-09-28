package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockCraftingSlot extends BlockTileCore {

    public BlockCraftingSlot() {
        super(Material.iron);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) world.getTileEntity(x, y, z);
            ItemStack held = player.getHeldItem();
            ItemStack contents = tileCraftingSlot.contents[0];

            if (contents == null && held != null && held.stackSize >= 1) {
                tileCraftingSlot.contents[0] = held.copy();
                tileCraftingSlot.contents[0].stackSize = 1;
                held.stackSize--;

                if (held.stackSize <= 0) {
                    player.setCurrentItemOrArmor(0, null);
                }
            } else if (contents != null) {
                if (held == null) {
                    player.setCurrentItemOrArmor(0, contents.copy());
                    tileCraftingSlot.contents[0] = null;
                } else {
                    //TODO Size checks
                    if (player.isSneaking()) {
                        tileCraftingSlot.contents[0].stackSize--;
                        held.stackSize++;

                        if (tileCraftingSlot.contents[0].stackSize <= 0) {
                            tileCraftingSlot.contents[0] = null;
                        }
                    } else {
                        tileCraftingSlot.contents[0].stackSize++;
                        held.stackSize--;

                        if (held.stackSize <= 0) {
                            player.setCurrentItemOrArmor(0, null);
                        }
                    }
                }
            }

            tileCraftingSlot.markForUpdate();
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCraftingSlot();
    }
}
