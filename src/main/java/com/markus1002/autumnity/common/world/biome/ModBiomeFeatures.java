package com.markus1002.autumnity.common.world.biome;

import com.google.common.collect.ImmutableList;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class ModBiomeFeatures
{
	private static final BlockState MAPLE_LOG = ModBlocks.MAPLE_LOG.get().getDefaultState();
	private static final BlockState MAPLE_LEAVES = ModBlocks.MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState YELLOW_MAPLE_LEAVES = ModBlocks.YELLOW_MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState ORANGE_MAPLE_LEAVES = ModBlocks.ORANGE_MAPLE_LEAVES.get().getDefaultState();
	private static final BlockState RED_MAPLE_LEAVES = ModBlocks.RED_MAPLE_LEAVES.get().getDefaultState();

	public static final TreeFeatureConfig MAPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(MAPLE_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)ModBlocks.MAPLE_SAPLING.get()).build();
	public static final TreeFeatureConfig YELLOW_MAPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(YELLOW_MAPLE_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)ModBlocks.YELLOW_MAPLE_SAPLING.get()).build();
	public static final TreeFeatureConfig ORANGE_MAPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(ORANGE_MAPLE_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)ModBlocks.ORANGE_MAPLE_SAPLING.get()).build();
	public static final TreeFeatureConfig RED_MAPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(MAPLE_LOG), new SimpleBlockStateProvider(RED_MAPLE_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)ModBlocks.RED_MAPLE_SAPLING.get()).build();

	public static void addMapleFeatures(Biome biomeIn)
	{
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).func_227227_a_(0.025F),
				Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM).func_227227_a_(0.05F),
				ModFeatures.MAPLE_TREE.withConfiguration(RED_MAPLE_TREE_CONFIG).func_227227_a_(0.4F),
				ModFeatures.MAPLE_TREE.withConfiguration(ORANGE_MAPLE_TREE_CONFIG).func_227227_a_(0.4F),
				ModFeatures.MAPLE_TREE.withConfiguration(YELLOW_MAPLE_TREE_CONFIG).func_227227_a_(0.2F)),
				ModFeatures.MAPLE_TREE.withConfiguration(MAPLE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LUSH_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.FALLEN_LEAVES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(64))));
	}

	public static void addPumpkinFieldsFeatures(Biome biomeIn)
	{
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).func_227227_a_(0.025F),
				Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM).func_227227_a_(0.05F),
				ModFeatures.MAPLE_TREE.withConfiguration(RED_MAPLE_TREE_CONFIG).func_227227_a_(0.4F),
				ModFeatures.MAPLE_TREE.withConfiguration(ORANGE_MAPLE_TREE_CONFIG).func_227227_a_(0.4F),
				ModFeatures.MAPLE_TREE.withConfiguration(YELLOW_MAPLE_TREE_CONFIG).func_227227_a_(0.2F)),
				ModFeatures.MAPLE_TREE.withConfiguration(MAPLE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.2F, 1))));

		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.PLAINS_FLOWER_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(new NoiseDependant(-0.8D, 15, 4))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseDependant(-0.8D, 5, 10))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(4))));

	}
}