package dmillerw.factory.block;

import dmillerw.factory.block.prefab.BlockTileCore;
import dmillerw.factory.tile.TileCraftingSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockCraftingSlot extends BlockTileCore {

    public BlockCraftingSlot() {
        super(Material.iron);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!p_149727_1_.isRemote) {
            TileCraftingSlot tileCraftingSlot = (TileCraftingSlot) p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
            p_149727_5_.addChatComponentMessage(new ChatComponentText(String.valueOf(tileCraftingSlot.getType())));
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCraftingSlot();
    }
}
