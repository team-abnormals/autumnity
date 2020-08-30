package com.markus1002.autumnity.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class LargePumpkinSliceBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public LargePumpkinSliceBlock(Properties properties)
	{
		super(properties);
	      this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(HALF, Half.BOTTOM));
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, HALF);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();
		BlockState bottomblock = context.getWorld().getBlockState(blockpos.down());
		BlockState topblock = context.getWorld().getBlockState(blockpos.up());

		if (bottomblock.isIn(this) && bottomblock.get(HALF) == Half.BOTTOM)
		{
			return this.getDefaultState().with(FACING, bottomblock.get(FACING)).with(HALF, Half.TOP);
		}
		else if (topblock.isIn(this) && topblock.get(HALF) == Half.TOP)
		{
			return this.getDefaultState().with(FACING, topblock.get(FACING)).with(HALF, Half.BOTTOM);
		}
		
		return this.getDefaultState().with(FACING, getFacing(context)).with(HALF, MathHelper.sin(context.getPlayer().getPitch(1.0F) * ((float)Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP);
	}

	private static Direction getFacing(BlockItemUseContext context)
	{
		float f = MathHelper.wrapDegrees(context.getPlacementYaw());
		Direction direction = context.getPlacementHorizontalFacing();
		
		if (direction == Direction.SOUTH && f > 0)
		{
			return Direction.WEST;
		}
		else if (direction == Direction.WEST && f > 90)
		{
			return Direction.NORTH;
		}
		else if (direction == Direction.NORTH && f < 0)
		{
			return Direction.EAST;
		}
		else if (direction == Direction.EAST && f > -90)
		{
			return Direction.SOUTH;
		}
		
		return direction;
	}
	
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}