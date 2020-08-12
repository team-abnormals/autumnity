package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.core.Reference;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnitySoundEvents
{
	public static final SoundEvent ENTITY_SNAIL_EAT = new SoundEvent(Reference.location("entity.snail.eat"));
	public static final SoundEvent ENTITY_SNAIL_HURT = new SoundEvent(Reference.location("entity.snail.hurt"));

	@SubscribeEvent
	public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
	{
		registerSoundEvent(ENTITY_SNAIL_EAT, "entity.snail.eat");
		registerSoundEvent(ENTITY_SNAIL_HURT, "entity.snail.hurt");
	}

	private static void registerSoundEvent(SoundEvent soundEvent, String name)
	{
		soundEvent.setRegistryName(Reference.location(name));
		ForgeRegistries.SOUND_EVENTS.register(soundEvent);
	}
}