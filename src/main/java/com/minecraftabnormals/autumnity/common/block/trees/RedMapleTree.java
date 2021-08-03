package com.minecraftabnormals.autumnity.common.block.trees;

import com.minecraftabnormals.autumnity.core.registry.AutumnityFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class RedMapleTree extends Tree {
	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean beehiveIn) {
		return AutumnityFeatures.MAPLE_TREE.get().configured(AutumnityFeatures.Configs.RED_MAPLE_TREE_CONFIG);
	}
}