package dmillerw.factory.block.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class ItemBlockVerify extends ItemBlock {

    public ItemBlockVerify(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (result) {
            ForgeDirection forgeDirection = ForgeDirection.getOrientation(side).getOpposite();
            int originX = x + forgeDirection.offsetX;
            int originY = y + forgeDirection.offsetY;
            int originZ = z + forgeDirection.offsetZ;
            if (!world.isSideSolid(originX, originY, originZ, forgeDirection.getOpposite())) {
                return false;
            }
        }
        return result;
    }
}
