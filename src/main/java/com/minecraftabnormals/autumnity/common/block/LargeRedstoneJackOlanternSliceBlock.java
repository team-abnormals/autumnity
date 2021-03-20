package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.CarvedSide;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class LargeRedstoneJackOlanternSliceBlock extends CarvedLargePumpkinSliceBlock {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

	public LargeRedstoneJackOlanternSliceBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(LIT, Boolean.TRUE));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		return blockstate.with(LIT, !isBlockPowered(blockstate, context.getWorld(), context.getPos()));
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		for (Direction direction : Direction.values()) {
			worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving) {
			for (Direction direction : Direction.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
			}
		}
	}

	@Override
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return state.get(LIT) && side.getAxis() != Axis.Y && state.get(FACING) == (checkAxis(state) ? side.getOpposite() : side.rotateYCCW()) ? 15 : 0;
	}

	@Override
	public int getStrongPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return state.getWeakPower(blockAccess, pos, side);
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isRemote) {
			boolean flag = state.get(LIT);
			if (flag == isBlockPowered(state, worldIn, pos)) {
				if (flag) {
					worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
				} else {
					worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
				}
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!state.get(LIT) && !isBlockPowered(state, worldIn, pos)) {
			worldIn.setBlockState(pos, state.func_235896_a_(LIT), 3);
		}
	}

	private static boolean isBlockPowered(BlockState state, World world, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (world.isSidePowered(pos.offset(direction), direction) && !(direction.getAxis() != Axis.Y && state.get(FACING) == (checkAxis(state) ? direction : direction.getOpposite().rotateYCCW()))) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkAxis(BlockState state) {
		Direction facing = state.get(FACING);
		CarvedSide side = state.get(CARVED_SIDE);
		return facing.getAxis() == Axis.X && side == CarvedSide.X || facing.getAxis() == Axis.Z && side == CarvedSide.Z;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, CARVED_SIDE, LIT);
	}
}