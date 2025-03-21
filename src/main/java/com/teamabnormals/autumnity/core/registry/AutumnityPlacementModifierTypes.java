package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AutumnityPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Autumnity.MOD_ID);

	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<BetterNoiseBasedCountPlacement>> BETTER_NOISE_BASED_COUNT = PLACEMENT_MODIFIER_TYPES.register("better_noise_based_count", () -> () -> BetterNoiseBasedCountPlacement.CODEC);
}
