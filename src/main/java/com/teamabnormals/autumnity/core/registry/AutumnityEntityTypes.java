package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.common.entity.projectile.ThrownTurkeyEgg;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityEntityTypes {
	public static final EntitySubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getEntitySubHelper();

	public static final RegistryObject<EntityType<Snail>> SNAIL = HELPER.createLivingEntity("snail", Snail::new, MobCategory.CREATURE, 0.8F, 0.9F);
	public static final RegistryObject<EntityType<Turkey>> TURKEY = HELPER.createLivingEntity("turkey", Turkey::new, MobCategory.CREATURE, 0.6F, 0.8F);
	public static final RegistryObject<EntityType<ThrownTurkeyEgg>> TURKEY_EGG = HELPER.createEntity("turkey_egg", ThrownTurkeyEgg::new, ThrownTurkeyEgg::new, MobCategory.MISC, 0.25F, 0.25F);

	@SubscribeEvent
	public static void registerEntitySpawnPlacements(SpawnPlacementRegisterEvent event) {
		event.register(SNAIL.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
		event.register(TURKEY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SNAIL.get(), Snail.registerAttributes().build());
		event.put(TURKEY.get(), Turkey.registerAttributes().build());
	}
}