package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityGeneration {

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		MobSpawnSettingsBuilder spawns = event.getSpawns();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();

		if (biome == null) return;

		if (!DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
			if (AutumnityConfig.COMMON.snailSpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.SNAIL.get(), 10, 2, 2));
			}

			if (AutumnityConfig.COMMON.turkeySpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.TURKEY.get(), 10, 4, 4));
			}

			if (AutumnityConfig.COMMON.mapleTreeBiomes.get().contains(biome.toString())) {
				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE.getHolder().get());
			}

			if (AutumnityConfig.COMMON.generateSpottedForests.get()) {
				if (DataUtil.matchesKeys(biome, Biomes.FOREST)) {
					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_YELLOW.getHolder().get());
				}

				if (DataUtil.matchesKeys(biome, Biomes.DARK_FOREST)) {
					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_ORANGE.getHolder().get());
				}

				if (DataUtil.matchesKeys(biome, Biomes.TAIGA, Biomes.WINDSWEPT_FOREST)) {
					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_RED.getHolder().get());
				}
			}
		}

		removeSpawns(event);
	}

	private static void removeSpawns(BiomeLoadingEvent event) {
		MobSpawnSettingsBuilder spawns = event.getSpawns();
		List<MobSpawnSettings.SpawnerData> entrysToRemove = new ArrayList<>();
		for (MobSpawnSettings.SpawnerData entry : spawns.getSpawner(MobCategory.CREATURE)) {
			if (AutumnityConfig.COMMON.turkeySpawnBiomes.get().contains(event.getName().toString())) {
				if (entry.type == EntityType.CHICKEN) {
					entrysToRemove.add(entry);
				}
			}
		}
		spawns.getSpawner(MobCategory.CREATURE).removeAll(entrysToRemove);
	}
}