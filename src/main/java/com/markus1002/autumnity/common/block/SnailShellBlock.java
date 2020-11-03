package com.markus1002.autumnity.common.block;

import javax.annotation.Nullable;

import com.markus1002.autumnity.common.block.properties.SnailShellOrientation;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class SnailShellBlock extends HorizontalBlock
{
	public static final EnumProperty<SnailShellOrientation> ORIENTATION = AutumnityBlockStateProperties.ORIENTATION;

	public SnailShellBlock(AbstractBlock.Properties builder)
	{
		super(builder);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		Direction direction = context.getFace();
		if (direction.getAxis() == Direction.Axis.Y)
		{
			return this.getDefaultState().with(ORIENTATION, direction == Direction.UP ? SnailShellOrientation.DOWN : SnailShellOrientation.UP).with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
		}
		else
		{
			return this.getDefaultState().with(ORIENTATION, SnailShellOrientation.HORIZONTAL).with(HORIZONTAL_FACING, direction.getOpposite());
		}
	}

	protected static Direction getFacing(BlockState state)
	{
		switch((SnailShellOrientation)state.get(ORIENTATION))
		{
		case UP:
			return Direction.UP;
		case DOWN:
			return Direction.DOWN;
		default:
			return state.get(HORIZONTAL_FACING);
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, ORIENTATION);
	}
}