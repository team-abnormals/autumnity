package com.markus1002.autumnity.common.block.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.markus1002.autumnity.common.world.biome.AutumnityBiomeFeatures;
import com.markus1002.autumnity.core.registry.AutumnityFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MapleTree extends Tree
{
	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehiveIn)
	{
		return AutumnityFeatures.MAPLE_TREE.get().withConfiguration(AutumnityBiomeFeatures.MAPLE_TREE_CONFIG);
	}
}