package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.helper.WorldHelper;
import dmillerw.factory.lib.ModInfo;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
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

    public static IIcon top;
    public static IIcon bottom;
    public static IIcon[] sides;
    public static IIcon[] out;

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
                if (tileCraftingSlot.outputSide == ForgeDirection.getOrientation(side)) {
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
        if (side == 0 || side == 1) return super.getIcon(world, x, y, z, side);
        TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) world.getTileEntity(x, y, z);
        WorldHelper.BlockPos blockPos = new WorldHelper.BlockPos(x, y, z);
        if (tileCraftingSlot != null) {
            ForgeDirection forgeDirection = ForgeDirection.getOrientation(side);
            if (side == tileCraftingSlot.outputSide.ordinal()) {
                boolean left = WorldHelper.getOffsetBlock(tileCraftingSlot.getWorldObj(), blockPos, forgeDirection.getRotation(ForgeDirection.UP).getOpposite()) == this;
                boolean right = WorldHelper.getOffsetBlock(tileCraftingSlot.getWorldObj(), blockPos, forgeDirection.getRotation(ForgeDirection.UP)) == this;
                if (left && right) return out[2];
                else if (!left && !right) return out[1];
                else if (left) return out[0];
                else if (right) return out[3];
            } else {
                boolean left = WorldHelper.getOffsetBlock(tileCraftingSlot.getWorldObj(), blockPos, forgeDirection.getRotation(ForgeDirection.UP).getOpposite()) == this;
                boolean right = WorldHelper.getOffsetBlock(tileCraftingSlot.getWorldObj(), blockPos, forgeDirection.getRotation(ForgeDirection.UP)) == this;
                if (left && right) return sides[2];
                else if (!left && !right) return sides[1];
                else if (left) return sides[0];
                else if (right) return sides[3];
            }
        }
        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 0) {
            return bottom;
        } else if (side == 1) {
            return top;
        } else {
            return sides[1];
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        top = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/top"));
        bottom = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/bottom"));
        sides = new IIcon[4];
        sides[0] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/side_left"));
        sides[1] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/side_middle"));
        sides[2] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/side_middle_open"));
        sides[3] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/side_right"));
        out = new IIcon[4];
        out[0] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/out_left"));
        out[1] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/out_middle"));
        out[2] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/out_middle_open"));
        out[3] = iconRegister.registerIcon(ModInfo.prefix("craftingSlot/out_right"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCraftingSlot();
    }
}
