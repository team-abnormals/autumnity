package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBiomeModifiers;
import com.teamabnormals.autumnity.core.other.AutumnityBiomeSlices;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityNoiseParameters;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AutumnityDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, AutumnityConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, AutumnityPlacedFeatures::bootstrap)
			.add(Registries.NOISE, AutumnityNoiseParameters::bootstrap)
			.add(Registries.BIOME, AutumnityBiomes::bootstrap)
			.add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, AutumnityBiomeSlices::bootstrap)
			.add(ForgeRegistries.Keys.BIOME_MODIFIERS, AutumnityBiomeModifiers::bootstrap);

	public AutumnityDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, BUILDER, Set.of(Autumnity.MOD_ID));
	}
}