package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class FallenLeavesMapleTreeFeature extends MapleTreeFeature {
	public FallenLeavesMapleTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		boolean flag = super.place(context);

		RandomSource random = context.random();
		BlockPos position = context.origin();
		WorldGenLevel worldIn = context.level();
		TreeConfiguration config = context.config();

		if (flag && random.nextInt(6) != 0) {
			BlockState blockstate = config.foliageProvider.getState(random, position);
			BlockState leafcarpet;

			if (blockstate == AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.RED_MAPLE_LEAF_PILE.get().defaultBlockState();
			} else if (blockstate == AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get().defaultBlockState();
			} else if (blockstate == AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState()) {
				leafcarpet = AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get().defaultBlockState();
			} else {
				leafcarpet = AutumnityBlocks.MAPLE_LEAF_PILE.get().defaultBlockState();
			}

			for (int x = -3; x <= 3; ++x) {
				for (int z = -3; z <= 3; ++z) {
					if (Math.abs(x) < 2 || Math.abs(z) < 2) {
						for (int y = -3; y <= 3; ++y) {
							BlockPos blockpos = position.offset(x, y, z);
							if (random.nextInt(8) > 0 && worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
								worldIn.setBlock(blockpos, leafcarpet
												.setValue(PipeBlock.UP, false)
												.setValue(PipeBlock.DOWN, true)
												.setValue(PipeBlock.NORTH, false)
												.setValue(PipeBlock.SOUTH, false)
												.setValue(PipeBlock.EAST, false)
												.setValue(PipeBlock.WEST, false)
										, 2);
							}
						}
					}
				}
			}
		}

		return flag;
	}
}