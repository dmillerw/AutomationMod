package dmillerw.automation.lib;

import net.minecraft.util.ResourceLocation;

/**
 * @author dmillerw
 */
public class ModInfo {

    public static final String ID = "AutomationMod";
    public static final String NAME = "AutomationMod";
    public static final String VERSION = "%MOD_VERSION%";
    public static final String DEPENDENCIES = "required-after:Forge@[%FORGE_VERSION%,)";

    public static final String CLIENT = "dmillerw.automation.client.ClientProxy";
    public static final String SERVER = "dmillerw.automation.core.CommonProxy";

    public static final String RESOURCE_PREFIX = "automation:";

    public static ResourceLocation getResource(String resource) {
        return new ResourceLocation(RESOURCE_PREFIX + resource);
    }
}
