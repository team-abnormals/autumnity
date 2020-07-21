package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions
{
	public static Potion EXTENSION;

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event)
	{
		EXTENSION = registerPotion(new Potion("extension", new EffectInstance(ModEffects.EXTENSION, 1)), "extension");
	}
	
	private static Potion registerPotion(Potion potion, String name)
	{
		potion.setRegistryName(Reference.location(name));
		ForgeRegistries.POTION_TYPES.register(potion);
		return potion;
	}
	
	public static void setupBrewingRecipes()
	{
		PotionBrewing.addMix(Potions.AWKWARD, ModBlocks.SNAIL_SLIME.get().asItem(), EXTENSION);
	}
}