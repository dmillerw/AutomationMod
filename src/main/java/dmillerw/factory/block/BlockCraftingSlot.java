package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

            if (held != null && held.getItem() == Items.stick && side != ForgeDirection.UP.ordinal()) {
                if (player.isSneaking()) {
                    tileCraftingSlot.outputSide = ForgeDirection.UNKNOWN;
                } else {
                    tileCraftingSlot.outputSide = ForgeDirection.getOrientation(side);
                }
                tileCraftingSlot.markForUpdate();
                return true;
            }

            if (contents == null && held != null && held.stackSize >= 1) {
                tileCraftingSlot.contents[0] = held.copy();
                tileCraftingSlot.contents[0].stackSize = 1;
                held.stackSize--;

                if (held.stackSize <= 0) {
                    player.setCurrentItemOrArmor(0, null);
                }
            } else if (contents != null) {
                if (held == null) {
                    ItemStack copy = contents.copy();
                    player.setCurrentItemOrArmor(0, copy);
                    tileCraftingSlot.contents[0] = null;
                } else {
                    if (held.isItemEqual(contents)) {
                        if (player.isSneaking()) {
                            if (held.stackSize + 1 <= held.getMaxStackSize()) {
                                tileCraftingSlot.contents[0].stackSize--;
                                held.stackSize++;

                                if (tileCraftingSlot.contents[0].stackSize <= 0) {
                                    tileCraftingSlot.contents[0] = null;
                                }
                            }
                        } else {
                            if (contents.stackSize <= contents.getMaxStackSize()) {
                                tileCraftingSlot.contents[0].stackSize++;
                                held.stackSize--;

                                if (held.stackSize <= 0) {
                                    player.setCurrentItemOrArmor(0, null);
                                }
                            }
                        }
                    }
                }
            }

            tileCraftingSlot.markForUpdate();
        }
        return true;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) world.getTileEntity(x, y, z);
        if (tileCraftingSlot != null && tileCraftingSlot.outputSide != ForgeDirection.UNKNOWN) {
            if (side == tileCraftingSlot.outputSide.ordinal()) {
                return Blocks.gold_block.getIcon(0, 0);
            }
        }
        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.iron_block.getIcon(0, 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCraftingSlot();
    }
}
