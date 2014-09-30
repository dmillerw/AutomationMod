package dmillerw.factory.block.item;

import dmillerw.factory.helper.EntityHelper;
import dmillerw.factory.tile.TileConveyor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemBlockConveyor extends ItemBlock {

    public ItemBlockConveyor(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (result) {
            TileConveyor tileConveyor = (TileConveyor) world.getTileEntity(x, y, z);
            if (tileConveyor != null) {
                tileConveyor.orientation = EntityHelper.get2DRotation(player);
            }
        }
        return result;
    }
}
