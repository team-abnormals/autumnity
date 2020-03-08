package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.potion.KnockbackResistanceEffect;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects
{
	public static final Effect STURDINESS = new KnockbackResistanceEffect().addAttributesModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "F8853D04-64C6-45FC-B158-950BF7143829", 1.0D, AttributeModifier.Operation.ADDITION);

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event)
	{
		registerEffect(STURDINESS, "sturdiness");
	}
	
	private static void registerEffect(Effect effect, String name)
	{
		effect.setRegistryName(Reference.location(name));
		ForgeRegistries.POTIONS.register(effect);
	}
}