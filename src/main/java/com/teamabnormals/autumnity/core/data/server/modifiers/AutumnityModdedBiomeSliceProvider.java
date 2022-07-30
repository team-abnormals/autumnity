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
		this.registerSlice("maple_forest", 5, new MultiNoiseModdedBiomeProvider(new Climate.ParameterList<>(List.of(
				Pair.of(Climate.parameters(0, 0, 0, 0, 0, 0, 0), BlueprintBiomes.ORIGINAL_SOURCE_MARKER.getKey()),
				Pair.of(Climate.parameters(Climate.Parameter.span(-0.15F, 0.2F), Climate.Parameter.span(-0.1F, -0.1F), Climate.Parameter.span(-0.11F, 0.3F), Climate.Parameter.span(-0.7799F, -0.375F), zero, Climate.Parameter.span(-1.0F, -0.9333F), 0.0F), AutumnityBiomes.MAPLE_FOREST.getKey())
		))), new ResourceLocation("overworld"));
		this.registerSlice("pumpkin_fields", 2, new MultiNoiseModdedBiomeProvider(new Climate.ParameterList<>(List.of(
				Pair.of(Climate.parameters(0, 0, 0, 0, 0, 0, 0), AutumnityBiomes.MAPLE_FOREST.getKey()),
				Pair.of(Climate.parameters(Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-1.0F, -0.35F), Climate.Parameter.span(-0.11F, 0.3F), Climate.Parameter.span(-0.7799F, -0.375F), zero, Climate.Parameter.span(-1.0F, -0.9333F), 0.0F), AutumnityBiomes.PUMPKIN_FIELDS.getKey())
		))), new ResourceLocation("overworld"));
	}
}
