package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AutumnityMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Autumnity.MOD_ID);

	public static final DeferredHolder<MobEffect, MobEffect> EXTENSION = MOB_EFFECTS.register("extension", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 16767620));
	public static final DeferredHolder<MobEffect, MobEffect> FOUL_TASTE = MOB_EFFECTS.register("foul_taste", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 14722092));
}