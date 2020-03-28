package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.gen.feature.FallenLeavesFeature;
import com.markus1002.autumnity.common.world.gen.feature.MapleTreeFeature;
import com.markus1002.autumnity.core.Config;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures
{
	public static final Feature<NoFeatureConfig> MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize, false, ModBlocks.MAPLE_LOG, ModBlocks.MAPLE_LEAVES, ModBlocks.MAPLE_SAPLING);
	public static final Feature<NoFeatureConfig> RED_MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize, false, ModBlocks.MAPLE_LOG, ModBlocks.RED_MAPLE_LEAVES, ModBlocks.RED_MAPLE_SAPLING);
	public static final Feature<NoFeatureConfig> ORANGE_MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize, false, ModBlocks.MAPLE_LOG, ModBlocks.ORANGE_MAPLE_LEAVES, ModBlocks.ORANGE_MAPLE_SAPLING);
	public static final Feature<NoFeatureConfig> YELLOW_MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize, false, ModBlocks.MAPLE_LOG, ModBlocks.YELLOW_MAPLE_LEAVES, ModBlocks.YELLOW_MAPLE_SAPLING);
	public static final Feature<NoFeatureConfig> FALLEN_LEAVES = new FallenLeavesFeature(NoFeatureConfig::deserialize);

	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		registerFeature(MAPLE_TREE, "maple_tree");
		registerFeature(RED_MAPLE_TREE, "red_maple_tree");
		registerFeature(ORANGE_MAPLE_TREE, "orange_maple_tree");
		registerFeature(YELLOW_MAPLE_TREE, "yellow_maple_tree");
		registerFeature(FALLEN_LEAVES, "fallen_leaves");
	}

	private static void registerFeature(Feature<?> feature, String name)
	{
		feature.setRegistryName(name);
		ForgeRegistries.FEATURES.register(feature);
	}

	public static void setupBiomeFeatures(Biome biome)
	{
		if (Config.COMMON.mapleTreeBiomes.get().contains(biome.getRegistryName().toString()))
		{
			addBiomeFeature(biome, GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(MAPLE_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(2, 0.1F, 1)));
		}
	}

	private static void addBiomeFeature(Biome biome, Decoration decorationStage, ConfiguredFeature<?> configuredFeature)
	{
		if(!biome.getFeatures(decorationStage).contains(configuredFeature))
		{
			biome.addFeature(decorationStage, configuredFeature);
		}
	}
}