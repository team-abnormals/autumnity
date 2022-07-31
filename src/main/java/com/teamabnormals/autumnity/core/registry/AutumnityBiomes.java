package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper.KeyedBiome;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityBiomes {
	private static final BiomeSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBiomeSubHelper();

	public static final KeyedBiome MAPLE_FOREST = HELPER.createBiome("maple_forest", AutumnityBiomes::createMapleForestBiome);
	public static final KeyedBiome PUMPKIN_FIELDS = HELPER.createBiome("pumpkin_fields", AutumnityBiomes::createPumpkinFieldsBiome);

	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(MAPLE_FOREST.getKey(), Type.FOREST, Type.OVERWORLD);
		BiomeDictionary.addTypes(PUMPKIN_FIELDS.getKey(), Type.PLAINS, Type.SPARSE, Type.RARE, Type.OVERWORLD);
	}

	private static Biome createMapleForestBiome() {
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.FOREST).temperature(0.7F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.7F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).grassColorOverride(0x9AB839).foliageColorOverride(0x9FC944).build()).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).build()).build();
	}

	private static Biome createPumpkinFieldsBiome() {
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(0.8F).downfall(0.4F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.8F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).grassColorOverride(0x9AB839).foliageColorOverride(0x9FC944).build()).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}