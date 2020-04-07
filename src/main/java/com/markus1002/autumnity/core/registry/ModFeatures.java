package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.biome.ModBiomeFeatures;
import com.markus1002.autumnity.common.world.gen.feature.FallenLeavesFeature;
import com.markus1002.autumnity.common.world.gen.feature.MapleTreeFeature;
import com.markus1002.autumnity.core.Config;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures
{
	public static final Feature<TreeFeatureConfig> MAPLE_TREE = new MapleTreeFeature(TreeFeatureConfig::func_227338_a_);
	public static final Feature<NoFeatureConfig> FALLEN_LEAVES = new FallenLeavesFeature(NoFeatureConfig::deserialize);

	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		registerFeature(MAPLE_TREE, "maple_tree");
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
			addBiomeFeature(biome, GenerationStage.Decoration.VEGETAL_DECORATION, MAPLE_TREE.withConfiguration(ModBiomeFeatures.MAPLE_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.05F, 1))));
		}
	}

	private static void addBiomeFeature(Biome biome, Decoration decorationStage, ConfiguredFeature<?, ?> featureIn)
	{
		if(!biome.getFeatures(decorationStage).contains(featureIn))
		{
			biome.addFeature(decorationStage, featureIn);
		}
	}
}