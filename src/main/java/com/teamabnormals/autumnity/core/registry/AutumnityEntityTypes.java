package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.common.entity.projectile.ThrownTurkeyEgg;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityEntityTypes {
	public static final EntitySubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getEntitySubHelper();

	public static final DeferredHolder<EntityType<?>, EntityType<Snail>> SNAIL = HELPER.createEntity("snail", Snail::new, MobCategory.CREATURE, 0.8F, 0.9F);
	public static final DeferredHolder<EntityType<?>, EntityType<Turkey>> TURKEY = HELPER.createEntity("turkey", Turkey::new, MobCategory.CREATURE, 0.6F, 0.8F);
	public static final DeferredHolder<EntityType<?>, EntityType<ThrownTurkeyEgg>> TURKEY_EGG = HELPER.createEntity("turkey_egg", ThrownTurkeyEgg::new, ThrownTurkeyEgg::new, MobCategory.MISC, 0.25F, 0.25F);

	@SubscribeEvent
	public static void registerEntitySpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(SNAIL.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
		event.register(TURKEY.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SNAIL.get(), Snail.registerAttributes().build());
		event.put(TURKEY.get(), Turkey.registerAttributes().build());
	}
}