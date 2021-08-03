package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
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

import java.util.function.Predicate;

public class AutumnityJackOLanternBlock extends CarvedPumpkinBlock {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.JACK_O_LANTERN);

	private static final Predicate<BlockState> IS_PUMPKIN = (state) -> {
		return state != null && (state.is(AutumnityBlocks.SOUL_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.ENDER_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.CURSED_JACK_O_LANTERN.get()));
	};

	public AutumnityJackOLanternBlock(Properties properties) {
		super(properties);
	}

	protected BlockPattern getOrCreateSnowGolemFull() {
		if (this.snowGolemFull == null) {
			this.snowGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemFull;
	}

	protected BlockPattern getOrCreateIronGolemFull() {
		if (this.ironGolemFull == null) {
			this.ironGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
		}

		return this.ironGolemFull;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}