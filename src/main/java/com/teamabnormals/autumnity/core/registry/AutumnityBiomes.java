package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AutumnityBiomes {
	public static final ResourceKey<Biome> MAPLE_FOREST = createKey("maple_forest");
	public static final ResourceKey<Biome> PUMPKIN_FIELDS = createKey("pumpkin_fields");

	public static void bootstrap(BootstapContext<Biome> context) {
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

		context.register(MAPLE_FOREST, mapleForest(features, carvers));
		context.register(PUMPKIN_FIELDS, pumpkinFields(features, carvers));
	}

	public static ResourceKey<Biome> createKey(String name) {
		return ResourceKey.create(Registries.BIOME, new ResourceLocation(Autumnity.MOD_ID, name));
	}

	private static Biome mapleForest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		MobSpawnSettings.Builder spawns = baseMapleSpawns();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addForestGrass(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.MAPLE_FOREST_VEGETATION);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FLOWER_MAPLE_FOREST);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FALLEN_LEAVES);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_FOUL_BERRY_BUSH);
		return (new Biome.BiomeBuilder()).hasPrecipitation(true).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.7F)).foliageColorOverride(0x9FC944).grassColorOverride(0x9AB839).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(OverworldBiomes.NORMAL_MUSIC).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome pumpkinFields(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		MobSpawnSettings.Builder spawns = baseMapleSpawns();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addPlainGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PUMPKIN_FIELDS_VEGETATION);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FLOWER_PUMPKIN_FIELDS);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_TALL_GRASS_PUMPKIN_FIELDS);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_PUMPKIN_PUMPKIN_FIELDS);
		return (new Biome.BiomeBuilder()).hasPrecipitation(true).temperature(0.8F).downfall(0.4F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.8F)).foliageColorOverride(0x9FC944).grassColorOverride(0x9AB839).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(OverworldBiomes.NORMAL_MUSIC).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static MobSpawnSettings.Builder baseMapleSpawns() {
		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(spawns);
		BiomeDefaultFeatures.farmAnimals(spawns);
		return spawns;
	}

	private static int calculateSkyColor(float temperature) {
		float clampedTemp = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - clampedTemp * 0.05F, 0.5F + clampedTemp * 0.1F, 1.0F);
	}
}