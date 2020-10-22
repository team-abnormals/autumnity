package com.markus1002.autumnity.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PancakeBlock extends Block
{
	public static final IntegerProperty PANCAKES = IntegerProperty.create("pancakes", 1, 12);
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D)};

	public PancakeBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(PANCAKES, Integer.valueOf(2)));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPES[state.get(PANCAKES) - 1];
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if (blockstate.getBlock() == this)
		{
			return blockstate.with(PANCAKES, Integer.valueOf(Math.min(12, blockstate.get(PANCAKES) + 2)));
		}
		else
		{
			return super.getStateForPlacement(context);
		}
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack itemstack = player.getHeldItem(handIn);
		if (worldIn.isRemote)
		{
			if (this.eatCake(worldIn, pos, state, player, itemstack) == ActionResultType.SUCCESS)
			{
				return ActionResultType.SUCCESS;
			}

			if (itemstack.isEmpty())
			{
				return ActionResultType.CONSUME;
			}
		}

		return this.eatCake(worldIn, pos, state, player, itemstack);
	}

	private ActionResultType eatCake(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemstack)
	{
		int i = state.get(PANCAKES);
		if (!player.canEat(false) || (i < 11 && itemstack.getItem() == this.asItem()))
		{
			return ActionResultType.PASS;
		}
		else
		{
			ItemStack stack = this.getPickBlock(state, null, worldIn, pos, player);
			player.playSound(player.getEatSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
			player.getFoodStats().addStats(4, 0.3F);
			if (i > 1)
			{
				worldIn.setBlockState(pos, state.with(PANCAKES, Integer.valueOf(i - 1)), 3);
			}
			else
			{
				worldIn.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		}
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		return useContext.getItem().getItem() == this.asItem() && state.get(PANCAKES) < 11 ? true : super.isReplaceable(state, useContext);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PANCAKES);
	}

	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return !state.getCollisionShape(worldIn, pos).project(Direction.UP).isEmpty();
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return false;
	}
	
	public boolean hasComparatorInputOverride(BlockState state)
	{
		return true;
	}

	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.get(PANCAKES);
	}
}