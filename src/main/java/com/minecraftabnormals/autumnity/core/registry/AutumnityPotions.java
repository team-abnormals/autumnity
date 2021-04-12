package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Autumnity.MOD_ID);

	public static final RegistryObject<Potion> EXTENSION = POTIONS.register("extension", () -> new Potion("extension", new EffectInstance(AutumnityEffects.EXTENSION.get(), 1800)));

	public static void registerBrewingRecipes() {
		DataUtil.addMix(Potions.AWKWARD, AutumnityBlocks.SNAIL_SLIME.get().asItem(), EXTENSION.get());
	}
}