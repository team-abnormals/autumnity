package com.teamabnormals.autumnity.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BerryPipsBlockItem extends ItemNameBlockItem {

	public BerryPipsBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> item) {
		if (this.allowedIn(tab)) {
			item.add(new ItemStack(this));
		}
	}
}