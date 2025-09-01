package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.common.block.AbstractLargePumpkinSliceBlock;
import com.teamabnormals.autumnity.common.block.CarvedLargePumpkinSliceBlock;
import com.teamabnormals.autumnity.common.block.LargePumpkinSliceBlock;
import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PumpkinFieldsPumpkinFeature extends Feature<NoneFeatureConfiguration> {
	public PumpkinFieldsPumpkinFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		RandomSource rand = context.random();
		BlockPos blockpos = context.origin();
		WorldGenLevel worldIn = context.level();

		int i = 0;
		int j = 0;
		boolean spooky = rand.nextInt(160) == 0;

		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for (int k = 0; k < 64; ++k) {
			blockpos$mutable.setWithOffset(blockpos, rand.nextInt(10) - rand.nextInt(10), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(10) - rand.nextInt(10));
			if (j <= 2 && rand.nextInt(8) == 0 && checkPositions(worldIn, blockpos$mutable)) {
				createLargePumpkinHalf(worldIn, blockpos$mutable, Half.BOTTOM);
				createLargePumpkinHalf(worldIn, blockpos$mutable.above(), Half.TOP);

				if (spooky) {
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
					carveLargePumpkin(worldIn, blockpos1.above());
				}

				++j;
				++i;
			} else if (isAirOrReplaceable(worldIn, blockpos$mutable) && worldIn.getBlockState(blockpos$mutable.below()).getBlock() == Blocks.GRASS_BLOCK) {
				BlockState blockstate = spooky ? Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(rand)) : Blocks.PUMPKIN.defaultBlockState();

				worldIn.setBlock(blockpos$mutable, blockstate, 2);

				++i;
			}
		}

		return i > 0;
	}

	private static void createLargePumpkinHalf(WorldGenLevel worldIn, BlockPos pos, Half half) {
		BlockState blockstate = AutumnityBlocks.LARGE_PUMPKIN_SLICE.get().defaultBlockState();

		worldIn.setBlock(pos, blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, Direction.WEST).setValue(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlock(pos.north(), blockstate.setValue(LargePumpkinSliceBlock.FACING, Direction.NORTH).setValue(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlock(pos.east(), blockstate.setValue(LargePumpkinSliceBlock.FACING, Direction.SOUTH).setValue(LargePumpkinSliceBlock.HALF, half), 2);
		worldIn.setBlock(pos.north().east(), blockstate.setValue(LargePumpkinSliceBlock.FACING, Direction.EAST).setValue(LargePumpkinSliceBlock.HALF, half), 2);
	}

	private static void carveLargePumpkin(WorldGenLevel worldIn, BlockPos pos) {
		BlockState blockstate = AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get().defaultBlockState();

		BlockState blockstate1 = worldIn.getBlockState(pos);
		BlockState newblockstate1 = blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, blockstate1.getValue(AbstractLargePumpkinSliceBlock.FACING)).setValue(LargePumpkinSliceBlock.HALF, blockstate1.getValue(AbstractLargePumpkinSliceBlock.HALF));

		Direction direction = blockstate1.getValue(AbstractLargePumpkinSliceBlock.FACING);
		BlockPos blockpos = pos.relative(direction.getClockWise());
		BlockState blockstate2 = worldIn.getBlockState(blockpos);
		BlockState newblockstate2 = blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, blockstate2.getValue(AbstractLargePumpkinSliceBlock.FACING)).setValue(LargePumpkinSliceBlock.HALF, blockstate2.getValue(AbstractLargePumpkinSliceBlock.HALF));

		CarvedSide carvedside = CarvedSide.getCarvedSide(direction.getAxis());

		worldIn.setBlock(pos, newblockstate1.setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside), 2);
		worldIn.setBlock(blockpos, newblockstate2.setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside), 2);
	}

	private static boolean checkPositions(WorldGenLevel worldIn, BlockPos pos) {
		return isValidPosition(worldIn, pos) && isValidPosition(worldIn, pos.north()) && isValidPosition(worldIn, pos.east()) && isValidPosition(worldIn, pos.north().east());
	}

	private static boolean isValidPosition(WorldGenLevel worldIn, BlockPos pos) {
		return isAirOrReplaceable(worldIn, pos) && isAirOrReplaceable(worldIn, pos.above()) && worldIn.getBlockState(pos.below()).getBlock() == Blocks.GRASS_BLOCK;
	}

	private static boolean isAirOrReplaceable(WorldGenLevel worldIn, BlockPos pos) {
		return worldIn.isEmptyBlock(pos) || worldIn.getBlockState(pos).canBeReplaced();
	}
}