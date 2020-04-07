package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.potion.ExtensionEffect;
import com.markus1002.autumnity.common.potion.StenchEffect;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects
{
	public static Effect EXTENSION;
	public static Effect STENCH;

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event)
	{
		EXTENSION = registerEffect(new ExtensionEffect(), "extension");
		// STENCH = registerEffect(new StenchEffect(), "stench");
	}
	
	private static Effect registerEffect(Effect effect, String name)
	{
		effect.setRegistryName(Reference.location(name));
		ForgeRegistries.POTIONS.register(effect);
		return effect;
	}
}