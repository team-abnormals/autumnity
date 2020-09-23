package com.markus1002.autumnity.common.block;

import javax.annotation.Nullable;

import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.other.AutumnityTags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SnailSlimeFullBlock extends BreakableBlock
{
	public static final BooleanProperty SLIPPERY = BooleanProperty.create("slippery");
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public SnailSlimeFullBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(SLIPPERY, Boolean.valueOf(false)));
	}

	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return !state.get(SLIPPERY) ? SHAPE : super.getCollisionShape(state, worldIn, pos, context);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(SLIPPERY, Boolean.valueOf(this.shouldBeSlippery(context.getPos(), context.getWorld())));
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return stateIn.with(SLIPPERY, Boolean.valueOf(this.shouldBeSlippery(currentPos, worldIn)));
	}

	public final boolean shouldBeSlippery(BlockPos blockPos, IBlockReader iBlockReader)
	{
		for(Direction direction : Direction.values())
		{
			BlockPos blockpos1 = blockPos.offset(direction);
			Block block = iBlockReader.getBlockState(blockpos1).getBlock();
			if (this.doesBlockMakeSlippery(blockpos1, block, iBlockReader))
			{
				return true;
			}
		}
		return false;
	}

	public final boolean doesBlockMakeSlippery(BlockPos blockPos, Block block, IBlockReader iBlockReader)
	{
		FluidState fluidstate = iBlockReader.getFluidState(blockPos);
		if (AutumnityTags.SLIPPERY_SNAIL_SLIME_BLOCKS.contains(block) || fluidstate.isTagged(FluidTags.WATER))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		if (entityIn.isSneaking())
		{
			super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
		}
		else
		{
			entityIn.onLivingFall(fallDistance, 0.0F);
		}
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if (!state.get(SLIPPERY) && !(entityIn instanceof SnailEntity))
		{
			if (entityIn.getBoundingBox().maxY <= pos.getY() + 0.0625D)
			{
				if (!entityIn.isSneaking())
				{
					entityIn.setMotionMultiplier(state, new Vector3d(1.0D, (double)0.0F, 1.0D));
				}
			}
			else
			{
				entityIn.setMotion(entityIn.getMotion().mul(0.4D, 1.0D, 0.4D));
			}
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(SLIPPERY);
	}

	@Override
	public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity)
	{
		return state.get(SLIPPERY) ? 0.98F : 0.6F;
	}
	
	public boolean isStickyBlock(BlockState state)
	{
		return !state.get(SLIPPERY);
	}
}