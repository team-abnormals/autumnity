package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.autumnity.core.registry.AutumnityEntities;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityGeneration {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEarlyBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnSettingsBuilder spawns = event.getSpawns();

		if (biome == null) return;

		if (DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey(), AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
//			BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation);
//			BiomeDefaultFeatures.addDefaultCarvers(generation);
//			BiomeDefaultFeatures.addDefaultLakes(generation);
			BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
			BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
			BiomeDefaultFeatures.addDefaultOres(generation);
			BiomeDefaultFeatures.addDefaultSoftDisks(generation);
			BiomeDefaultFeatures.addDefaultMushrooms(generation);
			BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
			BiomeDefaultFeatures.addDefaultSprings(generation);
			BiomeDefaultFeatures.addSurfaceFreezing(generation);

//			generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
//			generation.addStructureStart(AutumnityConfiguredStructureFeatures.MAPLE_WITCH_HUT);

			BiomeDefaultFeatures.commonSpawns(spawns);
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntities.SNAIL.get(), 16, 2, 2));
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));

			if (DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey())) {
				BiomeDefaultFeatures.addDefaultFlowers(generation);
				BiomeDefaultFeatures.addForestGrass(generation);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_FOREST_VEGETATION);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FLOWER_MAPLE_FOREST);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_GRASS_MAPLE_FOREST);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FALLEN_LEAVES);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_FOUL_BERRY_BUSH);
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			}

			if (DataUtil.matchesKeys(biome, AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);
				BiomeDefaultFeatures.addPlainGrass(generation);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PUMPKIN_FIELDS_VEGETATION);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FLOWER_PUMPKIN_FIELDS);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_GRASS_PUMPKIN_FIELDS);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_PUMPKINS_PUMPKIN_FIELDS);
			}
		} else {
			if (DataUtil.matchesKeys(biome, AutumnityBiomes.YELLOW_SPOTTED_FOREST.getKey())) {
//				BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation);
//				BiomeDefaultFeatures.addDefaultCarvers(generation);
//				BiomeDefaultFeatures.addDefaultLakes(generation);
				BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
				BiomeDefaultFeatures.addForestFlowers(generation);
				BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
				BiomeDefaultFeatures.addDefaultOres(generation);
				BiomeDefaultFeatures.addDefaultSoftDisks(generation);
				BiomeDefaultFeatures.addDefaultFlowers(generation);
				BiomeDefaultFeatures.addForestGrass(generation);
				BiomeDefaultFeatures.addDefaultMushrooms(generation);
				BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
				BiomeDefaultFeatures.addDefaultSprings(generation);
				BiomeDefaultFeatures.addSurfaceFreezing(generation);

//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_TREE_YELLOW_SPOTTED_FOREST);
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.YELLOW_SPOTTED_FOREST_VEGETATION);

//				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

				BiomeDefaultFeatures.commonSpawns(spawns);
				BiomeDefaultFeatures.farmAnimals(spawns);
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
			} else if (DataUtil.matchesKeys(biome, AutumnityBiomes.ORANGE_SPOTTED_DARK_FOREST.getKey())) {
//				BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation);
//				BiomeDefaultFeatures.addDefaultCarvers(generation);
//				BiomeDefaultFeatures.addDefaultLakes(generation);
				BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
				BiomeDefaultFeatures.addForestFlowers(generation);
				BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
				BiomeDefaultFeatures.addDefaultOres(generation);
				BiomeDefaultFeatures.addDefaultSoftDisks(generation);
				BiomeDefaultFeatures.addDefaultFlowers(generation);
				BiomeDefaultFeatures.addForestGrass(generation);
				BiomeDefaultFeatures.addDefaultMushrooms(generation);
				BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
				BiomeDefaultFeatures.addDefaultSprings(generation);
				BiomeDefaultFeatures.addSurfaceFreezing(generation);

//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.ORANGE_SPOTTED_DARK_FOREST_VEGETATION);

//				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
//				generation.addStructureStart(StructureFeatures.WOODLAND_MANSION);

				BiomeDefaultFeatures.commonSpawns(spawns);
				BiomeDefaultFeatures.farmAnimals(spawns);
			} else if (DataUtil.matchesKeys(biome, AutumnityBiomes.RED_SPOTTED_TAIGA.getKey())) {
//				BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation);
//				BiomeDefaultFeatures.addDefaultCarvers(generation);
//				BiomeDefaultFeatures.addDefaultLakes(generation);
				BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
				BiomeDefaultFeatures.addFerns(generation);
				BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
				BiomeDefaultFeatures.addDefaultOres(generation);
				BiomeDefaultFeatures.addDefaultSoftDisks(generation);
				BiomeDefaultFeatures.addDefaultFlowers(generation);
				BiomeDefaultFeatures.addTaigaGrass(generation);
				BiomeDefaultFeatures.addDefaultMushrooms(generation);
				BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
				BiomeDefaultFeatures.addDefaultSprings(generation);
//				BiomeDefaultFeatures.addSparseBerryBushes(generation);
				BiomeDefaultFeatures.addSurfaceFreezing(generation);

//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.RED_SPOTTED_TAIGA_VEGETATION);

//				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
//				generation.addStructureStart(StructureFeatures.VILLAGE_TAIGA);
//				generation.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);

				BiomeDefaultFeatures.commonSpawns(spawns);
				BiomeDefaultFeatures.farmAnimals(spawns);
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
			}
		}
	}

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		MobSpawnSettingsBuilder spawns = event.getSpawns();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();

		if (biome == null) return;

		if (!DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey(), AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
			if (AutumnityConfig.COMMON.snailSpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntities.SNAIL.get(), 10, 2, 2));
			}

			if (AutumnityConfig.COMMON.turkeySpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			}

			if (AutumnityConfig.COMMON.mapleTreeBiomes.get().contains(biome.toString())) {
				//generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_TREE);
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