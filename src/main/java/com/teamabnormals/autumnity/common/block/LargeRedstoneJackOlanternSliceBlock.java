package com.teamabnormals.autumnity.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class LargeRedstoneJackOlanternSliceBlock extends LargeJackOLanternSliceBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public LargeRedstoneJackOlanternSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		boolean powered = blockstate.getValue(POWERED);
		return blockstate.setValue(LIT, powered || isAnotherSlicePowered(context.getLevel(), blockstate, context.getClickedPos()));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, POWERED, CARVED_SIDE, LIT);
	}
}