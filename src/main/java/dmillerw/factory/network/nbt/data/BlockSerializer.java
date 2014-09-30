package dmillerw.factory.network.nbt.data;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class BlockSerializer extends AbstractSerializer<Block> {

    @Override
    public boolean canHandle(Class<?> fieldType) {
        return Block.class.isAssignableFrom(fieldType);
    }

    @Override
    public void serialize(String name, Object object, NBTTagCompound nbt) {
        nbt.setInteger(name, Block.getIdFromBlock((Block) object));
    }

    @Override
    public Block deserialize(String name, NBTTagCompound nbt) {
        return Block.getBlockById(nbt.getInteger(name));
    }
}
