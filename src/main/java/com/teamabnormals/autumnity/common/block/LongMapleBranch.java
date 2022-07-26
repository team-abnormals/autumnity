package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.BranchPart;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class LongMapleBranch extends BushBlock {
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<BranchPart> PART = AutumnityBlockStateProperties.BRANCH_PART;

	public LongMapleBranch(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(PART, BranchPart.BASE));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
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
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (!context.replacingClickedOnBlock()) {
			BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
			if (blockstate.is(this) && blockstate.getValue(HORIZONTAL_FACING) == context.getClickedFace()) {
				return null;
			}
		}

		BlockState blockstate1 = this.defaultBlockState();
		LevelReader iworldreader = context.getLevel();
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
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlock(pos.relative(state.getValue(HORIZONTAL_FACING)), state.setValue(PART, BranchPart.TIP), 3);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);

		if (state.getValue(PART) != BranchPart.TIP) {
			return blockstate.is(AutumnityBlockTags.MAPLE_LOGS);
		} else {
			if (state.getBlock() != this) {
				return true;
			} else {
				return blockstate.is(this) && blockstate.getValue(PART) == BranchPart.BASE && blockstate.getValue(HORIZONTAL_FACING) == state.getValue(HORIZONTAL_FACING);
			}
		}
	}

	public static void placeAt(LevelAccessor worldIn, BlockPos pos, Direction direction, int flags) {
		/*
		worldIn.setBlockState(pos, AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.BASE).with(HORIZONTAL_FACING, direction), flags);
		worldIn.setBlockState(pos.offset(direction), AutumnityBlocks.LONG_MAPLE_BRANCH.get().getDefaultState().with(PART, BranchPart.TIP).with(HORIZONTAL_FACING, direction), flags);
		*/
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		if (!worldIn.isClientSide) {
			if (player.isCreative()) {
				preventCreativeDropFromBottomPart(worldIn, pos, state, player);
			} else {
				dropResources(state, worldIn, pos, null, player, player.getMainHandItem());
			}
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}

	protected static void preventCreativeDropFromBottomPart(Level world, BlockPos pos, BlockState state, Player player) {
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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