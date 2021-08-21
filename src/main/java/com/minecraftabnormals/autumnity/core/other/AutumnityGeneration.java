package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.AutumnityConfig;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBiomes;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityFeatures;
import com.minecraftabnormals.autumnity.core.registry.AutumnityStructures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityGeneration {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEarlyBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnInfoBuilder spawns = event.getSpawns();

		if (biome == null) return;

		if (DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey(), AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
			DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation);
			DefaultBiomeFeatures.addDefaultCarvers(generation);
			DefaultBiomeFeatures.addDefaultLakes(generation);
			DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
			DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
			DefaultBiomeFeatures.addDefaultOres(generation);
			DefaultBiomeFeatures.addDefaultSoftDisks(generation);
			DefaultBiomeFeatures.addDefaultMushrooms(generation);
			DefaultBiomeFeatures.addDefaultExtraVegetation(generation);
			DefaultBiomeFeatures.addDefaultSprings(generation);
			DefaultBiomeFeatures.addSurfaceFreezing(generation);

			generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
			generation.addStructureStart(AutumnityStructures.Configured.MAPLE_WITCH_HUT);

			DefaultBiomeFeatures.commonSpawns(spawns);
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(AutumnityEntities.SNAIL.get(), 16, 2, 2));
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));

			if (DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey())) {
				DefaultBiomeFeatures.addDefaultFlowers(generation);
				DefaultBiomeFeatures.addForestGrass(generation);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_FOREST_VEGETATION);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FLOWER_MAPLE_FOREST);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_GRASS_MAPLE_FOREST);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FALLEN_LEAVES);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_FOUL_BERRY_BUSH);
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			}

			if (DataUtil.matchesKeys(biome, AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);
				DefaultBiomeFeatures.addPlainGrass(generation);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PUMPKIN_FIELDS_VEGETATION);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.FLOWER_PUMPKIN_FIELDS);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_GRASS_PUMPKIN_FIELDS);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.PATCH_PUMPKINS_PUMPKIN_FIELDS);
			}
		} else {
			if (DataUtil.matchesKeys(biome, AutumnityBiomes.YELLOW_SPOTTED_FOREST.getKey())) {
				DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation);
				DefaultBiomeFeatures.addDefaultCarvers(generation);
				DefaultBiomeFeatures.addDefaultLakes(generation);
				DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
				DefaultBiomeFeatures.addForestFlowers(generation);
				DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
				DefaultBiomeFeatures.addDefaultOres(generation);
				DefaultBiomeFeatures.addDefaultSoftDisks(generation);
				DefaultBiomeFeatures.addDefaultFlowers(generation);
				DefaultBiomeFeatures.addForestGrass(generation);
				DefaultBiomeFeatures.addDefaultMushrooms(generation);
				DefaultBiomeFeatures.addDefaultExtraVegetation(generation);
				DefaultBiomeFeatures.addDefaultSprings(generation);
				DefaultBiomeFeatures.addSurfaceFreezing(generation);

				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_TREE_YELLOW_SPOTTED_FOREST);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.YELLOW_SPOTTED_FOREST_VEGETATION);

				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

				DefaultBiomeFeatures.commonSpawns(spawns);
				DefaultBiomeFeatures.farmAnimals(spawns);
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4));
			} else if (DataUtil.matchesKeys(biome, AutumnityBiomes.ORANGE_SPOTTED_DARK_FOREST.getKey())) {
				DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation);
				DefaultBiomeFeatures.addDefaultCarvers(generation);
				DefaultBiomeFeatures.addDefaultLakes(generation);
				DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
				DefaultBiomeFeatures.addForestFlowers(generation);
				DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
				DefaultBiomeFeatures.addDefaultOres(generation);
				DefaultBiomeFeatures.addDefaultSoftDisks(generation);
				DefaultBiomeFeatures.addDefaultFlowers(generation);
				DefaultBiomeFeatures.addForestGrass(generation);
				DefaultBiomeFeatures.addDefaultMushrooms(generation);
				DefaultBiomeFeatures.addDefaultExtraVegetation(generation);
				DefaultBiomeFeatures.addDefaultSprings(generation);
				DefaultBiomeFeatures.addSurfaceFreezing(generation);

				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.ORANGE_SPOTTED_DARK_FOREST_VEGETATION);

				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
				generation.addStructureStart(StructureFeatures.WOODLAND_MANSION);

				DefaultBiomeFeatures.commonSpawns(spawns);
				DefaultBiomeFeatures.farmAnimals(spawns);
			} else if (DataUtil.matchesKeys(biome, AutumnityBiomes.RED_SPOTTED_TAIGA.getKey())) {
				DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation);
				DefaultBiomeFeatures.addDefaultCarvers(generation);
				DefaultBiomeFeatures.addDefaultLakes(generation);
				DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
				DefaultBiomeFeatures.addFerns(generation);
				DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
				DefaultBiomeFeatures.addDefaultOres(generation);
				DefaultBiomeFeatures.addDefaultSoftDisks(generation);
				DefaultBiomeFeatures.addDefaultFlowers(generation);
				DefaultBiomeFeatures.addTaigaGrass(generation);
				DefaultBiomeFeatures.addDefaultMushrooms(generation);
				DefaultBiomeFeatures.addDefaultExtraVegetation(generation);
				DefaultBiomeFeatures.addDefaultSprings(generation);
				DefaultBiomeFeatures.addSparseBerryBushes(generation);
				DefaultBiomeFeatures.addSurfaceFreezing(generation);

				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.RED_SPOTTED_TAIGA_VEGETATION);

				generation.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
				generation.addStructureStart(StructureFeatures.VILLAGE_TAIGA);
				generation.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);

				DefaultBiomeFeatures.commonSpawns(spawns);
				DefaultBiomeFeatures.farmAnimals(spawns);
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4));
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
			}
		}
	}

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		MobSpawnInfoBuilder spawns = event.getSpawns();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();

		if (biome == null) return;

		if (!DataUtil.matchesKeys(biome, AutumnityBiomes.MAPLE_FOREST.getKey(), AutumnityBiomes.MAPLE_FOREST_HILLS.getKey(), AutumnityBiomes.PUMPKIN_FIELDS.getKey())) {
			if (AutumnityConfig.COMMON.snailSpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(AutumnityEntities.SNAIL.get(), 10, 2, 2));
			}

			if (AutumnityConfig.COMMON.turkeySpawnBiomes.get().contains(biome.toString())) {
				spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(AutumnityEntities.TURKEY.get(), 10, 4, 4));
			}

			if (AutumnityConfig.COMMON.mapleTreeBiomes.get().contains(biome.toString())) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AutumnityFeatures.Configured.MAPLE_TREE);
			}
		}

		removeSpawns(event);
	}

	private static void removeSpawns(BiomeLoadingEvent event) {
		MobSpawnInfoBuilder spawns = event.getSpawns();
		List<MobSpawnInfo.Spawners> entrysToRemove = new ArrayList<>();
		for (MobSpawnInfo.Spawners entry : spawns.getSpawner(EntityClassification.CREATURE)) {
			if (AutumnityConfig.COMMON.turkeySpawnBiomes.get().contains(event.getName().toString())) {
				if (entry.type == EntityType.CHICKEN) {
					entrysToRemove.add(entry);
				}
			}
		}
		spawns.getSpawner(EntityClassification.CREATURE).removeAll(entrysToRemove);
	}
}