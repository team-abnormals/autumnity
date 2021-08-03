package com.minecraftabnormals.autumnity.common.world.gen.feature;

import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

import java.util.Random;

public class FallenLeavesMapleTreeFeature extends MapleTreeFeature {
	public FallenLeavesMapleTreeFeature(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos position, BaseTreeFeatureConfig config) {
		boolean flag = super.place(worldIn, generator, random, position, config);

		if (flag && random.nextInt(6) != 0) {
			BlockState blockstate = config.leavesProvider.getState(random, position);
			BlockState leafcarpet = null;

			if (blockstate == AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get().defaultBlockState();
			} else if (blockstate == AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get().defaultBlockState();
			} else if (blockstate == AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get().defaultBlockState();
			} else {
				leafcarpet = AutumnityBlocks.MAPLE_LEAF_CARPET.get().defaultBlockState();
			}

			for (int x = -3; x <= 3; ++x) {
				for (int z = -3; z <= 3; ++z) {
					if (Math.abs(x) < 2 || Math.abs(z) < 2) {
						for (int y = -3; y <= 3; ++y) {
							BlockPos blockpos = position.offset(x, y, z);
							if (random.nextInt(8) > 0 && worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
								worldIn.setBlock(blockpos, leafcarpet, 2);
							}
						}
					}
				}
			}
		}

		return flag;
	}
}