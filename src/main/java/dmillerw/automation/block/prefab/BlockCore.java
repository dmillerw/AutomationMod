package dmillerw.automation.block.prefab;

import dmillerw.automation.lib.ModTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author dmillerw
 */
public class BlockCore extends Block {

    public BlockCore(Material material) {
        super(material);

        setCreativeTab(ModTab.TAB);
    }
}
