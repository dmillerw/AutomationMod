package dmillerw.automation;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import dmillerw.automation.core.CommonProxy;
import dmillerw.automation.lib.ModInfo;

/**
 * @author dmillerw
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
public class AutomationMod {

    @Mod.Instance(ModInfo.ID)
    public static AutomationMod instance;

    @SidedProxy(serverSide = ModInfo.SERVER, clientSide = ModInfo.CLIENT)
    public static CommonProxy proxy;
}
