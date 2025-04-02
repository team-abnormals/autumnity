package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class FallenLeavesMapleTreeFeature extends MapleTreeFeature {
	public FallenLeavesMapleTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		boolean flag = super.place(context);
		if (flag) {
			TreeConfiguration config = context.config();
			RandomSource random = context.random();
			BlockPos origin = context.origin();

			int xoffset = random.nextInt(4) > 0 ? 0 : random.nextBoolean() ? -1 : 1;
			int yoffset = random.nextInt(4) > 0 ? 0 : random.nextBoolean() ? -1 : 1;

			FallenMapleLeavesFeature.placeLeaves(FallenMapleLeavesFeature.getMatchingLeafPile(config.foliageProvider.getState(random, origin).getBlock()), origin.offset(xoffset, 0, yoffset), context.level(), random);
		}
		return flag;
	}
}