package com.minecraftabnormals.autumnity.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LargeRedstoneJackOlanternSliceBlock extends CarvedLargePumpkinSliceBlock
{
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
	
	public LargeRedstoneJackOlanternSliceBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(LIT, false));
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return super.getStateForPlacement(context).with(LIT, context.getWorld().isBlockPowered(context.getPos()));
	}
	
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		if (!worldIn.isRemote)
		{
			boolean flag = state.get(LIT);
			if (flag != worldIn.isBlockPowered(pos))
			{
				worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
			}
		}
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		if (state.get(LIT) && !worldIn.isBlockPowered(pos))
		{
			worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, HALF, CARVED_SIDE, LIT);
	}
}