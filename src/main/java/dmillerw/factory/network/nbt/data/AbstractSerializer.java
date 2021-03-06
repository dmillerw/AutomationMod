package dmillerw.factory.network.nbt.data;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * @author dmillerw
 */
public abstract class AbstractSerializer<T> {

    public static List<AbstractSerializer<?>> serializerList = Lists.newArrayList();

    static {
        serializerList.add(new FluidStackSerializer());
        serializerList.add(new ItemStackSerializer());
        serializerList.add(new BlockSerializer());
        serializerList.add(new NBTSerializer());
    }

    public abstract boolean canHandle(Class<?> fieldType);

    public abstract void serialize(String name, Object object, NBTTagCompound nbt);

    public abstract T deserialize(String name, NBTTagCompound nbt);

    public void serializeDescription(String name, Object object, NBTTagCompound nbtTagCompound) {
        serialize(name, object, nbtTagCompound);
    }

    public T deserializeDescription(String name, NBTTagCompound nbtTagCompound) {
        return deserialize(name, nbtTagCompound);
    }
}