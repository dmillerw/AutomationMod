package dmillerw.factory.block.prefab;

import dmillerw.factory.lib.ModTab;
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
