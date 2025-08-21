package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.common.block.AbstractLargePumpkinSliceBlock;
import com.teamabnormals.autumnity.common.block.GiantCarvedPumpkinChunkBlock;
import com.teamabnormals.autumnity.common.block.GiantPumpkinChunkBlock;
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
		WorldGenLevel level = context.level();

		int i = 0;
		int j = 0;
		boolean spooky = rand.nextInt(160) == 0;

		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for (int k = 0; k < 64; ++k) {
			blockpos$mutable.setWithOffset(blockpos, rand.nextInt(10) - rand.nextInt(10), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(10) - rand.nextInt(10));
			if (j <= 2 && rand.nextInt(8) == 0 && checkPositions(level, blockpos$mutable)) {
				createGiantPumpkinHalf(level, blockpos$mutable, Half.BOTTOM);
				createGiantPumpkinHalf(level, blockpos$mutable.above(), Half.TOP);

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

					carveGaintPumpkin(level, blockpos1);
					carveGaintPumpkin(level, blockpos1.above());
				}

				++j;
				++i;
			} else if (isAirOrReplaceable(level, blockpos$mutable) && level.getBlockState(blockpos$mutable.below()).getBlock() == Blocks.GRASS_BLOCK) {
				BlockState blockstate = spooky ? Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(rand)) : Blocks.PUMPKIN.defaultBlockState();

				level.setBlock(blockpos$mutable, blockstate, 2);

				++i;
			}
		}

		return i > 0;
	}

	private static void createGiantPumpkinHalf(WorldGenLevel level, BlockPos pos, Half half) {
		BlockState blockstate = AutumnityBlocks.GIANT_PUMPKIN_CHUNK.get().defaultBlockState();

		level.setBlock(pos, blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, Direction.WEST).setValue(GiantPumpkinChunkBlock.HALF, half), 2);
		level.setBlock(pos.north(), blockstate.setValue(GiantPumpkinChunkBlock.FACING, Direction.NORTH).setValue(GiantPumpkinChunkBlock.HALF, half), 2);
		level.setBlock(pos.east(), blockstate.setValue(GiantPumpkinChunkBlock.FACING, Direction.SOUTH).setValue(GiantPumpkinChunkBlock.HALF, half), 2);
		level.setBlock(pos.north().east(), blockstate.setValue(GiantPumpkinChunkBlock.FACING, Direction.EAST).setValue(GiantPumpkinChunkBlock.HALF, half), 2);
	}

	private static void carveGaintPumpkin(WorldGenLevel level, BlockPos pos) {
		BlockState blockstate = AutumnityBlocks.GIANT_CARVED_PUMPKIN_CHUNK.get().defaultBlockState();

		BlockState blockstate1 = level.getBlockState(pos);
		BlockState newblockstate1 = blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, blockstate1.getValue(AbstractLargePumpkinSliceBlock.FACING)).setValue(GiantPumpkinChunkBlock.HALF, blockstate1.getValue(AbstractLargePumpkinSliceBlock.HALF));

		Direction direction = blockstate1.getValue(AbstractLargePumpkinSliceBlock.FACING);
		BlockPos blockpos = pos.relative(direction.getClockWise());
		BlockState blockstate2 = level.getBlockState(blockpos);
		BlockState newblockstate2 = blockstate.setValue(AbstractLargePumpkinSliceBlock.FACING, blockstate2.getValue(AbstractLargePumpkinSliceBlock.FACING)).setValue(GiantPumpkinChunkBlock.HALF, blockstate2.getValue(AbstractLargePumpkinSliceBlock.HALF));

		CarvedSide carvedside = CarvedSide.getCarvedSide(direction.getAxis());

		level.setBlock(pos, newblockstate1.setValue(GiantCarvedPumpkinChunkBlock.CARVED_SIDE, carvedside), 2);
		level.setBlock(blockpos, newblockstate2.setValue(GiantCarvedPumpkinChunkBlock.CARVED_SIDE, carvedside), 2);
	}

	private static boolean checkPositions(WorldGenLevel level, BlockPos pos) {
		return isValidPosition(level, pos) && isValidPosition(level, pos.north()) && isValidPosition(level, pos.east()) && isValidPosition(level, pos.north().east());
	}

	private static boolean isValidPosition(WorldGenLevel level, BlockPos pos) {
		return isAirOrReplaceable(level, pos) && isAirOrReplaceable(level, pos.above()) && level.getBlockState(pos.below()).getBlock() == Blocks.GRASS_BLOCK;
	}

	private static boolean isAirOrReplaceable(WorldGenLevel level, BlockPos pos) {
		return level.isEmptyBlock(pos) || level.getBlockState(pos).canBeReplaced();
	}
}