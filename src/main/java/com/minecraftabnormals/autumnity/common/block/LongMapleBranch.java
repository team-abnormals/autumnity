package com.minecraftabnormals.autumnity.common.block;

import javax.annotation.Nullable;

import com.minecraftabnormals.autumnity.common.block.properties.BranchPart;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class LongMapleBranch extends BushBlock
{
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<BranchPart> PART = AutumnityBlockStateProperties.BRANCH_PART;

	public LongMapleBranch(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(PART, BranchPart.BASE));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		BranchPart branchpart = stateIn.get(PART);
		Direction direction = stateIn.get(HORIZONTAL_FACING);
		if (facing.getAxis() != direction.getAxis() || branchpart == BranchPart.BASE != (facing == direction) || facingState.isIn(this) && facingState.get(PART) != branchpart && facingState.get(HORIZONTAL_FACING) == direction)
		{
			return branchpart == BranchPart.BASE && facing == direction.getOpposite() && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
		else
		{
			return Blocks.AIR.getDefaultState();
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		if (!context.replacingClickedOnBlock())
		{
			BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(context.getFace().getOpposite()));
			if (blockstate.isIn(this) && blockstate.get(HORIZONTAL_FACING) == context.getFace())
			{
				return null;
			}
		}

		BlockState blockstate1 = this.getDefaultState();
		IWorldReader iworldreader = context.getWorld();
		BlockPos blockpos = context.getPos();

		for(Direction direction : context.getNearestLookingDirections())
		{
			if (direction.getAxis().isHorizontal())
			{
				Direction direction1 = direction.getOpposite();
				blockstate1 = blockstate1.with(HORIZONTAL_FACING, direction1);
				if (blockstate1.isValidPosition(iworldreader, blockpos) && context.getWorld().getBlockState(blockpos.offset(direction1)).isReplaceable(context))
				{
					return blockstate1;
				}
			}
		}

		return null;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING)), state.with(PART, BranchPart.TIP), 3);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);

		if (state.get(PART) != BranchPart.TIP)
		{
			return blockstate.getBlock().isIn(AutumnityTags.MAPLE_LOGS);
		}
		else
		{
			if (state.getBlock() != this)
			{
				return true;
			}
			else
			{
				return blockstate.isIn(this) && blockstate.get(PART) == BranchPart.BASE && blockstate.get(HORIZONTAL_FACING) == state.get(HORIZONTAL_FACING);
			}
		}
	}

	public static void placeAt(IWorld worldIn, BlockPos pos, Direction direction, int flags)
	{
		worldIn.setBlockState(pos, AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.BASE).with(HORIZONTAL_FACING, direction), flags);
		worldIn.setBlockState(pos.offset(direction), AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.TIP).with(HORIZONTAL_FACING, direction), flags);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		if (!worldIn.isRemote)
		{
			if (player.isCreative())
			{
				func_241471_b_(worldIn, pos, state, player);
			}
			else
			{
				spawnDrops(state, worldIn, pos, (TileEntity)null, player, player.getHeldItemMainhand());
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	protected static void func_241471_b_(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		BranchPart branchpart = state.get(PART);
		if (branchpart == BranchPart.TIP)
		{
			BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.get(PART) == BranchPart.TIP)
			{
				world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
				world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, PART);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}
}