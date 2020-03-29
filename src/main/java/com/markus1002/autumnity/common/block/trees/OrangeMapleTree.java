package com.markus1002.autumnity.common.block.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.markus1002.autumnity.common.world.biome.ModBiomeFeatures;
import com.markus1002.autumnity.core.registry.ModFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class OrangeMapleTree extends Tree
{
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_)
	{
		return ModFeatures.MAPLE_TREE.withConfiguration(ModBiomeFeatures.ORANGE_MAPLE_TREE_CONFIG);
	}
}