package com.markus1002.autumnity.common.world.gen.feature;

import java.util.Random;

import com.markus1002.autumnity.common.block.LargePumpkinSliceBlock;
import com.markus1002.autumnity.core.registry.AutumnityBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class LargePumpkinFeature extends Feature<NoFeatureConfig>
{
	public LargePumpkinFeature(Codec<NoFeatureConfig> config)
	{
		super(config);
	}

	public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos blockpos, NoFeatureConfig p_230362_6_)
	{
		int i = 0;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(int j = 0; j < 64; ++j)
		{
			blockpos$mutable.func_239621_a_(blockpos, rand.nextInt(10) - rand.nextInt(10), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(10) - rand.nextInt(10));
			if (rand.nextInt(6) == 0 && checkPositions(worldIn, blockpos$mutable))
			{
				createLargePumpkinHalf(worldIn, blockpos$mutable, Half.BOTTOM);
				createLargePumpkinHalf(worldIn, blockpos$mutable.up(), Half.TOP);

				++i;
			}
			else if (isAirOrReplaceable(worldIn, blockpos$mutable) && worldIn.getBlockState(blockpos$mutable.down()).getBlock() == Blocks.GRASS_BLOCK)
			{
				worldIn.setBlockState(blockpos$mutable, Blocks.PUMPKIN.getDefaultState(), 2);

				++i;
			}
		}

		return i > 0;
	}

	private static void createLargePumpkinHalf(ISeedReader worldIn, BlockPos blockpos, Half half)
	{
		BlockState blockstate = AutumnityBlocks.LARGE_PUMPKIN_SLICE.get().getDefaultState();

		worldIn.setBlockState(blockpos, blockstate.with(LargePumpkinSliceBlock.FACING, Direction.WEST).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(blockpos.north(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.NORTH).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(blockpos.east(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.SOUTH).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(blockpos.north().east(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.EAST).with(LargePumpkinSliceBlock.HALF, half), 2);
	}

	private static boolean checkPositions(ISeedReader worldIn, BlockPos pos)
	{
		return isValidPosition(worldIn, pos) && isValidPosition(worldIn, pos.north()) && isValidPosition(worldIn, pos.east()) && isValidPosition(worldIn, pos.north().east());
	}

	private static boolean isValidPosition(ISeedReader worldIn, BlockPos pos)
	{
		return isAirOrReplaceable(worldIn, pos) && isAirOrReplaceable(worldIn, pos.up()) && worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK;
	}

	private static boolean isAirOrReplaceable(ISeedReader worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).getMaterial().isReplaceable();
	}
}