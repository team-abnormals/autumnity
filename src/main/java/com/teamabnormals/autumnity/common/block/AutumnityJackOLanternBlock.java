package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.function.Predicate;

public class AutumnityJackOLanternBlock extends CarvedPumpkinBlock {

	private static final Predicate<BlockState> IS_PUMPKIN = (state) -> state != null && (state.is(AutumnityBlocks.SOUL_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.ENDER_JACK_O_LANTERN.get()) || state.is(AutumnityBlocks.CUPRIC_JACK_O_LANTERN.get()));

	public AutumnityJackOLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected BlockPattern getOrCreateSnowGolemFull() {
		if (this.snowGolemFull == null) {
			this.snowGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', BlockInWorld.hasState(IS_PUMPKIN)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemFull;
	}

	@Override
	protected BlockPattern getOrCreateIronGolemFull() {
		if (this.ironGolemFull == null) {
			this.ironGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(IS_PUMPKIN)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', block -> block.getState().isAir()).build();
		}

		return this.ironGolemFull;
	}
}