package com.teamabnormals.autumnity.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class MapleTreeFeature extends Feature<TreeConfiguration> {
	public MapleTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		RandomSource random = context.random();
		BlockPos position = context.origin();
		WorldGenLevel level = context.level();
		TreeConfiguration config = context.config();

		int i = config.trunkPlacer.getTreeHeight(random);

		boolean flag = true;
		if (position.getY() > level.getMinBuildHeight() && position.getY() + i + 1 <= level.getMaxBuildHeight()) {
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
						if (j >= 0 && j < level.getMaxBuildHeight()) {
							if (!TreeFeature.isAirOrLeaves(level, blockpos$mutableblockpos.set(l, j, i1))) {
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
			} else if (TreeUtil.isValidGround(level, position.below(), (SaplingBlock) AutumnityBlocks.MAPLE_SAPLING.get()) && position.getY() < level.getMaxBuildHeight() - i - 1) {
				Set<BlockPos> logPosSet = Sets.newHashSet();

				BiConsumer<BlockPos, BlockState> logBiConsumer = (pos, state) -> {
					logPosSet.add(pos.immutable());
					TreeUtil.setForcedState(level, pos, state);
				};

				for (int i2 = 0; i2 < 2; ++i2) {
					BlockPos blockpos = position.above(i - 1 - i2);
					for (BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-2, -1, -2), blockpos.offset(2, 3, 2))) {
						double d0 = blockpos1.distSqr(blockpos);
						if (d0 <= (double) (2.35F * 2.35F) || (d0 <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
							TreeUtil.placeLeafAt(level, blockpos1, random, config);
						}
					}
				}

				config.trunkPlacer.placeTrunk(level, logBiConsumer, random, i, position, config);
				TreeUtil.updateLeaves(level, logPosSet);

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}