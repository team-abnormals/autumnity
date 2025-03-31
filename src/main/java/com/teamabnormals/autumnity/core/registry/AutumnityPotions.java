package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Autumnity.MOD_ID);

	public static final DeferredHolder<Potion, Potion> EXTENSION = POTIONS.register("extension", () -> new Potion("extension", new MobEffectInstance(AutumnityMobEffects.EXTENSION, 1800)));

	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addMix(Potions.AWKWARD, AutumnityBlocks.SNAIL_GOO.get().asItem(), EXTENSION);
	}
}