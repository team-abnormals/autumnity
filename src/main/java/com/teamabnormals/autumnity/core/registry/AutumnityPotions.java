package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Autumnity.MOD_ID);

	public static final RegistryObject<Potion> EXTENSION = POTIONS.register("extension", () -> new Potion("extension", new MobEffectInstance(AutumnityMobEffects.EXTENSION.get(), 1800)));

	public static void registerBrewingRecipes() {
		DataUtil.addMix(Potions.AWKWARD, AutumnityBlocks.SNAIL_GOO.get().asItem(), EXTENSION.get());
	}
}