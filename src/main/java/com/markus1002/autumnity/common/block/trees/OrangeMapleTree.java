package com.markus1002.autumnity.common.block.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.markus1002.autumnity.common.world.gen.feature.MapleTreeFeature;
import com.markus1002.autumnity.core.registry.ModBlocks;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class OrangeMapleTree extends Tree
{
	@Nullable
	protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random)
	{
		return new MapleTreeFeature(NoFeatureConfig::deserialize, true, ModBlocks.MAPLE_LOG, ModBlocks.ORANGE_MAPLE_LEAVES, ModBlocks.ORANGE_MAPLE_SAPLING);
	}
}