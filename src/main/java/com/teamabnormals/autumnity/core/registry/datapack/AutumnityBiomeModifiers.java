package com.teamabnormals.autumnity.core.registry.datapack;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.RemoveSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutumnityBiomeModifiers {

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		addFeature(context, "maple_tree", AutumnityBiomeTags.HAS_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE);
		addFeature(context, "orange_spotted_maple_tree", AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.SPOTTED_MAPLES_ORANGE);
		addFeature(context, "red_spotted_maple_tree", AutumnityBiomeTags.HAS_RED_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.SPOTTED_MAPLES_RED);

		removeSpawn(context, "chicken", AutumnityBiomeTags.HAS_TURKEY, EntityType.CHICKEN);
		addSpawn(context, "turkey", AutumnityBiomeTags.HAS_TURKEY, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.TURKEY.get(), 10, 4, 4));
		addSpawn(context, "snail", AutumnityBiomeTags.HAS_SNAIL, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.SNAIL.get(), 10, 2, 2));
	}

	@SafeVarargs
	private static void addFeature(BootstrapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
	}

	private static void addSpawn(BootstrapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
		register(context, "add_spawn/" + name, () -> new AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
	}

	private static void removeSpawn(BootstrapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, EntityType<?>... types) {
		register(context, "remove_spawn/" + name, () -> new RemoveSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), HolderSet.direct(Stream.of(types).map(BuiltInRegistries.ENTITY_TYPE::wrapAsHolder).collect(Collectors.toList()))));
	}

	private static void register(BootstrapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
		context.register(ResourceKey.create(Keys.BIOME_MODIFIERS, Autumnity.location(name)), modifier.get());
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(BootstrapContext<?> context, ResourceKey<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
	}
}