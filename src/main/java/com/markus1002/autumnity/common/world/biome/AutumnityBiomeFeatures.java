package com.markus1002.autumnity.common.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.markus1002.autumnity.core.registry.AutumnityBlocks;
import com.markus1002.autumnity.core.registry.AutumnityFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class AutumnityBiomeFeatures
{
	private static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.get().getDefaultState();
	private static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState TALL_FOUL_BERRY_BUSH = AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get().getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3));
	private static final BlockState AUTUMN_CROCUS = AutumnityBlocks.AUTUMN_CROCUS.get().getDefaultState();
	private static final BlockState ROSE_BUSH = Blocks.ROSE_BUSH.getDefaultState();
	private static final BlockState OXEYE_DAISY = Blocks.OXEYE_DAISY.getDefaultState();
	private static final BlockState CORNFLOWER = Blocks.CORNFLOWER.getDefaultState();

	public static final BaseTreeFeatureConfig MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(MAPLE_LEAVES), null, null, null)).setIgnoreVines().build();
	public static final BaseTreeFeatureConfig YELLOW_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(YELLOW_MAPLE_LEAVES), null, null, null)).setIgnoreVines().build();
	public static final BaseTreeFeatureConfig ORANGE_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(ORANGE_MAPLE_LEAVES), null, null, null)).setIgnoreVines().build();
	public static final BaseTreeFeatureConfig RED_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(RED_MAPLE_LEAVES), null, null, null)).setIgnoreVines().build();
	public static final BlockClusterFeatureConfig TALL_FOUL_BERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TALL_FOUL_BERRY_BUSH), new DoublePlantBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
	public static final BlockClusterFeatureConfig AUTUMN_CROCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(AUTUMN_CROCUS), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig ROSE_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ROSE_BUSH), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig OXEYE_DAISY_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OXEYE_DAISY), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(CORNFLOWER), new SimpleBlockPlacer())).tries(64).build();

	public static void addMapleFeatures(Biome biomeIn)
	{
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withChance(0.025F),
				Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM).withChance(0.05F),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(RED_MAPLE_TREE_CONFIG).withChance(0.3F),
				AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get().withConfiguration(ORANGE_MAPLE_TREE_CONFIG).withChance(0.4F),
				AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get().withConfiguration(YELLOW_MAPLE_TREE_CONFIG).withChance(0.2F)),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(MAPLE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.RANDOM_PATCH.withConfiguration(ROSE_BUSH_CONFIG), Feature.FLOWER.withConfiguration(AUTUMN_CROCUS_CONFIG)), 0)).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LUSH_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.FALLEN_LEAVES.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(32))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(TALL_FOUL_BERRY_BUSH_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
	}

	public static void addPumpkinFieldsFeatures(Biome biomeIn)
	{
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withChance(0.025F),
				Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM).withChance(0.05F),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(RED_MAPLE_TREE_CONFIG).withChance(0.3F),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(ORANGE_MAPLE_TREE_CONFIG).withChance(0.4F),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(YELLOW_MAPLE_TREE_CONFIG).withChance(0.2F)),
				AutumnityFeatures.MAPLE_TREE.get().withConfiguration(MAPLE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.2F, 1))));

		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.FLOWER.withConfiguration(OXEYE_DAISY_CONFIG), Feature.FLOWER.withConfiguration(CORNFLOWER_CONFIG), Feature.FLOWER.withConfiguration(AUTUMN_CROCUS_CONFIG)), 0)).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(new NoiseDependant(-0.8D, 15, 4))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseDependant(-0.8D, 5, 10))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(5))));
	}
}