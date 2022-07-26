package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class MapleTreeFeature extends Feature<TreeConfiguration> {
	public MapleTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		Random random = context.random();
		BlockPos position = context.origin();
		WorldGenLevel worldIn = context.level();
		TreeConfiguration config = context.config();

		int i = random.nextInt(2) + 5;

		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxBuildHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;
				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxBuildHeight()) {
							if (!TreeFeature.isAirOrLeaves(worldIn, blockpos$mutableblockpos.set(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isValidGround(worldIn, position.below()) && position.getY() < worldIn.getMaxBuildHeight() - i - 1) {
				setDirtAt(worldIn, position.below());

				for (int i2 = 0; i2 < 2; ++i2) {
					BlockPos blockpos = position.above(i - 1 - i2);

					for (BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-2, -1, -2), blockpos.offset(2, 3, 2))) {
						double d0 = blockpos1.distSqr(blockpos);
						if (d0 <= (double) (2.35F * 2.35F) || (d0 <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
							if (TreeFeature.isAirOrLeaves(worldIn, blockpos1)) {
								this.placeLeafAt(worldIn, blockpos1, random, config);
							}
						}
					}
				}

				for (int i2 = 0; i2 < i; ++i2) {
					if (TreeFeature.isAirOrLeaves(worldIn, position.above(i2))) {
						this.placeLogAt(worldIn, position.above(i2), random, config);
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void placeLogAt(LevelWriter worldIn, BlockPos pos, Random rand, TreeConfiguration config) {
		BlockState logState = config.trunkProvider.getState(rand, pos);
		TreeUtil.setForcedState(worldIn, pos, logState);
	}

	private void placeLeafAt(LevelSimulatedRW world, BlockPos pos, Random rand, TreeConfiguration config) {
		if (TreeFeature.isAirOrLeaves(world, pos)) {
			if (config.foliageProvider.getState(rand, pos).hasProperty(LeavesBlock.DISTANCE)) {
				TreeUtil.setForcedState(world, pos, config.foliageProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
			} else {
				TreeUtil.setForcedState(world, pos, config.foliageProvider.getState(rand, pos));
			}
		}
	}

	public static void setDirtAt(LevelAccessor worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
			worldIn.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
		}
	}

	public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) AutumnityBlocks.MAPLE_SAPLING.get());
	}
}