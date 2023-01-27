package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.mojang.serialization.JsonOps;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutumnityBiomeModifierProvider {

	public static JsonCodecProvider<BiomeModifier> create(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		RegistryAccess access = RegistryAccess.builtinCopy();
		Registry<Biome> biomeRegistry = access.registryOrThrow(Registry.BIOME_REGISTRY);
		Registry<PlacedFeature> placedFeatures = access.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

		addModifier(modifiers, "add_feature/maple_tree", new AddFeaturesBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_MAPLE_TREE), of(placedFeatures, AutumnityPlacedFeatures.TREES_MAPLE), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/spotted_maple_tree/yellow", new AddFeaturesBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_YELLOW_MAPLE_TREE), of(placedFeatures, AutumnityPlacedFeatures.TREES_MAPLE_YELLOW), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/spotted_maple_tree/orange", new AddFeaturesBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE), of(placedFeatures, AutumnityPlacedFeatures.TREES_MAPLE_ORANGE), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/spotted_maple_tree/red", new AddFeaturesBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_RED_MAPLE_TREE), of(placedFeatures, AutumnityPlacedFeatures.TREES_MAPLE_RED), GenerationStep.Decoration.VEGETAL_DECORATION));

		addModifier(modifiers, "remove_animal/chicken", new RemoveSpawnsBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_TURKEY), HolderSet.direct(List.of(ForgeRegistries.ENTITY_TYPES.getHolder(EntityType.CHICKEN).get()))));
		addModifier(modifiers, "add_animal/turkey", new AddSpawnsBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_TURKEY), List.of(new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.TURKEY.get(), 10, 4, 4))));
		addModifier(modifiers, "add_animal/snail", new AddSpawnsBiomeModifier(tag(biomeRegistry, AutumnityBiomeTags.HAS_SNAIL), List.of(new MobSpawnSettings.SpawnerData(AutumnityEntityTypes.SNAIL.get(), 10, 2, 2))));

		return JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, Autumnity.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, access), ForgeRegistries.Keys.BIOME_MODIFIERS, modifiers);
	}

	private static HolderSet<Biome> tag(Registry<Biome> biomeRegistry, TagKey<Biome> tagKey) {
		return new HolderSet.Named<>(biomeRegistry, tagKey);
	}

	private static void addModifier(HashMap<ResourceLocation, BiomeModifier> modifiers, String name, BiomeModifier modifier) {
		modifiers.put(new ResourceLocation(Autumnity.MOD_ID, name), modifier);
	}

	@SafeVarargs
	@SuppressWarnings("ConstantConditions")
	private static HolderSet<PlacedFeature> of(Registry<PlacedFeature> placedFeatures, RegistryObject<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(registryObject -> placedFeatures.getOrCreateHolderOrThrow(registryObject.getKey())).collect(Collectors.toList()));
	}
}