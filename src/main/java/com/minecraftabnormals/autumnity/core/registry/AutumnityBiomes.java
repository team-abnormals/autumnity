package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.BiomeSubRegistryHelper;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.AutumnityConfig;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityBiomes {
	private static final BiomeSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBiomeSubHelper();

	public static final BiomeSubRegistryHelper.KeyedBiome MAPLE_FOREST = HELPER.createBiome("maple_forest", () -> createMapleForestBiome(0.1F, 0.2F));
	public static final BiomeSubRegistryHelper.KeyedBiome MAPLE_FOREST_HILLS = HELPER.createBiome("maple_forest_hills", () -> createMapleForestBiome(0.45F, 0.3F));
	public static final BiomeSubRegistryHelper.KeyedBiome PUMPKIN_FIELDS = HELPER.createBiome("pumpkin_fields", () -> createPumpkinFieldsBiome());
	public static final BiomeSubRegistryHelper.KeyedBiome YELLOW_SPOTTED_FOREST = HELPER.createBiome("yellow_spotted_forest", () -> createYellowSpottedForestBiome());
	public static final BiomeSubRegistryHelper.KeyedBiome ORANGE_SPOTTED_DARK_FOREST = HELPER.createBiome("orange_spotted_dark_forest", () -> createOrangeSpottedDarkForest());
	public static final BiomeSubRegistryHelper.KeyedBiome RED_SPOTTED_TAIGA = HELPER.createBiome("red_spotted_taiga", () -> createRedSpottedTaigaBiome());

	public static void addBiomesToGeneration() {
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST.getKey(), AutumnityConfig.COMMON.mapleForestWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST_HILLS.getKey(), AutumnityConfig.COMMON.mapleForestHillsWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(PUMPKIN_FIELDS.getKey(), AutumnityConfig.COMMON.pumpkinFieldsWeight.get()));

		BiomeUtil.addHillBiome(MAPLE_FOREST.getKey(), Pair.of(MAPLE_FOREST_HILLS.getKey(), 1));
		BiomeUtil.addHillBiome(PUMPKIN_FIELDS.getKey(), Pair.of(MAPLE_FOREST.getKey(), 2), Pair.of(MAPLE_FOREST_HILLS.getKey(), 1));

		if (AutumnityConfig.COMMON.yellowSpottedForest.get())
			BiomeUtil.addHillBiome(Biomes.FOREST, Pair.of(YELLOW_SPOTTED_FOREST.getKey(), 1));
		if (AutumnityConfig.COMMON.redSpottedTaiga.get())
			BiomeUtil.addHillBiome(Biomes.TAIGA, Pair.of(RED_SPOTTED_TAIGA.getKey(), 1));
		if (AutumnityConfig.COMMON.orangeSpottedDarkForest.get())
			BiomeUtil.addHillBiome(Biomes.DARK_FOREST, Pair.of(ORANGE_SPOTTED_DARK_FOREST.getKey(), 1));
	}

	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(MAPLE_FOREST.getKey(), Type.FOREST, Type.OVERWORLD);
		BiomeDictionary.addTypes(MAPLE_FOREST_HILLS.getKey(), Type.FOREST, Type.HILLS, Type.RARE, Type.OVERWORLD);
		BiomeDictionary.addTypes(PUMPKIN_FIELDS.getKey(), Type.PLAINS, Type.SPARSE, Type.RARE, Type.OVERWORLD);
		BiomeDictionary.addTypes(YELLOW_SPOTTED_FOREST.getKey(), Type.FOREST, Type.OVERWORLD);
		BiomeDictionary.addTypes(ORANGE_SPOTTED_DARK_FOREST.getKey(), Type.SPOOKY, Type.DENSE, Type.FOREST, Type.RARE, Type.OVERWORLD);
		BiomeDictionary.addTypes(RED_SPOTTED_TAIGA.getKey(), Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.OVERWORLD);
	}

	private static Biome createMapleForestBiome(float depth, float scale) {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.7F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).grassColorOverride(0x9AB839).foliageColorOverride(0x9FC944).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()).build();
	}

	private static Biome createPumpkinFieldsBiome() {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.8F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).grassColorOverride(0x9AB839).foliageColorOverride(0x9FC944).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()).build();
	}

	private static Biome createYellowSpottedForestBiome() {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.FOREST).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.7F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()).build();
	}

	private static Biome createOrangeSpottedDarkForest() {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.FOREST).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.7F)).grassColorModifier(BiomeAmbience.GrassColorModifier.DARK_FOREST).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()).build();
	}

	private static Biome createRedSpottedTaigaBiome() {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.TAIGA).depth(0.2F).scale(0.2F).temperature(0.25F).downfall(0.8F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.25F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}