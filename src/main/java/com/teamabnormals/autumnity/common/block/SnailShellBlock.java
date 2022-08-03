package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.SnailShellOrientation;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class SnailShellBlock extends HorizontalDirectionalBlock {
	public static final EnumProperty<SnailShellOrientation> ORIENTATION = EnumProperty.create("orientation", SnailShellOrientation.class);

	public SnailShellBlock(BlockBehaviour.Properties builder) {
		super(builder);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return this.defaultBlockState().setValue(ORIENTATION, direction == Direction.UP ? SnailShellOrientation.DOWN : SnailShellOrientation.UP).setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return this.defaultBlockState().setValue(ORIENTATION, SnailShellOrientation.HORIZONTAL).setValue(FACING, direction.getOpposite());
		}
	}

	protected static Direction getFacing(BlockState state) {
		return switch (state.getValue(ORIENTATION)) {
			case UP -> Direction.UP;
			case DOWN -> Direction.DOWN;
			default -> state.getValue(FACING);
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, ORIENTATION);
	}
}