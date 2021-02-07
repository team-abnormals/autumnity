package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.EntitySubRegistryHelper;
import com.minecraftabnormals.autumnity.client.renderer.entity.SnailRenderer;
import com.minecraftabnormals.autumnity.client.renderer.entity.TurkeyEggRenderer;
import com.minecraftabnormals.autumnity.client.renderer.entity.TurkeyRenderer;
import com.minecraftabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import com.minecraftabnormals.autumnity.common.entity.projectile.TurkeyEggEntity;
import com.minecraftabnormals.autumnity.core.Autumnity;

import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityEntities {
	public static final EntitySubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getEntitySubHelper();

	public static final RegistryObject<EntityType<SnailEntity>> SNAIL = HELPER.createLivingEntity("snail", SnailEntity::new, EntityClassification.CREATURE, 0.8F, 0.9F);
	public static final RegistryObject<EntityType<TurkeyEntity>> TURKEY = HELPER.createLivingEntity("turkey", TurkeyEntity::new, EntityClassification.CREATURE, 0.6F, 0.8F);
	public static final RegistryObject<EntityType<TurkeyEggEntity>> TURKEY_EGG = HELPER.createEntity("turkey_egg", TurkeyEggEntity::new, TurkeyEggEntity::new, EntityClassification.MISC, 0.25F, 0.25F);
	public static final RegistryObject<EntityType<FallingHeadBlockEntity>> FALLING_HEAD_BLOCK = HELPER.createEntity("falling_head_block", FallingHeadBlockEntity::new, FallingHeadBlockEntity::new, EntityClassification.MISC, 0.98F, 0.98F);

	public static void registerSpawns() {
		EntitySpawnPlacementRegistry.register(SNAIL.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(TURKEY.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
	}

	@OnlyIn(Dist.CLIENT)
	public static void setupEntitiesClient() {
		RenderingRegistry.registerEntityRenderingHandler(SNAIL.get(), SnailRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TURKEY.get(), TurkeyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TURKEY_EGG.get(), TurkeyEggRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(FALLING_HEAD_BLOCK.get(), FallingBlockRenderer::new);
	}

	public static void registerAttributes() {
		GlobalEntityTypeAttributes.put(SNAIL.get(), SnailEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(TURKEY.get(), TurkeyEntity.registerAttributes().create());
	}
}