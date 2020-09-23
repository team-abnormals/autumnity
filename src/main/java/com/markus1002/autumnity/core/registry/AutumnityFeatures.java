package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.biome.AutumnityBiomeFeatures;
import com.markus1002.autumnity.common.world.gen.feature.FallenLeavesFeature;
import com.markus1002.autumnity.common.world.gen.feature.FallenLeavesMapleTreeFeature;
import com.markus1002.autumnity.common.world.gen.feature.MapleTreeFeature;
import com.markus1002.autumnity.common.world.gen.feature.PumpkinFieldsPumpkinFeature;
import com.markus1002.autumnity.core.Config;
import com.markus1002.autumnity.core.Reference;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityFeatures
{
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> FALLEN_LEAVES_MAPLE_TREE = FEATURES.register("fallen_leaves_maple_tree", () -> new FallenLeavesMapleTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoFeatureConfig.field_236558_a_));

	public static void setupBiomeFeatures(Biome biome)
	{
		if (Config.COMMON.mapleTreeBiomes.get().contains(biome.getRegistryName().toString()))
		{
			addBiomeFeature(biome, GenerationStage.Decoration.VEGETAL_DECORATION, MAPLE_TREE.get().withConfiguration(AutumnityBiomeFeatures.MAPLE_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
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