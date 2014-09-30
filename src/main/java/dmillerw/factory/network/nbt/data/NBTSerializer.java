package dmillerw.factory.network.nbt.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class NBTSerializer extends AbstractSerializer<NBTBase> {

    @Override
    public boolean canHandle(Class<?> fieldType) {
        return NBTBase.class.isAssignableFrom(fieldType);
    }

    @Override
    public void serialize(String name, Object object, NBTTagCompound nbt) {
        nbt.setTag(name, (NBTBase) object);
    }

    @Override
    public NBTBase deserialize(String name, NBTTagCompound nbt) {
        return nbt.getTag(name);
    }
}
