package com.minecraftabnormals.autumnity.common.block;

import java.util.function.Predicate;

import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.NonNullList;

public class AutumnityJackOLanternBlock extends CarvedPumpkinBlock
{
	private static final Predicate<BlockState> IS_PUMPKIN = (state) -> {
		return state != null && (state.isIn(AutumnityBlocks.SOUL_JACK_O_LANTERN.get()) || state.isIn(AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get()) || state.isIn(AutumnityBlocks.ENDER_JACK_O_LANTERN.get()));
	};

	public AutumnityJackOLanternBlock(Properties properties)
	{
		super(properties);
	}
	
	protected BlockPattern getSnowmanPattern()
	{
		if (this.snowmanPattern == null)
		{
			this.snowmanPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowmanPattern;
	}
	
	protected BlockPattern getGolemPattern()
	{
		if (this.golemPattern == null)
		{
			this.golemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
		}

		return this.golemPattern;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(ItemStackUtils.isInGroup(this.asItem(), group))
		{
			int targetIndex = ItemStackUtils.findIndexOfItem(Items.JACK_O_LANTERN, items);
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