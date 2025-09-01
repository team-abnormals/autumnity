package com.teamabnormals.autumnity.common.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractLargePumpkinSliceBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public AbstractLargePumpkinSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(POWERED, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState bottomblock = level.getBlockState(blockpos.below());
		BlockState topblock = level.getBlockState(blockpos.above());

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.getValue(HALF) == Half.BOTTOM) {
			return this.defaultBlockState().setValue(FACING, bottomblock.getValue(FACING)).setValue(HALF, Half.TOP).setValue(POWERED, level.hasNeighborSignal(blockpos));
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.getValue(HALF) == Half.TOP) {
			return this.defaultBlockState().setValue(FACING, topblock.getValue(FACING)).setValue(HALF, Half.BOTTOM).setValue(POWERED, level.hasNeighborSignal(blockpos));
		}

		Half half = Mth.sin(context.getPlayer().getViewXRot(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP;
		return this.defaultBlockState().setValue(FACING, getFacing(context)).setValue(HALF, half).setValue(POWERED, level.hasNeighborSignal(blockpos));
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		List<BlockPos> slices = getOtherSlices(level, pos, state);
		slices.add(pos);
		boolean pumpkinpowered = slices.stream().anyMatch(blockpos -> level.getBlockState(blockpos).getValue(POWERED));

		for (BlockPos blockpos : slices) {
			BlockState blockstate = level.getBlockState(blockpos);
			if (blockstate.getBlock() instanceof LargeRedstoneJackOlanternSliceBlock && blockstate.getValue(LargeRedstoneJackOlanternSliceBlock.LIT) != pumpkinpowered)
				level.setBlock(blockpos, blockstate.setValue(LargeRedstoneJackOlanternSliceBlock.LIT, pumpkinpowered), 2);
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		if (newState.getBlock() instanceof AbstractLargePumpkinSliceBlock)
			return;

		Direction facing = state.getValue(FACING);
		Half half = state.getValue(HALF);

		for (int i = 0; i < 3; i++) {
			Direction offsetdirection = i == 0 ? facing.getClockWise() : i == 1 ? facing.getOpposite() : half == Half.BOTTOM ? Direction.UP : Direction.DOWN;
			Direction offsetfacing = i == 0 ? facing.getClockWise() : i == 1 ? facing.getCounterClockWise() : facing;
			Half offsethalf = i < 2 ? half : half == Half.BOTTOM ? Half.TOP : Half.BOTTOM;

			BlockPos offsetpos = pos.relative(offsetdirection);
			BlockState offsetstate = level.getBlockState(offsetpos);

			if (offsetstate.getBlock() instanceof AbstractLargePumpkinSliceBlock && offsetstate.getValue(FACING) == offsetfacing && offsetstate.getValue(HALF) == offsethalf) {
				List<BlockPos> slices = getOtherSlices(level, offsetpos, offsetstate);
				slices.add(offsetpos);
				boolean pumpkinpowered = slices.stream().anyMatch(blockpos1 -> level.getBlockState(blockpos1).getValue(POWERED));

				for (BlockPos blockpos1 : slices) {
					BlockState blockstate1 = level.getBlockState(blockpos1);
					if (blockstate1.getBlock() instanceof LargeRedstoneJackOlanternSliceBlock && blockstate1.getValue(LargeRedstoneJackOlanternSliceBlock.LIT) != pumpkinpowered)
						level.setBlock(blockpos1, blockstate1.setValue(LargeRedstoneJackOlanternSliceBlock.LIT, pumpkinpowered), 2);
				}
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
		if (!level.isClientSide && state.getValue(POWERED) != level.hasNeighborSignal(pos))
			level.setBlock(pos, state.cycle(POWERED), 2);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		Direction.Axis axis = state.getValue(FACING).getAxis();

		if (mirror != Mirror.NONE) {
			if ((mirror == Mirror.FRONT_BACK && axis == Direction.Axis.X) || (mirror == Mirror.LEFT_RIGHT && axis == Direction.Axis.Z)) {
				return state.rotate(Rotation.COUNTERCLOCKWISE_90);
			} else {
				return state.rotate(Rotation.CLOCKWISE_90);
			}
		} else {
			return state;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, POWERED);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	protected static boolean canCarve(Direction hitFace, Direction facing) {
		return hitFace == facing || hitFace == facing.getCounterClockWise();
	}

	protected static Direction getFacing(BlockPlaceContext context) {
		float f = Mth.wrapDegrees(context.getRotation()) / 45;

		if (f > -2 && f <= 0) {
			return Direction.NORTH;
		} else if (f > 0 && f <= 2) {
			return Direction.EAST;
		} else if (f > 2) {
			return Direction.SOUTH;
		} else {
			return Direction.WEST;
		}
	}

	protected static boolean isAnotherSlicePowered(Level level, BlockState state, BlockPos pos) {
		return getOtherSlices(level, pos, state).stream().anyMatch(blockpos -> level.getBlockState(blockpos).getValue(POWERED));
	}

	protected static List<BlockPos> getOtherSlices(Level level, BlockPos pos, BlockState state) {
		List<BlockPos> slices = new ArrayList<>();
		List<BlockPos> visited = new ArrayList<>();
		List<Pair<BlockPos, BlockState>> list = new LinkedList<>();

		list.add(Pair.of(pos, state));

		while (!list.isEmpty()) {
			Pair<BlockPos, BlockState> pair = list.remove(0);
			BlockPos blockpos = pair.getFirst();
			BlockState blockstate = pair.getSecond();

			visited.add(blockpos);

			Direction facing = blockstate.getValue(FACING);
			Half half = blockstate.getValue(HALF);

			for (int i = 0; i < 3; i++) {
				Direction offsetdirection = i == 0 ? facing.getClockWise() : i == 1 ? facing.getOpposite() : half == Half.BOTTOM ? Direction.UP : Direction.DOWN;
				Direction offsetfacing = i == 0 ? facing.getClockWise() : i == 1 ? facing.getCounterClockWise() : facing;
				Half offsethalf = i < 2 ? half : half == Half.BOTTOM ? Half.TOP : Half.BOTTOM;

				BlockPos offsetpos = blockpos.relative(offsetdirection);
				BlockState offseststate = level.getBlockState(offsetpos);

				if (!visited.contains(offsetpos) && offseststate.getBlock() instanceof AbstractLargePumpkinSliceBlock && offseststate.getValue(FACING) == offsetfacing && offseststate.getValue(HALF) == offsethalf) {
					slices.add(offsetpos);
					list.add(Pair.of(offsetpos, offseststate));
				}
			}
		}

		return slices;
	}
}