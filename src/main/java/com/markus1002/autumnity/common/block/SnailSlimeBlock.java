package com.markus1002.autumnity.common.block;

import com.markus1002.autumnity.common.entity.passive.SnailEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnailSlimeBlock extends Block
{
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

	public SnailSlimeBlock(Properties properties)
	{
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side)
	{
		return side.getAxis() != Direction.Axis.Y && adjacentBlockState.getBlock() == this ? true : super.isSideInvisible(state, adjacentBlockState, side);
	}

	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if (!(entityIn instanceof SnailEntity))
		{
			entityIn.setMotion(entityIn.getMotion().mul(0.4D, 1.0D, 0.4D));
		}
	}
}