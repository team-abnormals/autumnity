package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AutumnitySoundEvents {
	public static final SoundSubRegistryHelper SOUND_EVENTS = Autumnity.REGISTRY_HELPER.getSoundSubHelper();

	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_EAT = SOUND_EVENTS.createSoundEvent("entity.snail.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_HURT = SOUND_EVENTS.createSoundEvent("entity.snail.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_DEATH = SOUND_EVENTS.createSoundEvent("entity.snail.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SNAIL_STEP = SOUND_EVENTS.createSoundEvent("entity.snail.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.turkey.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_AGGRO = SOUND_EVENTS.createSoundEvent("entity.turkey.aggro");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_HURT = SOUND_EVENTS.createSoundEvent("entity.turkey.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_DEATH = SOUND_EVENTS.createSoundEvent("entity.turkey.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TURKEY_EGG = SOUND_EVENTS.createSoundEvent("entity.turkey.egg");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_TURKEY_CUT = SOUND_EVENTS.createSoundEvent("block.turkey.cut");
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_SNAIL = SOUND_EVENTS.createSoundEvent("item.armor.equip_snail");
}