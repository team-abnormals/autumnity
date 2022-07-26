package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import com.teamabnormals.autumnity.common.entity.animal.SnailEntity;
import com.teamabnormals.autumnity.common.entity.animal.TurkeyEntity;
import com.teamabnormals.autumnity.common.entity.projectile.TurkeyEggEntity;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityEntityTypes {
	public static final EntitySubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getEntitySubHelper();

	public static final RegistryObject<EntityType<SnailEntity>> SNAIL = HELPER.createLivingEntity("snail", SnailEntity::new, MobCategory.CREATURE, 0.8F, 0.9F);
	public static final RegistryObject<EntityType<TurkeyEntity>> TURKEY = HELPER.createLivingEntity("turkey", TurkeyEntity::new, MobCategory.CREATURE, 0.6F, 0.8F);
	public static final RegistryObject<EntityType<TurkeyEggEntity>> TURKEY_EGG = HELPER.createEntity("turkey_egg", TurkeyEggEntity::new, TurkeyEggEntity::new, MobCategory.MISC, 0.25F, 0.25F);
	public static final RegistryObject<EntityType<FallingHeadBlockEntity>> FALLING_HEAD_BLOCK = HELPER.createEntity("falling_head_block", FallingHeadBlockEntity::new, FallingHeadBlockEntity::new, MobCategory.MISC, 0.98F, 0.98F);

	public static void registerSpawns() {
		SpawnPlacements.register(SNAIL.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(TURKEY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SNAIL.get(), SnailEntity.registerAttributes().build());
		event.put(TURKEY.get(), TurkeyEntity.registerAttributes().build());
	}
}