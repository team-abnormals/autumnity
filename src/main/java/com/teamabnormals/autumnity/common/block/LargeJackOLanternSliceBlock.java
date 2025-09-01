package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class LargeJackOLanternSliceBlock extends AbstractLargePumpkinSliceBlock {
	public static final EnumProperty<CarvedSide> CARVED_SIDE = EnumProperty.create("carved_side", CarvedSide.class);

	public LargeJackOLanternSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CARVED_SIDE, CarvedSide.X));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(CARVED_SIDE, context.getHorizontalDirection().getAxis() == Axis.X ? CarvedSide.X : CarvedSide.Z);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		if (!(oldState.getBlock() instanceof AbstractLargePumpkinSliceBlock) || state.getValue(FACING) != oldState.getValue(FACING) || state.getValue(HALF) != oldState.getValue(HALF) || state.getValue(POWERED) != oldState.getValue(POWERED))
			super.onPlace(state, level, pos, oldState, moving);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
			return super.rotate(state, rot).setValue(CARVED_SIDE, state.getValue(CARVED_SIDE) == CarvedSide.X ? CarvedSide.Z : CarvedSide.X);
		return super.rotate(state, rot);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, POWERED, CARVED_SIDE);
	}
}