package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnitySoundEvents {
	public static final SoundSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getSoundSubHelper();

	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_EAT = HELPER.createSoundEvent("entity.snail.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_HURT = HELPER.createSoundEvent("entity.snail.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_DEATH = HELPER.createSoundEvent("entity.snail.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_STEP = HELPER.createSoundEvent("entity.snail.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_AMBIENT = HELPER.createSoundEvent("entity.turkey.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_AGGRO = HELPER.createSoundEvent("entity.turkey.aggro");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_HURT = HELPER.createSoundEvent("entity.turkey.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_DEATH = HELPER.createSoundEvent("entity.turkey.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_EGG = HELPER.createSoundEvent("entity.turkey.egg");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_TURKEY_CUT = HELPER.createSoundEvent("block.turkey.cut");
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_SNAIL = HELPER.createSoundEvent("item.armor.equip_snail");
}