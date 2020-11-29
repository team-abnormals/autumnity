package com.minecraftabnormals.autumnity.common.block;

import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.RegistryObject;

public class SappyWoodBlock extends SappyLogBlock
{
	public SappyWoodBlock(RegistryObject<Block> saplessBlockIn, Properties properties)
	{
		super(saplessBlockIn, properties);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(ItemStackUtils.isInGroup(this.asItem(), group))
		{
			int targetIndex = ItemStackUtils.findIndexOfItem(Items.STRIPPED_WARPED_HYPHAE, items);
			if(targetIndex != -1)
			{
				items.add(targetIndex + 1, new ItemStack(this));
			}
			else
			{
				super.fillItemGroup(group, items);
			}
		}
	}
}