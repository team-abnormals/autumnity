package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper.KeyedBiome;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityBiomes {
	public static final BiomeSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBiomeSubHelper();

	public static final KeyedBiome MAPLE_FOREST = HELPER.createBiome("maple_forest", AutumnityBiomes::mapleForest);
	public static final KeyedBiome PUMPKIN_FIELDS = HELPER.createBiome("pumpkin_fields", AutumnityBiomes::pumpkinFields);

	private static Biome mapleForest() {
		MobSpawnSettings.Builder spawns = baseMapleSpawns();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addForestGrass(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.MAPLE_FOREST_VEGETATION.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FLOWER_MAPLE_FOREST.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FALLEN_LEAVES.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_FOUL_BERRY_BUSH.getHolder().get());
		return (new Biome.BiomeBuilder()).precipitation(Precipitation.RAIN).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.7F)).foliageColorOverride(0x9FC944).grassColorOverride(0x9AB839).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(OverworldBiomes.NORMAL_MUSIC).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome pumpkinFields() {
		MobSpawnSettings.Builder spawns = baseMapleSpawns();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addPlainGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PUMPKIN_FIELDS_VEGETATION.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.FLOWER_PUMPKIN_FIELDS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_TALL_GRASS_PUMPKIN_FIELDS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.PATCH_PUMPKIN_PUMPKIN_FIELDS.getHolder().get());
		return (new Biome.BiomeBuilder()).precipitation(Precipitation.RAIN).temperature(0.8F).downfall(0.4F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.8F)).foliageColorOverride(0x9FC944).grassColorOverride(0x9AB839).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(OverworldBiomes.NORMAL_MUSIC).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
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