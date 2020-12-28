package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.BiomeSubRegistryHelper;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.Config;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
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

	public static void addBiomesToGeneration() {
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST.getKey(), Config.COMMON.mapleForestWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(PUMPKIN_FIELDS.getKey(), Config.COMMON.pumpkinFieldsWeight.get()));

		BiomeUtil.addHillBiome(MAPLE_FOREST.getKey(), Pair.of(MAPLE_FOREST_HILLS.getKey(), 1));
		BiomeUtil.addHillBiome(PUMPKIN_FIELDS.getKey(), Pair.of(MAPLE_FOREST.getKey(), 2), Pair.of(MAPLE_FOREST_HILLS.getKey(), 1));
	}

	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(MAPLE_FOREST.getKey(), Type.FOREST, Type.OVERWORLD);
		BiomeDictionary.addTypes(MAPLE_FOREST_HILLS.getKey(), Type.FOREST, Type.HILLS, Type.RARE, Type.OVERWORLD);
		BiomeDictionary.addTypes(PUMPKIN_FIELDS.getKey(), Type.PLAINS, Type.SPARSE, Type.RARE, Type.OVERWORLD);
	}

	private static Biome createMapleForestBiome(float depth, float scale) {
		BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.7F).downfall(0.8F).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.7F)).withGrassColor(0x9AB839).withFoliageColor(0x9FC944).build()).withMobSpawnSettings(new MobSpawnInfo.Builder().copy()).withGenerationSettings((builder).build()).build();
	}

	private static Biome createPumpkinFieldsBiome() {
		BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.8F)).withGrassColor(0x9AB839).withFoliageColor(0x9FC944).build()).withMobSpawnSettings(new MobSpawnInfo.Builder().copy()).withGenerationSettings((builder).build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
		return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}