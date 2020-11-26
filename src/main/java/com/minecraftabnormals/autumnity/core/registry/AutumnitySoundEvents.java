package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.autumnity.core.Autumnity;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnitySoundEvents
{
	public static final RegistryHelper HELPER = Autumnity.REGISTRY_HELPER;
	
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_EAT = HELPER.createSoundEvent("entity.snail.eat");
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_HURT =HELPER.createSoundEvent("entity.snail.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_STEP = HELPER.createSoundEvent("entity.snail.step");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AMBIENT = HELPER.createSoundEvent("entity.turkey.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AGGRO = HELPER.createSoundEvent("entity.turkey.aggro");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_HURT = HELPER.createSoundEvent("entity.turkey.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_DEATH = HELPER.createSoundEvent("entity.turkey.death");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_EGG = HELPER.createSoundEvent("entity.turkey.egg");
}