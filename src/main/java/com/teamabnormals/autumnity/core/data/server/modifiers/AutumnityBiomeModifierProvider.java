package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutumnityBiomeModifierProvider {

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		addFeature(context, "add_feature/maple_tree", AutumnityBiomeTags.HAS_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE);
		addFeature(context, "add_feature/spotted_maple_tree/yellow", AutumnityBiomeTags.HAS_YELLOW_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_YELLOW);
		addFeature(context, "add_feature/spotted_maple_tree/orange", AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_ORANGE);
		addFeature(context, "add_feature/spotted_maple_tree/red", AutumnityBiomeTags.HAS_RED_MAPLE_TREE, GenerationStep.Decoration.VEGETAL_DECORATION, AutumnityPlacedFeatures.TREES_MAPLE_RED);

		removeSpawn(context, "remove_animal/chicken", AutumnityBiomeTags.HAS_TURKEY, EntityType.CHICKEN);
		addSpawn(context, "add_animal/turkey", AutumnityBiomeTags.HAS_TURKEY, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.TURKEY.get(), 10, 4, 4));
		addSpawn(context, "add_animal/snail", AutumnityBiomeTags.HAS_SNAIL, new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.SNAIL.get(), 10, 2, 2));
	}

	@SafeVarargs
	private static void addFeature(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
	}

	private static void addSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
		register(context, name, () -> new AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
	}

	private static void removeSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, EntityType<?>... types) {
		register(context, name, () -> new RemoveSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), HolderSet.direct(Stream.of(types).map(type -> ForgeRegistries.ENTITY_TYPES.getHolder(type).get()).collect(Collectors.toList()))));
	}

	private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
		context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Autumnity.MOD_ID, name)), modifier.get());
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
	}
}