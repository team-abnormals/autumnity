package com.minecraftabnormals.autumnity.common.world.gen.feature;

import java.util.Random;

import com.minecraftabnormals.autumnity.common.block.AbstractLargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.CarvedLargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.LargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.properties.CarvedSide;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class PumpkinFieldsPumpkinFeature extends Feature<NoFeatureConfig>
{
	public PumpkinFieldsPumpkinFeature(Codec<NoFeatureConfig> config)
	{
		super(config);
	}

	public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos blockpos, NoFeatureConfig p_230362_6_)
	{
		int i = 0;
		int j = 0;
		boolean spooky = rand.nextInt(100) == 0 ? true : false;

		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(int k = 0; k < 64; ++k)
		{
			blockpos$mutable.func_239621_a_(blockpos, rand.nextInt(10) - rand.nextInt(10), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(10) - rand.nextInt(10));
			if (j <= 2 && rand.nextInt(8) == 0 && checkPositions(worldIn, blockpos$mutable))
			{
				createLargePumpkinHalf(worldIn, blockpos$mutable, Half.BOTTOM);
				createLargePumpkinHalf(worldIn, blockpos$mutable.up(), Half.TOP);

				if (spooky)
				{
					BlockPos blockpos1;
					int l = rand.nextInt(4);
					
					if (l == 1)
						blockpos1 = blockpos$mutable;
					else if (l == 2)
						blockpos1 = blockpos$mutable.north();
					else if (l == 3)
						blockpos1 = blockpos$mutable.east();
					else
						blockpos1 = blockpos$mutable.north().east();

					carveLargePumpkin(worldIn, blockpos1);
					carveLargePumpkin(worldIn, blockpos1.up());
				}

				++j;
				++i;
			}
			else if (isAirOrReplaceable(worldIn, blockpos$mutable) && worldIn.getBlockState(blockpos$mutable.down()).getBlock() == Blocks.GRASS_BLOCK)
			{
				BlockState blockstate = spooky ? Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.Plane.HORIZONTAL.random(rand)) : Blocks.PUMPKIN.getDefaultState();

				worldIn.setBlockState(blockpos$mutable, blockstate, 2);

				++i;
			}
		}

		return i > 0;
	}

	private static void createLargePumpkinHalf(ISeedReader worldIn, BlockPos pos, Half half)
	{
		BlockState blockstate = AutumnityBlocks.LARGE_PUMPKIN_SLICE.get().getDefaultState();

		worldIn.setBlockState(pos, blockstate.with(AbstractLargePumpkinSliceBlock.FACING, Direction.WEST).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(pos.north(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.NORTH).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(pos.east(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.SOUTH).with(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlockState(pos.north().east(), blockstate.with(LargePumpkinSliceBlock.FACING, Direction.EAST).with(LargePumpkinSliceBlock.HALF, half), 2);
	}

	private static void carveLargePumpkin(ISeedReader worldIn, BlockPos pos)
	{
		BlockState blockstate = AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get().getDefaultState();
		
		BlockState blockstate1 = worldIn.getBlockState(pos);
		BlockState newblockstate1 = blockstate.with(AbstractLargePumpkinSliceBlock.FACING, blockstate1.get(AbstractLargePumpkinSliceBlock.FACING)).with(LargePumpkinSliceBlock.HALF, blockstate1.get(AbstractLargePumpkinSliceBlock.HALF));

		Direction direction = blockstate1.get(AbstractLargePumpkinSliceBlock.FACING);
		BlockPos blockpos = pos.offset(direction.rotateY());
		BlockState blockstate2 = worldIn.getBlockState(blockpos);
		BlockState newblockstate2 = blockstate.with(AbstractLargePumpkinSliceBlock.FACING, blockstate2.get(AbstractLargePumpkinSliceBlock.FACING)).with(LargePumpkinSliceBlock.HALF, blockstate2.get(AbstractLargePumpkinSliceBlock.HALF));

		CarvedSide carvedside = CarvedSide.getCarvedSide(direction.getAxis());

		worldIn.setBlockState(pos, newblockstate1.with(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside), 2);
		worldIn.setBlockState(blockpos, newblockstate2.with(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside), 2);
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