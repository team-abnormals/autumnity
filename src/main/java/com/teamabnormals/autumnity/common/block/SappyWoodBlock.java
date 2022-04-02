package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.NonNullList;
import net.minecraftforge.registries.RegistryObject;

public class SappyWoodBlock extends SappyLogBlock {
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.STRIPPED_WARPED_HYPHAE);

	public SappyWoodBlock(RegistryObject<Block> saplessBlockIn, Properties properties) {
		super(saplessBlockIn, properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}