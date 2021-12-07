package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.BranchPart;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
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
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LongMapleBranch extends BushBlock {
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<BranchPart> PART = AutumnityBlockStateProperties.BRANCH_PART;

	public LongMapleBranch(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(PART, BranchPart.BASE));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		BranchPart branchpart = stateIn.getValue(PART);
		Direction direction = stateIn.getValue(HORIZONTAL_FACING);
		if (facing.getAxis() != direction.getAxis() || branchpart == BranchPart.BASE != (facing == direction) || facingState.is(this) && facingState.getValue(PART) != branchpart && facingState.getValue(HORIZONTAL_FACING) == direction) {
			return branchpart == BranchPart.BASE && facing == direction.getOpposite() && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (!context.replacingClickedOnBlock()) {
			BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
			if (blockstate.is(this) && blockstate.getValue(HORIZONTAL_FACING) == context.getClickedFace()) {
				return null;
			}
		}

		BlockState blockstate1 = this.defaultBlockState();
		IWorldReader iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();

		for (Direction direction : context.getNearestLookingDirections()) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate1 = blockstate1.setValue(HORIZONTAL_FACING, direction1);
				if (blockstate1.canSurvive(iworldreader, blockpos) && context.getLevel().getBlockState(blockpos.relative(direction1)).canBeReplaced(context)) {
					return blockstate1;
				}
			}
		}

		return null;
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlock(pos.relative(state.getValue(HORIZONTAL_FACING)), state.setValue(PART, BranchPart.TIP), 3);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);

		if (state.getValue(PART) != BranchPart.TIP) {
			return blockstate.getBlock().is(AutumnityTags.MAPLE_LOGS);
		} else {
			if (state.getBlock() != this) {
				return true;
			} else {
				return blockstate.is(this) && blockstate.getValue(PART) == BranchPart.BASE && blockstate.getValue(HORIZONTAL_FACING) == state.getValue(HORIZONTAL_FACING);
			}
		}
	}

	public static void placeAt(IWorld worldIn, BlockPos pos, Direction direction, int flags) {
		/*
		worldIn.setBlockState(pos, AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.BASE).with(HORIZONTAL_FACING, direction), flags);
		worldIn.setBlockState(pos.offset(direction), AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.TIP).with(HORIZONTAL_FACING, direction), flags);
		*/
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isClientSide) {
			if (player.isCreative()) {
				preventCreativeDropFromBottomPart(worldIn, pos, state, player);
			} else {
				dropResources(state, worldIn, pos, null, player, player.getMainHandItem());
			}
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}

	protected static void preventCreativeDropFromBottomPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BranchPart branchpart = state.getValue(PART);
		if (branchpart == BranchPart.TIP) {
			BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(PART) == BranchPart.TIP) {
				world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, PART);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
	}
}