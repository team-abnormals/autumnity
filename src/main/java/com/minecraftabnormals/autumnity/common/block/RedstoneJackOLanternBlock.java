package com.minecraftabnormals.autumnity.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class RedstoneJackOLanternBlock extends AutumnityJackOLanternBlock {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

	public RedstoneJackOLanternBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(LIT, Boolean.FALSE));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(LIT, context.getWorld().isBlockPowered(context.getPos()));
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
		return state.get(LIT) && state.get(FACING) == side.getOpposite() ? 15 : 0;
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
			if (state.get(LIT) != isBlockPowered(state, worldIn, pos)) {
				worldIn.setBlockState(pos, state.func_235896_a_(LIT), 3);
				if (!worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
					worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
				}
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (state.get(LIT) && !isBlockPowered(state, worldIn, pos)) {
			worldIn.setBlockState(pos, state.func_235896_a_(LIT), 3);
		}
	}

	private static boolean isBlockPowered(BlockState state, World world, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (world.isSidePowered(pos.offset(direction), direction) && direction != state.get(FACING)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}
}
