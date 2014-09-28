package dmillerw.factory.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Locale;

/**
 * @author dmillerw
 */
public class ModTab extends CreativeTabs {

    public static final ModTab TAB = new ModTab();

    public ModTab() {
        super(ModInfo.ID.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public Item getTabIconItem() {
        return Items.stick;
    }
}