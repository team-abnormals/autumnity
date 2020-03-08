package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.item.Items;
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
	public static final Potion STURDINESS = new Potion("sturdiness", new EffectInstance(ModEffects.STURDINESS, 3600));
	public static final Potion LONG_STURDINESS = new Potion("sturdiness", new EffectInstance(ModEffects.STURDINESS, 9600));

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event)
	{
		registerPotion(STURDINESS, "sturdiness");
		registerPotion(LONG_STURDINESS, "long_sturdiness");
	}
	
	private static void registerPotion(Potion potion, String name)
	{
		potion.setRegistryName(Reference.location(name));
		ForgeRegistries.POTION_TYPES.register(potion);
	}
	
	public static void setupBrewingRecipes()
	{
		PotionBrewing.addMix(Potions.AWKWARD, ModBlocks.SNAIL_SLIME.asItem(), STURDINESS);
		PotionBrewing.addMix(STURDINESS, Items.REDSTONE, LONG_STURDINESS);
	}
}