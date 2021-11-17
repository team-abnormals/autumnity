package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.SnailShellOrientation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class SnailShellBlock extends HorizontalBlock {
	public static final EnumProperty<SnailShellOrientation> ORIENTATION = AutumnityBlockStateProperties.ORIENTATION;

	public SnailShellBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getNearestLookingDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return this.defaultBlockState().setValue(ORIENTATION, direction == Direction.UP ? SnailShellOrientation.DOWN : SnailShellOrientation.UP).setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return this.defaultBlockState().setValue(ORIENTATION, SnailShellOrientation.HORIZONTAL).setValue(FACING, direction.getOpposite());
		}
	}

	protected static Direction getFacing(BlockState state) {
		switch (state.getValue(ORIENTATION)) {
			case UP:
				return Direction.UP;
			case DOWN:
				return Direction.DOWN;
			default:
				return state.getValue(FACING);
		}
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, ORIENTATION);
	}
}