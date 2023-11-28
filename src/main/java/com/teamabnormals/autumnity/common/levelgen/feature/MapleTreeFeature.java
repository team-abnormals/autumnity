package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class MapleTreeFeature extends BlueprintTreeFeature {

	public MapleTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public void doPlace(FeaturePlaceContext<TreeConfiguration> context) {
		TreeConfiguration config = context.config();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		int trunkHeight = config.trunkPlacer.getTreeHeight(random);
		for (int y = 0; y < trunkHeight; y++) {
			this.addLog(origin.above(y));
		}

		for (int width = 0; width < 2; width++) {
			BlockPos offsetPos = origin.above(trunkHeight - 1 - width);
			for (BlockPos leafPos : BlockPos.betweenClosed(offsetPos.offset(-2, -1, -2), offsetPos.offset(2, 3, 2))) {
				double dist = leafPos.distSqr(offsetPos);
				if (dist <= (double) (2.35F * 2.35F) || (dist <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
					this.addFoliage(leafPos);
				}
			}
		}

	}

	@Override
	public BlockState getSapling() {
		return AutumnityBlocks.MAPLE_SAPLING.get().defaultBlockState();
	}
}