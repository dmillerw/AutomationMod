package dmillerw.factory.lib;

import net.minecraft.util.ResourceLocation;

/**
 * @author dmillerw
 */
public class ModInfo {

    public static final String ID = "FactoryWorks";
    public static final String NAME = "FactoryWorks";
    public static final String VERSION = "%MOD_VERSION%";
    public static final String DEPENDENCIES = "required-after:Forge@[%FORGE_VERSION%,)";

    public static final String CLIENT = "dmillerw.factory.client.ClientProxy";
    public static final String SERVER = "dmillerw.factory.core.CommonProxy";

    public static final String RESOURCE_PREFIX = "factoryworks:";

    public static ResourceLocation getResource(String resource) {
        return new ResourceLocation(prefix(resource));
    }

    public static String prefix(String str) {
        return RESOURCE_PREFIX + str;
    }
}
