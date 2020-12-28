package com.minecraftabnormals.autumnity.common.block.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.minecraftabnormals.autumnity.common.world.biome.AutumnityBiomeFeatures;
import com.minecraftabnormals.autumnity.core.registry.AutumnityFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class OrangeMapleTree extends Tree
{
	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehiveIn)
	{
		return AutumnityFeatures.MAPLE_TREE.get().withConfiguration(AutumnityFeatures.Configs.ORANGE_MAPLE_TREE_CONFIG);
	}
}