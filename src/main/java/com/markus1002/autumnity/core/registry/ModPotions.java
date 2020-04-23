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
	public static Potion EXTENSION;
	public static Potion ANTI_HEALING;
	public static Potion LONG_ANTI_HEALING;

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event)
	{
		EXTENSION = registerPotion(new Potion("extension", new EffectInstance(ModEffects.EXTENSION, 1)), "extension");
		ANTI_HEALING = registerPotion(new Potion("anti_healing", new EffectInstance(ModEffects.ANTI_HEALING, 3600)), "anti_healing");
		LONG_ANTI_HEALING = registerPotion(new Potion("anti_healing", new EffectInstance(ModEffects.ANTI_HEALING, 9600)), "long_anti_healing");
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
		PotionBrewing.addMix(Potions.AWKWARD, ModItems.FOUL_BERRY_LEAF.get(), ANTI_HEALING);
		PotionBrewing.addMix(ANTI_HEALING, Items.REDSTONE, LONG_ANTI_HEALING);
		PotionBrewing.addMix(ANTI_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.REGENERATION);
		PotionBrewing.addMix(LONG_ANTI_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_REGENERATION);
	}
}