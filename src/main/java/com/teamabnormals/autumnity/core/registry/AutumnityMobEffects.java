package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Autumnity.MOD_ID);

	public static final RegistryObject<MobEffect> EXTENSION = MOB_EFFECTS.register("extension", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 16767620));
	public static final RegistryObject<MobEffect> FOUL_TASTE = MOB_EFFECTS.register("foul_taste", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 14722092));
}