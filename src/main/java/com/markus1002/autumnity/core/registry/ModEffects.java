package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.potion.ExtensionEffect;
import com.markus1002.autumnity.common.potion.FoulTasteEffect;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects
{
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Reference.MOD_ID);
	
	public static Effect EXTENSION = new ExtensionEffect().setRegistryName("extension");
	public static Effect FOUL_TASTE = new FoulTasteEffect().setRegistryName("foul_taste");

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event)
	{
    	event.getRegistry().registerAll(EXTENSION, FOUL_TASTE);
	}
}