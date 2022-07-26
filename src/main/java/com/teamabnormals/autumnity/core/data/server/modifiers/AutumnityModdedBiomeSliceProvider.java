package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSliceProvider;
import com.teamabnormals.blueprint.core.registry.BlueprintBiomes;
import com.teamabnormals.blueprint.core.util.BiomeUtil.MultiNoiseModdedBiomeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;

import java.util.List;

public class AutumnityModdedBiomeSliceProvider extends ModdedBiomeSliceProvider {

	public AutumnityModdedBiomeSliceProvider(DataGenerator dataGenerator) {
		super(dataGenerator, Autumnity.MOD_ID);
	}

	@Override
	protected void registerSlices() {
		Climate.Parameter zero = Climate.Parameter.point(0.0F);
		this.registerSlice("maple_forest", 2, new MultiNoiseModdedBiomeProvider(new Climate.ParameterList<>(List.of(Pair.of(Climate.parameters(0, 0, 0, 0, 0, 0, 0), BlueprintBiomes.ORIGINAL_SOURCE_MARKER.getKey()), Pair.of(Climate.parameters(zero, zero, zero, zero, zero, zero, 0.0F), AutumnityBiomes.MAPLE_FOREST.getKey())))), new ResourceLocation("overworld"));
	}
}
