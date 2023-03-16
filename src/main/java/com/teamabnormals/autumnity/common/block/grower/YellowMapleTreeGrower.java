package com.teamabnormals.autumnity.common.block.grower;

import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class YellowMapleTreeGrower extends AbstractTreeGrower {
	@Override
	@Nullable
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean beehiveIn) {
		return AutumnityConfiguredFeatures.MAPLE_TREE_YELLOW.getHolder().get();
	}
}