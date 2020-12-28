package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.SoundSubRegistryHelper;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnitySoundEvents {
	public static final SoundSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getSoundSubHelper();

	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_EAT = HELPER.createSoundEvent("entity.snail.eat");
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_HURT = HELPER.createSoundEvent("entity.snail.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_DEATH = HELPER.createSoundEvent("entity.snail.death");
	public static final RegistryObject<SoundEvent> ENTITY_SNAIL_STEP = HELPER.createSoundEvent("entity.snail.step");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AMBIENT = HELPER.createSoundEvent("entity.turkey.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AGGRO = HELPER.createSoundEvent("entity.turkey.aggro");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_HURT = HELPER.createSoundEvent("entity.turkey.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_DEATH = HELPER.createSoundEvent("entity.turkey.death");
	public static final RegistryObject<SoundEvent> ENTITY_TURKEY_EGG = HELPER.createSoundEvent("entity.turkey.egg");
	public static final RegistryObject<SoundEvent> BLOCK_TURKEY_CUT = HELPER.createSoundEvent("block.turkey.cut");
}