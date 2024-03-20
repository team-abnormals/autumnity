package com.teamabnormals.autumnity.common.block.grower;

import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class OrangeMapleTreeGrower extends AbstractTreeGrower {
	@Override
	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean beehiveIn) {
		return AutumnityConfiguredFeatures.MAPLE_TREE_ORANGE;
	}
}